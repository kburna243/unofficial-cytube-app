import Reveal from './Reveal'
import { Download as DownloadIcon, MonitorSmartphone, Smartphone, TerminalSquare } from 'lucide-react'

const EDITIONS = [
  {
    icon: MonitorSmartphone,
    name: 'ANDROID LIGHT',
    target: 'Fire TV & Android TV',
    file: 'cytube-tv-light.apk',
    points: ['100% leanback D-pad controls', 'Minimal RAM footprint', 'Transparent subtitle chat'],
    color: '#ff2d95',
  },
  {
    icon: Smartphone,
    name: 'ANDROID FULL',
    target: 'Smartphones & Tablets',
    file: 'cytube-tv-full.apk',
    points: ['Interactive chat composer', 'CyTube login & user list', 'Wireless TV companion mode'],
    color: '#39ff14',
  },
]

const STEPS = [
  'Grab the latest release from GitHub',
  'Sideload via Downloader, adbLink or adb',
  'Launch the app, pick a channel, dim the lights',
]

export default function Download() {
  return (
    <section id="download" className="relative mx-auto max-w-6xl px-6 py-28 md:py-36">
      <Reveal>
        <p className="mb-3 text-center font-crt text-xl tracking-[0.35em] text-[#39ff14]">
          ▚ POP IN THE TAPE
        </p>
        <h2
          className="glitch font-display mb-16 text-center text-4xl text-white md:text-6xl"
          data-text="GET THE APP"
        >
          GET THE APP
        </h2>
      </Reveal>

      <div className="grid gap-6 md:grid-cols-2">
        {EDITIONS.map((e, i) => (
          <Reveal key={e.name} delay={i * 140}>
            <div
              className="group relative h-full overflow-hidden rounded-2xl border border-white/10 bg-[#0c0714]/80 p-8 transition-all duration-300 hover:border-white/25"
            >
              <div
                className="absolute -left-12 -top-12 h-40 w-40 rounded-full opacity-15 blur-3xl transition-opacity duration-500 group-hover:opacity-30"
                style={{ backgroundColor: e.color }}
              />
              <e.icon className="h-9 w-9" style={{ color: e.color }} />
              <h3 className="font-display mt-5 text-2xl text-white">{e.name}</h3>
              <p className="mt-1 font-crt text-lg tracking-widest" style={{ color: e.color }}>
                {e.target}
              </p>
              <ul className="mt-5 space-y-2.5">
                {e.points.map((p) => (
                  <li key={p} className="flex items-center gap-2.5 text-sm text-zinc-300">
                    <span className="font-crt" style={{ color: e.color }}>▸</span> {p}
                  </li>
                ))}
              </ul>
              <a
                href="https://github.com/kburna243/unofficial-cytube-app/releases/latest"
                target="_blank"
                rel="noreferrer"
                className="mt-7 flex items-center justify-center gap-2 rounded-xl border-2 px-5 py-3 font-display text-xs tracking-wider transition-all duration-300"
                style={{ borderColor: e.color, color: e.color }}
                onMouseEnter={(ev) => {
                  ev.currentTarget.style.backgroundColor = e.color
                  ev.currentTarget.style.color = '#000'
                }}
                onMouseLeave={(ev) => {
                  ev.currentTarget.style.backgroundColor = 'transparent'
                  ev.currentTarget.style.color = e.color
                }}
              >
                <DownloadIcon className="h-4 w-4" />
                {e.file}
              </a>
            </div>
          </Reveal>
        ))}
      </div>

      {/* Installation steps */}
      <Reveal delay={120}>
        <div className="mt-10 rounded-2xl border border-white/10 bg-[#0c0714]/80 p-6 md:p-8">
          <p className="font-crt flex items-center gap-2 text-xl tracking-widest text-purple-300">
            <TerminalSquare className="h-5 w-5" /> INSTALLATION IN 3 STEPS
          </p>
          <div className="mt-5 grid gap-4 md:grid-cols-3">
            {STEPS.map((s, i) => (
              <div key={s} className="flex items-start gap-3 rounded-xl bg-black/40 p-4">
                <span className="font-display text-2xl text-[#ff2d95]">{i + 1}</span>
                <p className="text-sm leading-relaxed text-zinc-300">{s}</p>
              </div>
            ))}
          </div>
          <p className="mt-5 font-crt text-lg text-zinc-500">
            $ adb install -r cytube-tv-light.apk <span className="text-[#39ff14]"># done. roll film.</span>
          </p>
        </div>
      </Reveal>
    </section>
  )
}
