# GITHUB COPILOT HANDOFF: Roohi AI Assistant OS Layer

**Target Audience:** GitHub Copilot, Automated Coding Agents, Future Developers  
**Project Name:** Roohi AI Assistant OS Layer (Tabadvance)  
**Last Consolidated:** July 23, 2026

---

## 1. What This Project Is

Roohi AI Assistant OS Layer is a dual-target AI assistant system:
1. **Cloud Web Application & PWA (Primary Web Runtime):** Built with React 18, Vite, TypeScript, Tailwind CSS, Framer Motion, and Service Worker. Runs locally or in container environments like Google AI Studio (Port 3000).
2. **Native Android OS Layer (`/android-app`):** Built with Kotlin 1.9, Jetpack Compose, Room, Hilt, and Coroutines. Implements system-level background listening, gesture automation, and on-device vector memory.

---

## 2. Current Repository State

- **Web App / PWA:** 100% functional, responsive, and production-ready. Contains AI Chat, Memory Store, Settings Panel, Module Manager, User Auth Abstraction, and Workspace OS views.
- **VoltBuilder Package:** Verified `config.xml` present at root and `/public`. Ready for cloud APK/IPA packaging via VoltBuilder.
- **Android Native (`/android-app`):** Complete Kotlin source code across 25 packages (Modules A → Ω). Compilation inside this sandbox is disabled due to missing Android SDK binaries; native builds are offloaded to Codemagic or local Android Studio.

---

## 3. Directory Structure & Key Files

```
/
├── config.xml                      # VoltBuilder W3C Widget Configuration
├── prepare-zip.js                  # Automated package & zip bundler script
├── index.html                      # PWA Entry point with manifest & sw script
├── metadata.json                   # AI Studio App metadata
├── public/                         # Public assets & generated downloadable zips
│   ├── manifest.json               # Web App Manifest
│   ├── sw.js                       # Service Worker offline cache
│   ├── Roohi_Master_Export.zip     # Complete source archive
│   ├── Roohi_VoltBuilder_Package.zip# VoltBuilder package
│   └── Roohi_Master_Documentation.zip# Consolidated docs archive
├── src/
│   ├── App.tsx                     # Main layout & view router
│   ├── types.ts                    # TypeScript interface definitions
│   ├── components/                 # Modular UI components
│   │   ├── WorkspaceOS.tsx         # Primary workspace dashboard
│   │   ├── AIChatView.tsx          # Multi-model AI chat interface
│   │   ├── MemorySystemView.tsx    # Vector memory manager & search
│   │   ├── SettingsPanelView.tsx   # Provider & API key configuration
│   │   ├── ModuleManagerView.tsx   # Modules A -> Ω telemetry & toggle
│   │   ├── UserProfileView.tsx     # Account & cloud sync controller
│   │   └── PwaInstallBanner.tsx    # PWA prompt banner
│   └── services/                   # Service layer & data abstraction
│       ├── aiProviderService.ts    # Multi-model generator proxy
│       ├── memoryStore.ts          # LocalStorage & vector store proxy
│       └── authService.ts          # User auth abstraction
└── android-app/                    # Native Android Kotlin source & legacy audits
    └── app/src/main/java/com/roohi/app/
```

---

## 4. How Copilot Should Continue Development

### Safe Files to Edit
- `/src/components/*`: Adding new UI panels or refining responsive layouts.
- `/src/services/*`: Extending API provider integrations (e.g., adding streaming or function calling).
- `/src/types.ts`: Defining additional data models or state configurations.
- `MASTER_ROADMAP.md`: Updating task status during future sprints.

### Unsafe Files / Do Not Remove
- `config.xml`: Required for VoltBuilder compatibility.
- `public/manifest.json` & `public/sw.js`: Required for PWA installation & offline functionality.
- `prepare-zip.js`: Script used to package deliverables for users.
- `/android-app/app/src/main/java/com/roohi/app/*`: Preserved native Android source files for Modules A → Ω.

---

## 5. Coding Standards & Principles

1. **Mobile-First & Tablet Responsive:** Ensure all UI views fit tablet screens and high-resolution desktops gracefully.
2. **Zero Hardcoded Secrets:** Never hardcode API keys or credentials in code or `.env` files. Use `SettingsPanelView.tsx` or `.env.example`.
3. **Modular Architecture:** Keep views separated in `/src/components/` and logic in `/src/services/`.
4. **Clean Verification:** Run `npm run lint` or `npx tsc --noEmit` after any code edits to ensure strict TypeScript compilation.
