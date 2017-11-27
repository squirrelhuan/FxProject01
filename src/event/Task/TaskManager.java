package event.Task;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class TaskManager {
	private Collection listeners;
	private static TaskManager logCateManager = new TaskManager();

	public static TaskManager getInstance() {
		return logCateManager;
	}

	/**
	 * 添加事件
	 * 
	 * @param listener
	 *            DoorListener
	 */
	public void addDoorListener(ITaskListener listener) {
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
	public void removeDoorListener(ITaskListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	/**
	 * 运行出错了事件
	 */
	public void onTaskError() {
		if (listeners == null)
			return;
		TaskEvent event = new TaskEvent(this, TaskEvent.TaskEventType.onErrored,
				1);
		notifyListeners(event);
	}

	/**
	 * 进度改变改变事件
	 */
	public void onProcessChanged(double process) {
		if (listeners == null)
			return;
		TaskEvent event = new TaskEvent(this,
				TaskEvent.TaskEventType.onProgressChanged, process);
		notifyListeners(event);
	}

	/**
	 * 任务开始触发
	 */
	public void onTaskStart() {
		if (listeners == null)
			return;
		TaskEvent event = new TaskEvent(this,
				TaskEvent.TaskEventType.onStarted, 1);
		notifyListeners(event);
	}

	/**
	 * 任务结束触发
	 */
	public void onTaskFinished() {
		if (listeners == null)
			return;
		TaskEvent event = new TaskEvent(this,
				TaskEvent.TaskEventType.onFinished, 1);
		notifyListeners(event);
	}

	/**
	 * 通知所有的DoorListener
	 */
	private void notifyListeners(TaskEvent event) {
		Iterator iter = listeners.iterator();
		while (iter.hasNext()) {
			ITaskListener listener = (ITaskListener) iter.next();
			listener.onTaskChangedEvent(event);
		}
	}
}
