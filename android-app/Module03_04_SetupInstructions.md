# Setup Instructions: Module 03 & 04 (Wake Word Engine + Owner Voice Authentication)

This module builds upon the permissions from Module 02.

## Required Android Permissions (AndroidManifest.xml)
- `RECORD_AUDIO`: Already added in base setup. Needed by `AudioRecorderFlow` to stream raw MIC bytes natively to TFLite models.

## Gradle Configurations
- Room Database dependencies added. 
- Ensure KAPT is properly processing `androidx.room:room-compiler`.
- TensorFlow Lite dependencies added (`org.tensorflow:tensorflow-lite`, `org.tensorflow:tensorflow-lite-support`).

## Manual Device Setup Instructions
When testing or installing this module:
1. Microphone Permission: The operating system will require the end user to explicitly accept Microphone tracking.
2. In a production state, you must acquire `.tflite` model binaries for Wake Word ("Roohi") and Speaker Verification (e.g. d-vector). These must go into the `app/src/main/assets` folder. The architecture is fully constructed to accept and invoke `interpreter.run(...)` on these structures.

*Note: Per the PERMISSION RULE, no permission prompts will block application startup/compilation flow.*
