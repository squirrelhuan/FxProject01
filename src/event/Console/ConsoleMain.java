package event.Console;

public class ConsoleMain {
	 public static void main(String[] args) {
	        ConsoleManager manager = new ConsoleManager();
	        manager.addDoorListener(new ConsoleListenerImpl());// 给门1增加监听器
	      //  manager.addDoorListener(new DoorListener2());// 给门2增加监听器
	        // 开门
	        System.out.println("我已经进来了");
	        String d = "ffg";
	        // 关门
	        manager.onProcessChanged(d);
	    }
}
