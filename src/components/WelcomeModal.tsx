import React from 'react';
import { motion } from 'motion/react';
import { Sparkles, ArrowRight, CheckCircle, Tablet } from 'lucide-react';

interface WelcomeModalProps {
  isOpen: boolean;
  onBeginSetup: () => void;
}

export const WelcomeModal: React.FC<WelcomeModalProps> = ({ isOpen, onBeginSetup }) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 bg-black/80 backdrop-blur-md flex items-center justify-center p-4">
      <motion.div
        initial={{ opacity: 0, scale: 0.9, y: 20 }}
        animate={{ opacity: 1, scale: 1, y: 0 }}
        exit={{ opacity: 0, scale: 0.9, y: 20 }}
        className="w-full max-w-md bg-[#0d1117] border border-[#30363d] rounded-2xl shadow-2xl overflow-hidden text-white flex flex-col p-8 text-center relative"
      >
        {/* Glow */}
        <div className="absolute top-0 left-1/2 -translate-x-1/2 w-64 h-32 bg-blue-500/20 blur-3xl pointer-events-none" />

        {/* Party Icon */}
        <div className="w-16 h-16 rounded-2xl bg-gradient-to-tr from-blue-500 to-indigo-600 p-0.5 mx-auto mb-6 shadow-xl flex items-center justify-center">
          <div className="w-full h-full bg-[#0d1117] rounded-[14px] flex items-center justify-center">
            <span className="text-3xl">🎉</span>
          </div>
        </div>

        {/* Title */}
        <h2 className="text-2xl font-bold tracking-tight text-white mb-2">
          Welcome to Roohi [PROTOTYPE]
        </h2>

        {/* Subtitle */}
        <p className="text-sm text-[#8b949e] leading-relaxed mb-6">
          Your AI Assistant OS Layer prototype has been prepared and built-in demo modules are initialized.
        </p>

        {/* Highlights */}
        <div className="bg-[#161b22] border border-[#30363d] rounded-xl p-4 text-left space-y-2.5 mb-8 text-xs text-[#c9d1d9]">
          <div className="flex items-center gap-2">
            <CheckCircle className="w-4 h-4 text-[#7ee787] shrink-0" />
            <span>Modules A → Ω architecture matrix registered</span>
          </div>
          <div className="flex items-center gap-2">
            <CheckCircle className="w-4 h-4 text-[#7ee787] shrink-0" />
            <span>Local prototype storage initialized</span>
          </div>
          <div className="flex items-center gap-2">
            <CheckCircle className="w-4 h-4 text-[#7ee787] shrink-0" />
            <span>Ready for setup walkthrough</span>
          </div>
        </div>

        {/* Action Button */}
        <button
          onClick={onBeginSetup}
          className="w-full py-3.5 px-6 rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-500 hover:to-indigo-500 text-white font-bold text-sm shadow-lg flex items-center justify-center gap-2 transition-all cursor-pointer group"
        >
          Begin Setup Walkthrough <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
        </button>
      </motion.div>
    </div>
  );
};
