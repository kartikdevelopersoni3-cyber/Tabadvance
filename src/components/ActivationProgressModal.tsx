import React, { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { Download, CheckCircle2, Loader2, Sparkles, AlertCircle, ShieldCheck } from 'lucide-react';
import { ActivationStep } from '../types';

interface ActivationProgressModalProps {
  isOpen: boolean;
  onComplete: () => void;
  onCancel: () => void;
}

const INITIAL_STEPS: ActivationStep[] = [
  { id: 1, label: "Download Roohi OS Layer Package", detail: "Fetching Roohi_Master_Export.zip (0.27 MB)...", status: 'pending' },
  { id: 2, label: "Verify Package Integrity", detail: "Checking SHA-256 signatures & AndroidManifest.xml...", status: 'pending' },
  { id: 3, label: "Install/Update Runtime", detail: "Applying native OS environment configuration...", status: 'pending' },
  { id: 4, label: "Configure Local AI Environment", detail: "Initializing on-device vector index & speech model...", status: 'pending' },
  { id: 5, label: "Create Roohi Workspace", detail: "Setting up tablet workspace directory & permissions...", status: 'pending' },
  { id: 6, label: "Register Built-in Modules", detail: "Binding Modules A → Ω (Voice, Memory, Reasoning, MultiAgent)...", status: 'pending' },
  { id: 7, label: "Launch Setup Wizard", detail: "Preparing personalizer guide module...", status: 'pending' }
];

export const ActivationProgressModal: React.FC<ActivationProgressModalProps> = ({ isOpen, onComplete, onCancel }) => {
  const [steps, setSteps] = useState<ActivationStep[]>(INITIAL_STEPS);
  const [currentStepIdx, setCurrentStepIdx] = useState(0);

  useEffect(() => {
    if (!isOpen) {
      setSteps(INITIAL_STEPS);
      setCurrentStepIdx(0);
      return;
    }

    let isSubscribed = true;

    const runProcess = async () => {
      for (let i = 0; i < INITIAL_STEPS.length; i++) {
        if (!isSubscribed) break;

        setCurrentStepIdx(i);
        setSteps(prev => prev.map((s, idx) => {
          if (idx < i) return { ...s, status: 'completed' };
          if (idx === i) return { ...s, status: 'active' };
          return { ...s, status: 'pending' };
        }));

        // Simulate step duration
        await new Promise(res => setTimeout(res, 800));
      }

      if (isSubscribed) {
        setSteps(prev => prev.map(s => ({ ...s, status: 'completed' })));
        setTimeout(() => {
          onComplete();
        }, 600);
      }
    };

    runProcess();

    return () => {
      isSubscribed = false;
    };
  }, [isOpen, onComplete]);

  if (!isOpen) return null;

  const currentStep = steps[currentStepIdx] || steps[steps.length - 1];

  return (
    <div className="fixed inset-0 z-50 bg-black/80 backdrop-blur-md flex items-center justify-center p-4">
      <motion.div
        initial={{ opacity: 0, scale: 0.95 }}
        animate={{ opacity: 1, scale: 1 }}
        exit={{ opacity: 0, scale: 0.95 }}
        className="w-full max-w-lg bg-[#0d1117] border border-[#30363d] rounded-2xl shadow-2xl overflow-hidden text-white flex flex-col"
      >
        {/* Modal Header */}
        <div className="p-6 border-b border-[#30363d] bg-[#161b22] flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-blue-500/10 border border-blue-500/30 flex items-center justify-center text-blue-400">
              <Download className="w-5 h-5 animate-bounce" />
            </div>
            <div>
              <h3 className="font-bold text-sm text-white">Activating Roohi OS Layer</h3>
              <p className="text-xs text-[#8b949e]">Automatic single-button installation flow</p>
            </div>
          </div>
          <span className="px-2.5 py-1 rounded bg-[#21262d] text-[#58a6ff] border border-[#30363d] text-[10px] font-mono font-bold">
            Step {Math.min(currentStepIdx + 1, 7)}/7
          </span>
        </div>

        {/* Modal Content */}
        <div className="p-6 space-y-4 max-h-[60vh] overflow-y-auto">
          {/* Active Highlight Card */}
          <div className="p-4 rounded-xl bg-[#161b22] border border-[#30363d] flex items-center gap-3">
            <Loader2 className="w-5 h-5 text-blue-400 animate-spin shrink-0" />
            <div>
              <div className="text-xs font-bold text-white">{currentStep.label}</div>
              <div className="text-[11px] text-[#8b949e] font-mono">{currentStep.detail}</div>
            </div>
          </div>

          {/* Progress Bar */}
          <div className="w-full bg-[#161b22] h-2 rounded-full overflow-hidden border border-[#30363d]">
            <motion.div
              className="bg-gradient-to-r from-blue-500 via-indigo-500 to-emerald-400 h-full"
              initial={{ width: "0%" }}
              animate={{ width: `${((currentStepIdx + 1) / 7) * 100}%` }}
              transition={{ duration: 0.3 }}
            />
          </div>

          {/* Steps List */}
          <div className="space-y-2 pt-2">
            {steps.map((step) => {
              const isDone = step.status === 'completed';
              const isActive = step.status === 'active';

              return (
                <div
                  key={step.id}
                  className={`p-2.5 rounded-lg border text-xs flex items-center justify-between transition-all ${
                    isDone
                      ? 'bg-emerald-950/20 border-emerald-800/40 text-emerald-300'
                      : isActive
                      ? 'bg-blue-950/30 border-blue-600/50 text-blue-200'
                      : 'bg-[#161b22]/50 border-[#21262d] text-[#484f58]'
                  }`}
                >
                  <div className="flex items-center gap-2.5">
                    {isDone ? (
                      <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
                    ) : isActive ? (
                      <Loader2 className="w-4 h-4 text-blue-400 animate-spin shrink-0" />
                    ) : (
                      <div className="w-4 h-4 rounded-full border border-[#30363d] text-[9px] flex items-center justify-center text-[#484f58]">
                        {step.id}
                      </div>
                    )}
                    <span className="font-medium">{step.label}</span>
                  </div>
                  <span className="text-[10px] font-mono opacity-70">
                    {isDone ? 'Done' : isActive ? 'Processing...' : 'Waiting'}
                  </span>
                </div>
              );
            })}
          </div>
        </div>

        {/* Modal Footer */}
        <div className="p-4 border-t border-[#30363d] bg-[#161b22] flex items-center justify-between text-xs text-[#8b949e]">
          <div className="flex items-center gap-1.5">
            <ShieldCheck className="w-4 h-4 text-emerald-400" />
            <span>Verified Package • Roohi_Master_Export.zip</span>
          </div>
          <button
            onClick={onCancel}
            className="px-3 py-1.5 rounded-lg border border-[#30363d] hover:bg-[#21262d] text-[#8b949e] hover:text-white transition-colors cursor-pointer"
          >
            Cancel
          </button>
        </div>
      </motion.div>
    </div>
  );
};
