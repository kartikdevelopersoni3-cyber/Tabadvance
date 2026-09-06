import React, { useState } from 'react';
import { Cpu, Power, Activity, HardDrive, Zap, Layers, RefreshCw } from 'lucide-react';
import { ModuleItem } from '../types';

const INITIAL_MODULES: ModuleItem[] = [
  { code: 'Module A', name: 'Voice & Wake Word Listener [SIMULATED]', status: 'Active', desc: 'Continuous 16kHz audio buffer & speaker verification stub', latencyMs: 12, memoryUsageMb: 48, enabled: true },
  { code: 'Module B', name: 'Conversation Dialogue Manager [DEMO]', status: 'Active', desc: 'Context stack & conversation turn router simulation', latencyMs: 24, memoryUsageMb: 36, enabled: true },
  { code: 'Module C', name: 'Vector Memory Index Engine [STUB]', status: 'Active', desc: 'In-memory prototype index (Not production vector DB)', latencyMs: 8, memoryUsageMb: 112, enabled: true },
  { code: 'Module D', name: 'Reasoning & Logic Decomposition [PROTOTYPE]', status: 'Active', desc: 'Step-by-step logic planner & sub-goal manager architecture', latencyMs: 45, memoryUsageMb: 84, enabled: true },
  { code: 'Module E', name: 'Multi-Agent Network Coordinator [SIMULATED]', status: 'Active', desc: 'Task delegation DAG across sub-agents simulation', latencyMs: 15, memoryUsageMb: 62, enabled: true },
  { code: 'Module F', name: 'Vision Frame & Screen Analyzer [SIMULATED STUB]', status: 'Active', desc: 'Camera frame analysis & screen parsing prototype stub', latencyMs: 65, memoryUsageMb: 140, enabled: true },
  { code: 'Module G', name: 'Automation Studio & Touch Gestures [PROTOTYPE]', status: 'Active', desc: 'Tablet gesture synthesis & accessibility automation blueprint', latencyMs: 18, memoryUsageMb: 32, enabled: true },
  { code: 'Module H', name: 'Preference & Learning Engine [DEMO]', status: 'Active', desc: 'Adaptive feedback loop & user habit tuning prototype', latencyMs: 10, memoryUsageMb: 28, enabled: true },
  { code: 'Module I', name: 'Knowledge Graph Relational Facts [PROTOTYPE]', status: 'Active', desc: 'Structured entity relational store blueprint', latencyMs: 14, memoryUsageMb: 76, enabled: true },
  { code: 'Module J', name: 'Proactive Context Suggestions [SIMULATED]', status: 'Active', desc: 'Context-triggered recommendations prototype', latencyMs: 30, memoryUsageMb: 44, enabled: true },
  { code: 'Module Workspace', name: 'Workspace OS Multi-Window [ACTIVE DEMO]', status: 'Active', desc: 'Multi-window layout manager & focus router', latencyMs: 6, memoryUsageMb: 22, enabled: true },
  { code: 'Module Cloud Sync', name: 'Cloud Sync Engine [NOT CONNECTED]', status: 'Active', desc: 'Simulated cloud backup & state synchronization stub', latencyMs: 85, memoryUsageMb: 50, enabled: true },
  { code: 'Module Auth', name: 'User Authentication Abstraction [DEMO STUB]', status: 'Active', desc: 'JWT token abstraction & permission guard prototype', latencyMs: 4, memoryUsageMb: 16, enabled: true },
  { code: 'Module Ω', name: 'Evolution & Self-Correction Engine [PROTOTYPE]', status: 'Active', desc: 'Architecture specification for self-inspection & patches', latencyMs: 50, memoryUsageMb: 95, enabled: true }
];

export const ModuleManagerView: React.FC = () => {
  const [modules, setModules] = useState<ModuleItem[]>(INITIAL_MODULES);

  const toggleModule = (code: string) => {
    setModules(prev =>
      prev.map(mod =>
        mod.code === code
          ? {
              ...mod,
              enabled: !mod.enabled,
              status: mod.enabled ? 'Disabled' : 'Active'
            }
          : mod
      )
    );
  };

  const activeCount = modules.filter(m => m.enabled).length;
  const totalMemory = modules.reduce((acc, m) => acc + (m.enabled ? m.memoryUsageMb : 0), 0);

  return (
    <div className="w-full flex flex-col space-y-6 my-2 text-white">
      {/* Header Banner */}
      <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 shadow-xl">
        <div className="flex items-center gap-3">
          <div className="w-12 h-12 rounded-2xl bg-purple-600/20 border border-purple-500/30 flex items-center justify-center text-purple-400 shrink-0">
            <Cpu className="w-6 h-6" />
          </div>
          <div>
            <h2 className="text-xl font-bold text-white flex items-center gap-2">
              Roohi Module Architecture Matrix (Modules A → Ω)
            </h2>
            <p className="text-xs text-[#8b949e]">
              Prototype architecture matrix • [SIMULATED STATUS & METRICS — PROTOTYPE SPECIFICATION]
            </p>
          </div>
        </div>

        <div className="flex items-center gap-4 text-xs font-mono">
          <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl text-center">
            <div className="text-[10px] text-[#8b949e]">Modules (Prototype)</div>
            <div className="text-sm font-bold text-emerald-400">{activeCount} / {modules.length}</div>
          </div>
          <div className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl text-center">
            <div className="text-[10px] text-[#8b949e]">Simulated Memory</div>
            <div className="text-sm font-bold text-purple-400">{totalMemory} MB</div>
          </div>
        </div>
      </div>

      {/* Modules Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {modules.map(mod => (
          <div
            key={mod.code}
            className={`p-5 rounded-2xl border transition-all flex flex-col justify-between ${
              mod.enabled
                ? 'bg-[#0d1117] border-[#30363d]'
                : 'bg-[#0d1117]/50 border-[#21262d] opacity-60'
            }`}
          >
            <div>
              <div className="flex items-center justify-between mb-3">
                <span className="text-xs font-mono font-bold text-purple-400">{mod.code}</span>
                <button
                  onClick={() => toggleModule(mod.code)}
                  className={`p-1.5 rounded-lg border transition-colors cursor-pointer ${
                    mod.enabled
                      ? 'bg-emerald-950/60 border-emerald-800/60 text-emerald-400'
                      : 'bg-rose-950/60 border-rose-800/60 text-rose-400'
                  }`}
                  title={mod.enabled ? 'Disable Module' : 'Enable Module'}
                >
                  <Power className="w-3.5 h-3.5" />
                </button>
              </div>

              <h3 className="font-bold text-sm text-white mb-1">{mod.name}</h3>
              <p className="text-xs text-[#8b949e] leading-relaxed mb-4">{mod.desc}</p>
            </div>

            <div className="pt-3 border-t border-[#30363d]/50 flex items-center justify-between text-[11px] font-mono text-[#8b949e]">
              <span className="flex items-center gap-1">
                <Activity className="w-3 h-3 text-blue-400" /> {mod.latencyMs} ms
              </span>
              <span className="flex items-center gap-1">
                <HardDrive className="w-3 h-3 text-purple-400" /> {mod.memoryUsageMb} MB
              </span>
              <span className={`font-bold ${mod.enabled ? 'text-emerald-400' : 'text-rose-400'}`}>
                {mod.status}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
