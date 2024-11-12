##  **Running Tracker App** ##
- The Running Tracker App is a **multi-module** native Android application built with **Kotlin** and **Jetpack Compose** to demonstrate **industry-level** Android development practices. This app is designed to track running activities, offering features like real-time route mapping, performance analytics, data synchronization across devices, and offline functionality. It leverages modern Android development techniques and libraries to ensure a **scalable**, **maintainable**, and **testability**. 

## Overview

- Safe authentication to access your running data from any device anywhere.
- Track your runs with a dynamic map view that highlights your route showing your pace at a glance—slow, medium, or fast.
- Monitor key statistics such as speed, distance, duration, pace, heart rate, and more.
- Analyze your performance with detailed graphical analytics.


## Features

- **Project Architecture:**
A strong foundation by incorporating the principles of software architecture to ensure a scalable, readable and maintainable app, with the clean architecture and MVI design pattern
- **Gradle & Multi-Module Architecture:**
Using version catalogues and convention plugins or an advanced and scalable Gradle setup. And enhancing code separation and build times with the implementation of the Multi-Module Architecture
- **Jetpack Compose UI:**
Modern UI implementation using Jetpack Compose, tailored for industry-scale projects.
- **Authentication:**
Secure and reliable OAuth authentication system integrated for user management.
- **Offline-First Development:**
Robust offline capabilities using Room for local storage and Ktor for network operations.
- **Maps Integration:**
Real-time route mapping, pace and distance highlighting using the Google Maps SDK.
- **Graphical Analytics:** Provides detailed performance insights through visually rich graphs and charts.
- **Wear OS Support:**
Comprehensive Wear OS support, including theming, exercise tracking, and health data management.
Features like discovering paired devices and syncing data between the watch and phone.
Implementation of always-on mode with ambient display.

## Tech Stack

- **Kotlin:** The primary language for Android development.
- **Jetpack Compose:** For creating responsive, dynamic, and visually appealing UIs.
- **Room:** Local database solution for robust offline data storage and seamless offline-first functionality.
- **Android Ktor:** For network operations and synchronization of running data across devices.
- **Google Maps SDK:** Enables route mapping with a visual path for pace and ditance.
- **Gradle:** Advanced setup utilizing version catalogs, convention plugins, and multi-module architecture for optimized build performance and code separation.
- **OAuth:** Implements secure and reliable authentication for user management across devices.
- **Wear OS SDK:** Comprehensive support for Wear OS devices, enabling exercise tracking, health data integration, and real-time syncing between watch and phone.
- **Dynamic Feature Modules:** Modular design to load features on demand, improving performance and flexibility.
- **Ktor:** A custom backend ([Running Tracker API](https://github.com/ahmed-guedmioui/Running-Tracker-API)) that manages user authentication and fetches data from a remote database.
- **MongoDB:** A scalable and flexible remote database solution, ensuring secure storage and real-time access to user data across devices.


## Getting Started
- Clone the repo:
git clone https://github.com/ahmed-guedmioui/Running-Tracker-App.git
- Open the project in Android Studio.
- Build the project and run it on your preferred device or emulator.


## Screenshots
<img width="678" alt="screens" src="https://github.com/user-attachments/assets/5b20ee52-67a7-485a-9c94-ccd64bae1413">













