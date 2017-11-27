package event.Console;

public class ConsoleListenerImpl implements IConsoleListener {


	@Override
	public void pushFileEvent(ConsoleEvent event) {
		if (event.getEventType() == ConsoleEvent.PushFileEventType.onLogsChanged) {
            System.out.println("logs changed");
        } else {
            System.out.println("tags changed");
        }
	}

}
