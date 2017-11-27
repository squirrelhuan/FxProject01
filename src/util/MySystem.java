package util;

import javafx.application.Platform;
import provider.ConsoleProvider;

public class MySystem {

	public static class out {

		public static void println(Object obj) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(obj==null||obj.toString()==null){
						ConsoleProvider.getInstance().getMessage_list()
						.add("null" + "\n\r");
						return;
					}
					ConsoleProvider.getInstance().getMessage_list()
							.add(obj.toString() + "\n\r");
				}
			});
		}
	}
}
