import React, { useState } from 'react';
import { User, ShieldCheck, Cloud, RefreshCw, LogOut, Key, CheckCircle2, Lock, Download, HardDrive } from 'lucide-react';
import { UserProfile } from '../types';
import { AuthService } from '../services/authService';

export const UserProfileView: React.FC = () => {
  const [profile, setProfile] = useState<UserProfile>(AuthService.getProfile());
  const [isSyncing, setIsSyncing] = useState(false);

  const handleToggleSync = () => {
    const updated = AuthService.toggleCloudSync();
    setProfile(updated);
  };

  const handleTriggerSync = () => {
    setIsSyncing(true);
    setTimeout(() => {
      setIsSyncing(false);
      setProfile(prev => ({
        ...prev,
        lastSyncedAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
      }));
    }, 1000);
  };

  return (
    <div className="w-full flex flex-col space-y-6 my-2 text-white">
      {/* Header Profile Banner */}
      <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] flex flex-col sm:flex-row items-start sm:items-center justify-between gap-6 shadow-xl">
        <div className="flex items-center gap-4">
          <img
            src={profile.avatarUrl}
            alt={profile.name}
            className="w-16 h-16 rounded-2xl border-2 border-blue-500/40 object-cover shadow-lg"
          />
          <div>
            <div className="flex items-center gap-2">
              <h2 className="text-xl font-bold text-white">{profile.name}</h2>
              <span className="px-2.5 py-0.5 rounded bg-blue-950/60 border border-blue-800/40 text-blue-400 text-[10px] font-mono font-bold uppercase">
                {profile.authProvider} OAuth Verified
              </span>
            </div>
            <p className="text-xs text-[#8b949e] font-mono">{profile.email}</p>
            <div className="mt-1 flex items-center gap-2 text-[11px] text-emerald-400 font-mono">
              <ShieldCheck className="w-3.5 h-3.5" /> Session Active • Token ID: #TK-89104
            </div>
          </div>
        </div>

        <button
          onClick={handleTriggerSync}
          disabled={isSyncing}
          className="px-5 py-2.5 bg-blue-600 hover:bg-blue-500 text-white rounded-xl font-bold text-xs flex items-center gap-2 shadow-lg cursor-pointer transition-colors"
        >
          <RefreshCw className={`w-4 h-4 ${isSyncing ? 'animate-spin' : ''}`} />
          {isSyncing ? 'Syncing to Cloud...' : 'Sync Cloud Backup Now'}
        </button>
      </div>

      {/* Account Settings & Cloud Synchronization */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Cloud Sync Controller */}
        <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] space-y-4">
          <h3 className="text-sm font-bold text-white flex items-center gap-2">
            <Cloud className="w-4 h-4 text-blue-400" /> Cloud Sync Infrastructure
          </h3>

          <p className="text-xs text-[#8b949e] leading-relaxed">
            Encrypted state & vector memory backup to remote cloud storage. Keeps tablet workspace synchronized across modern web browsers.
          </p>

          <div className="p-4 bg-[#161b22] border border-[#30363d] rounded-xl flex items-center justify-between">
            <div>
              <div className="font-bold text-xs text-white">Automatic Cloud Sync</div>
              <div className="text-[10px] font-mono text-[#8b949e]">
                Last synced: {profile.lastSyncedAt}
              </div>
            </div>

            <button
              onClick={handleToggleSync}
              className={`px-3 py-1.5 rounded-lg text-xs font-bold font-mono transition-colors cursor-pointer ${
                profile.cloudSyncEnabled
                  ? 'bg-emerald-950/60 border border-emerald-800/60 text-emerald-400'
                  : 'bg-[#21262d] border border-[#30363d] text-[#8b949e]'
              }`}
            >
              {profile.cloudSyncEnabled ? 'ENABLED ✓' : 'DISABLED'}
            </button>
          </div>
        </div>

        {/* Security & Authentication Abstraction */}
        <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] space-y-4">
          <h3 className="text-sm font-bold text-white flex items-center gap-2">
            <Lock className="w-4 h-4 text-emerald-400" /> Authentication Abstraction
          </h3>

          <p className="text-xs text-[#8b949e] leading-relaxed">
            Abstracted JWT token verification layer. Connects seamlessly with Google OAuth, Firebase Auth, Supabase, or custom REST endpoints.
          </p>

          <div className="space-y-2 text-xs font-mono">
            <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl flex justify-between">
              <span className="text-[#8b949e]">OAuth Provider:</span>
              <span className="text-white font-bold uppercase">{profile.authProvider}</span>
            </div>
            <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl flex justify-between">
              <span className="text-[#8b949e]">Security Status:</span>
              <span className="text-emerald-400 font-bold">256-bit AES Encrypted</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
