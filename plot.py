#!/usr/bin/env python
# -*- coding: ascii -*-
"""
Plots a given set of coordinates (csv) onto a canvas.
"""

from PIL import Image, ImageDraw
import csv

screen = (1366, 768)
line_color = 'black'
filename = 'positions.csv'

with open(filename, 'rb') as csvfile:
    reader = csv.reader(csvfile, delimiter=',', quotechar='|')
    coords = list(reader)

im = Image.new('RGBA', screen, (255, 255, 255, 0)) # empty image
draw = ImageDraw.Draw(im) # draw object
last_pos = 0

for coord in coords:
    pos = (int(coord[0]), int(coord[1]))
    if (last_pos == 0):
        last_pos = pos
        continue
    draw.line([last_pos, pos], fill=line_color)
    last_pos = pos

im.save('path.png', 'PNG') # save
