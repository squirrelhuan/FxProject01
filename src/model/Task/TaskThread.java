package model.Task;

public class TaskThread extends Thread{
	TaskModel taskModel;
	private double process;
	protected TaskThread(TaskModel taskModel){
		this.taskModel = taskModel;
		this.taskModel.setThread(this);
	}
	@Override
	public void run() {
		this.taskModel.onStarted();
		try {
			running();
		} catch (Exception e) {
			this.taskModel.onErrored();
		}finally{
			this.taskModel.onFinished();
		}
		super.run();
	}
	public void running(){}
	public double getProcess() {
		return process;
	}
	public void setProcess(double process) {
		this.process = process;
		this.taskModel.onProcessChanged(process);
	};
}
