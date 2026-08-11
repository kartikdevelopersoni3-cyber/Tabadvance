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
  ArrowUpRight
} from 'lucide-react';
import { motion, AnimatePresence } from 'motion/react';
import { UpdateRelease, BuildLog, DeviceStats } from './types';

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
      'Added Android 14 API 34 target SDK optimizations',
      'Improved background update verification speed by 40%',
      'Enhanced dark mode aesthetic and responsive tab layout'
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
      'Integrating live OTA update channel selection',
      'Added JDK 17 OpenJDK compatibility checks',
      'Real-time Gradle build artifact generator'
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
      'Security patch update for certificate store',
      'Resolved local cache lock file conflict'
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
    message: 'OpenJDK 17 configured successfully',
    durationMs: 420
  },
  {
    id: 'log-2',
    timestamp: '10:34:01',
    task: ':app:processDebugResources',
    status: 'SUCCESS',
    message: 'Processed vector drawables & mipmap assets',
    durationMs: 1200
  },
  {
    id: 'log-3',
    timestamp: '10:35:34',
    task: ':app:compileDebugKotlin',
    status: 'IN_PROGRESS',
    message: 'Compiling Kotlin source sets for API level 34...',
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
      {/* Top Header */}
      <header className="border-b border-slate-800 bg-slate-900/80 backdrop-blur-md sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 rounded-xl bg-indigo-600/20 border border-indigo-500/30 flex items-center justify-center text-indigo-400">
              <Smartphone className="w-5 h-5" />
            </div>
            <div>
              <h1 className="text-lg font-bold tracking-tight text-white flex items-center gap-2">
                Updates Manager
                <span className="px-2 py-0.5 text-xs font-medium rounded-full bg-indigo-500/10 text-indigo-400 border border-indigo-500/20">
                  Android API 34
                </span>
              </h1>
              <p className="text-xs text-slate-400">System Releases & Build Center</p>
            </div>
          </div>

          <div className="flex items-center space-x-3">
            <button
              onClick={handleTriggerBuild}
              disabled={isBuilding}
              className="inline-flex items-center space-x-2 px-3.5 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium transition shadow-lg shadow-indigo-600/20 disabled:opacity-50"
            >
              <RefreshCw className={`w-4 h-4 ${isBuilding ? 'animate-spin' : ''}`} />
              <span>{isBuilding ? 'Building APK...' : 'Trigger Build'}</span>
            </button>
            <button
              onClick={() => setShowNewReleaseModal(true)}
              className="inline-flex items-center space-x-2 px-3.5 py-2 rounded-lg bg-slate-800 hover:bg-slate-700 border border-slate-700 text-slate-200 text-sm font-medium transition"
            >
              <Plus className="w-4 h-4" />
              <span>New Release</span>
            </button>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8">
        {/* Metric Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
            <div className="flex items-center justify-between text-slate-400">
              <span className="text-xs font-medium uppercase tracking-wider">Active Devices</span>
              <Smartphone className="w-4 h-4 text-indigo-400" />
            </div>
            <div className="text-2xl font-bold text-white">{STATS.activeInstallations.toLocaleString()}</div>
            <div className="text-xs text-emerald-400 flex items-center gap-1">
              <ArrowUpRight className="w-3.5 h-3.5" /> +5.4% this week
            </div>
          </div>

          <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
            <div className="flex items-center justify-between text-slate-400">
              <span className="text-xs font-medium uppercase tracking-wider">Adoption Rate</span>
              <Activity className="w-4 h-4 text-emerald-400" />
            </div>
            <div className="text-2xl font-bold text-white">{STATS.updateAdoptionRate}%</div>
            <div className="text-xs text-slate-400">Targeting 95%+ coverage</div>
          </div>

          <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
            <div className="flex items-center justify-between text-slate-400">
              <span className="text-xs font-medium uppercase tracking-wider">Pending OTA Updates</span>
              <Clock className="w-4 h-4 text-amber-400" />
            </div>
            <div className="text-2xl font-bold text-white">{STATS.pendingUpdates.toLocaleString()}</div>
            <div className="text-xs text-amber-400">Scheduled for auto-install</div>
          </div>

          <div className="p-5 rounded-xl bg-slate-900 border border-slate-800 space-y-2">
            <div className="flex items-center justify-between text-slate-400">
              <span className="text-xs font-medium uppercase tracking-wider">Environment Health</span>
              <ShieldCheck className="w-4 h-4 text-emerald-400" />
            </div>
            <div className="text-2xl font-bold text-emerald-400">Verified</div>
            <div className="text-xs text-slate-400">JDK 17 + Gradle 8.5 Ready</div>
          </div>
        </div>

        {/* System Environment Info */}
        <div className="p-5 rounded-xl bg-slate-900/60 border border-slate-800 grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="flex items-center space-x-3">
            <Cpu className="w-5 h-5 text-indigo-400" />
            <div>
              <div className="text-xs text-slate-400">JDK Environment</div>
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
              <div className="text-xs text-slate-400">Build System</div>
              <div className="text-sm font-semibold text-slate-200">Gradle 8.5 Wrapper</div>
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
                  className={`px-3 py-1.5 rounded-lg text-xs font-medium transition ${
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
                      <button className="px-2.5 py-1 rounded bg-slate-800 hover:bg-slate-700 text-slate-300 text-xs font-medium transition flex items-center gap-1">
                        <Download className="w-3.5 h-3.5" /> APK Artifact
                      </button>
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
              <span className="font-semibold text-slate-200">Gradle Build Output Console</span>
            </div>
            <span className="font-mono text-emerald-400 flex items-center gap-1">
              <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span> Daemon Active
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
      </main>

      {/* New Release Modal */}
      {showNewReleaseModal && (
        <div className="fixed inset-0 z-50 bg-slate-950/80 backdrop-blur-sm flex items-center justify-center p-4">
          <motion.div
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            className="bg-slate-900 border border-slate-800 rounded-xl p-6 max-w-md w-full space-y-4 shadow-2xl"
          >
            <h3 className="text-lg font-bold text-white flex items-center gap-2">
              <Layers className="w-5 h-5 text-indigo-400" /> Create System Release
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
                  className="px-4 py-2 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-300 transition font-medium"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-500 text-white transition font-medium"
                >
                  Publish Release
                </button>
              </div>
            </form>
          </motion.div>
        </div>
      )}
    </div>
  );
}
