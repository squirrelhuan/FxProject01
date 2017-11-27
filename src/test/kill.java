package test;

import java.io.IOException;

public class kill {

	public static void main(String[] args) {
		String command = "taskkill /f /im javaw.exe";  
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
