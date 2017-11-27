package event.Console;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class ConsoleManager {
	private Collection listeners;
	private static ConsoleManager logCateManager = new ConsoleManager();
	public static ConsoleManager getInstance(){
		return logCateManager;
	}
	/**
     * 添加事件
     * 
     * @param listener
     *            DoorListener
     */
    public void addDoorListener(IConsoleListener listener) {
        if (listeners == null) {
            listeners = new HashSet();
        }
        listeners.add(listener);
    }

    /**
     * 移除事件
     * 
     * @param listener
     *            DoorListener
     */
    public void removeDoorListener(IConsoleListener listener) {
        if (listeners == null)
            return;
        listeners.remove(listener);
    }

  /*  *//**
     * 触发开门事件
     *//*
    public void fireWorkspaceOpened() {
        if (listeners == null)
            return;
        LogCateEvent event = new LogCateEvent(this, "open");
        notifyListeners(event);
    }*/
    
    /**
     * tags改变事件
     */
    public void onProcessChanged(String message) {
        if (listeners == null)
            return;
        ConsoleEvent event = new ConsoleEvent(this, ConsoleEvent.PushFileEventType.onLogsChanged,message);
        notifyListeners(event);
    }

    /**
     * 
     */
    public void onClearLog() {
        if (listeners == null)
            return;
        ConsoleEvent event = new ConsoleEvent(this, ConsoleEvent.PushFileEventType.onTagsChanged,"");
        notifyListeners(event);
    }
    
   /* *//**
     * 触发关门事件
     *//*
    protected void fireWorkspaceClosed() {
        if (listeners == null)
            return;
        LogCateEvent event = new LogCateEvent(this, "close");
        notifyListeners(event);
    }
*/
    /**
     * 通知所有的DoorListener
     */
    private void notifyListeners(ConsoleEvent event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            IConsoleListener listener = (IConsoleListener) iter.next();
            listener.pushFileEvent(event);
        }
    }
}
