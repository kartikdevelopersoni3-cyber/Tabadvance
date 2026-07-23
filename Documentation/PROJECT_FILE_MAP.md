# PROJECT FILE MAP: Roohi AI Assistant OS Layer

**Document Version:** 1.0.0  
**Last Consolidated:** July 23, 2026

---

## 1. Root Directory Overview

```
/
├── .env.example                    # Template for required environment variables
├── .gitignore                      # Git exclusion rules (node_modules, dist, secrets)
├── config.xml                      # VoltBuilder W3C Widget configuration (Root)
├── index.html                      # HTML entry point with PWA manifest & SW links
├── metadata.json                   # AI Studio App name, description, capabilities
├── package.json                    # Dependencies & npm scripts
├── package-lock.json               # Locked dependency tree
├── bun.lock                         # Alternative lockfile
├── prepare-zip.js                  # Automated package & documentation zip generator
├── tsconfig.json                   # TypeScript compiler configuration
├── vite.config.ts                  # Vite build & dev server config (Port 3000)
├── PROJECT_Tab_BACKUP.md           # Master backup documentation reference
├── STRUCTURE.md                    # System architecture outline
├── MASTER_PROJECT_STATUS.md        # Master Engineering Status (SSOT)
├── GITHUB_COPILOT_HANDOFF.md       # Copilot & developer handoff guidelines
├── MASTER_SUMMARY.txt              # Executive summary & quick reference
├── PROJECT_FILE_MAP.md             # Complete filesystem inventory (This file)
├── MASTER_ROADMAP.md               # Unified project roadmap
├── ZIP_INVENTORY.md                # Comprehensive archive inventory
└── DOCUMENTATION_INDEX.md          # Master index of all markdown reports
```

---

## 2. Web Source Code (`/src`)

```
/src/
├── main.tsx                        # React application bootstrap entry
├── App.tsx                         # Primary view router, state, header, & footer
├── index.css                       # Global Tailwind CSS directives
├── types.ts                        # Unified TypeScript type interfaces
├── components/                     # Modular React components
│   ├── HeroSection.tsx             # Hero banner & primary download call-to-action
│   ├── IntroSequence.tsx           # Multi-step feature tour modal
│   ├── WorkspaceOS.tsx             # Primary OS Layer dashboard & live voice console
│   ├── AIChatView.tsx              # Multi-model AI Chat with grounding toggle
│   ├── MemorySystemView.tsx        # Vector memory search, insertion & export
│   ├── SettingsPanelView.tsx       # Provider configuration & key management
│   ├── ModuleManagerView.tsx       # System telemetry & Modules A -> Omega toggles
│   ├── UserProfileView.tsx         # User profile, OAuth display, & cloud sync
│   ├── PwaInstallBanner.tsx        # PWA prompt banner
│   ├── SetupWizard.tsx             # Guided configuration wizard
│   ├── WelcomeModal.tsx            # Welcome modal after activation
│   └── ActivationProgressModal.tsx # Progress modal for activation workflow
└── services/                       # Data and service integration layers
    ├── aiProviderService.ts        # Gemini/OpenAI/Anthropic proxy service
    ├── memoryStore.ts              # LocalStorage & HNSW memory vector manager
    └── authService.ts              # User profile & authentication abstraction
```

---

## 3. Public Assets & Deliverables (`/public`)

```
/public/
├── config.xml                      # VoltBuilder configuration copy for public root
├── manifest.json                   # Web Application Manifest for PWA
├── sw.js                           # Service Worker for offline caching
├── PROJECT_BUILD_STATUS.md         # Public build status marker
├── Roohi_Master_Export.zip         # Full source export archive (0.39 MB)
├── Roohi_VoltBuilder_Package.zip   # VoltBuilder-ready source archive (0.39 MB)
├── Roohi_Master_Package.zip        # Master package copy (0.39 MB)
└── Roohi_Master_Documentation.zip  # Consolidated documentation archive (0.13 MB)
```

---

## 4. Native Android Source Code (`/android-app`)

```
/android-app/
├── build.gradle.kts                # Root Android build configuration
├── settings.gradle.kts             # Gradle settings & subproject definitions
├── gradle.properties               # JVM & Kotlin build properties
├── gradlew / gradlew.bat           # Gradle wrapper executables
└── app/
    ├── build.gradle.kts            # App module build script (Dependencies & SDK 34)
    ├── proguard-rules.pro          # ProGuard obfuscation rules
    └── src/main/
        ├── AndroidManifest.xml     # Android System permissions & services
        └── java/com/roohi/app/     # Kotlin package root (25 subpackages)
            ├── RoohiApp.kt         # Application class & initialization
            ├── wakeword/           # Porcupine wake-word service
            ├── speech/             # Biometric voice verification
            ├── conversation/       # Dialogue state engine
            ├── memory/             # Local HNSW vector store
            ├── reasoning/          # Logic decomposition engine
            ├── coordination/       # Multi-agent DAG coordinator
            ├── vision/             # Camera frame & OCR analyzer
            ├── automation/         # Accessibility gesture engine
            ├── learning/           # Preference tuning engine
            ├── knowledge/          # Entity facts store
            ├── proactive/          # Context suggestions
            ├── workspace/          # Tablet window controller
            ├── evolution/          # Module Omega self-modification
            └── [11 other packages] # DSP, DI, Core, Identity, Owner, etc.
```

---

## 5. Audit & Engineering Documents (`/android-app/*.md`)

Contains historical audit reports, module verification logs, and red-team testing notes from prior Android development phases. All essential information from these documents has been synthesized into `MASTER_PROJECT_STATUS.md` and `MASTER_ROADMAP.md`.
