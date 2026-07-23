import React, { useState } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import {
  Globe,
  ShieldAlert,
  Cpu,
  Key,
  Mic,
  Palette,
  CheckCircle2,
  ChevronRight,
  ChevronLeft,
  Sparkles,
  Volume2,
  Radio,
  Sliders,
  Check,
  X,
  Server,
  Zap,
  Layout,
  Bell
} from 'lucide-react';
import { SetupState, AIProvider, AIMode } from '../types';

interface SetupWizardProps {
  onComplete: (config: SetupState) => void;
}

const LANGUAGES = [
  { code: 'en', name: 'English (US)', flag: '🇺🇸' },
  { code: 'es', name: 'Spanish (Español)', flag: '🇪🇸' },
  { code: 'hi', name: 'Hindi (हिन्दी)', flag: '🇮🇳' },
  { code: 'fr', name: 'French (Français)', flag: '🇫🇷' },
  { code: 'de', name: 'German (Deutsch)', flag: '🇩🇪' },
  { code: 'ja', name: 'Japanese (日本語)', flag: '🇯🇵' },
  { code: 'zh', name: 'Mandarin (中文)', flag: '🇨🇳' }
];

export const SetupWizard: React.FC<SetupWizardProps> = ({ onComplete }) => {
  const [currentStep, setCurrentStep] = useState<number>(1);
  const [isRecordingVoice, setIsRecordingVoice] = useState(false);
  const [voiceRecordedSuccess, setVoiceRecordedSuccess] = useState(false);

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
      connectionStatus: 'success'
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

  const handleTestConnection = () => {
    setSetupState(prev => ({
      ...prev,
      cloudConfig: { ...prev.cloudConfig, connectionStatus: 'testing' }
    }));

    setTimeout(() => {
      setSetupState(prev => ({
        ...prev,
        cloudConfig: {
          ...prev.cloudConfig,
          isConnected: true,
          connectionStatus: 'success'
        }
      }));
    }, 1000);
  };

  const handleRecordVoice = () => {
    setIsRecordingVoice(true);
    setTimeout(() => {
      setIsRecordingVoice(false);
      setVoiceRecordedSuccess(true);
      setSetupState(prev => ({
        ...prev,
        voiceSetup: { ...prev.voiceSetup, isEnrolled: true }
      }));
    }, 2000);
  };

  const handleNext = () => {
    if (currentStep < 7) {
      setCurrentStep(prev => prev + 1);
    } else {
      onComplete(setupState);
    }
  };

  const handlePrev = () => {
    if (currentStep > 1) {
      setCurrentStep(prev => prev - 1);
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto my-6 bg-[#0d1117] border border-[#30363d] rounded-2xl shadow-2xl overflow-hidden text-white flex flex-col min-h-[640px]">
      {/* Wizard Header Progress Bar */}
      <div className="bg-[#161b22] border-b border-[#30363d] p-6">
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center gap-2">
            <span className="w-2.5 h-2.5 rounded-full bg-blue-500 animate-pulse" />
            <span className="text-xs font-mono font-bold tracking-wider text-white uppercase">
              Roohi Setup Guide • Step {currentStep} of 7
            </span>
          </div>
          <span className="text-xs text-[#8b949e]">
            {currentStep === 1 && "1. Choose Language"}
            {currentStep === 2 && "2. Grant Permissions"}
            {currentStep === 3 && "3. Select AI Mode"}
            {currentStep === 4 && "4. Cloud AI Configuration"}
            {currentStep === 5 && "5. Voice & Speaker Enrollment"}
            {currentStep === 6 && "6. Workspace Preferences"}
            {currentStep === 7 && "7. Complete Setup"}
          </span>
        </div>

        {/* Step Progress Indicators */}
        <div className="grid grid-cols-7 gap-1.5">
          {[1, 2, 3, 4, 5, 6, 7].map(stepNum => (
            <div
              key={stepNum}
              className={`h-1.5 rounded-full transition-all ${
                stepNum < currentStep
                  ? 'bg-[#7ee787]'
                  : stepNum === currentStep
                  ? 'bg-[#58a6ff]'
                  : 'bg-[#30363d]'
              }`}
            />
          ))}
        </div>
      </div>

      {/* Step Body */}
      <div className="flex-1 p-6 sm:p-10 flex flex-col justify-between">
        <AnimatePresence mode="wait">
          {/* STEP 1: Choose Language */}
          {currentStep === 1 && (
            <motion.div
              key="step1"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              className="space-y-6"
            >
              <div className="flex items-center gap-3">
                <div className="p-3 bg-blue-500/10 border border-blue-500/30 rounded-xl text-blue-400">
                  <Globe className="w-6 h-6" />
                </div>
                <div>
                  <h3 className="text-xl font-bold">Step 1: Choose Your Language</h3>
                  <p className="text-xs text-[#8b949e]">Select your primary interaction language for voice and text processing.</p>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 pt-4">
                {LANGUAGES.map(lang => (
                  <button
                    key={lang.code}
                    onClick={() => setSetupState(prev => ({ ...prev, language: lang.code }))}
                    className={`p-4 rounded-xl border text-left flex items-center justify-between transition-all cursor-pointer ${
                      setupState.language === lang.code
                        ? 'bg-blue-950/40 border-blue-500 text-white shadow-lg'
                        : 'bg-[#161b22] border-[#30363d] hover:border-[#8b949e] text-[#c9d1d9]'
                    }`}
                  >
                    <div className="flex items-center gap-3">
                      <span className="text-2xl">{lang.flag}</span>
                      <span className="font-semibold text-sm">{lang.name}</span>
                    </div>
                    {setupState.language === lang.code && (
                      <CheckCircle2 className="w-5 h-5 text-blue-400" />
                    )}
                  </button>
                ))}
              </div>
            </motion.div>
          )}

          {/* STEP 2: Permissions */}
          {currentStep === 2 && (
            <motion.div
              key="step2"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              className="space-y-6"
            >
              <div className="flex items-center gap-3">
                <div className="p-3 bg-emerald-500/10 border border-emerald-500/30 rounded-xl text-emerald-400">
                  <ShieldAlert className="w-6 h-6" />
                </div>
                <div>
                  <h3 className="text-xl font-bold">Step 2: Grant System Permissions</h3>
                  <p className="text-xs text-[#8b949e]">Configure hardware access for voice, automation, and background services.</p>
                </div>
              </div>

              <div className="space-y-3 pt-2">
                {[
                  { key: 'microphone', label: 'Microphone', detail: 'Required for "Hey Roohi" wake word & voice input.', req: true },
                  { key: 'storage', label: 'Storage & Memory', detail: 'Required to save local vector database & knowledge logs.', req: true },
                  { key: 'notifications', label: 'Notifications', detail: 'Required for proactive assistant alerts & automation updates.', req: true },
                  { key: 'accessibility', label: 'Accessibility Service', detail: 'Optional: Enables UI automation and tablet action gestures.', req: false },
                  { key: 'camera', label: 'Camera', detail: 'Optional: Enables real-time vision analysis (Module E).', req: false }
                ].map(perm => {
                  const isGranted = setupState.permissions[perm.key as keyof typeof setupState.permissions];
                  return (
                    <div
                      key={perm.key}
                      className="p-4 rounded-xl bg-[#161b22] border border-[#30363d] flex items-center justify-between"
                    >
                      <div>
                        <div className="flex items-center gap-2">
                          <span className="font-bold text-sm text-white">{perm.label}</span>
                          {perm.req ? (
                            <span className="px-2 py-0.5 rounded bg-red-950/60 text-red-400 border border-red-800/40 text-[9px] font-bold uppercase">Required</span>
                          ) : (
                            <span className="px-2 py-0.5 rounded bg-[#21262d] text-[#8b949e] border border-[#30363d] text-[9px] font-bold uppercase">Optional</span>
                          )}
                        </div>
                        <p className="text-xs text-[#8b949e] mt-0.5">{perm.detail}</p>
                      </div>

                      <button
                        onClick={() =>
                          setSetupState(prev => ({
                            ...prev,
                            permissions: {
                              ...prev.permissions,
                              [perm.key]: !isGranted
                            }
                          }))
                        }
                        className={`px-4 py-2 rounded-xl text-xs font-bold transition-all cursor-pointer ${
                          isGranted
                            ? 'bg-emerald-900/40 text-emerald-300 border border-emerald-600/50'
                            : 'bg-[#21262d] text-[#8b949e] border border-[#30363d] hover:text-white'
                        }`}
                      >
                        {isGranted ? 'Granted ✓' : 'Grant'}
                      </button>
                    </div>
                  );
                })}
              </div>
            </motion.div>
          )}

          {/* STEP 3: Choose AI Mode */}
          {currentStep === 3 && (
            <motion.div
              key="step3"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              className="space-y-6"
            >
              <div className="flex items-center gap-3">
                <div className="p-3 bg-purple-500/10 border border-purple-500/30 rounded-xl text-purple-400">
                  <Cpu className="w-6 h-6" />
                </div>
                <div>
                  <h3 className="text-xl font-bold">Step 3: Choose AI Mode</h3>
                  <p className="text-xs text-[#8b949e]">Select how intelligence is processed on your tablet.</p>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 pt-4">
                {[
                  {
                    id: 'offline',
                    title: 'Offline AI',
                    badge: '100% Private',
                    desc: 'Runs entirely on tablet NPU/CPU. Zero internet required, maximum privacy.',
                    icon: Server
                  },
                  {
                    id: 'cloud',
                    title: 'Cloud AI',
                    badge: 'Maximum Speed',
                    desc: 'Uses high-speed Gemini or API models for deep reasoning and fast responses.',
                    icon: Zap
                  },
                  {
                    id: 'hybrid',
                    title: 'Hybrid AI',
                    badge: 'Recommended',
                    desc: 'Combines local offline memory with cloud LLM power when connected to internet.',
                    icon: Sparkles
                  }
                ].map(mode => (
                  <button
                    key={mode.id}
                    onClick={() => setSetupState(prev => ({ ...prev, aiMode: mode.id as AIMode }))}
                    className={`p-5 rounded-2xl border text-left flex flex-col justify-between transition-all cursor-pointer ${
                      setupState.aiMode === mode.id
                        ? 'bg-purple-950/30 border-purple-500 shadow-xl'
                        : 'bg-[#161b22] border-[#30363d] hover:border-[#8b949e]'
                    }`}
                  >
                    <div>
                      <div className="flex items-center justify-between mb-3">
                        <mode.icon className={`w-6 h-6 ${setupState.aiMode === mode.id ? 'text-purple-400' : 'text-[#8b949e]'}`} />
                        <span className="px-2 py-0.5 rounded bg-[#21262d] text-purple-300 border border-purple-800/40 text-[10px] font-bold">
                          {mode.badge}
                        </span>
                      </div>
                      <h4 className="font-bold text-base text-white mb-1">{mode.title}</h4>
                      <p className="text-xs text-[#8b949e] leading-relaxed">{mode.desc}</p>
                    </div>

                    <div className="mt-4 pt-3 border-t border-[#30363d] flex items-center justify-between text-xs">
                      <span className="text-[#8b949e]">Status</span>
                      <span className={setupState.aiMode === mode.id ? 'text-purple-400 font-bold' : 'text-[#484f58]'}>
                        {setupState.aiMode === mode.id ? 'Selected ✓' : 'Select'}
                      </span>
                    </div>
                  </button>
                ))}
              </div>
            </motion.div>
          )}

          {/* STEP 4: Cloud AI Configuration */}
          {currentStep === 4 && (
            <motion.div
              key="step4"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              className="space-y-6"
            >
              <div className="flex items-center gap-3">
                <div className="p-3 bg-amber-500/10 border border-amber-500/30 rounded-xl text-amber-400">
                  <Key className="w-6 h-6" />
                </div>
                <div>
                  <h3 className="text-xl font-bold">Step 4: Cloud AI Configuration</h3>
                  <p className="text-xs text-[#8b949e]">Select your model provider and verify API connection credentials.</p>
                </div>
              </div>

              {/* Provider Selection Tabs */}
              <div className="grid grid-cols-2 sm:grid-cols-4 gap-2 pt-2">
                {[
                  { id: 'gemini', label: 'Gemini API' },
                  { id: 'openai', label: 'OpenAI API' },
                  { id: 'anthropic', label: 'Anthropic API' },
                  { id: 'local', label: 'Local Server' }
                ].map(prov => (
                  <button
                    key={prov.id}
                    onClick={() =>
                      setSetupState(prev => ({
                        ...prev,
                        cloudConfig: { ...prev.cloudConfig, provider: prov.id as AIProvider }
                      }))
                    }
                    className={`py-2.5 px-3 rounded-xl border text-xs font-bold transition-all cursor-pointer ${
                      setupState.cloudConfig.provider === prov.id
                        ? 'bg-amber-950/40 border-amber-500 text-amber-300'
                        : 'bg-[#161b22] border-[#30363d] text-[#8b949e] hover:text-white'
                    }`}
                  >
                    {prov.label}
                  </button>
                ))}
              </div>

              {/* API Key / Server URL Input */}
              <div className="p-5 rounded-2xl bg-[#161b22] border border-[#30363d] space-y-4">
                {setupState.cloudConfig.provider === 'local' ? (
                  <div>
                    <label className="text-xs font-bold text-[#8b949e] uppercase block mb-1">Local Ollama / Model Server URL</label>
                    <input
                      type="text"
                      value={setupState.cloudConfig.localServerUrl}
                      onChange={e =>
                        setSetupState(prev => ({
                          ...prev,
                          cloudConfig: { ...prev.cloudConfig, localServerUrl: e.target.value }
                        }))
                      }
                      className="w-full px-4 py-2.5 bg-[#0d1117] border border-[#30363d] rounded-xl text-xs font-mono text-white focus:outline-none focus:border-amber-500"
                      placeholder="http://localhost:11434"
                    />
                  </div>
                ) : (
                  <div>
                    <label className="text-xs font-bold text-[#8b949e] uppercase block mb-1">
                      {setupState.cloudConfig.provider.toUpperCase()} API Key
                    </label>
                    <input
                      type="password"
                      value={setupState.cloudConfig.apiKey}
                      onChange={e =>
                        setSetupState(prev => ({
                          ...prev,
                          cloudConfig: { ...prev.cloudConfig, apiKey: e.target.value }
                        }))
                      }
                      className="w-full px-4 py-2.5 bg-[#0d1117] border border-[#30363d] rounded-xl text-xs font-mono text-white focus:outline-none focus:border-amber-500"
                      placeholder="Enter secret API key or leave blank to use system environment..."
                    />
                  </div>
                )}

                {/* Connection Test Controls */}
                <div className="flex items-center justify-between pt-2">
                  <div className="flex items-center gap-2">
                    <span
                      className={`w-2.5 h-2.5 rounded-full ${
                        setupState.cloudConfig.connectionStatus === 'success'
                          ? 'bg-emerald-400 animate-pulse'
                          : setupState.cloudConfig.connectionStatus === 'testing'
                          ? 'bg-amber-400 animate-spin'
                          : 'bg-red-500'
                      }`}
                    />
                    <span className="text-xs font-mono text-[#8b949e]">
                      Status:{' '}
                      <span className="text-white font-bold uppercase">
                        {setupState.cloudConfig.connectionStatus}
                      </span>
                    </span>
                  </div>

                  <button
                    onClick={handleTestConnection}
                    className="px-4 py-2 bg-[#21262d] hover:bg-[#30363d] border border-[#30363d] text-white text-xs font-bold rounded-xl transition-all cursor-pointer"
                  >
                    Test Connection
                  </button>
                </div>
              </div>
            </motion.div>
          )}

          {/* STEP 5: Voice Setup */}
          {currentStep === 5 && (
            <motion.div
              key="step5"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              className="space-y-6"
            >
              <div className="flex items-center gap-3">
                <div className="p-3 bg-rose-500/10 border border-rose-500/30 rounded-xl text-rose-400">
                  <Mic className="w-6 h-6" />
                </div>
                <div>
                  <h3 className="text-xl font-bold">Step 5: Voice Setup & Enrollment</h3>
                  <p className="text-xs text-[#8b949e]">Customize wake word detection and enroll your biometric speaker profile.</p>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                {/* Wake Word Card */}
                <div className="p-5 rounded-2xl bg-[#161b22] border border-[#30363d] space-y-3">
                  <label className="text-xs font-bold text-[#8b949e] uppercase block">Wake Word Keyword</label>
                  {['Hey Roohi', 'Roohi Assistant', 'Computer'].map(word => (
                    <button
                      key={word}
                      onClick={() =>
                        setSetupState(prev => ({
                          ...prev,
                          voiceSetup: { ...prev.voiceSetup, wakeWord: word }
                        }))
                      }
                      className={`w-full p-3 rounded-xl border text-left text-xs font-bold flex items-center justify-between transition-all cursor-pointer ${
                        setupState.voiceSetup.wakeWord === word
                          ? 'bg-rose-950/40 border-rose-500 text-rose-300'
                          : 'bg-[#0d1117] border-[#30363d] text-[#8b949e]'
                      }`}
                    >
                      <span>"{word}"</span>
                      {setupState.voiceSetup.wakeWord === word && <CheckCircle2 className="w-4 h-4 text-rose-400" />}
                    </button>
                  ))}
                </div>

                {/* Speaker Enrollment Card */}
                <div className="p-5 rounded-2xl bg-[#161b22] border border-[#30363d] flex flex-col justify-between">
                  <div>
                    <label className="text-xs font-bold text-[#8b949e] uppercase block mb-1">Biometric Speaker Profile</label>
                    <p className="text-xs text-[#8b949e] leading-relaxed mb-4">
                      Record a 2-second voice phrase so Roohi exclusively responds to your specific voiceprint.
                    </p>
                  </div>

                  <div className="space-y-3">
                    <button
                      onClick={handleRecordVoice}
                      disabled={isRecordingVoice}
                      className={`w-full py-3 rounded-xl text-xs font-bold flex items-center justify-center gap-2 border transition-all cursor-pointer ${
                        isRecordingVoice
                          ? 'bg-rose-600 text-white border-rose-500 animate-pulse'
                          : voiceRecordedSuccess
                          ? 'bg-emerald-950/40 border-emerald-500 text-emerald-300'
                          : 'bg-[#21262d] border-[#30363d] text-white hover:bg-[#30363d]'
                      }`}
                    >
                      <Mic className="w-4 h-4" />
                      {isRecordingVoice ? 'Listening... Speak "Hey Roohi"' : voiceRecordedSuccess ? 'Voice Enrolled ✓' : 'Record Voice Sample'}
                    </button>

                    <div className="text-[11px] font-mono text-center text-[#8b949e]">
                      Status: {voiceRecordedSuccess ? <span className="text-emerald-400 font-bold">Speaker Profile Active</span> : 'Not Enrolled'}
                    </div>
                  </div>
                </div>
              </div>
            </motion.div>
          )}

          {/* STEP 6: Workspace Preferences */}
          {currentStep === 6 && (
            <motion.div
              key="step6"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              className="space-y-6"
            >
              <div className="flex items-center gap-3">
                <div className="p-3 bg-cyan-500/10 border border-cyan-500/30 rounded-xl text-cyan-400">
                  <Palette className="w-6 h-6" />
                </div>
                <div>
                  <h3 className="text-xl font-bold">Step 6: Workspace Preferences</h3>
                  <p className="text-xs text-[#8b949e]">Tailor visual theme, widget layout, and notification behavior.</p>
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                {/* Theme Selector */}
                <div className="p-5 rounded-2xl bg-[#161b22] border border-[#30363d] space-y-3">
                  <label className="text-xs font-bold text-[#8b949e] uppercase block">Visual Theme</label>
                  <div className="grid grid-cols-2 gap-2">
                    {[
                      { id: 'dark-luxury', label: 'Dark Luxury' },
                      { id: 'midnight-slate', label: 'Midnight Slate' },
                      { id: 'cyber-neon', label: 'Cyber Neon' },
                      { id: 'warm-neutral', label: 'Warm Neutral' }
                    ].map(t => (
                      <button
                        key={t.id}
                        onClick={() =>
                          setSetupState(prev => ({
                            ...prev,
                            workspacePrefs: { ...prev.workspacePrefs, theme: t.id as any }
                          }))
                        }
                        className={`p-2.5 rounded-xl border text-xs font-bold transition-all cursor-pointer ${
                          setupState.workspacePrefs.theme === t.id
                            ? 'bg-cyan-950/40 border-cyan-500 text-cyan-300'
                            : 'bg-[#0d1117] border-[#30363d] text-[#8b949e]'
                        }`}
                      >
                        {t.label}
                      </button>
                    ))}
                  </div>
                </div>

                {/* Widgets Toggle */}
                <div className="p-5 rounded-2xl bg-[#161b22] border border-[#30363d] space-y-2.5">
                  <label className="text-xs font-bold text-[#8b949e] uppercase block mb-1">Active Dashboard Widgets</label>
                  {[
                    { key: 'showQuickActions', label: 'Quick Action Shortcuts' },
                    { key: 'showMemoryLog', label: 'Vector Memory Stream' },
                    { key: 'showAudioWave', label: 'Live Audio Visualizer' },
                    { key: 'showSystemMonitor', label: 'NPU/CPU Telemetry' }
                  ].map(w => {
                    const active = setupState.workspacePrefs[w.key as keyof typeof setupState.workspacePrefs];
                    return (
                      <button
                        key={w.key}
                        onClick={() =>
                          setSetupState(prev => ({
                            ...prev,
                            workspacePrefs: {
                              ...prev.workspacePrefs,
                              [w.key]: !active
                            }
                          }))
                        }
                        className="w-full p-2.5 rounded-xl bg-[#0d1117] border border-[#30363d] flex items-center justify-between text-xs cursor-pointer"
                      >
                        <span className="text-[#c9d1d9]">{w.label}</span>
                        <span className={`font-mono text-[10px] ${active ? 'text-cyan-400 font-bold' : 'text-[#484f58]'}`}>
                          {active ? 'ON' : 'OFF'}
                        </span>
                      </button>
                    );
                  })}
                </div>
              </div>
            </motion.div>
          )}

          {/* STEP 7: Finish */}
          {currentStep === 7 && (
            <motion.div
              key="step7"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              className="space-y-6 text-center py-4"
            >
              <div className="w-16 h-16 rounded-2xl bg-gradient-to-tr from-emerald-500 to-teal-600 p-0.5 mx-auto shadow-2xl flex items-center justify-center">
                <div className="w-full h-full bg-[#0d1117] rounded-[14px] flex items-center justify-center text-emerald-400">
                  <CheckCircle2 className="w-8 h-8" />
                </div>
              </div>

              <div>
                <h3 className="text-2xl font-bold text-white mb-2">Roohi is now ready.</h3>
                <p className="text-xs text-[#8b949e] max-w-md mx-auto leading-relaxed">
                  Your AI Assistant OS Layer preferences have been saved and applied to your workspace environment.
                </p>
              </div>

              {/* Final Summary Card */}
              <div className="max-w-md mx-auto bg-[#161b22] border border-[#30363d] rounded-2xl p-5 text-left text-xs space-y-2 text-[#c9d1d9]">
                <div className="flex justify-between border-b border-[#30363d] pb-2">
                  <span className="text-[#8b949e]">Language:</span>
                  <span className="font-bold text-white">{setupState.language.toUpperCase()}</span>
                </div>
                <div className="flex justify-between border-b border-[#30363d] pb-2">
                  <span className="text-[#8b949e]">AI Mode:</span>
                  <span className="font-bold text-purple-300 capitalize">{setupState.aiMode} AI</span>
                </div>
                <div className="flex justify-between border-b border-[#30363d] pb-2">
                  <span className="text-[#8b949e]">Wake Word:</span>
                  <span className="font-bold text-rose-300">"{setupState.voiceSetup.wakeWord}"</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-[#8b949e]">Workspace Theme:</span>
                  <span className="font-bold text-cyan-300 capitalize">{setupState.workspacePrefs.theme}</span>
                </div>
              </div>
            </motion.div>
          )}
        </AnimatePresence>

        {/* Wizard Footer Navigation */}
        <div className="flex items-center justify-between pt-6 border-t border-[#30363d]">
          {currentStep > 1 ? (
            <button
              onClick={handlePrev}
              className="px-5 py-2.5 rounded-xl border border-[#30363d] bg-[#161b22] hover:bg-[#21262d] text-white text-xs font-bold flex items-center gap-1.5 transition-all cursor-pointer"
            >
              <ChevronLeft className="w-4 h-4" /> Previous
            </button>
          ) : <div />}

          <button
            onClick={handleNext}
            className={`px-8 py-3 rounded-xl text-xs font-bold flex items-center gap-2 transition-all cursor-pointer shadow-xl ${
              currentStep === 7
                ? 'bg-gradient-to-r from-emerald-500 to-teal-600 hover:from-emerald-400 hover:to-teal-500 text-white'
                : 'bg-blue-600 hover:bg-blue-500 text-white'
            }`}
          >
            {currentStep === 7 ? (
              <>
                Launch Workspace <Sparkles className="w-4 h-4" />
              </>
            ) : (
              <>
                Continue <ChevronRight className="w-4 h-4" />
              </>
            )}
          </button>
        </div>
      </div>
    </div>
  );
};
