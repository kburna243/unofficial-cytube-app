import { useCallback, useEffect, useState } from 'react'
import LoadingIntro from '../components/LoadingIntro'
import Hero from '../components/Hero'
import Marquee from '../components/Marquee'
import Genres from '../components/Genres'
import Zapper from '../components/Zapper'
import Features from '../components/Features'
import Manifesto from '../components/Manifesto'
import Download from '../components/Download'
import Footer from '../components/Footer'

const TICKER_ITEMS = [
  'GRINDHOUSE',
  'MIDNIGHT MOVIES',
  'B-MOVIES',
  'EXPLOITATION',
  '24/7 MARATHONS',
  'VHS AESTHETICS',
  'SYNCED CINEMA',
  'CULT CLASSICS',
  'NO ALGORITHM',
]

export default function Home() {
  const [loading, setLoading] = useState(true)

  const handleDone = useCallback(() => setLoading(false), [])

  // Scroll während des Intros sperren
  useEffect(() => {
    document.body.style.overflow = loading ? 'hidden' : ''
    return () => {
      document.body.style.overflow = ''
    }
  }, [loading])

  return (
    <div className="relative min-h-screen bg-[#06030a] text-zinc-100">
      {loading && <LoadingIntro onDone={handleDone} />}

      {/* Globale CRT-Effekte */}
      <div className="grain-overlay" />
      <div className="crt-vignette" />

      <Hero />

      <Marquee
        items={TICKER_ITEMS}
        className="border-y border-[#ff2d95]/30 bg-[#0c0714]/90 py-3.5 font-display text-sm tracking-widest text-zinc-200 md:text-base"
      />

      <Genres />

      <Marquee
        items={[...TICKER_ITEMS].reverse()}
        reverse
        duration={34}
        className="border-y border-purple-500/25 bg-[#0a0614]/90 py-3 font-crt text-xl tracking-[0.25em] text-purple-300/80"
      />

      <Zapper />

      <Manifesto />

      <Features />

      <Download />

      <Footer />
    </div>
  )
}
