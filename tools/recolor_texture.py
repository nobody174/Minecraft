"""
Recolor a vanilla Minecraft entity texture to create a Buddy Beast variant.
Operates on raw pixels of an existing UV-mapped texture, so the output
stays correctly aligned to whatever model uses that UV layout.

Usage:
    python recolor_texture.py <input.png> <output.png> [mode]

Modes:
    hue_shift:<0-1>          shift hue by fraction of the color wheel, keep shading
    solid:<r,g,b>            flat recolor, preserve lightness/alpha only
    stripes:<r,g,b>:<r,g,b>  horizontal stripe pattern alternating every 4px
    dots:<r,g,b>:<r,g,b>     base color with scattered dot pattern in second color
"""
import sys
import colorsys
from PIL import Image


def hue_shift(img, shift):
    pixels = img.load()
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = pixels[x, y]
            if a == 0:
                continue
            hh, ll, ss = colorsys.rgb_to_hls(r / 255, g / 255, b / 255)
            hh = (hh + shift) % 1.0
            nr, ng, nb = colorsys.hls_to_rgb(hh, ll, ss)
            pixels[x, y] = (int(nr * 255), int(ng * 255), int(nb * 255), a)


def solid(img, rgb):
    pixels = img.load()
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = pixels[x, y]
            if a == 0:
                continue
            _, ll, _ = colorsys.rgb_to_hls(r / 255, g / 255, b / 255)
            tr, tg, tb = rgb
            nr = int(tr * (0.5 + ll))
            ng = int(tg * (0.5 + ll))
            nb = int(tb * (0.5 + ll))
            pixels[x, y] = (min(nr, 255), min(ng, 255), min(nb, 255), a)


def stripes(img, rgb1, rgb2, width=4):
    pixels = img.load()
    w, h = img.size
    for y in range(h):
        band = rgb1 if (y // width) % 2 == 0 else rgb2
        for x in range(w):
            r, g, b, a = pixels[x, y]
            if a == 0:
                continue
            _, ll, _ = colorsys.rgb_to_hls(r / 255, g / 255, b / 255)
            nr = min(int(band[0] * (0.5 + ll)), 255)
            ng = min(int(band[1] * (0.5 + ll)), 255)
            nb = min(int(band[2] * (0.5 + ll)), 255)
            pixels[x, y] = (nr, ng, nb, a)


def dots(img, base_rgb, dot_rgb, spacing=6, radius=1):
    pixels = img.load()
    w, h = img.size
    solid(img, base_rgb)
    for y in range(0, h, spacing):
        for x in range(0, w, spacing):
            for dy in range(-radius, radius + 1):
                for dx in range(-radius, radius + 1):
                    px, py = x + dx, y + dy
                    if 0 <= px < w and 0 <= py < h:
                        r, g, b, a = pixels[px, py]
                        if a != 0:
                            pixels[px, py] = (*dot_rgb, a)


def parse_rgb(s):
    return tuple(int(v) for v in s.split(","))


def main():
    if len(sys.argv) < 4:
        print(__doc__)
        sys.exit(1)

    src, dst, mode_arg = sys.argv[1], sys.argv[2], sys.argv[3]
    img = Image.open(src).convert("RGBA")

    parts = mode_arg.split(":")
    mode = parts[0]

    if mode == "hue_shift":
        hue_shift(img, float(parts[1]))
    elif mode == "solid":
        solid(img, parse_rgb(parts[1]))
    elif mode == "stripes":
        stripes(img, parse_rgb(parts[1]), parse_rgb(parts[2]))
    elif mode == "dots":
        dots(img, parse_rgb(parts[1]), parse_rgb(parts[2]))
    else:
        print(f"Unknown mode: {mode}")
        sys.exit(1)

    img.save(dst)
    print(f"Saved {dst}")


if __name__ == "__main__":
    main()
