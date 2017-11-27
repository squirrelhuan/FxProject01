package test.compiler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Test {
	public static void main(String[] args) throws Exception {
		// Java 源代码
		String sourceStr = "public class Hello{public String sayHello (String name){return \"Hello,\" + name + \"!\";}}";
		sourceStr = "public class Hello{public void test(){System.out.println(\"ni hao\"); int a = 2;"
				+ " int b = 3;"
				+ "int c;"
				+ " c = a*b;"
				+ "System.out.println(\"c=\"+c);}}";
		// 类名及文件名
		String clsName = "Hello";
		// 方法名
		String methodName = "sayHello";
		methodName = "test";

		// 当前编译器
		JavaCompiler cmp = ToolProvider.getSystemJavaCompiler();
		// Java 标准文件管理器
		StandardJavaFileManager fm = cmp.getStandardFileManager(null, null,
				null);
		// Java 文件对象
		JavaFileObject jfo = new StringJavaObject(clsName, sourceStr);
		// 编译参数，类似于javac <options> 中的options
		List<String> optionsList = new ArrayList<String>();
		// 编译文件的存放地方，注意：此处是为Eclipse 工具特设的
		optionsList.addAll(Arrays.asList("-d", "./bin"));
		// 要编译的单元
		List<JavaFileObject> jfos = Arrays.asList(jfo);
		// 设置编译环境
		JavaCompiler.CompilationTask task = cmp.getTask(null, fm, null,
				optionsList, null, jfos);
		// 编译成功
		if (task.call()) {
			// 生成对象
			Object obj = Class.forName(clsName).newInstance();
			Class<? extends Object> cls = obj.getClass();
			// 调用sayHello 方法
			// Method m = cls.getMethod(methodName, String.class);
			Method m = cls.getMethod(methodName);
			m.invoke(obj, null);
			// String str = (String) m.invoke(obj, "Dynamic Compilation");
			// System.out.println(str);
		}
	}
}
