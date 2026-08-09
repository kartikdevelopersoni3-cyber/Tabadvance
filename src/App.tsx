import React, { useState } from 'react';
import { HeroSection } from './components/HeroSection';
import { IntroSequence } from './components/IntroSequence';
import { ActivationProgressModal } from './components/ActivationProgressModal';
import { WelcomeModal } from './components/WelcomeModal';
import { SetupWizard } from './components/SetupWizard';
import { WorkspaceOS } from './components/WorkspaceOS';
import { AIChatView } from './components/AIChatView';
import { MemorySystemView } from './components/MemorySystemView';
import { SettingsPanelView } from './components/SettingsPanelView';
import { ModuleManagerView } from './components/ModuleManagerView';
import { UserProfileView } from './components/UserProfileView';
import { PwaInstallBanner } from './components/PwaInstallBanner';
import { AppView, SetupState } from './types';
import {
  Layers,
  Download,
  Bot,
  Database,
  Cpu,
  Settings,
  User,
  ShieldCheck,
  FileArchive,
  Layout,
  Compass,
  SlidersHorizontal
} from 'lucide-react';

export default function App() {
  const [currentView, setCurrentView] = useState<AppView>('workspace');
  const [isActivating, setIsActivating] = useState(false);
  const [showWelcome, setShowWelcome] = useState(false);

  const [setupState, setSetupState] = useState<SetupState>({
    language: 'en',
    permissions: {
      microphone: true,
      storage: true,
      notifications: true,
      accessibility: false,
      camera: false
    },
    aiMode: 'hybrid',
    cloudConfig: {
      provider: 'gemini',
      apiKey: '',
      localServerUrl: 'http://localhost:11434',
      isConnected: true,
      connectionStatus: 'success',
      modelName: 'gemini-2.5-flash',
      useGrounding: true
    },
    voiceSetup: {
      wakeWord: 'Hey Roohi',
      isEnrolled: true,
      pitch: 1.0,
      speed: 1.0,
      tone: 'Balanced Warm'
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
  });

  const handleStartActivation = () => {
    setIsActivating(true);
  };

  const handleActivationComplete = () => {
    setIsActivating(false);
    setShowWelcome(true);
  };

  const handleBeginSetup = () => {
    setShowWelcome(false);
    setCurrentView('setup');
  };

  const handleWizardComplete = (finalConfig: SetupState) => {
    setSetupState(finalConfig);
    setCurrentView('workspace');
  };

  return (
    <div className="min-h-screen bg-[#06080c] text-[#c9d1d9] font-sans flex flex-col justify-between selection:bg-blue-600 selection:text-white">
      {/* Top Header Navigation Bar */}
      <header className="h-16 border-b border-[#30363d] bg-[#0d1117]/90 backdrop-blur-md sticky top-0 z-40 px-4 sm:px-6 flex items-center justify-between">
        <div className="flex items-center gap-3">
          <div
            onClick={() => setCurrentView('workspace')}
            className="flex items-center gap-2 text-blue-400 font-bold cursor-pointer hover:opacity-90 transition-opacity"
          >
            <div className="w-8 h-8 rounded-xl bg-gradient-to-tr from-blue-500 to-indigo-600 p-0.5 flex items-center justify-center">
              <div className="w-full h-full bg-[#0d1117] rounded-[10px] flex items-center justify-center">
                <Layers className="w-4 h-4 text-blue-400" />
              </div>
            </div>
            <span className="text-white text-base tracking-tight font-extrabold">Roohi.OS</span>
          </div>

          <div className="hidden md:flex items-center gap-2 text-xs text-[#8b949e] font-mono border-l border-[#30363d] pl-3">
            <span>Cloud Web App & PWA</span>
            <span className="text-emerald-400 font-bold bg-emerald-950/60 px-2 py-0.5 rounded border border-emerald-800/40">
              v1.0.4 Cloud
            </span>
          </div>
        </div>

        {/* View Selection Tabs */}
        <div className="flex items-center gap-1 overflow-x-auto max-w-[60vw] sm:max-w-none">
          <button
            onClick={() => setCurrentView('workspace')}
            className={`px-3 py-1.5 rounded-xl text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer ${
              currentView === 'workspace'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'text-[#8b949e] hover:text-white hover:bg-[#161b22]'
            }`}
          >
            <Layout className="w-3.5 h-3.5" /> Workspace
          </button>

          <button
            onClick={() => setCurrentView('chat')}
            className={`px-3 py-1.5 rounded-xl text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer ${
              currentView === 'chat'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'text-[#8b949e] hover:text-white hover:bg-[#161b22]'
            }`}
          >
            <Bot className="w-3.5 h-3.5" /> AI Chat
          </button>

          <button
            onClick={() => setCurrentView('memory')}
            className={`px-3 py-1.5 rounded-xl text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer ${
              currentView === 'memory'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'text-[#8b949e] hover:text-white hover:bg-[#161b22]'
            }`}
          >
            <Database className="w-3.5 h-3.5" /> Memory
          </button>

          <button
            onClick={() => setCurrentView('modules')}
            className={`px-3 py-1.5 rounded-xl text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer ${
              currentView === 'modules'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'text-[#8b949e] hover:text-white hover:bg-[#161b22]'
            }`}
          >
            <Cpu className="w-3.5 h-3.5" /> Modules
          </button>

          <button
            onClick={() => setCurrentView('auth')}
            className={`px-3 py-1.5 rounded-xl text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer ${
              currentView === 'auth'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'text-[#8b949e] hover:text-white hover:bg-[#161b22]'
            }`}
          >
            <User className="w-3.5 h-3.5" /> Account
          </button>

          <button
            onClick={() => setCurrentView('settings')}
            className={`px-3 py-1.5 rounded-xl text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer ${
              currentView === 'settings'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'text-[#8b949e] hover:text-white hover:bg-[#161b22]'
            }`}
          >
            <Settings className="w-3.5 h-3.5" /> Settings
          </button>

          <button
            onClick={() => setCurrentView('hero')}
            className={`px-3 py-1.5 rounded-xl text-xs font-bold transition-all cursor-pointer ${
              currentView === 'hero'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'text-[#8b949e] hover:text-white hover:bg-[#161b22]'
            }`}
          >
            Overview
          </button>

          {/* Download APK Action */}
          <a
            href="/roohi_apk.zip"
            download="roohi_apk.zip"
            className="ml-2 px-3.5 py-1.5 rounded-xl bg-gradient-to-r from-emerald-500 via-teal-500 to-blue-600 hover:from-emerald-400 hover:to-blue-500 text-white font-extrabold text-xs shadow-lg flex items-center gap-1.5 transition-all cursor-pointer shrink-0"
          >
            <Download className="w-3.5 h-3.5" /> Download APK (ZIP)
          </a>

          <button
            onClick={handleStartActivation}
            className="px-3 py-1.5 rounded-xl bg-[#161b22] hover:bg-[#21262d] border border-[#30363d] text-white font-bold text-xs flex items-center gap-1.5 transition-all cursor-pointer shrink-0"
          >
            Activate
          </button>
        </div>
      </header>

      {/* Main Body Content Container */}
      <main className="flex-1 w-full max-w-7xl mx-auto px-4 sm:px-6 py-4 flex flex-col justify-start">
        {/* PWA Banner */}
        <PwaInstallBanner />

        {currentView === 'hero' && (
          <HeroSection
            onStartActivation={handleStartActivation}
            onOpenIntro={() => setCurrentView('intro')}
          />
        )}

        {currentView === 'intro' && (
          <IntroSequence
            onStartActivation={handleStartActivation}
            onSkipToHero={() => setCurrentView('hero')}
          />
        )}

        {currentView === 'setup' && (
          <SetupWizard onComplete={handleWizardComplete} />
        )}

        {currentView === 'workspace' && (
          <WorkspaceOS
            setupConfig={setupState}
            onOpenSettings={() => setCurrentView('settings')}
          />
        )}

        {currentView === 'chat' && (
          <AIChatView
            setupConfig={setupState}
            onOpenSettings={() => setCurrentView('settings')}
          />
        )}

        {currentView === 'memory' && <MemorySystemView />}

        {currentView === 'modules' && <ModuleManagerView />}

        {currentView === 'auth' && <UserProfileView />}

        {currentView === 'settings' && (
          <SettingsPanelView
            setupConfig={setupState}
            onSaveConfig={(updated) => setSetupState(updated)}
          />
        )}
      </main>

      {/* Active Modals */}
      <ActivationProgressModal
        isOpen={isActivating}
        onComplete={handleActivationComplete}
        onCancel={() => setIsActivating(false)}
      />

      <WelcomeModal
        isOpen={showWelcome}
        onBeginSetup={handleBeginSetup}
      />

      {/* Footer Status Bar */}
      <footer className="border-t border-[#30363d] bg-[#0d1117] py-4 px-4 sm:px-6 text-xs text-[#8b949e] flex flex-col sm:flex-row items-center justify-between gap-3">
        <div className="flex flex-wrap items-center gap-3 font-mono text-[11px]">
          <span className="flex items-center gap-1 text-emerald-400 font-bold">
            <ShieldCheck className="w-3.5 h-3.5" /> Roohi OS Layer Active
          </span>
          <span>•</span>
          <a
            href="/roohi_apk.zip"
            download="roohi_apk.zip"
            className="text-emerald-400 hover:underline flex items-center gap-1 font-bold"
          >
            <Download className="w-3.5 h-3.5 text-emerald-400" /> roohi_apk.zip (Full Master Package)
          </a>
          <span>•</span>
          <a
            href="/Roohi_VoltBuilder_Package.zip"
            download="Roohi_VoltBuilder_Package.zip"
            className="text-[#58a6ff] hover:underline flex items-center gap-1 font-bold"
          >
            <FileArchive className="w-3.5 h-3.5 text-blue-400" /> Roohi_VoltBuilder_Package.zip
          </a>
        </div>

        <div className="text-[11px] font-mono text-[#484f58]">
          Module AI Assistant OS Layer • Production Cloud Web App & PWA
        </div>
      </footer>
    </div>
  );
}
