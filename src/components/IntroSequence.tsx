import React, { useState } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { Tablet, ShieldCheck, Cpu, Sparkles, ChevronRight, ChevronLeft, ArrowRight, CheckCircle2 } from 'lucide-react';

interface IntroSequenceProps {
  onStartActivation: () => void;
  onSkipToHero: () => void;
}

const SCREENS = [
  {
    id: 1,
    badge: "Prototype OS Overview",
    title: "Your Tablet. Smarter Than Ever.",
    subtitle: "An AI operating layer designed to assist, automate, and learn seamlessly across all workflows.",
    icon: Tablet,
    accentColor: "from-blue-500 to-indigo-600",
    glowColor: "rgba(59, 130, 246, 0.25)",
    features: [
      "Deep integration with tablet hardware",
      "Always-available voice command interface",
      "Context-aware workflow automation"
    ]
  },
  {
    id: 2,
    badge: "Security & Privacy",
    title: "Private by Design.",
    subtitle: "Choose between 100% offline AI, high-performance cloud AI, or an intelligent hybrid setup.",
    icon: ShieldCheck,
    accentColor: "from-emerald-500 to-teal-600",
    glowColor: "rgba(16, 185, 129, 0.25)",
    features: [
      "Local vector memory stays on-device",
      "Encrypted biometric speaker verification",
      "Zero telemetry without explicit permission"
    ]
  },
  {
    id: 3,
    badge: "Modular Architecture",
    title: "Expandable Intelligence.",
    subtitle: "Add new capabilities as Roohi evolves—from vision analysis to multi-agent task execution—without starting over.",
    icon: Cpu,
    accentColor: "from-purple-500 to-violet-600",
    glowColor: "rgba(168, 85, 247, 0.25)",
    features: [
      "14 autonomous system engines (Modules A → Ω)",
      "Self-learning workspace automation rules",
      "Hot-swappable AI provider engines"
    ]
  },
  {
    id: 4,
    badge: "Prototype Exploration",
    title: "Ready to Begin?",
    subtitle: "Tap Download & Activate to install the AI Assistant OS Layer and initialize your personalized workspace.",
    icon: Sparkles,
    accentColor: "from-amber-500 to-orange-600",
    glowColor: "rgba(245, 158, 11, 0.25)",
    features: [
      "Automated single-click package installation",
      "Integrity verification and environment check",
      "Automatic launch into Setup Wizard"
    ]
  }
];

export const IntroSequence: React.FC<IntroSequenceProps> = ({ onStartActivation, onSkipToHero }) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const currentScreen = SCREENS[currentIndex];
  const IconComponent = currentScreen.icon;

  const handleNext = () => {
    if (currentIndex < SCREENS.length - 1) {
      setCurrentIndex(prev => prev + 1);
    } else {
      onStartActivation();
    }
  };

  const handlePrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex(prev => prev - 1);
    }
  };

  return (
    <div className="w-full min-h-[600px] flex flex-col justify-between p-6 sm:p-10 bg-[#0d1117] text-white rounded-2xl border border-[#30363d] shadow-2xl relative overflow-hidden my-4">
      {/* Background Subtle Gradient */}
      <div 
        className="absolute -top-32 -right-32 w-96 h-96 rounded-full blur-3xl opacity-20 pointer-events-none transition-all duration-700"
        style={{ background: currentScreen.glowColor }}
      />
      
      {/* Top Header Controls */}
      <div className="flex items-center justify-between z-10 mb-6">
        <div className="flex items-center gap-2">
          <span className="w-2.5 h-2.5 rounded-full bg-blue-500 animate-pulse"></span>
          <span className="text-xs font-mono tracking-widest text-[#8b949e] uppercase">Onboarding Sequence • {currentIndex + 1}/4</span>
        </div>
        <button
          onClick={onSkipToHero}
          className="text-xs text-[#8b949e] hover:text-white transition-colors cursor-pointer px-3 py-1.5 rounded-lg hover:bg-[#161b22]"
        >
          Skip Intro
        </button>
      </div>

      {/* Slide Content */}
      <div className="flex-1 flex flex-col justify-center items-center text-center my-6 z-10 max-w-2xl mx-auto w-full">
        <AnimatePresence mode="wait">
          <motion.div
            key={currentScreen.id}
            initial={{ opacity: 0, y: 15 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -15 }}
            transition={{ duration: 0.3 }}
            className="flex flex-col items-center"
          >
            {/* Icon Card */}
            <div className={`w-20 h-20 rounded-2xl bg-gradient-to-br ${currentScreen.accentColor} p-0.5 shadow-lg mb-6 flex items-center justify-center`}>
              <div className="w-full h-full bg-[#0d1117] rounded-[14px] flex items-center justify-center">
                <IconComponent className="w-10 h-10 text-white" />
              </div>
            </div>

            {/* Badge */}
            <span className="px-3 py-1 rounded-full text-[11px] font-bold tracking-wider uppercase bg-[#161b22] border border-[#30363d] text-[#58a6ff] mb-3">
              {currentScreen.badge}
            </span>

            {/* Title */}
            <h2 className="text-2xl sm:text-4xl font-bold tracking-tight text-white mb-4">
              {currentScreen.title}
            </h2>

            {/* Subtitle */}
            <p className="text-sm sm:text-base text-[#8b949e] leading-relaxed mb-8 max-w-xl">
              {currentScreen.subtitle}
            </p>

            {/* Feature Bullets */}
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 w-full text-left mb-4">
              {currentScreen.features.map((feat, idx) => (
                <div key={idx} className="p-3 bg-[#161b22] border border-[#30363d] rounded-xl flex items-start gap-2.5">
                  <CheckCircle2 className="w-4 h-4 text-[#7ee787] shrink-0 mt-0.5" />
                  <span className="text-xs text-[#c9d1d9] leading-snug">{feat}</span>
                </div>
              ))}
            </div>
          </motion.div>
        </AnimatePresence>
      </div>

      {/* Footer Navigation */}
      <div className="flex items-center justify-between z-10 pt-4 border-t border-[#30363d]">
        {/* Step Indicator Dots */}
        <div className="flex items-center gap-2">
          {SCREENS.map((s, idx) => (
            <button
              key={s.id}
              onClick={() => setCurrentIndex(idx)}
              className={`h-2 rounded-full transition-all cursor-pointer ${
                idx === currentIndex ? 'w-8 bg-[#58a6ff]' : 'w-2 bg-[#30363d] hover:bg-[#8b949e]'
              }`}
            />
          ))}
        </div>

        {/* Action Buttons */}
        <div className="flex items-center gap-3">
          {currentIndex > 0 && (
            <button
              onClick={handlePrev}
              className="px-4 py-2 rounded-xl border border-[#30363d] bg-[#161b22] hover:bg-[#21262d] text-white text-xs font-semibold flex items-center gap-1.5 transition-all cursor-pointer"
            >
              <ChevronLeft className="w-4 h-4" /> Back
            </button>
          )}

          <button
            onClick={handleNext}
            className={`px-6 py-2.5 rounded-xl text-xs font-bold flex items-center gap-2 transition-all cursor-pointer shadow-lg ${
              currentIndex === SCREENS.length - 1
                ? 'bg-gradient-to-r from-emerald-500 to-teal-600 hover:from-emerald-400 hover:to-teal-500 text-white'
                : 'bg-[#238636] hover:bg-[#2ea043] text-white'
            }`}
          >
            {currentIndex === SCREENS.length - 1 ? (
              <>
                <Sparkles className="w-4 h-4 animate-spin" /> Download & Activate
              </>
            ) : (
              <>
                Next <ChevronRight className="w-4 h-4" />
              </>
            )}
          </button>
        </div>
      </div>
    </div>
  );
};
