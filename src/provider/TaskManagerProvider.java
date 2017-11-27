package provider;

import java.util.ArrayList;

import event.Task.TaskManager;
import model.Task.TaskModel;
/**
 * 
 * @author CGQ
 * @time 2016.11.26
 */
public class TaskManagerProvider {

	private static TaskList list_task = new TaskList();
	private TaskModel task_current;

	private static TaskManagerProvider taskManagerProvider = new TaskManagerProvider();
	public static TaskManagerProvider getInstance() {
		return taskManagerProvider;
	}

	private TaskManagerProvider() {
		changedListener = new onTaskManagerThreadChangedListener() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				TaskManager.getInstance().onTaskStart();
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				TaskManager.getInstance().onTaskError();
				
					list_task.get(0).start(changedListener);
			}

			@Override
			public void onProcessChanged(double process) {
				// TODO Auto-generated method stub
				TaskManager.getInstance().onProcessChanged(process);
			}

			@Override
			public void onFinished(TaskModel taskModel) {
				// TODO Auto-generated method stub
				list_task.remove(taskModel);
				TaskManager.getInstance().onTaskFinished();
				
			}
		};
	}
	static onTaskManagerThreadChangedListener changedListener;
	public interface onTaskManagerThreadChangedListener {
		void onStart();

		void onFinished(TaskModel taskModel);

		void onError();

		void onProcessChanged(double process);
	}

	public static TaskList getList_task() {
		return list_task;
	}

	public static void setList_task(TaskList list_task) {
		TaskManagerProvider.list_task = list_task;
	}

	public TaskModel getTask_current() {
		return task_current;
	}

	public void setTask_current(TaskModel task_current) {
		this.task_current = task_current;
	}

	public static TaskManagerProvider getTaskManagerProvider() {
		return taskManagerProvider;
	}

	public static void setTaskManagerProvider(
			TaskManagerProvider taskManagerProvider) {
		TaskManagerProvider.taskManagerProvider = taskManagerProvider;
	}


	public static class TaskList extends ArrayList<TaskModel> {

		@Override
		public void add(int index, TaskModel taskModel) {
			// TODO Auto-generated method stub
				super.add(index, taskModel);
					taskModel.start(changedListener);
		}

		@Override
		public boolean add(TaskModel taskModel) {
			// TODO Auto-generated method stub
				super.add(taskModel);
					taskModel.start(changedListener);
				return true;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			super.clear();
			//TaskManager.getInstance().onLogsChanged();
		}

	}
}
