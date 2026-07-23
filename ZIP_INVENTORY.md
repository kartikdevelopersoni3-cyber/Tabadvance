# ZIP INVENTORY & ARCHIVE REPORT: Roohi AI Assistant OS Layer

**Document Version:** 1.0.0  
**Last Consolidated:** July 23, 2026

---

## 1. Archive Inventory

All downloadable ZIP archives are stored in `/public` and are accessible directly via the web server root URL.

| Zip Filename | Location | Size | Target Purpose | Status |
| :--- | :--- | :--- | :--- | :--- |
| **`Roohi_Master_Export.zip`** | `/public/Roohi_Master_Export.zip` | ~0.39 MB | Complete project source code export for web and repository transfer. | `ACTIVE / CURRENT` |
| **`Roohi_VoltBuilder_Package.zip`** | `/public/Roohi_VoltBuilder_Package.zip` | ~0.39 MB | VoltBuilder W3C package containing `config.xml`, `manifest.json`, and web assets. | `ACTIVE / CURRENT` |
| **`Roohi_Master_Package.zip`** | `/public/Roohi_Master_Package.zip` | ~0.39 MB | Full codebase archive copy. | `ACTIVE / CURRENT` |
| **`Roohi_Master_Documentation.zip`** | `/public/Roohi_Master_Documentation.zip` | ~0.13 MB | Consolidated archive of all project documentation, audit reports, and roadmaps. | `ACTIVE / CURRENT` |
| **`Roohi_Master_Export_FIXED.zip`** | `/public/Roohi_Master_Export_FIXED.zip` | ~0.27 MB | Legacy export version from prior iteration. | `OBSOLETE (Superceded by Master Export)` |

---

## 2. Zip Generation Automation

The archive files are built dynamically by running:
```bash
node prepare-zip.js
```
This script reads the repository, filters out temporary build artifacts (`node_modules`, `.git`, `dist`, and recursive `.zip` files), bundles the project sources and documentation using `adm-zip`, and places the resulting files into `/public`.

---

## 3. How to Download Archives

When running in the AI Studio environment or any deployed server:
1. **Master Source Export:** `https://<YOUR_APP_URL>/Roohi_Master_Export.zip`
2. **VoltBuilder Package:** `https://<YOUR_APP_URL>/Roohi_VoltBuilder_Package.zip`
3. **Consolidated Documentation:** `https://<YOUR_APP_URL>/Roohi_Master_Documentation.zip`
