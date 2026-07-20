# Testing Instructions for Module 03 & 04 (Wake Word Engine + Owner Voice Authentication)

1. Build and install the Android app (`./gradlew :app:installDebug`).
2. Run the application to start `MainActivity` which spawns `RoohiBackgroundService`.
3. Give microphone permission via Android settings if not prompted.
4. Open LogCat and filter by: `WakeWordManager|ContinuousWakeWordEngine|AudioRecorderFlow|VoiceAuthManager|TFLiteSpeaker`
   `adb logcat -s "WakeWordManager" "ContinuousWakeWordEngine" "AudioRecorderFlow" "VoiceAuthManager" "TFLiteSpeaker" "RoohiBackgroundService"`
5. Verify AudioRecorderFlow says `Starting AudioRecord stream...` and begins consuming bytes.
6. The `ContinuousWakeWordEngine` has a simulated override to eventually detect the wake word for testing the architecture pipeline. Watch for: `WAKE WORD [ROOHI] DETECTED!`
7. After wake word is triggered, `WakeWordManager` will pass the audio flow to `VoiceAuthManager`.
8. `VoiceAuthManager` will output: `Starting verification of incoming audio payload.`
9. `TFLiteSpeakerVerificationEngine` will output: `Extracting embedding from live audio buffer...`
10. The security architecture will output `No enrolled voiceprint found. Security block active.` (Until the `VoiceEnrollmentViewModel` flow is bound to UI and executed by the user to store a voiceprint).
