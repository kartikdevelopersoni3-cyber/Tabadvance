import React, { useState } from 'react';
import { Send, Bot, User, Sparkles, Mic, MicOff, RefreshCw, Sliders, Globe, ShieldCheck, Download, Zap } from 'lucide-react';
import { SetupState, ChatMessage, AIProvider } from '../types';
import { AIProviderService } from '../services/aiProviderService';

interface AIChatViewProps {
  setupConfig: SetupState;
  onOpenSettings: () => void;
}

export const AIChatView: React.FC<AIChatViewProps> = ({ setupConfig, onOpenSettings }) => {
  const [messages, setMessages] = useState<ChatMessage[]>([
    {
      id: 'msg-welcome',
      sender: 'roohi',
      text: `Welcome to Roohi AI Chat Interface! Currently initialized with provider "${setupConfig.cloudConfig.provider.toUpperCase()}". How can I assist your tablet workspace today?`,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      tokens: 28,
      model: setupConfig.cloudConfig.modelName || 'gemini-2.5-flash',
      provider: setupConfig.cloudConfig.provider
    }
  ]);

  const [inputPrompt, setInputPrompt] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [useGrounding, setUseGrounding] = useState(setupConfig.cloudConfig.useGrounding ?? true);
  const [selectedProvider, setSelectedProvider] = useState<AIProvider>(setupConfig.cloudConfig.provider);

  const handleSend = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputPrompt.trim() || isLoading) return;

    const userMsg: ChatMessage = {
      id: 'user-' + Date.now(),
      sender: 'user',
      text: inputPrompt,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      tokens: Math.floor(inputPrompt.length / 4)
    };

    setMessages(prev => [...prev, userMsg]);
    const promptToSubmit = inputPrompt;
    setInputPrompt('');
    setIsLoading(true);

    try {
      const response = await AIProviderService.generateResponse({
        prompt: promptToSubmit,
        provider: selectedProvider,
        apiKey: setupConfig.cloudConfig.apiKey,
        modelName: setupConfig.cloudConfig.modelName,
        useGrounding
      });

      setMessages(prev => [...prev, response]);
    } catch (err) {
      const errorMsg: ChatMessage = {
        id: 'err-' + Date.now(),
        sender: 'system',
        text: 'Error generating response from AI Provider. Please check your API configuration in Settings.',
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
      };
      setMessages(prev => [...prev, errorMsg]);
    } finally {
      setIsLoading(false);
    }
  };

  const handleClearHistory = () => {
    setMessages([
      {
        id: 'msg-reset',
        sender: 'roohi',
        text: 'Chat history cleared. Roohi Assistant OS is ready for new instructions.',
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
      }
    ]);
  };

  return (
    <div className="w-full flex flex-col space-y-4 my-2 text-white">
      {/* Top Controls Header */}
      <div className="p-4 sm:p-5 rounded-2xl bg-[#0d1117] border border-[#30363d] flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 shadow-xl">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-xl bg-blue-600/20 border border-blue-500/30 flex items-center justify-center text-blue-400 shrink-0">
            <Bot className="w-5 h-5" />
          </div>
          <div>
            <h2 className="font-bold text-base text-white flex items-center gap-2">
              Roohi AI Chat Interface
              <span className="text-[10px] px-2 py-0.5 rounded bg-emerald-950/60 border border-emerald-800/40 text-emerald-400 font-mono font-bold uppercase">
                {selectedProvider}
              </span>
            </h2>
            <p className="text-xs text-[#8b949e]">
              Multi-model cloud interface with grounding & vector memory integration
            </p>
          </div>
        </div>

        {/* Provider Switcher Tabs */}
        <div className="flex items-center gap-2 w-full sm:w-auto overflow-x-auto pb-1 sm:pb-0">
          {(['gemini', 'openai', 'anthropic', 'local'] as AIProvider[]).map((prov) => (
            <button
              key={prov}
              onClick={() => setSelectedProvider(prov)}
              className={`px-3 py-1.5 rounded-xl text-xs font-mono font-bold uppercase transition-all cursor-pointer ${
                selectedProvider === prov
                  ? 'bg-blue-600 text-white shadow-lg'
                  : 'bg-[#161b22] text-[#8b949e] border border-[#30363d] hover:text-white'
              }`}
            >
              {prov}
            </button>
          ))}

          <button
            onClick={onOpenSettings}
            className="p-2 bg-[#161b22] hover:bg-[#21262d] border border-[#30363d] rounded-xl text-[#c9d1d9] transition-colors cursor-pointer shrink-0"
            title="Open API Settings"
          >
            <Sliders className="w-4 h-4" />
          </button>
        </div>
      </div>

      {/* Main Chat Feed Container */}
      <div className="bg-[#0d1117] border border-[#30363d] rounded-2xl p-4 sm:p-6 flex flex-col h-[560px] shadow-2xl">
        {/* Messages Scroll Area */}
        <div className="flex-1 overflow-y-auto space-y-4 pr-2">
          {messages.map((msg) => (
            <div
              key={msg.id}
              className={`flex flex-col ${
                msg.sender === 'user' ? 'items-end' : 'items-start'
              }`}
            >
              <div className="flex items-center gap-2 mb-1">
                <span className="text-[10px] font-mono text-[#8b949e]">
                  {msg.sender === 'user' ? 'You' : msg.sender === 'roohi' ? 'Roohi Assistant' : 'System Notice'}
                </span>
                <span className="text-[10px] font-mono text-[#484f58]">{msg.timestamp}</span>
                {msg.provider && (
                  <span className="text-[9px] font-mono px-1.5 py-0.2 rounded bg-[#161b22] border border-[#30363d] text-blue-400 uppercase">
                    {msg.provider}
                  </span>
                )}
              </div>

              <div
                className={`max-w-[88%] p-4 rounded-2xl text-xs sm:text-sm leading-relaxed ${
                  msg.sender === 'user'
                    ? 'bg-gradient-to-r from-blue-600 to-indigo-600 text-white rounded-tr-none shadow-lg'
                    : msg.sender === 'system'
                    ? 'bg-rose-950/40 border border-rose-800/40 text-rose-200'
                    : 'bg-[#161b22] border border-[#30363d] text-[#c9d1d9] rounded-tl-none'
                }`}
              >
                <p className="whitespace-pre-wrap">{msg.text}</p>

                {msg.tokens && (
                  <div className="mt-2 pt-2 border-t border-[#30363d]/50 flex items-center justify-between text-[10px] font-mono text-[#8b949e]">
                    <span>Tokens: ~{msg.tokens}</span>
                    {msg.groundingUsed && (
                      <span className="text-emerald-400 flex items-center gap-1">
                        <Globe className="w-3 h-3" /> Grounded
                      </span>
                    )}
                  </div>
                )}
              </div>
            </div>
          ))}

          {isLoading && (
            <div className="flex items-center gap-3 p-3 bg-[#161b22] border border-[#30363d] rounded-2xl w-fit text-xs text-[#8b949e]">
              <RefreshCw className="w-4 h-4 text-blue-400 animate-spin" />
              <span>Generating response using {selectedProvider.toUpperCase()}...</span>
            </div>
          )}
        </div>

        {/* Input Controls Bar */}
        <div className="pt-4 border-t border-[#30363d] space-y-3 mt-auto">
          <div className="flex items-center justify-between text-xs text-[#8b949e]">
            <div className="flex items-center gap-3">
              <label className="flex items-center gap-1.5 cursor-pointer">
                <input
                  type="checkbox"
                  checked={useGrounding}
                  onChange={(e) => setUseGrounding(e.target.checked)}
                  className="rounded bg-[#161b22] border-[#30363d] text-blue-600 focus:ring-0"
                />
                <span className="text-[11px] font-mono">Web Grounding</span>
              </label>

              <span className="text-[11px] font-mono text-[#484f58]">
                Mode: {setupConfig.aiMode.toUpperCase()}
              </span>
            </div>

            <button
              onClick={handleClearHistory}
              className="text-[11px] font-mono text-[#8b949e] hover:text-rose-400 cursor-pointer"
            >
              Clear Chat History
            </button>
          </div>

          <form onSubmit={handleSend} className="flex gap-2">
            <input
              type="text"
              value={inputPrompt}
              onChange={(e) => setInputPrompt(e.target.value)}
              placeholder={`Ask Roohi using ${selectedProvider.toUpperCase()}...`}
              disabled={isLoading}
              className="flex-1 px-4 py-3 bg-[#161b22] border border-[#30363d] rounded-xl text-xs sm:text-sm text-white font-mono placeholder-[#484f58] focus:outline-none focus:border-blue-500"
            />

            <button
              type="submit"
              disabled={isLoading || !inputPrompt.trim()}
              className="px-6 py-3 bg-blue-600 hover:bg-blue-500 disabled:opacity-50 rounded-xl text-white font-bold text-xs flex items-center gap-2 cursor-pointer transition-colors shadow-lg"
            >
              <Send className="w-4 h-4" /> Send
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};
