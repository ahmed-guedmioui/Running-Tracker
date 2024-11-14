package com.ahmed_apps.run.domain

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import com.ahmed_apps.core.connectivity.domain.messaging.MessagingAction
import com.ahmed_apps.core.domian.location.Location
import com.ahmed_apps.core.domian.location.LocationWithAltitude
import com.ahmed_apps.test.LocationObserverFake
import com.ahmed_apps.test.MainCoroutineExtension
import com.ahmed_apps.test.PhoneToWatchConnectorFake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.math.roundToInt


/**
 * @author Ahmed Guedmioui
 */
class RunningTrackerTest {

    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    private lateinit var runningTracker: RunningTracker
    private lateinit var locationObserver: LocationObserverFake
    private lateinit var watchConnector: PhoneToWatchConnectorFake

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: CoroutineScope

    @BeforeEach
    fun setUp() {
        locationObserver = LocationObserverFake()
        watchConnector = PhoneToWatchConnectorFake()

        testDispatcher = mainCoroutineExtension.testDispatcher
        testScope = TestScope(testDispatcher)

        runningTracker = RunningTracker(
            locationObserver = locationObserver,
            watchConnector = watchConnector,
            applicationScope = testScope
        )
    }

    @Test
    fun testCombiningRunData() = runTest {
        runningTracker.runData.test {
            skipItems(1)

            runningTracker.startObservingLocation()
            runningTracker.setIsTracking(true)

            val location1 = LocationWithAltitude(Location(1.0, 1.0), 1.0)
            locationObserver.trackLocation(location1)
            val emission1 = awaitItem()

            val location2 = LocationWithAltitude(Location(2.0, 2.0), 2.0)
            locationObserver.trackLocation(location2)
            val emission2 = awaitItem()

            watchConnector.sendFromWatchToPhone(MessagingAction.HeartRateUpdate(160))
            val emission3 = awaitItem()

            watchConnector.sendFromWatchToPhone(MessagingAction.HeartRateUpdate(170))
            val emission4 = awaitItem()

            testScope.cancel()

            assertThat(emission1.locations[0][0].locationWithAltitude).isEqualTo(location1)
            assertThat(emission2.locations[0][1].locationWithAltitude).isEqualTo(location2)
            assertThat(emission3.heartRates).isEqualTo(listOf(160))
            assertThat(emission4.heartRates).isEqualTo(listOf(160, 170))

            val expectedDistance = 156.9 * 1000L
            val tolerance = 0.03
            val lowerBound = (expectedDistance * (1 - tolerance)).roundToInt()
            val upperBound = (expectedDistance * (1 + tolerance)).roundToInt()
            assertThat(emission4.distanceMeters).isBetween(lowerBound, upperBound)

            assertThat(emission4.locations.first()).hasSize(2)
        }
    }


}