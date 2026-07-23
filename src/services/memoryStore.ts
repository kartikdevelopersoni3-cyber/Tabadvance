import { MemoryItem } from '../types';

const MEMORY_STORAGE_KEY = 'roohi_os_memory_items_v1';

const INITIAL_MEMORIES: MemoryItem[] = [
  {
    id: 'mem-1',
    key: 'user_preferred_wake_word',
    value: 'Hey Roohi',
    category: 'key-value',
    timestamp: '2026-07-22 09:00:00',
    score: 1.0
  },
  {
    id: 'mem-2',
    key: 'user_preferred_ai_mode',
    value: 'Hybrid (Cloud + On-Device Memory)',
    category: 'entity',
    timestamp: '2026-07-22 09:15:00',
    score: 0.98
  },
  {
    id: 'mem-3',
    key: 'tablet_workspace_layout',
    value: 'Multi-window OS Layer view with voice listener enabled',
    category: 'vector',
    timestamp: '2026-07-22 09:30:00',
    score: 0.94
  },
  {
    id: 'mem-4',
    key: 'module_e_coordinator_state',
    value: 'Routing 14 autonomous modules through local DAG pipeline',
    category: 'vector',
    timestamp: '2026-07-22 09:35:00',
    score: 0.91
  }
];

export class MemoryStore {
  public static getMemories(): MemoryItem[] {
    try {
      const stored = localStorage.getItem(MEMORY_STORAGE_KEY);
      if (stored) {
        return JSON.parse(stored);
      }
    } catch {
      // Fallback
    }
    return INITIAL_MEMORIES;
  }

  public static addMemory(item: Omit<MemoryItem, 'id' | 'timestamp'>): MemoryItem {
    const memories = this.getMemories();
    const newMemory: MemoryItem = {
      ...item,
      id: 'mem-' + Date.now(),
      timestamp: new Date().toISOString().replace('T', ' ').substring(0, 19),
      score: item.score ?? 0.95
    };
    const updated = [newMemory, ...memories];
    try {
      localStorage.setItem(MEMORY_STORAGE_KEY, JSON.stringify(updated));
    } catch {
      // storage error
    }
    return newMemory;
  }

  public static deleteMemory(id: string): void {
    const memories = this.getMemories().filter(m => m.id !== id);
    try {
      localStorage.setItem(MEMORY_STORAGE_KEY, JSON.stringify(memories));
    } catch {}
  }

  public static clearAll(): void {
    try {
      localStorage.removeItem(MEMORY_STORAGE_KEY);
    } catch {}
  }

  public static exportAsJson(): string {
    return JSON.stringify(this.getMemories(), null, 2);
  }
}
