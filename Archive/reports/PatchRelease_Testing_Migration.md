# Patch Release: Modules 01-04

We have successfully patched and hardened Modules 01-04 to resolve architectural and execution blockers.

## What Was Fixed:
1. **Android 13+ Notifications**: Handled runtime `POST_NOTIFICATIONS` permission so the foreground service won't crash on Boot or Start.
2. **Audio Recorder Race Conditions**: `AudioRecorderFlow` now implements sample-rate fallbacks (to support various device hardware) and correctly obtains/releases `AudioFocus` via `AudioFocusManager`.
3. **ML Runtime Validation**: Created `InterpreterProvider` and `ModelLoader` in `core.ml` to properly detect and load `.tflite` assets. If assets (like `roohi_wakeword.tflite` or `speaker_dvector_model.tflite`) are missing, the system detects this and engages "STUB MODE" to prevent catastrophic crashing.
4. **Voiceprint Keystore Encryption**: Voice embedding float arrays are now encrypted with standard `AES/GCM/NoPadding` using the `AndroidKeyStore` hardware-backed keystore before being persisted in Room.
5. **Release Readiness**: Updated `build.gradle.kts` with proper release types turning on `isMinifyEnabled`, `isShrinkResources`, and included a `proguard-rules.pro` file protecting TFLite, Room, and Hilt from aggressive obfuscation.
6. **Diagnostics UI added to MainActivity**: Giving developers clear access to Permission, Resource, and Asset statuses without digging through logs.

## Testing Instructions
1. Assemble Release: `./gradlew :app:assembleRelease` to test proguard rules.
2. Install Sandbox/Debug app: `./gradlew :app:installDebug`.
3. Open `Roohi`.
4. The system will prompt you for `Microphone` and `Notification` permissions. Accept them.
5. The UI will instantly update to show diagnostics:
    - Notification Permission: GRANTED ✔
    - Microphone Status: READY ✔
    - Wake Word Model: STUB MODE ⚠ (expected since the tflite is not bundled)
    - Voice Auth Model: STUB MODE ⚠ 

The application is now thoroughly hardened to safely wait for Module 05.
