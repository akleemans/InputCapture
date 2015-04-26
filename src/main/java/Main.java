


import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;


public class Main {
	public static void main(String[] args) {
		System.setProperty("file.encoding","UTF-8");
		// register hook
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.out.println("Error registering nativehook.");
		}
		
		// add mouse and key listeners
		Listener example = new Listener();

		GlobalScreen.addNativeKeyListener(example);
		GlobalScreen.addNativeMouseListener(example);
		GlobalScreen.addNativeMouseMotionListener(example);
	}
}