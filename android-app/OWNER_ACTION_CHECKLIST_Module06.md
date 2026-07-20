# OWNER ACTION CHECKLIST: Module 06

## 1. Permissions Needed
No new permissions are strictly required for the offline-first conversation engine. `INTERNET` permission is already in the manifest, which will be utilized later when online LLM providers (like Gemini) are integrated.

## 2. Manual Setup Needed
None at this stage. The Conversation Engine stores context and history in memory/repository automatically.

## 3. Future API Requirements
- **Gemini API Key** (or other Cloud AI keys) will be required when building the `GeminiProvider` implementation in a future module.
- For local offline LLMs (e.g., Gemma utilizing MediaPipe or Llama/MLC), a manual `.bin` or `.tflite` weights file will need to be downloaded to the device and mapped via asset loaders or external storage, similar to the Wake Word models.
