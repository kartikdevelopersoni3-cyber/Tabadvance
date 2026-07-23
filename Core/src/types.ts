export type AppView =
  | 'hero'
  | 'intro'
  | 'setup'
  | 'workspace'
  | 'chat'
  | 'memory'
  | 'auth'
  | 'modules'
  | 'settings';

export type AIMode = 'offline' | 'cloud' | 'hybrid';

export type AIProvider = 'gemini' | 'openai' | 'anthropic' | 'local';

export interface SetupState {
  language: string;
  permissions: {
    microphone: boolean;
    storage: boolean;
    notifications: boolean;
    accessibility: boolean;
    camera: boolean;
  };
  aiMode: AIMode;
  cloudConfig: {
    provider: AIProvider;
    apiKey: string;
    localServerUrl: string;
    isConnected: boolean;
    connectionStatus: 'idle' | 'testing' | 'success' | 'error';
    modelName: string;
    useGrounding: boolean;
  };
  voiceSetup: {
    wakeWord: string;
    isEnrolled: boolean;
    pitch: number;
    speed: number;
    tone: string;
  };
  workspacePrefs: {
    theme: 'dark-luxury' | 'midnight-slate' | 'cyber-neon' | 'warm-neutral';
    layout: 'compact' | 'balanced' | 'multi-agent';
    showQuickActions: boolean;
    showMemoryLog: boolean;
    showAudioWave: boolean;
    showSystemMonitor: boolean;
    soundNotifications: boolean;
  };
}

export interface ActivationStep {
  id: number;
  label: string;
  detail: string;
  status: 'pending' | 'active' | 'completed';
}

export interface ChatMessage {
  id: string;
  sender: 'user' | 'roohi' | 'system';
  text: string;
  timestamp: string;
  tokens?: number;
  model?: string;
  provider?: AIProvider;
  groundingUsed?: boolean;
}

export interface MemoryItem {
  id: string;
  key: string;
  value: string;
  category: 'vector' | 'entity' | 'key-value';
  timestamp: string;
  score?: number;
}

export interface UserProfile {
  id: string;
  name: string;
  email: string;
  avatarUrl: string;
  authProvider: 'google' | 'guest' | 'custom';
  isAuthenticated: boolean;
  cloudSyncEnabled: boolean;
  lastSyncedAt: string;
}

export interface ModuleItem {
  code: string;
  name: string;
  status: 'Active' | 'Idle' | 'Standby' | 'Disabled';
  desc: string;
  latencyMs: number;
  memoryUsageMb: number;
  enabled: boolean;
}
