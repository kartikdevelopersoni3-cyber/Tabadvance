export type AppView =
  | 'workspace'
  | 'chat'
  | 'memory'
  | 'modules'
  | 'auth'
  | 'settings'
  | 'updates'
  | 'hero'
  | 'intro'
  | 'setup';

export interface UpdateRelease {
  id: string;
  version: string;
  versionCode: number;
  channel: 'Stable' | 'Beta' | 'Alpha' | 'Nightly';
  releaseDate: string;
  sizeMb: number;
  downloadUrl?: string;
  status: 'Ready' | 'Building' | 'Staged' | 'Deprecated';
  changes: string[];
  minAndroidSdk: number;
  targetAndroidSdk: number;
  checksum: string;
}

export interface BuildLog {
  id: string;
  timestamp: string;
  task: string;
  status: 'SUCCESS' | 'FAILED' | 'IN_PROGRESS';
  message: string;
  durationMs: number;
}

export interface DeviceStats {
  activeInstallations: number;
  updateAdoptionRate: number;
  pendingUpdates: number;
  failedUpdates: number;
}

export type AIProvider = 'gemini' | 'openai' | 'anthropic' | 'ollama' | string;

export type AIMode = 'hybrid' | 'cloud' | 'local' | string;

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

export interface UserProfile {
  id: string;
  name: string;
  email: string;
  avatarUrl?: string;
  authProvider?: string;
  isAuthenticated: boolean;
  cloudSyncEnabled: boolean;
  lastSyncedAt?: string;
}

export interface MemoryItem {
  id: string;
  key: string;
  value: string;
  category: 'key-value' | 'entity' | 'vector' | string;
  timestamp: string;
  score?: number;
}

export interface ModuleItem {
  code: string;
  name: string;
  status: 'Active' | 'Paused' | 'Error' | string;
  desc: string;
  latencyMs: number;
  memoryUsageMb: number;
  enabled: boolean;
}

export interface ActivationStep {
  id: number;
  label: string;
  detail: string;
  status: 'pending' | 'in_progress' | 'completed' | 'error' | string;
}

export interface SetupPermissions {
  microphone: boolean;
  storage: boolean;
  notifications: boolean;
  accessibility: boolean;
  camera: boolean;
}

export interface SetupCloudConfig {
  provider: AIProvider;
  apiKey: string;
  modelName?: string;
  localServerUrl?: string;
  useGrounding?: boolean;
  isConnected: boolean;
  connectionStatus?: 'idle' | 'testing' | 'success' | 'error' | string;
}

export interface SetupVoiceConfig {
  wakeWord: string;
  isEnrolled: boolean;
  pitch: number;
  speed: number;
  tone: string;
}

export interface SetupWorkspacePrefs {
  theme: 'dark-luxury' | 'light-slate' | 'cyberpunk' | string;
  layout: 'balanced' | 'compact' | 'expanded' | string;
  showQuickActions: boolean;
  showMemoryLog: boolean;
  showAudioWave: boolean;
  showSystemMonitor: boolean;
  soundNotifications: boolean;
}

export interface SetupState {
  language: string;
  permissions: SetupPermissions;
  aiMode: AIMode;
  cloudConfig: SetupCloudConfig;
  voiceSetup: SetupVoiceConfig;
  workspacePrefs: SetupWorkspacePrefs;
}
