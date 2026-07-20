# OWNER ACTION CHECKLIST: Modules 04.5 and 05

This checklist identifies the manual actions, permissions, and setup steps required before running or fully utilizing the new modules.

## 1. Permissions Needed
- **Microphone (`RECORD_AUDIO`)**: Already handled in previous modules.
- **Internet (`INTERNET`)**: Required for Android's default `SpeechRecognizer` (online fallback for Hindi/Hinglish). Already in Manifest.

## 2. APIs Required
- **Android Speech Recognition Service**: Uses the on-device or Google-provided voice recognition engine. No specific API key is needed for the default Android provider, but the target device must have the Google App (or an equivalent speech provider) installed and enabled.

## 3. Manual Device Setup
- Ensure the **Google App** (or equivalent voice recognition service) is installed and selected as the default Voice Recognition service in Android Settings.
- Download offline language packs (English, Hindi) via the Google App settings for faster and guaranteed offline inference.
- Android 11+ requires `<queries>` in the manifest for `android.speech.RecognitionService` (which is automatically added in this generated code).

## Execution Strategy
Per the PERMISSION RULE, development and architecture construction proceeds immediately without blocking. All constraints are handled dynamically at runtime.
