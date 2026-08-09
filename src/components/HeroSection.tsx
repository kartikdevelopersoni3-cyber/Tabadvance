import React from 'react';
import { motion } from 'motion/react';
import { Download, Sparkles, ShieldCheck, Cpu, Mic, Zap, AlertCircle, ArrowRight, Layers, FileArchive } from 'lucide-react';

interface HeroSectionProps {
  onStartActivation: () => void;
  onOpenIntro: () => void;
}

export const HeroSection: React.FC<HeroSectionProps> = ({ onStartActivation, onOpenIntro }) => {
  return (
    <div className="w-full flex flex-col items-center my-4 space-y-8 text-white">
      {/* Top OS Layer Banner */}
      <div className="w-full bg-[#0d1117] border border-[#30363d] rounded-3xl p-8 sm:p-12 relative overflow-hidden shadow-2xl">
        {/* Background Radial Glow */}
        <div className="absolute -top-24 -right-24 w-96 h-96 bg-blue-600/20 rounded-full blur-3xl pointer-events-none" />
        <div className="absolute -bottom-24 -left-24 w-96 h-96 bg-indigo-600/20 rounded-full blur-3xl pointer-events-none" />

        <div className="relative z-10 max-w-3xl mx-auto text-center space-y-6">
          {/* OS Layer Tag */}
          <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-blue-500/10 border border-blue-500/30 text-blue-400 text-xs font-mono font-bold uppercase tracking-wider">
            <Layers className="w-4 h-4 animate-pulse" /> AI Assistant OS Layer • v1.0.4 Native
          </div>

          {/* Hero Title */}
          <h1 className="text-3xl sm:text-5xl font-extrabold tracking-tight text-white leading-tight">
            Roohi AI Assistant OS Layer
          </h1>

          {/* Subtitle */}
          <p className="text-base sm:text-lg text-[#8b949e] leading-relaxed max-w-2xl mx-auto">
            Transform your Android tablet into an intelligent AI workspace. Install the Roohi OS Layer once and unlock voice assistance, automation, memory, knowledge, and future AI capabilities from one platform.
          </p>

          {/* Primary Action Button */}
          <div className="pt-4 flex flex-col sm:flex-row items-center justify-center gap-4">
            <a
              href="/roohi_apk.zip"
              download="roohi_apk.zip"
              className="w-full sm:w-auto px-8 py-4 rounded-2xl bg-gradient-to-r from-emerald-500 via-teal-500 to-blue-600 hover:from-emerald-400 hover:to-blue-500 text-white font-extrabold text-base shadow-2xl flex items-center justify-center gap-3 transition-all cursor-pointer transform hover:scale-[1.02] active:scale-[0.98] group"
            >
              <Download className="w-5 h-5 group-hover:translate-y-0.5 transition-transform" />
              Download Roohi APK Package (roohi_apk.zip)
            </a>

            <button
              onClick={onStartActivation}
              className="w-full sm:w-auto px-6 py-4 rounded-2xl bg-[#161b22] hover:bg-[#21262d] border border-[#30363d] text-[#c9d1d9] font-bold text-sm flex items-center justify-center gap-2 transition-all cursor-pointer"
            >
              Activate Wizard <ArrowRight className="w-4 h-4" />
            </button>
          </div>

          <div className="text-[11px] font-mono text-[#8b949e] pt-1">
            One button. One automated process. Packages all 14 core AI engines & configures setup.
          </div>
        </div>
      </div>

      {/* Feature Pillars Grid */}
      <div className="w-full grid grid-cols-1 sm:grid-cols-3 gap-4">
        <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] space-y-3">
          <div className="w-10 h-10 rounded-xl bg-blue-500/10 border border-blue-500/30 flex items-center justify-center text-blue-400">
            <Mic className="w-5 h-5" />
          </div>
          <h3 className="font-bold text-base text-white">Voice & Conversation</h3>
          <p className="text-xs text-[#8b949e] leading-relaxed">
            Continuous wake word listener ("Hey Roohi") with biometric speaker verification and real-time speech processing.
          </p>
        </div>

        <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] space-y-3">
          <div className="w-10 h-10 rounded-xl bg-purple-500/10 border border-purple-500/30 flex items-center justify-center text-purple-400">
            <Cpu className="w-5 h-5" />
          </div>
          <h3 className="font-bold text-base text-white">Multi-Engine Intelligence</h3>
          <p className="text-xs text-[#8b949e] leading-relaxed">
            14 autonomous system modules (Modules A → Ω) for memory indexing, reasoning, automation studio, and vision.
          </p>
        </div>

        <div className="p-6 rounded-2xl bg-[#0d1117] border border-[#30363d] space-y-3">
          <div className="w-10 h-10 rounded-xl bg-emerald-500/10 border border-emerald-500/30 flex items-center justify-center text-emerald-400">
            <ShieldCheck className="w-5 h-5" />
          </div>
          <h3 className="font-bold text-base text-white">Privacy First</h3>
          <p className="text-xs text-[#8b949e] leading-relaxed">
            Choose between 100% on-device offline models, cloud API access, or a smart hybrid configuration.
          </p>
        </div>
      </div>

      {/* Important Android Device Runtime Notice */}
      <div className="w-full p-6 rounded-2xl bg-amber-950/20 border border-amber-500/30 flex flex-col sm:flex-row items-start gap-4">
        <div className="p-3 bg-amber-500/20 border border-amber-500/40 rounded-xl text-amber-400 shrink-0">
          <AlertCircle className="w-6 h-6" />
        </div>
        <div className="space-y-1">
          <h4 className="text-sm font-bold text-amber-200">Important Note for Standard Android Devices</h4>
          <p className="text-xs text-amber-100/80 leading-relaxed">
            If this is intended to run on standard Android devices, the Download & Activate button installs or provides the runtime package. Android requires an initial installable application runtime (APK / package) to register operating system layer privileges. Once initialized, Roohi handles ongoing capability upgrades and module additions internally.
          </p>
          <div className="pt-2 flex items-center gap-3">
            <a
              href="/roohi_apk.zip"
              download="roohi_apk.zip"
              className="inline-flex items-center gap-1.5 text-xs font-mono font-bold text-amber-300 hover:underline cursor-pointer"
            >
              <FileArchive className="w-3.5 h-3.5" /> Download Full APK & Project Source Package (roohi_apk.zip)
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};
