InputCapture
============

A simple tool for capturing user input like mouse positions, clicks, movement, key strokes and plotting them.

## Usage

Remember: add to src directory JNatvieHook.jar (2.0 version) when you want to use Maven or add the libary in Eclipse or different IDE.

For capturing mouse movement and keystrokes, just run the java code and press CTRL + Q to save.
It's also a project in Maven so after you **mvn compile** and **mvn package** it, there will be one ready-to-use *InputCapture.jar*.

Remember: **CTRL + Q** _saves_ the log files and **overwrites** the old log files. So you might save your files after a run.

For starting a new capture, I suggest to run the Code, and press CTRL + Q if you're ready (that will override the existing data). Then capture your run (or let the users do the tests) and at the end, hit CTRL + Q again to save the data until to that point. You can then end the running java code without writing that back into files.

Standard output variables includes (first three in *summary.txt*):

* time duration
* distance covered by mouse movement
* number of clicks
* a csv with all the position data of the mouse (*positions.csv*)
* a csv with all the position of place where there was a mouse click (*clicks.csv*)
* a transparent png file in which the movement(black color) and clicks(red color) are plotted from csv files (*path.png*) 

## Disclaimer
It should work on Ubuntu 15.04 and 14.04, no clue if it works on Windows. Use at your own risk.
