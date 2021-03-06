package event.Task;

import java.util.EventObject;


/*1.event object：事件状态对象，用于listener的相应的方法之中，作为参数，一般存在与listerner的方法之中

2.event source：具体的事件源，比如说，你点击一个button，那么button就是event source，要想使button对某些事件进行响应，你就需要注册特定的listener。

3.event listener：对每个明确的事件的发生，都相应地定义一个明确的Java方法。这些方法都集中定义在事件监听者（EventListener）接口中，这个接口要继承 java.util.EventListener。 实现了事件监听者接口中一些或全部方法的类就是事件监听者。
*/

/**
* 定义事件对象，必须继承EventObject
*/
public class TaskEvent extends EventObject {

	private int eventType = 0;// 表示门的状态，有“开”和“关”两种
	private double process = 0;
	public TaskEvent(Object source,int doorState,double process) {
		super(source);
		this.eventType = doorState;
		this.process = process;
	}

	public double getProcess() {
		return process;
	}

	public void setProcess(double process) {
		this.process = process;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public interface TaskEventType{
		int onStarted = 0;
		int onFinished = 1;
		int onProgressChanged = 2;
		int onErrored = 3;
	}
}
