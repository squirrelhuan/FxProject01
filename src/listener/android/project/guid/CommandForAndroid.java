package listener.android.project.guid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.setting.AndroidTargetModel;
import util.MySystem;
import util.PropertiesUtil;

public class CommandForAndroid {
	public static void exeCmd(String commandStr) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void test(String commandStr,OnSystemOutListener listener) {
		File wd = new File("/bin");
		// System.out.println(wd);
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec("cmd cd D:");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (proc != null) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(proc.getOutputStream())), true);
			out.println("D:");
			PropertiesUtil.readProperties("setting.properties");
			out.println("cd " + PropertiesUtil.getProperty("AndroidSDK")
					+ "\\tools");
			out.println(commandStr);
			// out.println("android create project -n abc123demo -p abcdemo -k com.lbs -a abc123demo -t 2");
			// //执行该语句后返回上一级目录
			// out.println("pwd"); //打印当前目录
			// out.println("java -jar xxx.jar");//执行该目录下的jar文件,Linux下执行jar文件必须进入其所在的文件夹
			out.println("exit");
			try {
				String line;
				String result = "";
				while ((line = in.readLine()) != null) {
					MySystem.out.println(line);
					result+=line;
				}
				if(listener!=null){
					listener.onFinish(result);
				}
				proc.waitFor();
				in.close();
				out.close();
				proc.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// test();
		String commandStr = "ping www.taobao.com";
		// commandStr = "cmd cd D:\\eclipse\\android-sdk-windows\tools";
		// String commandStr = "ipconfig";
		//CommandForAndroid.test(commandStr);

	}

	/*
	 * 参数说明 1. -n --name 应用程序的名字 2. -t --target SDK Target ID 3. -p --path
	 * 应用程序的工作目录 4. -k --package 应用程序的包名 5. -a --activity 默认创建的Activity的名字
	 */

	public static void creatAndroidProject(String project_Name,
			String project_Target, String project_Path,
			String project_PackageName, String project_Activity) {
		// Path_AndroidSDK;
		String commandStr = "android create project -n " + project_Name
				+ " -p " + project_Path + " -k " + project_PackageName
				+ " -a MainActivity -t 2";
		test(commandStr,null);
		commandStr = "cmd /c start " + project_Path;// 会打开一个新窗口后执行dir指令，原窗口会关闭。
		CommandForAndroid.exeCmd(commandStr);
	}

	public static List<AndroidTargetModel> GetAndroidTargets() {
		List<AndroidTargetModel> model_list = new ArrayList<AndroidTargetModel>();
		String commandStr = "android.bat list targets";
		test(commandStr,new OnSystemOutListener() {
			@Override
			public void onFinish(String str) {
				getAndroidTarget(str,model_list);
			}
		});
		return model_list;
	}

	/** * 获取查询的字符串 * 将匹配的字符串取出 */
	public static void getAndroidTarget(String str ,List<AndroidTargetModel> model_list) {
		//\\b 是指的边界值      
		String getStringReg = "id:(.*?) or \"";
		// 1.将正在表达式封装成对象Patten 类来实现
		Pattern pattern = Pattern.compile(getStringReg);
		// 2.将字符串和正则表达式相关联
		Matcher matcher = pattern.matcher(str);
		// 3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。
		//System.out.println(matcher.matches());
		// 查找符合规则的子串
		while (matcher.find()) {
			// 获取 字符串
			//System.out.println(matcher.group());
			AndroidTargetModel model = new AndroidTargetModel();
			model.setId(matcher.group().split(" ")[1]);
			model_list.add(model);
			// 获取的字符串的首位置和末位置
			//System.out.println(matcher.start() + "--" + matcher.end());
		}
		
		/**获取android名称*/
		//\\b 是指的边界值      
		getStringReg = "\"(.*?)\"";
		// 1.将正在表达式封装成对象Patten 类来实现
		pattern = Pattern.compile(getStringReg);
		// 2.将字符串和正则表达式相关联
		matcher = pattern.matcher(str);
		// 3.String 对象中的matches 方法就是通过这个Matcher和pattern来实现的。
		//System.out.println(matcher.matches());
		int i=0;
		// 查找符合规则的子串
		while (matcher.find()) {
			// 获取 字符串
			AndroidTargetModel model = model_list.get(i);
			model.setName(matcher.group().replace("\"", ""));
			//model_list.add(i, model);
			i++;
			System.out.println(matcher.group());
			// 获取的字符串的首位置和末位置
			//System.out.println(matcher.start() + "--" + matcher.end());
		}
	}
	
	public interface OnSystemOutListener{
		void onFinish(String str);
	} 
}
