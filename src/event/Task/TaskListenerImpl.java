package event.Task;

public class TaskListenerImpl implements ITaskListener {


	@Override
	public void onTaskChangedEvent(TaskEvent event) {
		/*if (event.getEventType() == TaskEvent.TaskEventType.onLogsChanged) {
            System.out.println("logs changed");
        } else {
            System.out.println("tags changed");
        }*/
	}

}
