import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main {
	public static void main(String[] args) {
		// register hook
		try { GlobalScreen.registerNativeHook(); } catch (NativeHookException ex) { }
		
		// add mouse and key listeners
		Listener example = new Listener();
		GlobalScreen.getInstance().addNativeKeyListener(example);
		GlobalScreen.getInstance().addNativeMouseListener(example);
		GlobalScreen.getInstance().addNativeMouseMotionListener(example);
	}
}