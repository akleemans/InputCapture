
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class Listener implements NativeKeyListener, NativeMouseInputListener {

	String pressed = "";
	int distance = 0, clicks = 0;
	int lastx = 0, lasty = 0;
	Timer t = new Timer();

	List<Pair> positions =  new LinkedList<Pair>();

	/*
	 * keyboard
	 */
	public void nativeKeyPressed(NativeKeyEvent e) {
		String key = NativeKeyEvent.getKeyText(e.getKeyCode());
		//System.out.println(key);
		if (e.getKeyCode()==0x01) { //!
			print();
			try{
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException ex){
				System.out.println("Error unregistering nativehook, exitting anyway");
				System.exit(1);
			}
			System.exit(0);
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
		Pair tmp = new Pair();
		tmp.x = e.getX();
		tmp.y = e.getY();
		positions.add(tmp);
	}

	public void print() {
		// print summary
		FileWriter fstream = null;
		BufferedWriter out = null;
		Plot plotter = null;
		try {
			fstream = new FileWriter("summary.txt");
			out = new BufferedWriter(fstream);
			out.flush();
			out.write(pressed + System.getProperty("line.separator"));
			out.write("Clicks: " + clicks + ", distance: " + distance + " pixel\n");
			out.write("Test duration: " + t.timeElapsed() / 1000.0 + "s");
			out.flush();
			plotter = Plot.getInstance();
			plotter.drawChart(positions,java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width,java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}	finally{
			try {

				out.close();
			}catch (IOException ex){
				System.err.println("Error: " + ex.getMessage());
			}
		}
		
		// print positions
		try {
			fstream = new FileWriter("positions.csv");
			out = new BufferedWriter(fstream);

			for (Pair pos : positions) {
				out.write(String.valueOf(pos.x) + "," + String.valueOf(pos.y) + "\n");
			}

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		finally{
			try {
				out.close();
			}catch (IOException ex){
				System.err.println("Error: " + ex.getMessage());
			}
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