# RUNTIME TEST PLAN: Tabadvance (Roohi AI Assistant OS Layer)

**Document Version:** 1.0.0  
**Date:** July 24, 2026  
**Scope:** Test Checklist for Web, PWA, VoltBuilder, and Native Android Runtimes

---

## 1. Web & Cloud Application Verification Checklist

| Test Item | Target Component | Status | Verification Method / Evidence |
| :--- | :--- | :--- | :--- |
| **Dev Server Startup** | `package.json` / `vite.config.ts` | `Verified` | Runs on Port 3000 (`host: "0.0.0.0"`). Linter & build pass cleanly. |
| **Workspace OS Dashboard Load** | `WorkspaceOS.tsx` | `Verified` | System telemetry cards, module matrix, and voice toggle render without console errors. |
| **Multi-Model AI Response Generation** | `AIChatView.tsx` | `Verified` | Prompt submission routes correctly to Gemini / OpenAI / Anthropic / Ollama via `aiProviderService.ts`. |
| **API Key Storage & Retrieval** | `SettingsPanelView.tsx` | `Verified` | Runtime keys saved to LocalStorage; zero keys hardcoded in client source code. |
| **Vector Memory Insertion & Search** | `MemorySystemView.tsx` | `Verified` | Test memory entries insert into local store and reflect in search queries. JSON state export works. |
| **Module Telemetry Toggle** | `ModuleManagerView.tsx` | `Verified` | Toggling module state switches UI status badge and updates active module count. |
| **User Profile Sync Display** | `UserProfileView.tsx` | `Verified` | Displays user status, auth token abstraction, and sync trigger controls. |

---

## 2. PWA & VoltBuilder Verification Checklist

| Test Item | Target Component | Status | Verification Method / Evidence |
| :--- | :--- | :--- | :--- |
| **Manifest Validation** | `/public/manifest.json` | `Verified` | Valid JSON with name, short_name, theme_color, background_color, and icons. |
| **Service Worker Registration** | `/public/sw.js` | `Verified` | Registered in `index.html`; caches app shell for offline availability. |
| **PWA Installation Prompt** | `PwaInstallBanner.tsx` | `Verified` | Detects `beforeinstallprompt` event and presents install button banner. |
| **VoltBuilder Packaging** | `config.xml` & `prepare-zip.js` | `Verified` | W3C widget preferences valid; `Roohi_VoltBuilder_Package.zip` passes structural check. |

---

## 3. Native Android Runtime Test Checklist (Hardware / Device Required)

| Test Item | Target Component | Status | Required Test Environment |
| :--- | :--- | :--- | :--- |
| **Wake-Word Listener Trigger** | `com.roohi.app.wakeword` | `Requires Device` | Physical Android tablet with microphone & Porcupine key. |
| **Speaker Voice Verification** | `com.roohi.app.voiceauth` | `Requires Device` | Physical Android tablet with audio recorder & registered voiceprint. |
| **Accessibility Gesture Tap** | `com.roohi.app.automation` | `Requires Device` | Physical Android tablet with Accessibility Permission enabled. |
| **Screen OCR Vision Analysis** | `com.roohi.app.vision` | `Requires Device` | Physical Android tablet with MediaProjection permission. |
| **Native APK Compilation** | `/Updates/build.gradle.kts` | `Requires Build` | External CI/CD (Codemagic) or local Android Studio with Android SDK 34 & JDK 17. |

---

## 4. Test Execution Summary

- **Web App & Cloud Suite:** 100% Tested & Verified in container environment.
- **PWA & VoltBuilder Suite:** 100% Verified structural compatibility.
- **Native Android Suite:** 100% Source verified; runtime execution requires physical hardware / Android SDK build runner.
