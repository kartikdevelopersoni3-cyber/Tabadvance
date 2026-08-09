import fs from 'fs';
import path from 'path';
import AdmZip from 'adm-zip';

const rootDir = process.cwd();
const publicDir = path.join(rootDir, 'public');
const exportsDir = path.join(rootDir, 'Exports');

if (!fs.existsSync(publicDir)) fs.mkdirSync(publicDir, { recursive: true });
if (!fs.existsSync(exportsDir)) fs.mkdirSync(exportsDir, { recursive: true });

// 1. Create APK_BUILD_INSTRUCTIONS.txt & README_APK.md
const apkInstructionsPath = path.join(publicDir, 'APK_BUILD_INSTRUCTIONS.txt');
const apkInstructionsContent = `===================================================================
 ROOHI AI ASSISTANT OS LAYER - APK BUILD & RUNTIME INSTRUCTIONS
===================================================================

This single master project ZIP contains the complete unified codebase for:
  - Native Android Runtime (Updates/ directory with 25 Kotlin package modules)
  - Cloud Web Application & PWA (Core/ & src/ directories)
  - CI/CD Workflows (.github/workflows/ & codemagic.yaml)
  - Documentation Catalog (Documentation/ directory)

-------------------------------------------------------------------
OPTION 1: BUILD APK LOCALLY WITH GRADLE
-------------------------------------------------------------------
1. Extract this zip archive.
2. Open a terminal in the 'Updates' directory.
3. Make gradlew executable:
   chmod +x gradlew
4. Run assembleDebug:
   ./gradlew assembleDebug
5. The output debug APK will be generated at:
   Updates/app/build/outputs/apk/debug/app-debug.apk

-------------------------------------------------------------------
OPTION 2: AUTOMATED BUILD VIA GITHUB ACTIONS
-------------------------------------------------------------------
1. Push this code to any GitHub repository.
2. The included workflow (.github/workflows/android-debug-apk.yml)
   will automatically build the Android APK and attach 'app-debug.apk'
   as an artifact on every push!

-------------------------------------------------------------------
OPTION 3: AUTOMATED BUILD VIA CODEMAGIC
-------------------------------------------------------------------
1. Connect your repository to Codemagic (codemagic.io).
2. The included 'codemagic.yaml' will automatically build the APK
   and deliver it directly to your email!

===================================================================
`;
fs.writeFileSync(apkInstructionsPath, apkInstructionsContent);

function addDirectoryToZip(dir, zip, zipDir, allowedExtensions = null) {
  if (!fs.existsSync(dir)) return;
  const items = fs.readdirSync(dir);
  for (const item of items) {
    if (item === 'node_modules' || item === '.git' || item === 'dist' || item === 'Archive') continue;
    const fullPath = path.join(dir, item);
    const relativePath = zipDir ? zipDir + '/' + item : item;

    if (fs.statSync(fullPath).isDirectory()) {
      addDirectoryToZip(fullPath, zip, relativePath, allowedExtensions);
    } else {
      if (item.endsWith('.zip')) continue;
      if (allowedExtensions && !allowedExtensions.some(ext => item.endsWith(ext))) continue;
      zip.addLocalFile(fullPath, zipDir);
    }
  }
}

// Generate Single Master Full Project Zip for APK (roohi_apk.zip)
const masterApkZip = new AdmZip();

// Add all core directories
addDirectoryToZip(path.join(rootDir, 'Updates'), masterApkZip, 'Updates');
addDirectoryToZip(path.join(rootDir, 'Core'), masterApkZip, 'Core');
addDirectoryToZip(path.join(rootDir, 'src'), masterApkZip, 'src');
addDirectoryToZip(path.join(rootDir, 'public'), masterApkZip, 'public');
addDirectoryToZip(path.join(rootDir, 'Documentation'), masterApkZip, 'Documentation');

if (fs.existsSync(path.join(rootDir, '.github'))) {
  addDirectoryToZip(path.join(rootDir, '.github'), masterApkZip, '.github');
}

// Add root configuration files
const rootFiles = [
  'config.xml',
  'package.json',
  'vite.config.ts',
  'tsconfig.json',
  'index.html',
  'codemagic.yaml',
  'metadata.json',
  '.env.example',
  'prepare-zip.js',
  'REPOSITORY_FREEZE.md',
  'NEXT_DEVELOPMENT_GUIDE.md',
  'ENGINEERING_STATUS_REPORT.md',
  'FEATURE_COMPLETION_MATRIX.md',
  'RUNTIME_TEST_PLAN.md',
  'FINAL_PRODUCTION_CHECKLIST.md',
  'REMAINING_WORK_ROADMAP.md',
  'PROJECT_SCORECARD.md',
  'MASTER_PRODUCT_ARCHITECTURE.md',
  'MULTI_PLATFORM_ROADMAP.md',
  'PLATFORM_FEATURE_MATRIX.md',
  'PRODUCTION_REQUIREMENTS.md',
  'ENTERPRISE_CHECKLIST.md',
  'PROJECT_FUTURE_VISION.md'
];

for (const file of rootFiles) {
  const p = path.join(rootDir, file);
  if (fs.existsSync(p)) {
    masterApkZip.addLocalFile(p, '');
  }
}

// Add instructions file directly to root of ZIP
masterApkZip.addLocalFile(apkInstructionsPath, '');

// Output master ZIP to multiple standard filenames in Exports and Public for direct user download
const mainApkPath = path.join(exportsDir, 'roohi_apk.zip');
masterApkZip.writeZip(mainApkPath);
masterApkZip.writeZip(path.join(exportsDir, 'Roohi_APK.zip'));
masterApkZip.writeZip(path.join(exportsDir, 'Roohi_AI_OS.zip'));
masterApkZip.writeZip(path.join(exportsDir, 'Update_Runtime.zip'));
masterApkZip.writeZip(path.join(exportsDir, 'Documentation.zip'));

masterApkZip.writeZip(path.join(publicDir, 'roohi_apk.zip'));
masterApkZip.writeZip(path.join(publicDir, 'Roohi_APK.zip'));
masterApkZip.writeZip(path.join(publicDir, 'Roohi_Master_Export.zip'));
masterApkZip.writeZip(path.join(publicDir, 'Roohi_VoltBuilder_Package.zip'));
masterApkZip.writeZip(path.join(publicDir, 'Roohi_AI_OS.zip'));
masterApkZip.writeZip(path.join(publicDir, 'Update_Runtime.zip'));
masterApkZip.writeZip(path.join(publicDir, 'Documentation.zip'));

const apkStats = fs.statSync(mainApkPath);

console.log(
  JSON.stringify({
    roohiApkZip: 'roohi_apk.zip (' + (apkStats.size / 1024 / 1024).toFixed(2) + ' MB)',
    status: 'Master Unified APK & Full Project ZIP Created Successfully at /public/roohi_apk.zip'
  })
);
