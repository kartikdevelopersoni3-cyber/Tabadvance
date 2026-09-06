import React, { useState } from 'react';
import { 
  Smartphone, 
  Download, 
  CheckCircle2, 
  Clock, 
  AlertTriangle, 
  Layers, 
  Terminal, 
  Plus, 
  Search, 
  ShieldCheck, 
  Cpu, 
  Activity,
  HardDrive,
  RefreshCw,
  Zap,
  ArrowUpRight,
  MessageSquare,
  Database,
  Grid,
  Settings,
  User,
  Sparkles,
  ExternalLink,
  PackageCheck
} from 'lucide-react';
import { motion, AnimatePresence } from 'motion/react';
import { UpdateRelease, BuildLog, DeviceStats, SetupState } from './types';
import { WorkspaceOS } from './components/WorkspaceOS';
import { AIChatView } from './components/AIChatView';
import { MemorySystemView } from './components/MemorySystemView';
import { ModuleManagerView } from './components/ModuleManagerView';
import { SettingsPanelView } from './components/SettingsPanelView';
import { UserProfileView } from './components/UserProfileView';
import { SetupWizard } from './components/SetupWizard';
import { HeroSection } from './components/HeroSection';
import { ActivationProgressModal } from './components/ActivationProgressModal';
import { WelcomeModal } from './components/WelcomeModal';
import { PwaInstallBanner } from './components/PwaInstallBanner';

type MainTab = 'workspace' | 'chat' | 'memory' | 'modules' | 'updates' | 'settings' | 'auth' | 'wizard';

const DEFAULT_SETUP_CONFIG: SetupState = {
  language: 'en',
  permissions: {
    microphone: true,
    storage: true,
    notifications: true,
    accessibility: true,
    camera: true
  },
  aiMode: 'hybrid',
  cloudConfig: {
    provider: 'gemini',
    apiKey: '',
    localServerUrl: 'http://localhost:11434',
    isConnected: false,
    connectionStatus: 'idle'
  },
  voiceSetup: {
    wakeWord: 'Hey Roohi',
    isEnrolled: true,
    pitch: 1.0,
    speed: 1.0,
    tone: 'Neutral Assistant'
  },
  workspacePrefs: {
    theme: 'dark-luxury',
    layout: 'balanced',
    showQuickActions: true,
    showMemoryLog: true,
    showAudioWave: true,
    showSystemMonitor: true,
    soundNotifications: true
  }
};

const INITIAL_RELEASES: UpdateRelease[] = [
  {
    id: 'rel-001',
    version: '2.4.0',
    versionCode: 240,
    channel: 'Stable',
    releaseDate: '2026-08-08',
    sizeMb: 18.4,
    status: 'Ready',
    changes: [
      'Android 14 API 34 target SDK optimizations blueprint',
      'Simulated background update verification pipeline',
      'Dark luxury aesthetic and responsive tab layout'
    ],
    minAndroidSdk: 26,
    targetAndroidSdk: 34,
    checksum: 'a8f9c12b7e0d3f4a'
  },
  {
    id: 'rel-002',
    version: '2.5.0-beta1',
    versionCode: 2501,
    channel: 'Beta',
    releaseDate: '2026-08-09',
    sizeMb: 19.2,
    status: 'Building',
    changes: [
      'Simulated OTA update channel selector interface',
      'Added JDK 17 OpenJDK compatibility checks',
      'Sample Gradle build output stream generator'
    ],
    minAndroidSdk: 28,
    targetAndroidSdk: 34,
    checksum: 'e3b0c44298fc1c14'
  },
  {
    id: 'rel-003',
    version: '2.3.9',
    versionCode: 2390,
    channel: 'Stable',
    releaseDate: '2026-08-01',
    sizeMb: 17.9,
    status: 'Ready',
    changes: [
      'Security patch specification for certificate store',
      'Local cache lock file resolution script'
    ],
    minAndroidSdk: 26,
    targetAndroidSdk: 33,
    checksum: 'f4d9a112bc8e0019'
  }
];

const INITIAL_BUILD_LOGS: BuildLog[] = [
  {
    id: 'log-1',
    timestamp: '10:33:46',
    task: ':app:preBuild',
    status: 'SUCCESS',
    message: 'OpenJDK 17 configured successfully [Sample Log]',
    durationMs: 420
  },
  {
    id: 'log-2',
    timestamp: '10:34:01',
    task: ':app:processDebugResources',
    status: 'SUCCESS',
    message: 'Processed vector drawables & mipmap assets [Sample Log]',
    durationMs: 1200
  },
  {
    id: 'log-3',
    timestamp: '10:35:34',
    task: ':app:compileDebugKotlin',
    status: 'IN_PROGRESS',
    message: 'Compiling Kotlin source sets for API level 34... [Simulated Task]',
    durationMs: 3400
  }
];

const STATS: DeviceStats = {
  activeInstallations: 48290,
  updateAdoptionRate: 94.2,
  pendingUpdates: 1840,
  failedUpdates: 12
};

export default function App() {
  const [activeTab, setActiveTab] = useState<MainTab>('workspace');
  const [setupConfig, setSetupConfig] = useState<SetupState>(DEFAULT_SETUP_CONFIG);

  // Modals
  const [showActivationModal, setShowActivationModal] = useState(false);
  const [showWelcomeModal, setShowWelcomeModal] = useState(false);

  // Releases & Builds state (from Updates Manager)
  const [releases, setReleases] = useState<UpdateRelease[]>(INITIAL_RELEASES);
  const [buildLogs] = useState<BuildLog[]>(INITIAL_BUILD_LOGS);
  const [selectedChannel, setSelectedChannel] = useState<string>('All');
  const [searchQuery, setSearchQuery] = useState('');
  const [isBuilding, setIsBuilding] = useState(false);
  const [showNewReleaseModal, setShowNewReleaseModal] = useState(false);

  const [newVersion, setNewVersion] = useState('');
  const [newChannel, setNewChannel] = useState<'Stable' | 'Beta' | 'Alpha' | 'Nightly'>('Beta');
  const [newChanges, setNewChanges] = useState('');

  const filteredReleases = releases.filter((rel) => {
    const matchesChannel = selectedChannel === 'All' || rel.channel === selectedChannel;
    const matchesSearch = rel.version.toLowerCase().includes(searchQuery.toLowerCase()) ||
      rel.changes.some(c => c.toLowerCase().includes(searchQuery.toLowerCase()));
    return matchesChannel && matchesSearch;
  });

  const handleCreateRelease = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newVersion.trim()) return;

    const created: UpdateRelease = {
      id: `rel-${Date.now()}`,
      version: newVersion,
      versionCode: Math.floor(Math.random() * 9000) + 1000,
      channel: newChannel,
      releaseDate: new Date().toISOString().split('T')[0],
      sizeMb: parseFloat((Math.random() * 5 + 15).toFixed(1)),
      status: 'Ready',
      changes: newChanges.split('\n').filter(c => c.trim().length > 0),
      minAndroidSdk: 28,
      targetAndroidSdk: 34,
      checksum: Math.random().toString(16).substring(2, 18)
    };

    setReleases([created, ...releases]);
    setNewVersion('');
    setNewChanges('');
    setShowNewReleaseModal(false);
  };

  const handleTriggerBuild = () => {
    setIsBuilding(true);
    setTimeout(() => {
      setIsBuilding(false);
    }, 2500);
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 font-sans antialiased">
      {/* Top Prototype & Demo Disclaimer Bar */}
      <div className="bg-amber-950/40 border-b border-amber-500/30 px-4 py-2 text-xs text-amber-300 flex flex-col sm:flex-row items-center justify-between gap-2">
        <div className="flex items-center gap-2 text-center sm:text-left">
          <span className="px-2 py-0.5 rounded bg-amber-500/20 text-amber-200 font-bold font-mono text-[10px] uppercase shrink-0">
            PROTOTYPE DEMO / CODEBASE SALE
          </span>
          <span className="leading-tight">
            This repository is a prototype demonstration & architectural codebase. External AI APIs, cloud sync, and vector DB are simulated/stubbed.
          </span>
        </div>
        <div className="flex items-center gap-2 shrink-0">
          <a
            href="/demosoldproject.zip"
            download="demosoldproject.zip"
            className="px-2.5 py-1 rounded bg-amber-500 hover:bg-amber-400 text-slate-950 font-bold text-xs flex items-center gap-1.5 transition shadow"
            title="Download full project deliverable ZIP"
          >
            <PackageCheck className="w-3.5 h-3.5" />
            <span>Download Master ZIP (demosoldproject.zip)</span>
          </a>
        </div>
      </div>

      {/* Main App Navigation Bar */}
      <header className="border-b border-slate-800 bg-slate-900/90 backdrop-blur-md sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 rounded-xl bg-indigo-600/20 border border-indigo-500/30 flex items-center justify-center text-indigo-400">
              <Layers className="w-5 h-5" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-base sm:text-lg font-bold tracking-tight text-white">
                  Roohi AI OS
                </h1>
                <span className="px-2 py-0.5 text-[10px] font-mono font-semibold rounded-full bg-blue-500/10 text-blue-400 border border-blue-500/20">
                  PROTOTYPE
                </span>
              </div>
              <p className="text-[11px] text-slate-400">Tablet AI Operating Layer & Multi-Module Architecture</p>
            </div>
          </div>

          {/* Quick Direct Download Links */}
          <div className="flex items-center space-x-2">
            <a
              href="/demosoldproject.zip"
              download="demosoldproject.zip"
              className="hidden lg:inline-flex items-center space-x-1.5 px-3 py-1.5 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white text-xs font-semibold transition shadow-md shadow-indigo-600/20"
            >
              <Download className="w-3.5 h-3.5" />
              <span>demosoldproject.zip</span>
            </a>
            <a
              href="/Roohi_APK.zip"
              download="Roohi_APK.zip"
              className="inline-flex items-center space-x-1.5 px-3 py-1.5 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white text-xs font-semibold transition shadow-md shadow-emerald-600/20"
            >
              <Download className="w-3.5 h-3.5" />
              <span>APK Package</span>
            </a>
          </div>
        </div>

        {/* View Switcher Tabs */}
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 border-t border-slate-800/80 overflow-x-auto flex items-center gap-1 py-1.5 scrollbar-none">
          <button
            onClick={() => setActiveTab('workspace')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'workspace'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <Layers className="w-3.5 h-3.5" />
            <span>Workspace OS</span>
          </button>

          <button
            onClick={() => setActiveTab('chat')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'chat'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <MessageSquare className="w-3.5 h-3.5" />
            <span>AI Chat [SIMULATED]</span>
          </button>

          <button
            onClick={() => setActiveTab('memory')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'memory'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <Database className="w-3.5 h-3.5" />
            <span>Memory Store [LOCAL STUB]</span>
          </button>

          <button
            onClick={() => setActiveTab('modules')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'modules'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <Grid className="w-3.5 h-3.5" />
            <span>Modules A→Ω [SPEC]</span>
          </button>

          <button
            onClick={() => setActiveTab('updates')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'updates'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <Smartphone className="w-3.5 h-3.5" />
            <span>Updates & Builds [DEMO]</span>
          </button>

          <button
            onClick={() => setActiveTab('settings')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'settings'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <Settings className="w-3.5 h-3.5" />
            <span>Settings [DEMO]</span>
          </button>

          <button
            onClick={() => setActiveTab('auth')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'auth'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <User className="w-3.5 h-3.5" />
            <span>User Profile [DEMO]</span>
          </button>

          <button
            onClick={() => setActiveTab('wizard')}
            className={`px-3 py-1.5 rounded-lg text-xs font-medium whitespace-nowrap flex items-center gap-1.5 transition ${
              activeTab === 'wizard'
                ? 'bg-indigo-600 text-white shadow-sm'
                : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
            }`}
          >
            <Sparkles className="w-3.5 h-3.5" />
            <span>Onboarding Wizard [PROTOTYPE]</span>
          </button>
        </div>
      </header>

      {/* Main Content Area */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <PwaInstallBanner />

        {/* Tab 1: Workspace OS */}
        {activeTab === 'workspace' && (
          <div className="space-y-6">
            <HeroSection
              onStartActivation={() => setShowActivationModal(true)}
              onOpenIntro={() => setActiveTab('wizard')}
            />
            <WorkspaceOS
              setupConfig={setupConfig}
              onOpenSettings={() => setActiveTab('settings')}
            />
          </div>
        )}

        {/* Tab 2: AI Chat View */}
        {activeTab === 'chat' && (
          <div className="space-y-6">
            <div className="p-4 rounded-xl bg-slate-900 border border-slate-800 text-xs text-slate-400 flex items-center justify-between">
              <div className="flex items-center gap-2">
                <span className="w-2.5 h-2.5 rounded-full bg-emerald-400 animate-pulse" />
                <span className="font-semibold text-slate-200">AI Dialogue Simulation Engine Active</span>
                <span>• Responses are generated locally via simulated reasoning templates.</span>
              </div>
              <button
                onClick={() => setActiveTab('settings')}
                className="text-indigo-400 hover:underline cursor-pointer"
              >
                Configure Provider →
              </button>
            </div>
            <AIChatView
              setupConfig={setupConfig}
              onOpenSettings={() => setActiveTab('settings')}
            />
          </div>
        )}

        {/* Tab 3: Memory System View */}
        {activeTab === 'memory' && (
          <MemorySystemView />
        )}

        {/* Tab 4: Module Manager View */}
        {activeTab === 'modules' && (
          <ModuleManagerView />
        )}

        {/* Tab 5: Settings Panel View */}
        {activeTab === 'settings' && (
          <SettingsPanelView
            setupConfig={setupConfig}
            onSaveConfig={(updated) => setSetupConfig(updated)}
          />
        )}

        {/* Tab 6: User Profile & Sync */}
        {activeTab === 'auth' && (
          <UserProfileView />
        )}

        {/* Tab 7: Setup Wizard */}
        {activeTab === 'wizard' && (
          <div className="space-y-6">
            <SetupWizard
              onComplete={(completedConfig) => {
                setSetupConfig(completedConfig);
                setActiveTab('workspace');
              }}
            />
          </div>
        )}

        {/* Tab 8: Updates & Builds (Android OTA & Gradle) */}
        {activeTab === 'updates' && (
          <div className="space-y-8">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-slate-800 pb-4">
              <div>
                <h2 className="text-xl font-bold text-white flex items-center gap-2">
                  Updates Manager [SIMULATED BUILD SYSTEM]
                  <span className="px-2 py-0.5 text-xs font-medium rounded-full bg-indigo-500/10 text-indigo-400 border border-indigo-500/20">
                    Android API 34
                  </span>
                </h2>
                <p className="text-xs text-slate-400">Demonstration of OTA release channels, Kotlin compilation tasks, and package distribution.</p>
              </div>

              <div className="flex items-center space-x-2">
                <button
                  onClick={handleTriggerBuild}
                  disabled={isBuilding}
                  className="inline-flex items-center space-x-2 px-3.5 py-2 rounded-lg bg-slate-800 hover:bg-slate-700 border border-slate-700 text-slate-200 text-xs font-medium transition disabled:opacity-50 cursor-pointer"
                >
                  <RefreshCw className={`w-3.5 h-3.5 ${isBuilding ? 'animate-spin' : ''}`} />
                  <span>{isBuilding ? 'Simulating Build...' : 'Simulate Gradle Build'}</span>
                </button>
                <button
                  onClick={() => setShowNewReleaseModal(true)}
                  className="inline-flex items-center space-x-2 px-3.5 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white text-xs font-medium transition cursor-pointer"
                >
                  <Plus className="w-3.5 h-3.5" />
                  <span>New Release Spec</span>
                </button>
              </div>
            </div>

            {/* Metric Cards */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
              <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
                <div className="flex items-center justify-between text-slate-400">
                  <span className="text-xs font-medium uppercase tracking-wider">Active Devices [SIMULATED]</span>
                  <Smartphone className="w-4 h-4 text-indigo-400" />
                </div>
                <div className="text-2xl font-bold text-white">{STATS.activeInstallations.toLocaleString()}</div>
                <div className="text-xs text-emerald-400 flex items-center gap-1">
                  <ArrowUpRight className="w-3.5 h-3.5" /> Sample telemetry metric
                </div>
              </div>

              <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
                <div className="flex items-center justify-between text-slate-400">
                  <span className="text-xs font-medium uppercase tracking-wider">Adoption Rate [SIMULATED]</span>
                  <Activity className="w-4 h-4 text-emerald-400" />
                </div>
                <div className="text-2xl font-bold text-white">{STATS.updateAdoptionRate}%</div>
                <div className="text-xs text-slate-400">Demonstration target 95%+</div>
              </div>

              <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
                <div className="flex items-center justify-between text-slate-400">
                  <span className="text-xs font-medium uppercase tracking-wider">Pending OTA [STUB]</span>
                  <Clock className="w-4 h-4 text-amber-400" />
                </div>
                <div className="text-2xl font-bold text-white">{STATS.pendingUpdates.toLocaleString()}</div>
                <div className="text-xs text-amber-400">Simulated queue</div>
              </div>

              <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
                <div className="flex items-center justify-between text-slate-400">
                  <span className="text-xs font-medium uppercase tracking-wider">Environment Health</span>
                  <ShieldCheck className="w-4 h-4 text-emerald-400" />
                </div>
                <div className="text-2xl font-bold text-emerald-400">Verified</div>
                <div className="text-xs text-slate-400">JDK 17 + Gradle 8.5 Source Sets</div>
              </div>
            </div>

            {/* System Environment Info */}
            <div className="p-5 rounded-xl bg-slate-900/60 border border-slate-800 grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="flex items-center space-x-3">
                <Cpu className="w-5 h-5 text-indigo-400" />
                <div>
                  <div className="text-xs text-slate-400">JDK Environment Target</div>
                  <div className="text-sm font-semibold text-slate-200">OpenJDK 17.0.20</div>
                </div>
              </div>
              <div className="flex items-center space-x-3">
                <HardDrive className="w-5 h-5 text-indigo-400" />
                <div>
                  <div className="text-xs text-slate-400">Android SDK Target</div>
                  <div className="text-sm font-semibold text-slate-200">Android 14 (API 34)</div>
                </div>
              </div>
              <div className="flex items-center space-x-3">
                <Zap className="w-5 h-5 text-indigo-400" />
                <div>
                  <div className="text-xs text-slate-400">Build System Spec</div>
                  <div className="text-sm font-semibold text-slate-200">Gradle 8.5 Wrapper in /Updates</div>
                </div>
              </div>
            </div>

            {/* Content Tabs / Filter & List */}
            <div className="space-y-4">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
                <div className="flex items-center space-x-2 border-b border-slate-800 pb-2 sm:pb-0 sm:border-0">
                  {['All', 'Stable', 'Beta', 'Alpha', 'Nightly'].map((ch) => (
                    <button
                      key={ch}
                      onClick={() => setSelectedChannel(ch)}
                      className={`px-3 py-1.5 rounded-lg text-xs font-medium transition cursor-pointer ${
                        selectedChannel === ch
                          ? 'bg-indigo-600 text-white shadow-md'
                          : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/60'
                      }`}
                    >
                      {ch}
                    </button>
                  ))}
                </div>

                <div className="relative w-full sm:w-64">
                  <Search className="w-4 h-4 absolute left-3 top-2.5 text-slate-500" />
                  <input
                    type="text"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="Search releases..."
                    className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-9 pr-3 py-1.5 text-xs text-slate-200 focus:outline-none focus:border-indigo-500"
                  />
                </div>
              </div>

              {/* Releases List */}
              <div className="space-y-3">
                <AnimatePresence>
                  {filteredReleases.map((rel) => (
                    <motion.div
                      key={rel.id}
                      initial={{ opacity: 0, y: 10 }}
                      animate={{ opacity: 1, y: 0 }}
                      exit={{ opacity: 0, height: 0 }}
                      className="p-5 rounded-xl bg-slate-900 border border-slate-800 hover:border-slate-700 transition space-y-4"
                    >
                      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2">
                        <div className="flex items-center space-x-3">
                          <span className="text-lg font-bold text-white">v{rel.version}</span>
                          <span className={`px-2.5 py-0.5 text-xs font-semibold rounded-full border ${
                            rel.channel === 'Stable' 
                              ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/20'
                              : rel.channel === 'Beta'
                              ? 'bg-amber-500/10 text-amber-400 border-amber-500/20'
                              : 'bg-indigo-500/10 text-indigo-400 border-indigo-500/20'
                          }`}>
                            {rel.channel}
                          </span>
                          <span className="text-xs text-slate-500">
                            Code: {rel.versionCode}
                          </span>
                        </div>

                        <div className="flex items-center space-x-3 text-xs text-slate-400">
                          <span className="flex items-center gap-1">
                            <Clock className="w-3.5 h-3.5" /> {rel.releaseDate}
                          </span>
                          <span>•</span>
                          <span>{rel.sizeMb} MB</span>
                          <span>•</span>
                          <span className="flex items-center gap-1 text-emerald-400">
                            <CheckCircle2 className="w-3.5 h-3.5" /> {rel.status}
                          </span>
                        </div>
                      </div>

                      {/* Changes list */}
                      <div className="space-y-1.5">
                        <div className="text-xs font-medium text-slate-400">Release Highlights:</div>
                        <ul className="list-disc list-inside text-xs text-slate-300 space-y-1 pl-1">
                          {rel.changes.map((c, idx) => (
                            <li key={idx}>{c}</li>
                          ))}
                        </ul>
                      </div>

                      <div className="pt-2 border-t border-slate-800/60 flex items-center justify-between text-xs text-slate-500">
                        <div className="font-mono text-[11px] truncate max-w-xs">
                          SHA256: {rel.checksum}
                        </div>
                        <div className="flex items-center space-x-2">
                          <a
                            href="/Roohi_APK.zip"
                            download="Roohi_APK.zip"
                            className="px-3 py-1.5 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white text-xs font-semibold transition flex items-center gap-1.5 shadow-md shadow-emerald-600/20"
                          >
                            <Download className="w-3.5 h-3.5" /> Download APK Package
                          </a>
                          <a
                            href="/Roohi_PWA.zip"
                            download="Roohi_PWA.zip"
                            className="px-3 py-1.5 rounded-lg bg-slate-800 hover:bg-slate-700 border border-slate-700 text-slate-300 text-xs font-medium transition flex items-center gap-1.5"
                          >
                            <Download className="w-3.5 h-3.5" /> PWA Package
                          </a>
                        </div>
                      </div>
                    </motion.div>
                  ))}
                </AnimatePresence>
              </div>
            </div>

            {/* Build Terminal Console */}
            <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-3">
              <div className="flex items-center justify-between text-xs text-slate-400 border-b border-slate-800 pb-3">
                <div className="flex items-center space-x-2">
                  <Terminal className="w-4 h-4 text-indigo-400" />
                  <span className="font-semibold text-slate-200">Gradle Build Output Console [SAMPLE LOG STREAM]</span>
                </div>
                <span className="font-mono text-emerald-400 flex items-center gap-1">
                  <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span> Daemon Active (Demonstration)
                </span>
              </div>

              <div className="bg-slate-950 rounded-lg p-4 font-mono text-xs text-slate-300 space-y-2 overflow-x-auto border border-slate-800/80">
                {buildLogs.map((log) => (
                  <div key={log.id} className="flex items-start space-x-3">
                    <span className="text-slate-600 select-none">[{log.timestamp}]</span>
                    <span className={log.status === 'SUCCESS' ? 'text-emerald-400 font-semibold' : 'text-amber-400'}>
                      {log.task}
                    </span>
                    <span className="text-slate-400 flex-1">{log.message}</span>
                    <span className="text-slate-600">{log.durationMs}ms</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}
      </main>

      {/* Activation Progress Modal */}
      <ActivationProgressModal
        isOpen={showActivationModal}
        onComplete={() => {
          setShowActivationModal(false);
          setShowWelcomeModal(true);
        }}
        onCancel={() => setShowActivationModal(false)}
      />

      {/* Welcome Modal */}
      <WelcomeModal
        isOpen={showWelcomeModal}
        onBeginSetup={() => {
          setShowWelcomeModal(false);
          setActiveTab('wizard');
        }}
      />

      {/* New Release Modal */}
      {showNewReleaseModal && (
        <div className="fixed inset-0 z-50 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center p-4">
          <motion.div
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            className="bg-slate-900 border border-slate-800 rounded-xl p-6 max-w-md w-full space-y-4 shadow-2xl"
          >
            <h3 className="text-lg font-bold text-white flex items-center gap-2">
              <Layers className="w-5 h-5 text-indigo-400" /> Create System Release Spec [DEMO]
            </h3>

            <form onSubmit={handleCreateRelease} className="space-y-4 text-xs">
              <div>
                <label className="block text-slate-400 mb-1">Version Name (e.g. 2.5.0)</label>
                <input
                  type="text"
                  required
                  value={newVersion}
                  onChange={(e) => setNewVersion(e.target.value)}
                  placeholder="2.5.0"
                  className="w-full bg-slate-950 border border-slate-800 rounded-lg px-3 py-2 text-slate-200 focus:outline-none focus:border-indigo-500"
                />
              </div>

              <div>
                <label className="block text-slate-400 mb-1">Release Channel</label>
                <select
                  value={newChannel}
                  onChange={(e) => setNewChannel(e.target.value as any)}
                  className="w-full bg-slate-950 border border-slate-800 rounded-lg px-3 py-2 text-slate-200 focus:outline-none focus:border-indigo-500"
                >
                  <option value="Stable">Stable</option>
                  <option value="Beta">Beta</option>
                  <option value="Alpha">Alpha</option>
                  <option value="Nightly">Nightly</option>
                </select>
              </div>

              <div>
                <label className="block text-slate-400 mb-1">Changelog (One line per change)</label>
                <textarea
                  rows={3}
                  value={newChanges}
                  onChange={(e) => setNewChanges(e.target.value)}
                  placeholder="Added API 34 support&#10;Fixed build wrapper configuration"
                  className="w-full bg-slate-950 border border-slate-800 rounded-lg px-3 py-2 text-slate-200 focus:outline-none focus:border-indigo-500"
                />
              </div>

              <div className="flex justify-end space-x-2 pt-2">
                <button
                  type="button"
                  onClick={() => setShowNewReleaseModal(false)}
                  className="px-4 py-2 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-300 transition font-medium cursor-pointer"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white transition font-medium cursor-pointer"
                >
                  Publish Release Spec
                </button>
              </div>
            </form>
          </motion.div>
        </div>
      )}
    </div>
  );
}
