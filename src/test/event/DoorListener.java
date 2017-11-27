package test.event;

import java.util.EventListener;

/**
* 定义监听接口，负责监听DoorEvent事件
*/
public interface DoorListener extends EventListener {
    public void doorEvent(DoorEvent event);
}
