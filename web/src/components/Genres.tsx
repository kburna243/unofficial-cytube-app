import Reveal from './Reveal'
import genreSplash from '../assets/genre-splash.webp'

export interface Genre {
  name: string
  desc: string
  color: string
  bgPos: string
  channel: number
}

export const GENRES: Genre[] = [
  { name: 'ACTION', desc: 'Explosions, one-liners, heroes without insurance.', color: '#ff3b30', bgPos: '0% 0%', channel: 1 },
  { name: 'HORROR', desc: 'Haunted houses, slashfests and things in the basement.', color: '#8bd42a', bgPos: '36% 8%', channel: 2 },
  { name: 'SCI-FI', desc: 'Lasers, aliens and futures made of cardboard.', color: '#d9d9d9', bgPos: '100% 0%', channel: 3 },
  { name: 'THRILLER', desc: 'Calls at 3 a.m. Do not answer.', color: '#f5a623', bgPos: '0% 62%', channel: 4 },
  { name: 'EXPLOITATION', desc: 'Oil, dust and films with zero shame.', color: '#ff7a1a', bgPos: '100% 55%', channel: 5 },
  { name: 'SERIES', desc: 'Cheap sets, expensive cliffhangers.', color: '#3aa7ff', bgPos: '8% 100%', channel: 6 },
  { name: 'COMEDY', desc: 'A fly on the lens? Intentional. Probably.', color: '#ffd23a', bgPos: '52% 100%', channel: 7 },
  { name: 'ANIME', desc: 'Neon, swords and at least one tournament.', color: '#b44df0', bgPos: '86% 100%', channel: 8 },
]

export default function Genres() {
  const zapTo = (channel: number) => {
    document.getElementById('zapper')?.scrollIntoView({ behavior: 'smooth' })
    setTimeout(() => {
      window.dispatchEvent(new CustomEvent('cytube-zap', { detail: channel }))
    }, 700)
  }

  return (
    <section id="kino" className="relative mx-auto max-w-7xl px-6 py-28 md:py-36">
      <Reveal>
        <p className="mb-3 text-center font-crt text-xl tracking-[0.35em] text-[#39ff14]">
          ▚ TV GUIDE
        </p>
        <h2
          className="glitch font-display mb-4 text-center text-4xl text-white md:text-6xl"
          data-text="THE MIDNIGHT CINEMA"
        >
          THE MIDNIGHT CINEMA
        </h2>
        <p className="mx-auto mb-16 max-w-2xl text-center text-zinc-400">
          Eight genres, one couch, no mercy. CyTube rooms run around the clock —
          pick your genre and zap straight into the channel.
        </p>
      </Reveal>

      <div className="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4 md:gap-6">
        {GENRES.map((g, i) => (
          <Reveal key={g.name} delay={i * 70}>
            <button
              onClick={() => zapTo(g.channel)}
              className="group relative block w-full aspect-[3/4] overflow-hidden rounded-2xl border border-white/10 text-left transition-all duration-300 hover:-translate-y-2 hover:border-white/30 focus:outline-none focus-visible:ring-2 focus-visible:ring-fuchsia-400"
              style={{ boxShadow: '0 10px 40px rgba(0,0,0,0.5)' }}
            >
              {/* Bildausschnitt aus dem Splashscreen */}
              <div
                className="absolute inset-0 transition-transform duration-500 group-hover:scale-110"
                style={{
                  backgroundImage: `url(${genreSplash})`,
                  backgroundSize: '420% auto',
                  backgroundPosition: g.bgPos,
                }}
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black via-black/35 to-transparent" />
              <div
                className="absolute inset-0 opacity-0 transition-opacity duration-300 group-hover:opacity-100"
                style={{ boxShadow: `inset 0 0 60px ${g.color}55` }}
              />

              <span className="font-crt absolute left-3 top-3 rounded bg-black/70 px-2 py-0.5 text-sm tracking-widest text-zinc-300">
                CH {String(i + 1).padStart(2, '0')}
              </span>

              <div className="absolute inset-x-0 bottom-0 p-4">
                <h3
                  className="chroma font-display text-xl leading-tight md:text-2xl"
                  style={{ color: g.color }}
                >
                  {g.name}
                </h3>
                <p className="mt-1.5 text-xs leading-snug text-zinc-300/90 opacity-0 transition-all duration-300 group-hover:opacity-100 md:text-sm">
                  {g.desc}
                </p>
                <p className="mt-2 font-crt text-sm tracking-widest text-fuchsia-300 opacity-0 transition-all duration-300 group-hover:opacity-100">
                  ▶ ZAP NOW
                </p>
              </div>
            </button>
          </Reveal>
        ))}
      </div>
    </section>
  )
}
