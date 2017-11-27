package event.LogCateEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class LogCateManager {
	private Collection listeners;
	private static LogCateManager logCateManager = new LogCateManager();
	public static LogCateManager getInstance(){
		return logCateManager;
	}
	/**
     * 添加事件
     * 
     * @param listener
     *            DoorListener
     */
    public void addDoorListener(ILogCateListener listener) {
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
    public void removeDoorListener(ILogCateListener listener) {
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
     * logs改变事件
     */
    public void onLogsChanged() {
        if (listeners == null)
            return;
        LogCateEvent event = new LogCateEvent(this, LogCateEvent.LogCateEventType.onLogsChanged);
        notifyListeners(event);
    }
    
    /**
     * tags改变事件
     */
    public void onTagsChanged() {
        if (listeners == null)
            return;
        LogCateEvent event = new LogCateEvent(this, LogCateEvent.LogCateEventType.onTagsChanged);
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
    private void notifyListeners(LogCateEvent event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            ILogCateListener listener = (ILogCateListener) iter.next();
            listener.doorEvent(event);
        }
    }
}
