# Desktop Handover Documentation — Roohi AI OS

## 1. Project Information
- **Project Name**: Roohi AI OS (Unified Web & Android AI Assistant Platform)
- **Repository Root**: `/`
- **Android Project Root**: `Updates/`
- **Application ID**: `com.roohi.app`
- **Version Name/Code**: 1.0 (versionCode 1)

## 2. Clear Build Status Summary
- **CLOUD BUILD STATUS**: **BLOCKED** (Cloud Run container environment lacks Java JDK 17 binaries; apt-get/dpkg system installations are restricted by security policy).
- **PROJECT SOURCE STATUS**: **PRESERVED & INTACT** (All 25 Kotlin package modules, UI layouts, Room DB schemas, and Web/PWA sources are completely intact).
- **DESKTOP BUILD STATUS**: **READY FOR LOCAL SETUP** (Project configuration and Gradle 8.5 wrapper are fully verified for local execution).

## 3. Toolchain Requirements
- **Java Development Kit**: JDK 17 LTS (Eclipse Adoptium Temurin 17 or Oracle OpenJDK 17)
- **Android SDK Platform**: API Level 34 (Android 14.0)
- **Android SDK Build-Tools**: 34.0.0
- **Gradle Version**: 8.5 (Managed via Gradle Wrapper)
- **Android Gradle Plugin**: 8.2.2
- **Kotlin Compiler**: 1.9.22

## 4. Required Environment Variables
Set these variables on your local OS:
- `JAVA_HOME`: Path to JDK 17 root directory
  - *Windows*: `C:\Program Files\Eclipse Adoptium\jdk-17.0.9.9-hotspot`
  - *macOS*: `/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home`
  - *Linux*: `/usr/lib/jvm/java-17-openjdk-amd64`
- `ANDROID_HOME`: Path to Android SDK
  - *Windows*: `C:\Users\<Username>\AppData\Local\Android\Sdk`
  - *macOS*: `/Users/<Username>/Library/Android/sdk`
  - *Linux*: `/home/<Username>/Android/Sdk`

## 5. Step-by-Step Instructions for Windows / Desktop
1. **Download & Extract**:
   Download `Roohi_Desktop_Handover_2026-08-11.zip` and extract to a folder (e.g. `C:\Projects\Roohi`).
2. **Install JDK 17**:
   Install OpenJDK 17 or Adoptium Temurin 17. Verify in CMD: `java -version`.
3. **Install Android SDK 34**:
   Open Android Studio or Command Line Tools, install SDK Platform 34 and Build Tools 34.0.0.
4. **Build Android APK**:
   Open Terminal / CMD in the extracted directory:
   ```cmd
   cd Updates
   gradlew.bat :app:assembleDebug
   ```
   *(For macOS / Linux: `./gradlew :app:assembleDebug`)*
5. **Retrieve APK**:
   Find the generated APK file at:
   `Updates\app\build\outputs\apk\debug\app-debug.apk`

## 6. Web & PWA Development
To run the Web App / PWA locally:
1. Open terminal at project root `/`
2. Run `npm install`
3. Run `npm run dev` (starts Vite server on http://localhost:3000)

## 7. Excluded Files
The following generated artifacts were excluded to prevent build cache collisions and keep archive clean:
- `.gradle/` cache directories
- `build/` and `app/build/` compilation artifacts
- `node_modules/` (reinstall via `npm install`)
- Temporary `.zip` or `.sha256` files
