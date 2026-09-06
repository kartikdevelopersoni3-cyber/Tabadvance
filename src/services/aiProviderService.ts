import { AIProvider, ChatMessage } from '../types';

export interface GenerateResponseOptions {
  prompt: string;
  provider: AIProvider;
  apiKey?: string;
  modelName?: string;
  useGrounding?: boolean;
}

export class AIProviderService {
  public static async generateResponse(options: GenerateResponseOptions): Promise<ChatMessage> {
    const { prompt, provider, apiKey, modelName, useGrounding } = options;
    const now = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    // Simulate network delay for real AI response experience
    await new Promise((resolve) => setTimeout(resolve, 800));

    const selectedModel = modelName || (
      provider === 'gemini' ? 'gemini-2.5-flash' :
      provider === 'openai' ? 'gpt-4o-mini' :
      provider === 'anthropic' ? 'claude-3-5-sonnet' : 'ollama-llama3'
    );

    // Truthful demo simulation response - not connected to live external APIs
    const responseText = `[DEMO / SIMULATED RESPONSE - NOT CONNECTED]\n\nSimulated Model: ${selectedModel} (${provider.toUpperCase()})\n\nPrompt: "${prompt}"\n\n• Roohi OS Pipeline: Executed in local prototype demonstration mode.\n• Module E (Multi-Agent Coordinator): Simulated sub-task routing.\n• Memory Retrieval: In-memory demo store query.\n• External AI API: NOT CONNECTED (Zero external paid API requirements for prototype demonstration).`;

    return {
      id: 'msg-' + Date.now(),
      sender: 'roohi',
      text: responseText,
      timestamp: now,
      tokens: Math.floor(Math.random() * 120) + 40,
      model: selectedModel,
      provider: provider,
      groundingUsed: !!useGrounding
    };
  }
}
