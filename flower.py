"""
Mandala flower drawn with Python's turtle graphics.

Run it:
    /usr/bin/python3 flower.py            # opens a window and draws
    /usr/bin/python3 flower.py --save out.eps   # draws, then saves a PostScript copy

Homebrew Python has no Tk bundled, so `python3 flower.py` may fail with
"No module named '_tkinter'".  Use /usr/bin/python3, or `brew install python-tk`.

The picture is built back to front in five passes:
    1. guilloche ring   - overlapping circles, the lace border
    2. rose filigree    - a rhodonea curve, r = A*cos(k*theta)
    3. petal layers     - five rings of two-arc petals, large to small
    4. stamens          - spokes tipped with dots
    5. seed head        - phyllotaxis spiral at the golden angle
"""

import colorsys
import math
import sys
import turtle

# ---------------------------------------------------------------- settings

WIDTH, HEIGHT = 900, 900
BACKGROUND = "#07060d"

# How many turtle steps to batch before repainting.  Higher = faster draw,
# lower = smoother animation.  0 would mean "never repaint until the end".
BATCH = 12

# Each entry is one ring of petals:
#   (count, arc radius, arc extent in degrees, hue, brightness, pen width)
PETAL_LAYERS = [
    (36, 200, 58, 0.79, 0.55, 1),
    (30, 158, 64, 0.86, 0.68, 1),
    (24, 120, 72, 0.93, 0.80, 1),
    (18,  86, 82, 0.02, 0.92, 1),
    (12,  56, 94, 0.09, 1.00, 2),
]

GOLDEN_ANGLE = math.radians(137.507764)  # 360 / phi^2, the seed-packing angle


# ---------------------------------------------------------------- helpers

def hsv(hue, sat, val):
    """HSV in 0..1 -> an (r, g, b) tuple turtle accepts in colormode 1.0."""
    return colorsys.hsv_to_rgb(hue % 1.0, sat, val)


def jump(t, x, y, heading=0):
    """Move without drawing, then face `heading`."""
    t.penup()
    t.goto(x, y)
    t.setheading(heading)
    t.pendown()


def petal(t, radius, extent):
    """Two mirrored arcs meeting at a point: the classic turtle petal.

    Drawn from the turtle's current position and heading, and the turtle
    finishes back where it started, facing the way it started.
    """
    for _ in range(2):
        t.circle(radius, extent)
        t.left(180 - extent)


def circle_at(t, cx, cy, radius):
    """A full circle centred on (cx, cy).

    turtle.circle() puts the centre 90 degrees to the turtle's left, so the
    turtle has to start at the bottom of the circle facing east.
    """
    jump(t, cx, cy - radius, 0)
    t.circle(radius)


def polyline(t, points):
    """Draw a connected path through a list of (x, y) points."""
    jump(t, *points[0])
    for x, y in points[1:]:
        t.goto(x, y)


# ---------------------------------------------------------------- the passes

def guilloche_ring(t, screen, count=64, orbit=252, radius=84):
    """Overlapping circles whose centres sit on one big circle."""
    t.pensize(1)
    for i in range(count):
        angle = 2 * math.pi * i / count
        t.pencolor(hsv(0.62 + 0.10 * math.sin(3 * angle), 0.45, 0.42))
        circle_at(t, orbit * math.cos(angle), orbit * math.sin(angle), radius)
        screen.update()


def rose_filigree(t, screen, amplitude=228, k_num=11, k_den=5, steps=2200):
    """Rhodonea (rose) curve: r = A * cos(k * theta), k = k_num / k_den.

    An odd numerator over an even-ish denominator gives a many-lobed curve
    that only closes after k_den full turns, which is where the lace comes
    from.
    """
    k = k_num / k_den
    turns = k_den  # full revolutions needed before the curve repeats
    points = []
    for i in range(steps + 1):
        theta = 2 * math.pi * turns * i / steps
        r = amplitude * math.cos(k * theta)
        points.append((r * math.cos(theta), r * math.sin(theta)))

    t.pensize(1)
    t.pencolor(hsv(0.55, 0.35, 0.60))
    jump(t, *points[0])
    for n, (x, y) in enumerate(points[1:], start=1):
        # Slide the hue along the curve so the overlaps stay readable.
        t.pencolor(hsv(0.52 + 0.18 * n / steps, 0.40, 0.66))
        t.goto(x, y)
        if n % BATCH == 0:
            screen.update()
    screen.update()


def petal_ring(t, screen, count, radius, extent, hue, value, pensize):
    """One ring of `count` petals radiating from the origin."""
    t.pensize(pensize)
    for i in range(count):
        heading = 360 * i / count
        # A slow hue drift around the ring keeps each layer from going flat.
        shade = hue + 0.05 * math.sin(2 * math.pi * i / count)
        t.pencolor(hsv(shade, 0.62, value))
        jump(t, 0, 0, heading)
        petal(t, radius, extent)
        screen.update()


def stamens(t, screen, count=48, inner=18, outer=66):
    """Thin spokes from the core, each capped with a pollen dot."""
    t.pensize(1)
    for i in range(count):
        angle = 2 * math.pi * i / count
        # Alternate long and short spokes for a bit of texture.
        reach = outer if i % 2 == 0 else outer * 0.78
        t.pencolor(hsv(0.13, 0.35, 0.95))
        polyline(t, [(inner * math.cos(angle), inner * math.sin(angle)),
                     (reach * math.cos(angle), reach * math.sin(angle))])
        t.penup()
        t.goto(reach * math.cos(angle), reach * math.sin(angle))
        t.dot(4, hsv(0.11, 0.75, 1.0))
    screen.update()


def seed_head(t, screen, count=320, spacing=3.0):
    """Phyllotaxis spiral: seed i sits at angle i*GOLDEN_ANGLE, r = c*sqrt(i).

    This is how a real sunflower packs its florets, and it is why the eye
    picks out two families of spirals in the middle of the flower.
    """
    t.penup()
    for i in range(count):
        angle = i * GOLDEN_ANGLE
        r = spacing * math.sqrt(i)
        t.goto(r * math.cos(angle), r * math.sin(angle))
        # Small seeds in the packed centre, larger ones toward the rim.
        t.dot(2 + 3 * i / count, hsv(0.10 - 0.06 * i / count, 0.55, 1.0))
        if i % BATCH == 0:
            screen.update()
    screen.update()


# ---------------------------------------------------------------- driver

def draw():
    screen = turtle.Screen()
    screen.setup(WIDTH, HEIGHT)
    screen.title("Turtle mandala flower")
    screen.bgcolor(BACKGROUND)
    screen.colormode(1.0)
    screen.tracer(0, 0)  # manual repaints; see screen.update() calls above

    t = turtle.Turtle(visible=False)
    t.speed(0)
    t.hideturtle()

    guilloche_ring(t, screen)
    rose_filigree(t, screen)
    for layer in PETAL_LAYERS:
        petal_ring(t, screen, *layer)
    stamens(t, screen)
    seed_head(t, screen)

    screen.update()
    return screen, t


def main():
    screen, t = draw()

    if "--save" in sys.argv:
        i = sys.argv.index("--save")
        path = sys.argv[i + 1] if i + 1 < len(sys.argv) else "flower.eps"
        canvas = screen.getcanvas()
        canvas.postscript(file=path, colormode="color",
                          x=-WIDTH // 2, y=-HEIGHT // 2,
                          width=WIDTH, height=HEIGHT)
        print("saved " + path)
        return

    screen.exitonclick()  # window stays up until you click it


if __name__ == "__main__":
    main()
