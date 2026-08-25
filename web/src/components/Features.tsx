import Reveal from './Reveal'
import {
  LayoutGrid, Zap, PlusSquare, Radio, Clapperboard, MessageSquareText, Info, ListVideo,
} from 'lucide-react'

const FEATURES = [
  {
    icon: LayoutGrid,
    title: '10-Foot Channel Hub',
    desc: 'Visual grid with live rooms, active badges and one-click access to the top communities — readable from the couch.',
    color: '#ff2d95',
  },
  {
    icon: Zap,
    title: 'Real-Time D-Pad Zapping',
    desc: 'Switch channels mid-playback with ▲ / ▼. An animated OSD banner shows the channel, badge and movie title.',
    color: '#39ff14',
  },
  {
    icon: PlusSquare,
    title: 'Custom Rooms',
    desc: 'Add, manage and delete any CyTube channel (cytu.be/r/your-room) directly on your TV.',
    color: '#b565ff',
  },
  {
    icon: Radio,
    title: 'Live Public Directory',
    desc: 'Automatically syncs the public channels from cytu.be on startup — with 420 Grindhouse as the home channel.',
    color: '#3aa7ff',
  },
  {
    icon: Clapperboard,
    title: 'Hybrid Playback Engine',
    desc: 'Media3 ExoPlayer for direct streams (HLS, MP4, Drive) plus hardware-accelerated WebView for YouTube & web media.',
    color: '#ffd23a',
  },
  {
    icon: MessageSquareText,
    title: 'Subtitle Chat Ticker',
    desc: 'Live chat glides across the screen like subtitles — font size, lines and opacity fully customizable.',
    color: '#ff7a1a',
  },
  {
    icon: Info,
    title: 'Smart Movie Info & Trivia',
    desc: 'Automatic scene-tag cleanup (1080p, BluRay, x264) and IMDb/Wikidata lookups for posters, year & facts.',
    color: '#8bd42a',
  },
  {
    icon: ListVideo,
    title: 'Live Schedule & Queue',
    desc: 'Pull up the upcoming schedule and queue anytime with D-Pad ►.',
    color: '#f5a623',
  },
]

const REMOTE: [string, string, string][] = [
  ['▲ / ▼', 'Zap to previous / next channel', 'Move cursor'],
  ['◄ / ►', '◄ Trivia & movie info · ► Schedule & queue', 'Move cursor'],
  ['OK / SELECT', 'Play / pause stream', 'Open & watch channel'],
  ['≡ MENU', 'Open settings', '„+ Add custom channel“ dialog'],
  ['⮌ BACK', 'Return to channel hub', 'Exit confirmation dialog'],
  ['T / INFO', 'Toggle movie details & trivia', '—'],
]

export default function Features() {
  return (
    <section id="features" className="relative mx-auto max-w-7xl px-6 py-28 md:py-36">
      <Reveal>
        <p className="mb-3 text-center font-crt text-xl tracking-[0.35em] text-[#b565ff]">
          ▚ THE UNOFFICIAL APP
        </p>
        <h2
          className="glitch font-display mb-4 text-center text-4xl text-white md:text-6xl"
          data-text="BUILT FOR THE COUCH"
        >
          BUILT FOR THE COUCH
        </h2>
        <p className="mx-auto mb-16 max-w-2xl text-center text-zinc-400">
          A native leanback client for Fire TV, Android TV, smart TVs and handhelds —
          no mouse pointer, no browser lag. Built by fans, for fans.
        </p>
      </Reveal>

      <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-4">
        {FEATURES.map((f, i) => (
          <Reveal key={f.title} delay={i * 60} className="h-full">
            <div className="group relative h-full overflow-hidden rounded-2xl border border-white/10 bg-[#0c0714]/80 p-6 transition-all duration-300 hover:-translate-y-1.5 hover:border-white/25">
              <div
                className="absolute -right-10 -top-10 h-32 w-32 rounded-full opacity-0 blur-3xl transition-opacity duration-500 group-hover:opacity-25"
                style={{ backgroundColor: f.color }}
              />
              <f.icon className="h-7 w-7" style={{ color: f.color }} />
              <h3 className="font-display mt-4 text-base leading-snug text-white">{f.title}</h3>
              <p className="mt-2.5 text-sm leading-relaxed text-zinc-400">{f.desc}</p>
            </div>
          </Reveal>
        ))}
      </div>

      {/* Remote control table */}
      <Reveal delay={100}>
        <div className="mt-20 overflow-hidden rounded-2xl border border-white/10 bg-[#0c0714]/80">
          <div className="border-b border-white/10 bg-gradient-to-r from-[#ff2d95]/15 via-transparent to-[#39ff14]/15 px-6 py-4">
            <h3 className="font-display text-lg text-white">REMOTE CONTROL COMMANDS</h3>
          </div>
          <div className="divide-y divide-white/5">
            {REMOTE.map(([key, player, hub]) => (
              <div
                key={key}
                className="grid grid-cols-[110px_1fr] items-center gap-4 px-6 py-3.5 transition-colors hover:bg-white/[0.03] md:grid-cols-[160px_1fr_1fr]"
              >
                <span className="font-crt text-lg tracking-widest text-[#39ff14]">{key}</span>
                <span className="text-sm text-zinc-300">
                  <span className="mr-2 rounded bg-[#ff2d95]/15 px-1.5 py-0.5 font-crt text-xs tracking-wider text-[#ff7fbd]">
                    PLAYER
                  </span>
                  {player}
                </span>
                <span className="hidden text-sm text-zinc-500 md:block">
                  <span className="mr-2 rounded bg-purple-500/15 px-1.5 py-0.5 font-crt text-xs tracking-wider text-purple-300">
                    HUB
                  </span>
                  {hub}
                </span>
              </div>
            ))}
          </div>
        </div>
      </Reveal>
    </section>
  )
}
