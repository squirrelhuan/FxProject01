package event.PushFileEvent;

import java.util.EventListener;

/**
* 定义监听接口，负责监听DoorEvent事件
*/
public interface IPushFileListener extends EventListener {
    public void pushFileEvent(PushFileEvent event);
}
