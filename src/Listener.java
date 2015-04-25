import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class Listener implements NativeKeyListener, NativeMouseInputListener {
	
	private static final int ASCII_TABLE_BEGINNING = ' ';
	private static final int ASCII_TABLE_ENDING = '~';
	String pressed = "";
	int distance = 0, clicks = 0;
	int lastx = 0, lasty = 0;
	Timer timer = new Timer();
	List<String> positions = new LinkedList<String>();
	List<String> clicksCoordinates = new LinkedList<String>();
	
	/*
	 * Check if the pressed by user key is special.
	 * @ret true - if special false - if notspecial.
	 */
	public static boolean isSpecialKey(String key){
		if(key.equals("Left Control")) return true;
		if(key.equals("Rigth Control")) return true;
		if(key.equals("Left Shift")) return true;
		if(key.equals("Right Shift")) return true;
		if(key.equals("Left Alt")) return true;
		if(key.equals("Right Alt")) return true;
		return false;
	}
	/*
	 * keyboard
	 */
	public void nativeKeyPressed(NativeKeyEvent e) {
		int k = e.getKeyCode();
		int rawCode = e.getRawCode();
		String key = NativeKeyEvent.getKeyText(k);
		String mods = NativeInputEvent.getModifiersText(e.getModifiers());
		
		if (mods.equals("Ctrl")/*(pressed.endsWith("[Left Control]") || pressed.endsWith("[Rigth Control]"))*/ && key.toUpperCase().equals("Q")) {
			print();
		}
		
		//Adding to String pressed a sign from the ASCII code.
		if(ASCII_TABLE_BEGINNING <= rawCode && rawCode <= ASCII_TABLE_ENDING) pressed = pressed+(char)rawCode;
		//Handling any other situation.
		else{
			if(isSpecialKey(key)){
				pressed = pressed + "[" + key + "]";
			} else if (key.length() < 2) {
				if(e.getModifiers() == 0) pressed = pressed + key;
				else pressed = pressed + key + "(mods:" + mods + ")";
			} else {
				if(e.getModifiers() == 0) pressed = pressed + "[" + key + "]";
				else pressed = pressed + "[" + key + "]" + "(mod:" + mods + ")";
			}
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
		if(e.getClickCount()!=0) clicksCoordinates.add(e.getX() + "," + e.getY());
	}

	public void print() {
		// print summary
		try {
			FileWriter fstream = new FileWriter("summary.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(pressed + "\n\n");
			out.write("Clicks: " + clicks + ", distance: " + distance + " pixel\n");
			out.write("Test duration: " + timer.timeElapsed() / 1000.0 + "s");
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
			
			FileWriter fstreamNd = new FileWriter("clicks.csv");
			BufferedWriter outNd = new BufferedWriter(fstreamNd);
			for (String cli : clicksCoordinates) {
				outNd.write(cli + "\n");
			}
			outNd.close();
			
			Plot.generatePath();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("Saved logs and cleared history.");
		positions.clear();
		clicksCoordinates.clear();
	}

	public void nativeKeyTyped(NativeKeyEvent e) { }
	public void nativeKeyReleased(NativeKeyEvent e) { }
	public void nativeMousePressed(NativeMouseEvent e) { }
	public void nativeMouseReleased(NativeMouseEvent e) { }
	public void nativeMouseDragged(NativeMouseEvent e) { }
}