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

	/*
	 * keyboard
	 */
	public void nativeKeyPressed(NativeKeyEvent e) {
		String key = NativeKeyEvent.getKeyText(e.getKeyCode());
		//System.out.println(key);
		if (pressed.endsWith("[Strg]") && key.toUpperCase().equals("Q")) {
			print();
		}
		
		if (key.length() < 3) {
			pressed = pressed + key;
		} else if (key.equals("Leertaste")) {
			pressed = pressed + " ";
		} else if (key.equals("Sternchen")) {
			pressed = pressed + "*";
		} else if (key.equals("Doppelte Anführungszeichen")) {
			pressed = pressed + "''";
		} else if (key.equals("Rechte Klammer")) {
			pressed = pressed + ")";
		} else if (key.equals("Linke Klammer")) {
			pressed = pressed + "(";
		} else if (key.equals("Anführungszeichen")) {
			pressed = pressed + "(";
		} else {
			pressed = pressed + "[" + key + "]";
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
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		// print positions
		try {
			FileWriter fstream = new FileWriter("positions.csv");
			BufferedWriter out = new BufferedWriter(fstream);

			for (String pos : positions) {
				out.write(pos + "\n");
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("Saved logs and cleared history.");
		positions.clear();
	}

	public void nativeKeyTyped(NativeKeyEvent e) { }
	public void nativeKeyReleased(NativeKeyEvent e) { }
	public void nativeMousePressed(NativeMouseEvent e) { }
	public void nativeMouseReleased(NativeMouseEvent e) { }
	public void nativeMouseDragged(NativeMouseEvent e) { }
}