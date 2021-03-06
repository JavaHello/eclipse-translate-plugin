package translation;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleUtil {
	private static IConsoleManager consoleManager;
	private static MessageConsole console;
	private static String CONSOLE_NAME = "翻译";
	private static ConsoleUtil ct;

	private ConsoleUtil() {

	}

	public static ConsoleUtil getIns() {
		if (ct == null) {
			ct = new ConsoleUtil();
			return ct;
		} else {
			return ct;
		}
	}

	public void show(String message) {
		if (consoleManager == null) {
			consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		}
		if (console == null) {
			IConsole[] consoles = consoleManager.getConsoles();
			if (consoles.length > 0) {
				if (console instanceof MessageConsole) {
					console = (MessageConsole) consoles[0];
				}
			}
			if (console == null) {
				console = new MessageConsole(CONSOLE_NAME, null);
				consoleManager.addConsoles(new IConsole[] { console });
			}
		}
		consoleManager.showConsoleView(console);
		MessageConsoleStream newMessageStream = console.newMessageStream();
		newMessageStream.print(message);
	}
}
