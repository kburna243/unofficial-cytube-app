import { useCallback, useEffect, useRef, useState } from 'react'
import gsap from 'gsap'
import { ChevronDown, ChevronRight, ChevronUp, Info, ListVideo } from 'lucide-react'
import genreSplash from '../assets/genre-splash.webp'

interface Channel {
  name: string
  badge: string
  color: string
  glow: string
  bgPos: string
  nowPlaying: string
  meta: string
  trivia: string
  upNext: string[]
}

const CHANNELS: Channel[] = [
  {
    name: '420 GRINDHOUSE',
    badge: 'HOME',
    color: '#ff2d95',
    glow: 'rgba(255,45,149,0.35)',
    bgPos: '50% 50%',
    nowPlaying: 'Midnight Marathon: Best of Trash',
    meta: 'ON AIR SINCE 2012 · 24/7',
    trivia: 'The channel has been running non-stop for over a decade — the longest cinema screening in the world.',
    upNext: ['Steel Fist at Midnight (1987)', 'The House on Blood Lake (1974)', 'Highway of the Damned (1976)'],
  },
  {
    name: 'ACTION ALL NIGHT',
    badge: 'ACTION',
    color: '#ff3b30',
    glow: 'rgba(255,59,48,0.35)',
    bgPos: '0% 0%',
    nowPlaying: 'Steel Fist at Midnight',
    meta: '1987 · 96 MIN · RATED R',
    trivia: 'The lead did all his own stunts — the insurance company only found out during the end credits.',
    upNext: ['Return of the Steel Fist (1989)', 'Helicopter over L.A. (1985)', 'One Man, One Tank (1991)'],
  },
  {
    name: 'HORROR VAULT',
    badge: 'HORROR',
    color: '#8bd42a',
    glow: 'rgba(139,212,42,0.3)',
    bgPos: '36% 8%',
    nowPlaying: 'The House on Blood Lake',
    meta: '1974 · 88 MIN · RATED R',
    trivia: 'The "blood" was syrup. The location was actually demolished in 1979 — after filming wrapped. Allegedly.',
    upNext: ['It Whispers in the Basement (1978)', 'Night of the Living Statues (1981)', 'Cemetery of Dolls (1973)'],
  },
  {
    name: 'SCI-FI STATION',
    badge: 'SCI-FI',
    color: '#d9d9d9',
    glow: 'rgba(217,217,217,0.25)',
    bgPos: '100% 0%',
    nowPlaying: 'Robot Aliens from Planet X',
    meta: '1962 · 74 MIN · B/W',
    trivia: 'The spaceships were soup plates on fishing line. The effect has never been topped.',
    upNext: ['Invasion of the Brain Eaters (1965)', 'Mars Colony Zero (1971)', 'The Last Android (1984)'],
  },
  {
    name: 'THRILLER THEATER',
    badge: 'THRILLER',
    color: '#f5a623',
    glow: 'rgba(245,166,35,0.3)',
    bgPos: '0% 62%',
    nowPlaying: 'The 3 A.M. Caller',
    meta: '1981 · 101 MIN · PG-16',
    trivia: 'The phone really rang on set — a wrong number. The take stayed in the film.',
    upNext: ['No Witness Survives (1979)', 'Down the Staircase (1983)', 'Call from Beyond (1977)'],
  },
  {
    name: 'EXPLOITATION EXPRESS',
    badge: 'XPLOIT',
    color: '#ff7a1a',
    glow: 'rgba(255,122,26,0.3)',
    bgPos: '100% 55%',
    nowPlaying: 'Highway of the Damned',
    meta: '1976 · 92 MIN · UNRATED',
    trivia: 'Shot in 11 days, edited in 3. The film escaped censorship thanks to a typo in the title.',
    upNext: ['Oilfield of Revenge (1978)', 'Desert Wolves (1975)', 'Highway to Nowhere (1980)'],
  },
  {
    name: 'SERIES SALOON',
    badge: 'SERIES',
    color: '#3aa7ff',
    glow: 'rgba(58,167,255,0.3)',
    bgPos: '8% 100%',
    nowPlaying: 'Captain Cosmos & the Snail Monster',
    meta: 'SEASON 2 · EPISODE 7 · 1983',
    trivia: 'The snail monster costume was sewn from a carpet. It sits in a museum today.',
    upNext: ['Episode 8: Revenge of the Snail', 'Episode 9: Double Snail', 'Season Finale: Snail X'],
  },
  {
    name: 'COMEDY CLUB',
    badge: 'COMEDY',
    color: '#ffd23a',
    glow: 'rgba(255,210,58,0.3)',
    bgPos: '52% 100%',
    nowPlaying: 'The Nerd Who Knew Too Much',
    meta: '1985 · 94 MIN · PG',
    trivia: "The lead's glasses had no lenses — they would have reflected the studio lights.",
    upNext: ['Exam Panic (1986)', 'The Class Clown Strikes Back (1988)', 'Detention at Midnight (1984)'],
  },
  {
    name: 'ANIME ARENA',
    badge: 'ANIME',
    color: '#b44df0',
    glow: 'rgba(180,77,240,0.35)',
    bgPos: '86% 100%',
    nowPlaying: 'Sword of the Neon Samurai',
    meta: '1993 · OVA · 58 MIN',
    trivia: 'Every sword scene was hand-drawn — over 4,000 individual frames for the fights alone.',
    upNext: ['Neon Samurai II: The Return', 'Katanas over Tokyo (1995)', 'Tournament of Shadows (1991)'],
  },
]

const CHAT_POOL: [string, string][] = [
  ['NightHawk88', 'this movie is an absolute masterpiece, no objections'],
  ['VHS_Vicky', 'the best scene is coming up, everyone quiet please'],
  ['GrindhouseGerd', 'who keeps zapping?? it is 3 a.m., stay!'],
  ['CelluloidZoe', 'that mask looks like my toaster and i love it'],
  ['MidnightMike', 'fun fact: the director also delivered the pizza'],
  ['TapeTom', 'REWIND! REWIND! REWIND!'],
  ['CinemaKat', 'this dialogue was definitely improvised on set'],
  ['SlasherSam', '10/10 would watch again at 4 a.m.'],
  ['PixelPaul', 'the chat is synced, the movie is synced, my life is not'],
  ['RetroRita', 'popcorn is gone. i repeat: popcorn is gone.'],
]

export default function Zapper() {
  const [channelIdx, setChannelIdx] = useState(0)
  const [showQueue, setShowQueue] = useState(false)
  const [showTrivia, setShowTrivia] = useState(false)
  const [zapFlash, setZapFlash] = useState(0)
  const [msgIdx, setMsgIdx] = useState(0)
  const osdRef = useRef<HTMLDivElement>(null)
  const msgRef = useRef<HTMLDivElement>(null)

  const channel = CHANNELS[channelIdx]

  const zap = useCallback((next: number) => {
    setChannelIdx(((next % CHANNELS.length) + CHANNELS.length) % CHANNELS.length)
    setZapFlash((f) => f + 1)
    setShowTrivia(false)
  }, [])

  // Keyboard controls, just like the TV remote
  useEffect(() => {
    const onKey = (e: KeyboardEvent) => {
      if (e.key === 'ArrowUp') { zap(channelIdx - 1); e.preventDefault() }
      if (e.key === 'ArrowDown') { zap(channelIdx + 1); e.preventDefault() }
      if (e.key === 'ArrowRight') setShowQueue((s) => !s)
      if (e.key === 'ArrowLeft') setShowTrivia((s) => !s)
    }
    window.addEventListener('keydown', onKey)
    return () => window.removeEventListener('keydown', onKey)
  }, [channelIdx, zap])

  // Genre cards can zap straight here
  useEffect(() => {
    const onExternal = (e: Event) => {
      const detail = (e as CustomEvent<number>).detail
      if (typeof detail === 'number') zap(detail)
    }
    window.addEventListener('cytube-zap', onExternal)
    return () => window.removeEventListener('cytube-zap', onExternal)
  }, [zap])

  // OSD banner on every zap
  useEffect(() => {
    if (!osdRef.current) return
    gsap.fromTo(
      osdRef.current,
      { yPercent: -140, opacity: 0 },
      { yPercent: 0, opacity: 1, duration: 0.45, ease: 'back.out(1.6)' },
    )
    gsap.to(osdRef.current, { opacity: 0, yPercent: -140, delay: 2.6, duration: 0.5, ease: 'power2.in' })
  }, [channelIdx, zapFlash])

  // Chat ticker
  useEffect(() => {
    const t = setInterval(() => setMsgIdx((i) => i + 1), 3400)
    return () => clearInterval(t)
  }, [])
  useEffect(() => {
    if (!msgRef.current) return
    gsap.fromTo(
      msgRef.current,
      { x: 80, opacity: 0 },
      { x: 0, opacity: 1, duration: 0.5, ease: 'power3.out' },
    )
  }, [msgIdx])

  const [user, text] = CHAT_POOL[msgIdx % CHAT_POOL.length]

  return (
    <section id="zapper" className="relative mx-auto max-w-6xl px-6 py-28 md:py-36">
      <p className="mb-3 text-center font-crt text-xl tracking-[0.35em] text-[#ff2d95]">
        ▚ INTERACTIVE — GIVE IT A TRY
      </p>
      <h2
        className="glitch font-display mb-4 text-center text-4xl text-white md:text-6xl"
        data-text="ZAP YOUR WAY THROUGH"
      >
        ZAP YOUR WAY THROUGH
      </h2>
      <p className="mx-auto mb-14 max-w-2xl text-center text-zinc-400">
        This is exactly how the app feels: <span className="font-crt text-lg text-[#39ff14]">▲ ▼</span> on
        your keyboard (or the buttons) to zap, <span className="font-crt text-lg text-[#39ff14]">◄</span> for
        trivia, <span className="font-crt text-lg text-[#39ff14]">►</span> for the queue.
      </p>

      {/* TV frame */}
      <div
        className="relative rounded-[2rem] border-2 border-purple-500/50 bg-[#0a0612] p-3 md:p-5"
        style={{ boxShadow: `0 0 80px ${channel.glow}, inset 0 0 40px rgba(0,0,0,0.8)` }}
      >
        <div className="scanlines relative aspect-video overflow-hidden rounded-2xl bg-black">
          {/* Channel picture */}
          <div
            key={channelIdx}
            className="absolute inset-0 transition-all duration-300"
            style={{
              backgroundImage: `url(${genreSplash})`,
              backgroundSize: '300% auto',
              backgroundPosition: channel.bgPos,
              filter: 'brightness(0.72) saturate(1.25)',
            }}
          />
          <div
            className="absolute inset-0"
            style={{
              background: `radial-gradient(ellipse at center, transparent 20%, rgba(0,0,0,0.85) 100%), linear-gradient(to top, rgba(0,0,0,0.9), transparent 55%)`,
            }}
          />

          {/* Zap static flash */}
          <div
            key={`flash-${zapFlash}`}
            className="static-noise pointer-events-none absolute inset-0 z-20"
            style={{ animation: 'zapOut 0.45s ease-out forwards' }}
          />
          <style>{`@keyframes zapOut { from { opacity: 0.85; } to { opacity: 0; } }`}</style>

          {/* OSD banner */}
          <div
            ref={osdRef}
            className="absolute left-4 right-4 top-4 z-30 flex items-center justify-between rounded-xl border border-white/15 bg-black/75 px-4 py-3 backdrop-blur-md md:left-6 md:right-6"
          >
            <div className="flex items-center gap-3">
              <span
                className="font-display rounded-lg px-3 py-1 text-xs tracking-wider text-black md:text-sm"
                style={{ backgroundColor: channel.color }}
              >
                {channel.badge}
              </span>
              <div>
                <p className="font-crt text-lg leading-none text-white md:text-2xl">
                  CH {String(channelIdx).padStart(2, '0')} — {channel.name}
                </p>
                <p className="mt-1 text-xs text-zinc-300 md:text-sm">▶ {channel.nowPlaying}</p>
              </div>
            </div>
            <span className="hidden items-center gap-2 font-crt text-lg text-red-400 md:flex">
              <span className="live-dot inline-block h-2 w-2 rounded-full bg-red-500" /> LIVE
            </span>
          </div>

          {/* Movie title, bottom */}
          <div className="absolute bottom-16 left-6 right-6 z-10 md:bottom-20">
            <h3 className="font-display text-2xl leading-tight text-white drop-shadow-[0_2px_12px_rgba(0,0,0,0.9)] md:text-4xl">
              {channel.nowPlaying}
            </h3>
            <p className="mt-1 font-crt text-base tracking-widest md:text-xl" style={{ color: channel.color }}>
              {channel.meta}
            </p>
          </div>

          {/* Chat ticker */}
          <div className="absolute inset-x-0 bottom-0 z-10 border-t border-white/10 bg-black/70 px-4 py-2.5 backdrop-blur-sm">
            <div ref={msgRef} key={msgIdx} className="flex items-baseline gap-2 font-crt text-lg md:text-xl">
              <span style={{ color: channel.color }}>{user}:</span>
              <span className="truncate text-zinc-200">{text}</span>
            </div>
          </div>

          {/* Trivia overlay (◄) */}
          {showTrivia && (
            <div className="absolute inset-x-4 top-24 z-40 rounded-xl border border-[#39ff14]/40 bg-black/85 p-4 backdrop-blur-md md:inset-x-auto md:right-6 md:w-80">
              <p className="font-crt text-lg tracking-widest text-[#39ff14]">ⓘ MOVIE TRIVIA</p>
              <p className="mt-2 text-sm leading-relaxed text-zinc-200">{channel.trivia}</p>
            </div>
          )}

          {/* Queue overlay (►) */}
          {showQueue && (
            <div className="absolute inset-y-4 right-4 z-40 w-64 rounded-xl border border-purple-400/40 bg-black/85 p-4 backdrop-blur-md md:right-6">
              <p className="font-crt flex items-center gap-2 text-lg tracking-widest text-purple-300">
                <ListVideo className="h-4 w-4" /> UP NEXT
              </p>
              <ul className="mt-3 space-y-2.5">
                {channel.upNext.map((t, i) => (
                  <li key={t} className="flex gap-2 text-xs leading-snug text-zinc-300 md:text-sm">
                    <span className="font-crt text-purple-400">{String(i + 1).padStart(2, '0')}</span>
                    {t}
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>

        {/* Controls */}
        <div className="mt-5 flex flex-col items-center justify-between gap-4 md:flex-row">
          <div className="flex items-center gap-2">
            <button onClick={() => zap(channelIdx - 1)} className="dpad-btn h-12 w-12" aria-label="Channel up">
              <ChevronUp className="h-6 w-6" />
            </button>
            <button onClick={() => zap(channelIdx + 1)} className="dpad-btn h-12 w-12" aria-label="Channel down">
              <ChevronDown className="h-6 w-6" />
            </button>
            <button
              onClick={() => setShowTrivia((s) => !s)}
              className={`dpad-btn h-12 w-12 ${showTrivia ? 'border-[#39ff14] text-[#39ff14]' : ''}`}
              aria-label="Trivia"
            >
              <Info className="h-5 w-5" />
            </button>
            <button
              onClick={() => setShowQueue((s) => !s)}
              className={`dpad-btn h-12 w-12 ${showQueue ? 'border-purple-400 text-purple-300' : ''}`}
              aria-label="Queue"
            >
              <ListVideo className="h-5 w-5" />
            </button>
          </div>
          <p className="font-crt text-center text-lg tracking-widest text-zinc-500 md:text-right">
            KEYBOARD: ▲▼ ZAP · ◄ TRIVIA · ► QUEUE
            <ChevronRight className="ml-1 inline h-4 w-4 text-[#39ff14]" />
          </p>
        </div>
      </div>
    </section>
  )
}
