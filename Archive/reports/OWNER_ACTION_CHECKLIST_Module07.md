# OWNER ACTION CHECKLIST: Module 07

## 1. Permissions Needed
- **CAMERA**: Required for Flashlight control.
- **WRITE_SETTINGS**: Required for Brightness control. This requires the user to explicitly grant permission in system settings.
- **MODIFY_AUDIO_SETTINGS**: Required to change volume state.
- **SET_ALARM**: Required for setting system alarms/reminders.

## 2. Android Restrictions
- Background activity starts are strictly restricted in Android 10+. Starting activities (like AppLaunchExecutor or SearchExecutor) from `RoohiBackgroundService` may require the app to have `SYSTEM_ALERT_WINDOW` or the user to interact with a notification, unless the tablet grants special access to background activity starts. (For offline prototype, we use standard intents with `FLAG_ACTIVITY_NEW_TASK`, but Android restrictions apply).
- Wi-Fi/Bluetooth toggling has been heavily restricted since Android 10/12. We prepare the interfaces but acknowledge user prompts may explicitly open Settings Panes rather than toggling invisibly.

## 3. Manual Setup Required
- Grant `WRITE_SETTINGS` manually to allow brightness control if needed by system commands later.
- Grant "Appear on top" / Background activity start permissions if you want Roohi to launch apps purely from the background without screen interaction in Android 14.

## 4. Future API Requirements
- No cloud APIs required. Local SQLite/Room integration for permanent reminder syncing in future modules.
