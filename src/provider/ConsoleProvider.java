package provider;

import java.util.ArrayList;

import event.Console.ConsoleManager;

public class ConsoleProvider {
	
	private MessageList message_list = new MessageList();
	private static ConsoleProvider consoleProvider = new ConsoleProvider();
	public static ConsoleProvider getInstance() {
		return consoleProvider;
	}
	
	private ConsoleProvider(){
		
	}
	
	public MessageList getMessage_list() {
		return message_list;
	}

	public void setMessage_list(MessageList message_list) {
		this.message_list = message_list;
	}

	public static class MessageList extends ArrayList<String> {

		@Override
		public void add(int index, String message) {
			// TODO Auto-generated method stub
			super.add(index, message);
			ConsoleManager.getInstance().onProcessChanged(message);
		}

		@Override
		public boolean add(String message) {
			super.add(message);
			ConsoleManager.getInstance().onProcessChanged(message);
			return true;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			super.clear();
			ConsoleManager.getInstance().onClearLog();
		}

	}
}
