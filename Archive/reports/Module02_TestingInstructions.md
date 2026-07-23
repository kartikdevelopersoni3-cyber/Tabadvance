# Testing Instructions for Module 02 (Background AI Service)

1. Build and install the Android app (`./gradlew :app:installDebug`).
2. Run the application from the launcher.
3. Observe logcat for MainActivity lifecycle logs and the battery optimization helper invocation:
    `adb logcat -s "MainActivity" "BatteryOptimizationHelper" "RoohiBackgroundService"`
4. Verify you get prompted to ignore battery optimizations (if testing on API >= 23).
5. Verify the Background Service starts properly and see `RoohiBackgroundService: Service Created` in the logs.
6. Check your notification shade to verify the "Roohi is running" persistent notification is present.
7. Reboot the device. Then watch the logcat for `BootReceiver`. It should catch the device boot and successfully start `RoohiBackgroundService` again automatically.
8. Simulate a service crash or force close the service component (not the whole app) and see if the auto-restart intent (`com.roohi.app.RESTART_SERVICE`) gets fired and starts the service back up as implemented in `ServiceRestartReceiver`.
