import { useEffect, useRef, useState } from 'react'

const WORDS = 'Grab your remote, dim the lights, and see what is playing on CyTube tonight.'.split(' ')

export default function Manifesto() {
  const ref = useRef<HTMLDivElement>(null)
  const [visible, setVisible] = useState(false)

  useEffect(() => {
    const el = ref.current
    if (!el) return
    const obs = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setVisible(true)
          obs.disconnect()
        }
      },
      { threshold: 0.35 },
    )
    obs.observe(el)
    return () => obs.disconnect()
  }, [])

  return (
    <section className="relative overflow-hidden px-6 py-32 md:py-44">
      <div
        className="absolute inset-0 opacity-[0.13]"
        style={{
          backgroundImage: 'url(/assets/genre-splash.webp)',
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      />
      <div className="absolute inset-0 bg-gradient-to-b from-[#06030a] via-transparent to-[#06030a]" />

      <div ref={ref} className="relative mx-auto max-w-4xl text-center">
        <p className="mb-8 font-crt text-xl tracking-[0.35em] text-[#ff2d95]">▚ WHY IT EXISTS</p>
        <p className="font-display text-3xl leading-snug text-white md:text-5xl md:leading-tight">
          {WORDS.map((w, i) => (
            <span
              key={i}
              className={`mw inline-block ${visible ? 'mw-on' : ''}`}
              style={{ transitionDelay: `${i * 55}ms` }}
            >
              {w}&nbsp;
            </span>
          ))}
        </p>
        <p className="mt-10 text-balance text-lg text-zinc-400">
          Not a commercial product. No subscription, no algorithm, no endless feed.
          Just great cinema, live streams and channel marathons — together, in sync,
          from the couch, without fighting browser interfaces.
        </p>
      </div>
    </section>
  )
}
