# MASTER PRODUCT ARCHITECTURE: Tabadvance Multi-Platform AI OS Layer

**Document Version:** 4.0.0 (Master Vision & Multi-Platform Operating Layer Specification)  
**Date:** July 24, 2026  
**Vision:** Long-Term Production-Grade Multi-OS Autonomous Assistant System

---

## 1. Executive Multi-Platform Architectural Vision

The **Tabadvance – Roohi AI Assistant OS Layer** is engineered as a universal, cross-platform AI Operating Layer. Rather than restricting the architecture to a single mobile runtime or fast APK wrapper, Tabadvance establishes a modular, multi-tiered core capable of running across:

- **Android Tablets & Phones** (Touch UI, background services, accessibility automation)
- **Windows 11** (Desktop workspace, system tray, Win32/UI Automation accessibility)
- **Linux** (Desktop, daemon execution, AT-SPI accessibility, CLI orchestration)
- **macOS** (Desktop workspace, Accessibility API adapters)
- **Progressive Web App & Modern Web Browsers** (Universal web availability, local vector storage)

---

## 2. Decoupled System Layer Architecture

```
+-----------------------------------------------------------------------------------+
|                            SHARED UI & USER INTERFACE                              |
|   Workspace OS Dashboard  |  AI Chat View  |  Memory Store View  |  Settings UI   |
|   React 18 / TypeScript / Tailwind CSS / Framer Motion / Custom Component System  |
+-----------------------------------------------------------------------------------+
                                         |
                                         v
+-----------------------------------------------------------------------------------+
|                              SHARED CORE AI & OS ENGINES                           |
|   AI Provider Proxy Layer   |  Multi-Agent DAG Router  |  Vector Memory (HNSW)    |
|   Entity Knowledge Graph    |  Context Suggestion      |  Zero-Key Key Vault      |
+-----------------------------------------------------------------------------------+
                                         |
                                         v
+-----------------------------------------------------------------------------------+
|                           PLATFORM RUNTIME ADAPTER LAYER                          |
+------------------+-------------------+------------------+-------------------------+
| Android Adapter  |  Windows Adapter  |  Linux Adapter   |    Web/PWA Adapter      |
| Kotlin / Jetpack |  Tauri/Rust C++   |  Tauri/Python    | Service Worker / W3C    |
| Foreground Serv. | Win32 / UIAuto    | AT-SPI / DBus    | LocalStorage/IndexedDB  |
| Accessibility    | System Tray / CLI | System Daemon    | VoltBuilder W3C Widget  |
+------------------+-------------------+------------------+-------------------------+
```

---

## 3. Core Engine Component Subsystems

### 3.1 Shared Core Engine (`/src/services` & Core Logic)
- **AI Provider Proxy Service (`aiProviderService.ts`):** Unified multi-model client routing calls to Gemini (`gemini-2.5-flash`), OpenAI (`gpt-4o-mini`), Anthropic (`claude-3-5-sonnet`), and Local Ollama with web grounding capabilities.
- **Vector Memory Engine (`memoryStore.ts`):** In-browser HNSW vector indexing simulation, key-value preference store, and structured entity graph store.
- **Authentication & State Abstraction (`authService.ts`):** OAuth profile status display, token handling, and encrypted sync state controller.

### 3.2 Platform-Specific Native Runtimes (`Updates/` & Adapters)
- **Android Runtime (`Updates/`):** Kotlin 1.9 native OS layer with 25 package modules handling Porcupine wake-word listening, biometric voice verification, accessibility gesture automation, and on-device TFLite intent execution.
- **Desktop Runtimes (Windows, Linux, macOS):** Lightweight native shell wrappers (e.g., Tauri / Rust) interfacing with OS-native accessibility APIs, desktop notifications, and local file system hooks.
- **Web / PWA Runtime (`public/sw.js` & `config.xml`):** Web application manifest and offline caching service worker for universal browser execution.
