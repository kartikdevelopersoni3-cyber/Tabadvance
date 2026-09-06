import React, { useState, useEffect } from 'react';
import { Database, Plus, Trash2, Download, Search, RefreshCw, Cpu, Layers, HardDrive } from 'lucide-react';
import { MemoryItem } from '../types';
import { MemoryStore } from '../services/memoryStore';

export const MemorySystemView: React.FC = () => {
  const [memories, setMemories] = useState<MemoryItem[]>([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState<string>('all');
  const [newKey, setNewKey] = useState('');
  const [newValue, setNewValue] = useState('');
  const [newCategory, setNewCategory] = useState<'vector' | 'entity' | 'key-value'>('key-value');

  useEffect(() => {
    setMemories(MemoryStore.getMemories());
  }, []);

  const handleAddMemory = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newKey.trim() || !newValue.trim()) return;

    MemoryStore.addMemory({
      key: newKey,
      value: newValue,
      category: newCategory
    });

    setNewKey('');
    setNewValue('');
    setMemories(MemoryStore.getMemories());
  };

  const handleDelete = (id: string) => {
    MemoryStore.deleteMemory(id);
    setMemories(MemoryStore.getMemories());
  };

  const handleExportJson = () => {
    const jsonStr = MemoryStore.exportAsJson();
    const blob = new Blob([jsonStr], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `roohi_memory_backup_${Date.now()}.json`;
    a.click();
    URL.revokeObjectURL(url);
  };

  const filteredMemories = memories.filter((m) => {
    const matchesSearch =
      m.key.toLowerCase().includes(searchQuery.toLowerCase()) ||
      m.value.toLowerCase().includes(searchQuery.toLowerCase());
    const matchesCat = selectedCategory === 'all' || m.category === selectedCategory;
    return matchesSearch && matchesCat;
  });

  return (
    <div className="w-full flex flex-col space-y-6 my-2 text-white">
      {/* Header Banner */}
      <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 shadow-xl">
        <div className="flex items-center gap-3">
          <div className="w-12 h-12 rounded-2xl bg-purple-600/20 border border-purple-500/30 flex items-center justify-center text-purple-400 shrink-0">
            <Database className="w-6 h-6" />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <h2 className="text-xl font-bold text-white">Local Memory Store [DEMO / SIMULATION]</h2>
              <span className="px-2 py-0.5 rounded bg-purple-950/60 border border-purple-800/40 text-purple-300 text-[10px] font-mono font-bold uppercase">
                In-Memory & Storage Stub
              </span>
            </div>
            <p className="text-xs text-[#8b949e]">
              Local client-side prototype store. (Demonstration stub using local storage; not a production cloud/HNSW vector DB).
            </p>
          </div>
        </div>

        <button
          onClick={handleExportJson}
          className="px-4 py-2 bg-[#161b22] hover:bg-[#21262d] border border-[#30363d] rounded-xl text-xs font-mono font-bold text-white flex items-center gap-2 transition-all cursor-pointer shadow-lg"
        >
          <Download className="w-4 h-4 text-purple-400" /> Export JSON
        </button>
      </div>

      {/* Add Memory Form */}
      <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] space-y-4">
        <h3 className="text-sm font-bold text-white flex items-center gap-2">
          <Plus className="w-4 h-4 text-purple-400" /> Insert Demo Memory Entry
        </h3>

        <form onSubmit={handleAddMemory} className="grid grid-cols-1 sm:grid-cols-4 gap-3">
          <input
            type="text"
            value={newKey}
            onChange={(e) => setNewKey(e.target.value)}
            placeholder="Key / Topic (e.g., user_preferred_language)"
            className="px-4 py-2.5 bg-[#161b22] border border-[#30363d] rounded-xl text-xs font-mono text-white focus:outline-none focus:border-purple-500"
          />

          <input
            type="text"
            value={newValue}
            onChange={(e) => setNewValue(e.target.value)}
            placeholder="Value / Context description..."
            className="px-4 py-2.5 bg-[#161b22] border border-[#30363d] rounded-xl text-xs font-mono text-white focus:outline-none focus:border-purple-500"
          />

          <select
            value={newCategory}
            onChange={(e) => setNewCategory(e.target.value as any)}
            className="px-4 py-2.5 bg-[#161b22] border border-[#30363d] rounded-xl text-xs font-mono text-white focus:outline-none focus:border-purple-500 cursor-pointer"
          >
            <option value="key-value">Key-Value (Local)</option>
            <option value="entity">Entity (Simulated)</option>
            <option value="vector">Vector Mock (Stub)</option>
          </select>

          <button
            type="submit"
            className="px-5 py-2.5 bg-purple-600 hover:bg-purple-500 rounded-xl font-bold text-xs text-white flex items-center justify-center gap-2 transition-colors cursor-pointer"
          >
            <Plus className="w-4 h-4" /> Insert Entry
          </button>
        </form>
      </div>

      {/* Filter and Search Bar */}
      <div className="flex flex-col sm:flex-row items-center justify-between gap-4">
        <div className="relative w-full sm:w-80">
          <Search className="w-4 h-4 text-[#8b949e] absolute left-3 top-3" />
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search vector memory..."
            className="w-full pl-9 pr-4 py-2 bg-[#0d1117] border border-[#30363d] rounded-xl text-xs font-mono text-white focus:outline-none focus:border-purple-500"
          />
        </div>

        <div className="flex items-center gap-2 w-full sm:w-auto overflow-x-auto">
          {['all', 'vector', 'entity', 'key-value'].map((cat) => (
            <button
              key={cat}
              onClick={() => setSelectedCategory(cat)}
              className={`px-3 py-1.5 rounded-xl text-xs font-mono font-bold capitalize transition-all cursor-pointer ${
                selectedCategory === cat
                  ? 'bg-purple-600 text-white'
                  : 'bg-[#0d1117] border border-[#30363d] text-[#8b949e] hover:text-white'
              }`}
            >
              {cat}
            </button>
          ))}
        </div>
      </div>

      {/* Memory Cards Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {filteredMemories.map((item) => (
          <div
            key={item.id}
            className="p-4 rounded-2xl bg-[#0d1117] border border-[#30363d] space-y-2 flex flex-col justify-between"
          >
            <div>
              <div className="flex items-center justify-between mb-2">
                <span className="text-[10px] font-mono px-2 py-0.5 rounded bg-purple-950/60 text-purple-300 border border-purple-800/40 font-bold uppercase">
                  {item.category}
                </span>
                <span className="text-[10px] font-mono text-[#8b949e]">{item.timestamp}</span>
              </div>

              <h4 className="font-mono text-xs font-bold text-white mb-1">{item.key}</h4>
              <p className="text-xs text-[#8b949e] leading-relaxed font-mono bg-[#161b22] p-2.5 rounded-xl border border-[#30363d]/50">
                {item.value}
              </p>
            </div>

            <div className="pt-2 flex items-center justify-between text-[10px] font-mono border-t border-[#30363d]/50 text-[#8b949e]">
              <span>Score: {item.score?.toFixed(2) ?? '0.95'}</span>
              <button
                onClick={() => handleDelete(item.id)}
                className="text-rose-400 hover:text-rose-300 cursor-pointer flex items-center gap-1"
              >
                <Trash2 className="w-3 h-3" /> Remove
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
