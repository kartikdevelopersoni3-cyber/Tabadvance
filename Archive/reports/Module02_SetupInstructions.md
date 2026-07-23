# Setup Instructions: Module 02 (Background AI Service)

This module requires the following permissions and setup, which should be granted during the testing/install phase:

## Required Android Permissions (AndroidManifest.xml)
- `FOREGROUND_SERVICE`: To run the service continuously in the foreground.
- `FOREGROUND_SERVICE_MICROPHONE`: For continuous listening capabilities of the AI.
- `FOREGROUND_SERVICE_SPECIAL_USE`: Required by Android 14+ for special continuous background processing.
- `RECEIVE_BOOT_COMPLETED`: To automatically start Roohi when the tablet boots up.
- `WAKE_LOCK`: To keep the CPU running if necessary.
- `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`: To request the user to disable battery optimizations for the app.

## Manual Device Setup Instructions
When testing or installing this module on the target device:
1.  **Notification Permissions**: On Android 13+, the system will prompt for notification permissions to show the Foreground Service notification. Allow this prompt.
2.  **Battery Optimization**: 
    - The `BatteryOptimizationHelper` can be invoked from the main UI to prompt the user.
    - Alternatively, go to **Settings > Apps > Roohi > Battery** and set to **Unrestricted**.
3.  **Microphone Permission**: The app will eventually need runtime permission for the microphone (`RECORD_AUDIO`). This is handled by the permissions manager but must be granted by the user.

*Note: Per the PERMISSION RULE, do NOT block development on these permissions. They are to be handled only during execution/installation.*
