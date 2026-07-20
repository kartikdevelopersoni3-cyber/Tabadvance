# Owner Readiness Report

A precise checklist for Human-In-The-Loop actions required to advance the product.

### A. Required Immediately Before Module 10
- **Live Gemini API Key**: Must be manually populated into the system for actual LLM generation to replace `ResponseGenerator` and `ReasoningManager` STUB modes.
- **Physical ML Models**: `voice_auth.tflite` (or similar) into `assets/`.
- **UI Launch Structure**: So users can initiate configuration workflows.

### B. Required Before Production
- **Battery Optimization Exemptions**: Manual OS-level toggle ensuring Roohi doesn't sleep.
- **Microphone / Accessibility Permissions**: Must be securely accepted through standard OS dialogs.
- **Physical Emergency Contacts**: Must manually add trusted contacts for system controls.

### C. Required Before Public Release
- **Analytics/Crashlytics Configuration**: If telemetry is desired. Ensure Privacy Policies exist.
