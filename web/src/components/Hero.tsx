import { useEffect, useRef } from 'react'
import gsap from 'gsap'
import { ChevronDown, Github } from 'lucide-react'

export default function Hero() {
  const bgRef = useRef<HTMLDivElement>(null)
  const logoRef = useRef<HTMLImageElement>(null)
  const contentRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    // Sanfter Ken-Burns-Zoom auf dem Genre-Splash
    gsap.to(bgRef.current, {
      scale: 1.12,
      duration: 24,
      ease: 'sine.inOut',
      yoyo: true,
      repeat: -1,
    })

    // Parallaxe folgt der Maus
    const xTo = gsap.quickTo(bgRef.current, 'x', { duration: 0.9, ease: 'power3.out' })
    const yTo = gsap.quickTo(bgRef.current, 'y', { duration: 0.9, ease: 'power3.out' })
    const lxTo = gsap.quickTo(logoRef.current, 'x', { duration: 1.1, ease: 'power3.out' })
    const lyTo = gsap.quickTo(logoRef.current, 'y', { duration: 1.1, ease: 'power3.out' })

    const onMove = (e: MouseEvent) => {
      const nx = e.clientX / window.innerWidth - 0.5
      const ny = e.clientY / window.innerHeight - 0.5
      xTo(nx * -34)
      yTo(ny * -22)
      lxTo(nx * 20)
      lyTo(ny * 12)
    }
    window.addEventListener('mousemove', onMove)

    gsap.from(contentRef.current?.children ?? [], {
      y: 60,
      opacity: 0,
      stagger: 0.14,
      duration: 1.1,
      ease: 'power3.out',
      delay: 0.3,
    })

    return () => window.removeEventListener('mousemove', onMove)
  }, [])

  return (
    <header className="relative flex min-h-screen flex-col items-center justify-center overflow-hidden">
      {/* Hintergrund: Genre-Splash */}
      <div ref={bgRef} className="absolute inset-[-6%] will-change-transform">
        <img
          src="/assets/genre-splash.webp"
          alt=""
          className="h-full w-full object-cover opacity-60"
          draggable={false}
        />
      </div>
      <div className="absolute inset-0 bg-gradient-to-b from-[#06030a]/70 via-[#06030a]/35 to-[#06030a]" />
      <div className="absolute inset-0 bg-[radial-gradient(ellipse_at_center,transparent_30%,rgba(6,3,10,0.75)_100%)]" />
      <div className="tracking-bar" />

      {/* LIVE-Badge */}
      <div className="absolute right-6 top-6 z-20 flex items-center gap-2 rounded-md border border-red-500/50 bg-black/60 px-3 py-1.5 font-crt text-xl text-red-400 backdrop-blur-sm md:right-10 md:top-10">
        <span className="live-dot inline-block h-2.5 w-2.5 rounded-full bg-red-500" />
        ON AIR — 24/7
      </div>

      {/* Inhalt */}
      <div ref={contentRef} className="relative z-10 flex flex-col items-center px-6 text-center">
        <p className="mb-6 font-crt text-xl tracking-[0.35em] text-purple-300/90 md:text-2xl">
          A HOMAGE TO CYTUBE
        </p>

        <img
          ref={logoRef}
          src="/assets/cytube-app-logo.webp"
          alt="CyTube App"
          className="flicker w-[min(80vw,640px)] select-none will-change-transform"
          style={{
            filter:
              'drop-shadow(0 0 18px rgba(255,45,149,0.55)) drop-shadow(0 0 60px rgba(139,47,255,0.45))',
          }}
          draggable={false}
        />

        <h1 className="sr-only">CyTube TV — A Homage to Midnight Cinema</h1>

        <p className="mt-8 max-w-2xl text-balance text-lg text-zinc-300 md:text-xl">
          … and to the cinema that never sleeps. Grindhouse, B-movies, horror and
          sci-fi trash — watched in sync, around the clock, together from the couch.
        </p>

        <div className="mt-10 flex flex-wrap items-center justify-center gap-4">
          <a
            href="#kino"
            className="font-display rounded-xl border-2 border-[#ff2d95] bg-[#ff2d95]/10 px-7 py-3.5 text-sm tracking-wider text-[#ff7fbd] transition-all duration-300 hover:bg-[#ff2d95] hover:text-white hover:shadow-[0_0_40px_rgba(255,45,149,0.6)]"
          >
            ENTER THE CINEMA
          </a>
          <a
            href="https://github.com/kburna243/unofficial-cytube-app"
            target="_blank"
            rel="noreferrer"
            className="font-display flex items-center gap-2.5 rounded-xl border-2 border-[#39ff14]/60 bg-[#39ff14]/5 px-7 py-3.5 text-sm tracking-wider text-[#39ff14] transition-all duration-300 hover:bg-[#39ff14] hover:text-black hover:shadow-[0_0_40px_rgba(57,255,20,0.5)]"
          >
            <Github className="h-4 w-4" />
            GET THE APP
          </a>
        </div>
      </div>

      {/* Scroll-Hinweis */}
      <div className="absolute bottom-8 z-10 flex flex-col items-center gap-1 text-purple-300/70">
        <span className="font-crt text-lg tracking-[0.3em]">SCROLL</span>
        <ChevronDown className="scroll-hint h-5 w-5" />
      </div>
    </header>
  )
}
