interface MarqueeProps {
  items: string[]
  reverse?: boolean
  className?: string
  duration?: number
}

export default function Marquee({ items, reverse = false, className = '', duration = 28 }: MarqueeProps) {
  const row = (
    <>
      {items.map((item, i) => (
        <span key={i} className="mx-6 flex items-center gap-6 whitespace-nowrap">
          <span>{item}</span>
          <span className="text-fuchsia-500">★</span>
        </span>
      ))}
    </>
  )
  return (
    <div className={`overflow-hidden ${className}`}>
      <div
        className={`marquee-track ${reverse ? 'reverse' : ''}`}
        style={{ animationDuration: `${duration}s` }}
      >
        <div className="flex">{row}</div>
        <div className="flex" aria-hidden="true">{row}</div>
      </div>
    </div>
  )
}
