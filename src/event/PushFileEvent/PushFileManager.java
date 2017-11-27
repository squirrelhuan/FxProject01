package event.PushFileEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class PushFileManager {
	private Collection listeners;
	private static PushFileManager logCateManager = new PushFileManager();
	public static PushFileManager getInstance(){
		return logCateManager;
	}
	/**
     * 添加事件
     * 
     * @param listener
     *            DoorListener
     */
    public void addDoorListener(IPushFileListener listener) {
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
    public void removeDoorListener(IPushFileListener listener) {
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
        PushFileEvent event = new PushFileEvent(this, PushFileEvent.PushFileEventType.onLogsChanged,1);
        notifyListeners(event);
    }
    
    /**
     * tags改变事件
     */
    public void onProcessChanged(double d) {
        if (listeners == null)
            return;
        PushFileEvent event = new PushFileEvent(this, PushFileEvent.PushFileEventType.onTagsChanged,d);
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
    private void notifyListeners(PushFileEvent event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            IPushFileListener listener = (IPushFileListener) iter.next();
            listener.pushFileEvent(event);
        }
    }
}
