# PLATFORM FEATURE MATRIX: Tabadvance Multi-OS AI Assistant

**Document Version:** 1.0.0  
**Date:** July 24, 2026  
**Scope:** Shared vs Native Feature Boundary Breakdown Across Platforms

---

## 1. Universal Feature Distribution Matrix

| Feature / Subsystem | Shared (Web/All) | Android Native | Windows 11 | Linux | macOS | Cloud Only |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **Workspace OS Dashboard** | `YES` | `YES` | `YES` | `YES` | `YES` | - |
| **Multi-Model AI Chat** | `YES` | `YES` | `YES` | `YES` | `YES` | - |
| **Vector Memory Store** | `YES` | `YES (Room+HNSW)` | `YES` | `YES` | `YES` | - |
| **Zero-Key Security Settings**| `YES` | `YES` | `YES` | `YES` | `YES` | - |
| **Module Telemetry Manager** | `YES` | `YES` | `YES` | `YES` | `YES` | - |
| **PWA Service Worker** | `YES` | - | - | - | - | - |
| **VoltBuilder Package** | `YES` | - | - | - | - | - |
| **Porcupine Wake-Word** | - | `YES` | `Via Tauri` | `Via Tauri` | `Via Tauri` | - |
| **Biometric Voice Auth** | - | `YES` | `Via Mic` | `Via Mic` | `Via Mic` | - |
| **Screen OCR & Vision** | - | `YES (MediaProj)` | `YES (GDI/DX)` | `YES (X11/Wayland)` | `YES (Quartz)` | - |
| **System Automation** | - | `YES (Accessib.)` | `YES (UIAuto)` | `YES (AT-SPI)` | `YES (Accessib.)` | - |
| **Live Web Search Grounding**| - | - | - | - | - | `YES` |
| **Cloud User Sync** | - | - | - | - | - | `YES` |

---

## 2. Platform Architectural Boundary Definitions

1. **Shared (Web / All Platforms):** User interfaces, state routers, multi-model LLM abstraction, vector index logic, and settings management run identically on all platforms via web standard runtime.
2. **Android Native:** Touch gestures, system accessibility service, camera frame capture, Porcupine continuous microphone background service, and TFLite execution.
3. **Desktop Native (Windows / Linux / Mac):** System tray icons, desktop global hotkeys, window management, OS screen capture APIs, and desktop accessibility automation frameworks.
4. **Cloud Only:** Live internet search grounding (Google Search / Web Scraping APIs) and encrypted user profile cloud sync servers.
