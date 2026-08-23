# Android Integration Guide

## Directory Structure

```
android/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/roohi/app/
│   │       │   ├── background/      # Background services
│   │       │   ├── command/         # Command processing
│   │       │   ├── conversation/    # AI conversations
│   │       │   ├── memory/          # Memory system
│   │       │   ├── reasoning/       # Reasoning engine
│   │       │   ├── speech/          # Speech I/O
│   │       │   ├── voiceauth/       # Voice authentication
│   │       │   ├── wakeword/        # Wake word detection
│   │       │   ├── presentation/    # UI/Activities
│   │       │   ├── identity/        # Identity management
│   │       │   ├── core/            # Core utilities
│   │       │   ├── di/              # Dependency injection
│   │       │   └── RoohiApplication.kt
│   │       ├── res/
│   │       │   ├── values/
│   │       │   ├── drawable/
│   │       │   ├── layout/
│   │       │   └── mipmap/
│   │       └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── INTEGRATION_GUIDE.md
```

## Building

```bash
cd android
./gradlew build
```

## Key Components

- **Background Service**: Continuous app operations
- **Command Engine**: Voice command processing
- **Conversation Module**: AI interaction management
- **Memory System**: Context and history storage
- **Voice Authentication**: Voice-based security
- **Wake Word Detection**: Always-on listening

## Integration with Web Platform

- Android app communicates via REST APIs
- Shared data models through serialization
- Unified authentication system
- Synchronized user profiles
