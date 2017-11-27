package event.Task;

import java.util.EventListener;

/**
* 定义监听接口，负责监听DoorEvent事件
*/
public interface ITaskListener extends EventListener {
    public void onTaskChangedEvent(TaskEvent event);
}
