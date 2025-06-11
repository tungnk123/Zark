# Zark Application Overview

Zark is a comprehensive messaging and scheduling platform built for Android using Jetpack Compose and MVVM architecture. It integrates real-time chat, AI-powered schedule detection, calendar management, and user search into a seamless mobile experience.
![image](https://github.com/user-attachments/assets/09142f8d-0771-4823-bfe5-7953664bbaa6)

![image](https://github.com/user-attachments/assets/b308596e-5262-4568-8e07-619fa9617591)

https://deepwiki.com/tungnk123/Zark
---

## Core Features

### Real-Time Chat
- Uses SignalR for WebSocket communication.
- Manages connections and reconnections with token-based authentication.
- End-to-end encryption using RSA for keys and AES-256 for message content.

### AI-Powered Schedule Detection
- Automatically analyzes chat messages to detect scheduling intent.
- Integrates with an external AI service to extract event information and suggest scheduling actions.

### Calendar Integration
- Supports multiple view types: day, three-day, schedule, and month.
- Synchronizes event data with backend.
- Allows users to create events directly from chat conversations when scheduling intent is detected.

### User Search
- Debounced and parallel search for users and conversations.
- Organizes results into users and existing chats.
- Allows instant creation of encrypted private conversations.

---

## Application Structure

### Navigation
- Bottom navigation bar with the following tabs:
  - Home (chat)
  - Calendar (event management)
  - More (profile and settings)

### Architecture
- Follows Clean Architecture principles with presentation, domain, and data layers.
- Uses ViewModels and StateFlow for state management.
- Repository pattern for clean data access.

### Backend Integration
- Real-time chat service hosted on Azure.
- AI schedule detection service for natural language understanding.

### Permissions
- Internet access
- Notification access for push messages

---

## Language

The application interface is primarily in Vietnamese, tailored for Vietnamese-speaking users.

---

## Installation

Zark is an Android application built with the Gradle build system.

### Prerequisites
- Android SDK level 26 or higher  
- Target SDK: 35  
- Kotlin with Jetpack Compose  
- Hilt for dependency injection  
- Firebase services integration

### Build & Install Steps
1. Clone the repository.
2. Set up the `local.properties` file with the required `ACCESS_TOKEN`.
3. Build the project using Gradle (`./gradlew assembleDebug`).
4. Install the APK on an Android device with required permissions.

---

## Summary

Zark demonstrates the use of modern Android development techniques to build a secure, real-time communication app with productivity-enhancing features like AI-assisted scheduling and calendar integration. Built with Jetpack Compose, Hilt, and Retrofit, Zark applies clean architecture to ensure scalability and maintainability.
