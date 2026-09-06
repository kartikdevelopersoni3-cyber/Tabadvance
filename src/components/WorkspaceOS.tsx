import React, { useState } from 'react';
import { motion } from 'motion/react';
import {
  Mic,
  MicOff,
  Send,
  Cpu,
  Database,
  ShieldCheck,
  Download,
  Settings,
  Sparkles,
  Zap,
  Activity,
  Layers,
  CheckCircle2,
  Terminal,
  Radio,
  FileArchive
} from 'lucide-react';
import { SetupState } from '../types';

interface WorkspaceOSProps {
  setupConfig: SetupState;
  onOpenSettings: () => void;
}

const MODULES_REGISTRY = [
  { code: 'Module A', name: 'Voice & Wake Word [SIMULATED]', status: 'Active', desc: 'Continuous listener & speaker verification [STUB]' },
  { code: 'Module B', name: 'Conversation Engine [DEMO]', status: 'Active', desc: 'Dialogue management & turn handling simulation' },
  { code: 'Module C', name: 'Memory Engine [LOCAL STUB]', status: 'Active', desc: 'In-memory & Web storage entity index (Not a vector DB)' },
  { code: 'Module D', name: 'Reasoning Engine [PROTOTYPE]', status: 'Active', desc: 'Step-by-step logic & problem decomposition stub' },
  { code: 'Module E', name: 'Multi-Agent Coordinator [SIMULATED]', status: 'Active', desc: 'Task routing across sub-agent network simulation' },
  { code: 'Module F', name: 'Vision Engine [DEMO STUB]', status: 'Active', desc: 'Real-time camera frame & screen analysis stub' },
  { code: 'Module G', name: 'Automation Studio [PROTOTYPE]', status: 'Active', desc: 'Tablet gestures, apps & accessibility automation specification' },
  { code: 'Module H', name: 'Learning Engine [DEMO]', status: 'Active', desc: 'User feedback & preference tuning simulation' },
  { code: 'Module I', name: 'Knowledge Graph [PROTOTYPE]', status: 'Active', desc: 'Structured relational facts & context store' },
  { code: 'Module J', name: 'Proactive Engine [SIMULATED]', status: 'Active', desc: 'Autonomous context-aware suggestions simulation' },
  { code: 'Module Workspace', name: 'Workspace OS [ACTIVE DEMO]', status: 'Active', desc: 'Multi-window tablet layout controller' },
  { code: 'Module Ω', name: 'Evolution Engine [PROTOTYPE]', status: 'Active', desc: 'Self-modification & code enhancement architecture blueprint' }
];

export const WorkspaceOS: React.FC<WorkspaceOSProps> = ({ setupConfig, onOpenSettings }) => {
  const [isVoiceActive, setIsVoiceActive] = useState(true);
  const [inputText, setInputText] = useState('');
  const [messages, setMessages] = useState([
    {
      id: 1,
      sender: 'roohi',
      text: `[DEMO PROTOTYPE INITIALIZED]\n\nHello! I am Roohi, your AI Assistant OS Layer prototype. Operating in ${setupConfig.aiMode.toUpperCase()} mode with simulated wake word "${setupConfig.voiceSetup.wakeWord}". External APIs are not connected in this prototype demonstration.`,
      timestamp: '01:10 AM'
    }
  ]);

  const handleSendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputText.trim()) return;

    const userMsg = {
      id: Date.now(),
      sender: 'user',
      text: inputText,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };

    setMessages(prev => [...prev, userMsg]);
    const submittedText = inputText;
    setInputText('');

    // Simulate AI Assistant response
    setTimeout(() => {
      const aiResponse = {
        id: Date.now() + 1,
        sender: 'roohi',
        text: `[SIMULATED RESPONSE]\n\nCommand received: "${submittedText}". Simulated execution routed to Module E and local memory store. Note: External model provider (${setupConfig.cloudConfig.provider.toUpperCase()}) is NOT CONNECTED in prototype demo mode.`,
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
      };
      setMessages(prev => [...prev, aiResponse]);
    }, 600);
  };

  return (
    <div className="w-full flex flex-col space-y-6 my-4 text-white">
      {/* Workspace Header Bar */}
      <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 shadow-2xl">
        <div className="flex items-center gap-3">
          <div className="w-12 h-12 rounded-2xl bg-gradient-to-tr from-blue-500 to-indigo-600 p-0.5 shadow-lg flex items-center justify-center">
            <div className="w-full h-full bg-[#0d1117] rounded-[14px] flex items-center justify-center">
              <Layers className="w-6 h-6 text-blue-400" />
            </div>
          </div>
          <div>
            <div className="flex items-center gap-2">
              <h2 className="text-xl font-bold text-white">Roohi Workspace OS</h2>
              <span className="px-2 py-0.5 rounded bg-emerald-950/60 border border-emerald-800/40 text-emerald-400 text-[10px] font-mono font-bold uppercase">
                Active OS Layer
              </span>
            </div>
            <p className="text-xs text-[#8b949e]">
              Language: {setupConfig.language.toUpperCase()} • Mode: {setupConfig.aiMode.toUpperCase()} • Wake Word: "{setupConfig.voiceSetup.wakeWord}"
            </p>
          </div>
        </div>

        <div className="flex items-center gap-3 w-full sm:w-auto">
          <button
            onClick={onOpenSettings}
            className="px-4 py-2 bg-[#161b22] hover:bg-[#21262d] border border-[#30363d] rounded-xl text-xs font-bold text-[#c9d1d9] flex items-center gap-2 transition-all cursor-pointer"
          >
            <Settings className="w-4 h-4" /> Setup Preferences
          </button>

          <a
            href="/roohi_apk.zip"
            download="roohi_apk.zip"
            className="px-4 py-2 bg-[#238636] hover:bg-[#2ea043] rounded-xl text-xs font-bold text-white flex items-center gap-2 transition-all cursor-pointer shadow-lg"
          >
            <Download className="w-4 h-4" /> Download APK (roohi_apk.zip)
          </a>
        </div>
      </div>

      {/* Main Grid: Assistant Chat & Telemetry */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Chat / Voice Command Console */}
        <div className="lg:col-span-2 bg-[#0d1117] border border-[#30363d] rounded-2xl p-6 flex flex-col h-[520px]">
          {/* Header */}
          <div className="flex items-center justify-between pb-4 border-b border-[#30363d] mb-4">
            <div className="flex items-center gap-2">
              <Radio className={`w-4 h-4 ${isVoiceActive ? 'text-rose-500 animate-pulse' : 'text-[#484f58]'}`} />
              <span className="text-xs font-bold text-white">Voice Assistant Interface</span>
            </div>

            <button
              onClick={() => setIsVoiceActive(prev => !prev)}
              className={`px-3 py-1 rounded-lg text-[11px] font-mono font-bold flex items-center gap-1.5 transition-all cursor-pointer ${
                isVoiceActive ? 'bg-rose-950/50 text-rose-300 border border-rose-800/50' : 'bg-[#161b22] text-[#8b949e] border border-[#30363d]'
              }`}
            >
              {isVoiceActive ? <Mic className="w-3.5 h-3.5" /> : <MicOff className="w-3.5 h-3.5" />}
              {isVoiceActive ? 'Listener ON' : 'Listener Muted'}
            </button>
          </div>

          {/* Messages Feed */}
          <div className="flex-1 overflow-y-auto space-y-3 pr-2 text-xs">
            {messages.map(msg => (
              <div
                key={msg.id}
                className={`flex flex-col ${msg.sender === 'user' ? 'items-end' : 'items-start'}`}
              >
                <div
                  className={`max-w-[85%] p-3.5 rounded-2xl ${
                    msg.sender === 'user'
                      ? 'bg-blue-600 text-white rounded-br-none'
                      : 'bg-[#161b22] border border-[#30363d] text-[#c9d1d9] rounded-bl-none'
                  }`}
                >
                  <p className="leading-relaxed">{msg.text}</p>
                </div>
                <span className="text-[10px] text-[#484f58] mt-1 font-mono">{msg.timestamp}</span>
              </div>
            ))}
          </div>

          {/* Command Input Bar */}
          <form onSubmit={handleSendMessage} className="pt-4 border-t border-[#30363d] flex gap-2 mt-auto">
            <input
              type="text"
              value={inputText}
              onChange={e => setInputText(e.target.value)}
              placeholder={`Type a command or say "${setupConfig.voiceSetup.wakeWord}"...`}
              className="flex-1 px-4 py-3 bg-[#161b22] border border-[#30363d] rounded-xl text-xs font-mono text-white focus:outline-none focus:border-blue-500"
            />
            <button
              type="submit"
              className="px-5 py-3 bg-blue-600 hover:bg-blue-500 rounded-xl text-white font-bold text-xs flex items-center gap-1.5 cursor-pointer transition-colors"
            >
              <Send className="w-4 h-4" /> Send
            </button>
          </form>
        </div>

        {/* Telemetry & System Status Sidebar */}
        <div className="space-y-6">
          {/* Active Engines Counter */}
          <div className="bg-[#0d1117] border border-[#30363d] rounded-2xl p-5 space-y-4">
            <div className="flex items-center justify-between border-b border-[#30363d] pb-3">
              <span className="text-xs font-bold text-white uppercase tracking-wider">System Telemetry [SIMULATED]</span>
              <Activity className="w-4 h-4 text-emerald-400 animate-pulse" />
            </div>

            <div className="grid grid-cols-2 gap-3 text-xs">
              <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl">
                <div className="text-[10px] text-[#8b949e]">Registered Modules</div>
                <div className="text-base font-bold text-emerald-400">14 (Prototype)</div>
              </div>
              <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl">
                <div className="text-[10px] text-[#8b949e]">Vector Memory</div>
                <div className="text-sm font-bold text-blue-400">Simulated Stub</div>
              </div>
              <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl">
                <div className="text-[10px] text-[#8b949e]">Voiceprint</div>
                <div className="text-sm font-bold text-rose-400">
                  Simulated Stub
                </div>
              </div>
              <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl">
                <div className="text-[10px] text-[#8b949e]">AI Provider</div>
                <div className="text-sm font-bold text-amber-400 uppercase">
                  Not Connected (Demo)
                </div>
              </div>
            </div>
          </div>

          {/* Export Packages Card */}
          <div className="bg-[#0d1117] border border-[#30363d] rounded-2xl p-5 space-y-3">
            <div className="flex items-center gap-2 text-xs font-bold text-white">
              <FileArchive className="w-4 h-4 text-blue-400" />
              <span>Project Export Archives & APK Package</span>
            </div>
            <p className="text-xs text-[#8b949e]">
              Verified physical master zip package containing complete Android source code, Gradle configurations, and documentation.
            </p>
            <div className="space-y-2 pt-1">
              <a
                href="/roohi_apk.zip"
                download="roohi_apk.zip"
                className="w-full py-2.5 px-3 bg-gradient-to-r from-emerald-950/60 to-teal-950/60 hover:from-emerald-900/80 hover:to-teal-900/80 border border-emerald-500/40 rounded-xl text-xs font-mono text-white flex items-center justify-between transition-colors cursor-pointer"
              >
                <span className="font-bold text-emerald-300">/roohi_apk.zip (Full Master Package)</span>
                <span className="text-[10px] text-emerald-400 font-bold">0.29 MB ✓</span>
              </a>
              <a
                href="/Roohi_VoltBuilder_Package.zip"
                download="Roohi_VoltBuilder_Package.zip"
                className="w-full py-2 px-3 bg-[#161b22] hover:bg-[#21262d] border border-[#30363d] rounded-xl text-xs font-mono text-[#c9d1d9] flex items-center justify-between transition-colors cursor-pointer"
              >
                <span>/Roohi_VoltBuilder_Package.zip</span>
                <span className="text-[10px] text-blue-400 font-bold">W3C Widget</span>
              </a>
            </div>
          </div>
        </div>
      </div>

      {/* Registered Modules Matrix */}
      <div className="bg-[#0d1117] border border-[#30363d] rounded-2xl p-6">
        <h3 className="text-sm font-bold text-white mb-4 flex items-center gap-2">
          <Cpu className="w-4 h-4 text-purple-400" /> Registered Assistant OS Modules (Modules A → Ω)
        </h3>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
          {MODULES_REGISTRY.map(mod => (
            <div key={mod.code} className="p-3.5 bg-[#161b22] border border-[#30363d] rounded-xl flex flex-col justify-between">
              <div>
                <div className="flex items-center justify-between mb-1">
                  <span className="text-[10px] font-mono text-purple-400 font-bold">{mod.code}</span>
                  <span className="text-[9px] px-1.5 py-0.5 rounded bg-emerald-950/60 text-emerald-400 border border-emerald-800/40 font-mono">
                    {mod.status}
                  </span>
                </div>
                <h4 className="font-bold text-xs text-white mb-0.5">{mod.name}</h4>
                <p className="text-[11px] text-[#8b949e] leading-snug">{mod.desc}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
