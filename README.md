InputCapture
============

A simple tool for capturing user input like mouse positions, movement, key strokes and plotting them.

## Usage

For capturing mouse movement and keystrokes, just run the java code. If you have trouble starting it, import it as a Eclipse project and it should work.

Remember: **CTRL + W** _saves_ the log files and **overwrites** the old log files. So you might save your files after a run.

For starting a new capture, I suggest to run the Code, and press CTRL + W if you're ready (that will override the existing data). Then capture your run (or let the users do the tests) and at the end, hit CTRL + W again to save the data until to that point. You can then end the running java code without writing that back into files.

Standard output variables includes (first three in *summary.txt*):

* time duration
* distance covered by mouse movement
* number of clicks
* a csv with all the position data of the mouse (*positions.csv*)


The java code makes use of the [jnativehook](http://code.google.com/p/jnativehook/) library (you guys are awesome!) which can capture mouse gestures and keyboard movement system-wide. 

You can visualize the data by applying the `plot.py` program (you'll need python installed).
You may want to customize your screen size and the line color, depending on the background of your screenshot you use to make the captured data visible:

    screen = (1366, 768)
    line_color = 'black'

There's some sample data also in there, and you see a capture of me playing Angry Birds:

![](InputCapture/blob/master/result.png)


## Disclaimer
This code is mostly untested and was written for quick use in the course Human-Computer-Interaction. It should work on Ubuntu 12.04, no clue if it works on Windows. Use at your own risk.
