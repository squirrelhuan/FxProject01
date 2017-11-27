package event.LogCateEvent;

import java.util.EventListener;

/**
* 定义监听接口，负责监听DoorEvent事件
*/
public interface ILogCateListener extends EventListener {
    public void doorEvent(LogCateEvent event);
}
