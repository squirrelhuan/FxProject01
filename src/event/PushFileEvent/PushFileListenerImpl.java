package event.PushFileEvent;

public class PushFileListenerImpl implements IPushFileListener {


	@Override
	public void pushFileEvent(PushFileEvent event) {
		if (event.getEventType() == PushFileEvent.PushFileEventType.onLogsChanged) {
            System.out.println("logs changed");
        } else {
            System.out.println("tags changed");
        }
	}

}
