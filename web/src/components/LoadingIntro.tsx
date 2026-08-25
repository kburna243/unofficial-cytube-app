import { useEffect, useRef, useState } from 'react'
import gsap from 'gsap'

const BOOT_LINES = [
  'REWINDING VHS TAPE …',
  'SYNCHRONIZING CHANNELS …',
  'CONNECTING TO CYTU.BE …',
  'POLISHING THE POPCORN …',
  'DIMMING THE LIGHTS …',
]

export default function LoadingIntro({ onDone }: { onDone: () => void }) {
  const [progress, setProgress] = useState(0)
  const overlayRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    let p = 0
    const timer = setInterval(() => {
      p = Math.min(100, p + Math.random() * 9 + 2)
      setProgress(Math.floor(p))
      if (p >= 100) {
        clearInterval(timer)
        setTimeout(() => {
          gsap.to(overlayRef.current, {
            yPercent: -100,
            duration: 1,
            ease: 'power4.inOut',
            onComplete: onDone,
          })
        }, 650)
      }
    }, 130)
    return () => clearInterval(timer)
  }, [onDone])

  const line = BOOT_LINES[Math.min(BOOT_LINES.length - 1, Math.floor(progress / (100 / BOOT_LINES.length)))]

  return (
    <div
      ref={overlayRef}
      className="fixed inset-0 z-[100] flex flex-col items-center justify-center bg-[#050308]"
    >
      <div className="relative w-[min(92vw,900px)]">
        {/* Loading-Bar Asset */}
        <img
          src="/assets/loading-bar.webp"
          alt="Loading"
          className="w-full select-none"
          draggable={false}
        />
        {/* Maske, die den grünen Balken synchron zum Fortschritt „auffüllt" */}
        <div
          className="absolute bg-[#050308]"
          style={{
            top: '62%',
            height: '27%',
            left: `${6.5 + 59 * (progress / 100)}%`,
            width: `${59 * (1 - progress / 100)}%`,
          }}
        />
      </div>

      <div className="mt-6 flex w-[min(92vw,900px)] items-end justify-between font-crt text-2xl md:text-3xl">
        <span className="neon-green">{line}</span>
        <span className="neon-pink text-4xl md:text-5xl">{progress}%</span>
      </div>
      <p className="mt-10 font-crt text-lg tracking-widest text-purple-300/50">
        [ IGNORE ANY KEY — THE MOVIE STARTS SOON ]
      </p>
    </div>
  )
}
