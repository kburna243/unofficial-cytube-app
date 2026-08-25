import { Github, Heart } from 'lucide-react'

export default function Footer() {
  return (
    <footer className="relative border-t border-white/10 bg-[#080410] px-6 py-16">
      <div className="mx-auto max-w-6xl">
        <div className="flex flex-col items-center gap-10 md:flex-row md:items-start md:justify-between">
          <div className="max-w-sm text-center md:text-left">
            <img
              src="/assets/cytube-app-logo.webp"
              alt="CyTube App"
              className="mx-auto w-44 md:mx-0"
              style={{ filter: 'drop-shadow(0 0 14px rgba(255,45,149,0.4))' }}
            />
            <p className="mt-4 text-sm leading-relaxed text-zinc-500">
              Not a commercial product — a passion project by fans, for fans.
              Grab the remote, dim the lights and see what is playing on CyTube tonight.
            </p>
          </div>

          <div className="text-center md:text-left">
            <p className="font-crt text-lg tracking-[0.3em] text-[#ff2d95]">CREDITS</p>
            <ul className="mt-4 space-y-2.5 text-sm text-zinc-400">
              <li>
                <span className="text-zinc-200">Fried</span> — Core dev, architecture, UI & Android engineering
              </li>
              <li>
                <span className="text-zinc-200">Mike</span> — Co-dev, concept, UI design & testing
              </li>
              <li>
                <span className="text-zinc-200">SPUDZARENEAT</span> — Inspiration for the sync model (grindhouse-tv)
              </li>
              <li>
                <span className="text-zinc-200">calzoneman/sync</span> — CyTube sync & WebSocket architecture
              </li>
              <li>
                <span className="text-zinc-200">CyTube Community</span> — hosts, DJs, mods & night owls
              </li>
            </ul>
          </div>

          <div className="text-center md:text-left">
            <p className="font-crt text-lg tracking-[0.3em] text-[#39ff14]">LINKS</p>
            <ul className="mt-4 space-y-2.5 text-sm">
              <li>
                <a
                  href="https://github.com/kburna243/unofficial-cytube-app"
                  target="_blank"
                  rel="noreferrer"
                  className="inline-flex items-center gap-2 text-zinc-300 transition-colors hover:text-white"
                >
                  <Github className="h-4 w-4" /> GitHub Repository
                </a>
              </li>
              <li>
                <a
                  href="https://github.com/kburna243/unofficial-cytube-app/releases/latest"
                  target="_blank"
                  rel="noreferrer"
                  className="text-zinc-400 transition-colors hover:text-white"
                >
                  ↳ Latest release
                </a>
              </li>
              <li>
                <a
                  href="https://cytu.be"
                  target="_blank"
                  rel="noreferrer"
                  className="text-zinc-400 transition-colors hover:text-white"
                >
                  ↳ cytu.be
                </a>
              </li>
              <li>
                <a
                  href="https://github.com/kburna243/unofficial-cytube-app/issues/new"
                  target="_blank"
                  rel="noreferrer"
                  className="text-zinc-400 transition-colors hover:text-white"
                >
                  ↳ Report a bug / share an idea
                </a>
              </li>
            </ul>
          </div>
        </div>

        <div className="mt-14 flex flex-col items-center justify-between gap-4 border-t border-white/5 pt-8 md:flex-row">
          <p className="font-crt text-base tracking-widest text-zinc-600">
            GPL-3.0 LICENSE · UNOFFICIAL COMMUNITY PROJECT
          </p>
          <p className="flex items-center gap-1.5 text-xs text-zinc-600">
            Made with <Heart className="h-3.5 w-3.5 text-[#ff2d95]" /> and too much popcorn — not affiliated with CyTube.
          </p>
        </div>
      </div>
    </footer>
  )
}
