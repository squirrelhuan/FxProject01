package event.LogCateEvent;

public class LogCateListenerImpl implements ILogCateListener {

	public void doorEvent(LogCateEvent event) {
		if (event.getEventType() == LogCateEvent.LogCateEventType.onLogsChanged) {
            System.out.println("logs changed");
        } else {
            System.out.println("tags changed");
        }
	}

}
