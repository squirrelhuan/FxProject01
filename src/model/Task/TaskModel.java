package model.Task;

import provider.TaskManagerProvider.onTaskManagerThreadChangedListener;

/**
 * my task model
 * 
 * @author CGQ
 *
 */
public class TaskModel {

	private int id;
	private String name;
	private double process;
	private int state;
	private TaskThread thread;
	onTaskManagerThreadChangedListener changedListener;
	public TaskModel(String name){
		this.name = name;
	}
	
	public void start(onTaskManagerThreadChangedListener changedListener) {
		this.changedListener = changedListener;
		thread.start();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getProcess() {
		return process;
	}

	public void setProcess(double process) {
		this.process = process;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(TaskThread taskThread) {
		this.thread = taskThread;
	}

	void onStarted() {
		state = TaskState.start;
		this.changedListener.onStart();
	}

	void onFinished() {
		state = TaskState.finish;
		this.changedListener.onFinished(this);
	}
	
	void onErrored() {
		state = TaskState.error;
		this.changedListener.onError();
	}
	void onProcessChanged(double process) {
		state = TaskState.running;
		this.process = process;
		this.changedListener.onProcessChanged(process);
	}
	
	public interface TaskState{
		int stop = 0;
		int start = 1;
		int running = 2;
		int pause = 3;
		int finish = 4;
		int error = 5;
	}
}
