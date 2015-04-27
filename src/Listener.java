import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class Listener implements NativeKeyListener, NativeMouseInputListener {

	String pressed = "";
	int distance = 0, clicks = 0;
	int lastx = 0, lasty = 0;
	Timer t = new Timer();
	List<String> positions = new LinkedList<String>();
	List<Counter> buttonFrequency = new LinkedList<Counter>();
	Counter currentSecond = new Counter(-1); //just to avoid complicating code during counting

	/*
	 * keyboard[]()*"'
	 */
	public void nativeKeyPressed(NativeKeyEvent e) {
		int keyCode = e.getKeyCode();
		String key = NativeKeyEvent.getKeyText(keyCode);
		//System.out.println(key);
		if (pressed.endsWith("[" + NativeKeyEvent.getKeyText(NativeKeyEvent.VC_CONTROL_L) + "]") && key.toUpperCase().equals("Q")) {
			print();
		}
		if (key.length() < 3) {
			pressed = pressed + key;
		} else {
			pressed = pressed + "[" + key + "]";
		}
		
		//incrementing number of keys pressed in this second
		int second = new Double(Math.floor(this.t.timeElapsed()/1000)).intValue();
		if(this.currentSecond.getSecond() == second) {
			this.currentSecond.increment();
		} else {
			this.currentSecond = new Counter(second);
			this.currentSecond.increment();
			this.buttonFrequency.add(this.currentSecond);
		}
	}

	/*
	 * mouse
	 */
	public void nativeMouseClicked(NativeMouseEvent e) {
		clicks++;
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		distance += Math.sqrt(Math.pow(e.getX() - lastx, 2) + Math.pow(e.getY() - lasty, 2));
		lastx = e.getX();
		lasty = e.getY();
		positions.add(e.getX() + "," + e.getY());
	}

	public void print() {
		// print summary
		try {
			FileWriter fstream = new FileWriter("summary.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(pressed + "\n\n");
			out.write("Clicks: " + clicks + ", distance: " + distance + " pixel\n");
			out.write("Test duration: " + t.timeElapsed() / 1000.0 + "s");
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		// print positions
		try {
			FileWriter fstream = new FileWriter("positions.csv");
			BufferedWriter out = new BufferedWriter(fstream);

			for (String pos : this.positions) {
				out.write(pos + "\n");
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		//create graphical representation of mouse movement and pressing keys frequency (basing on config file)
		Draw draw = new Draw();
		draw.drawPath(this.positions);
		draw.drawFrequencyDiagram(this.buttonFrequency);
		
		// print frequency of pressing buttons
		try {
			FileWriter fstream = new FileWriter("buttonFrequency.csv");
			BufferedWriter out = new BufferedWriter(fstream);
			
			for(Counter freq : this.buttonFrequency) {
				out.write(freq.getSecond() + "," + freq.getResult() + System.lineSeparator());
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("Saved logs and cleared history.");
		this.positions.clear();
		this.buttonFrequency.clear();
		this.currentSecond = new Counter(-1);
	}

	public void nativeKeyTyped(NativeKeyEvent e) { }
	public void nativeKeyReleased(NativeKeyEvent e) { }
	public void nativeMousePressed(NativeMouseEvent e) { }
	public void nativeMouseReleased(NativeMouseEvent e) { }
	public void nativeMouseDragged(NativeMouseEvent e) { }
	
}