package event.Console;

import java.util.EventListener;

/**
* 定义监听接口，负责监听DoorEvent事件
*/
public interface IConsoleListener extends EventListener {
    public void pushFileEvent(ConsoleEvent event);
}
