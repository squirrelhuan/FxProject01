package util;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import interfaces.PluginService;

public class JarUtils {

	
	public static String[] findClassesImplementInterfaceFromJar(String interfaceName, String jarPath)
			throws ClassNotFoundException, IOException {
			URL url = new URL("jar:file:" + jarPath + "!/");
			JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
			JarFile jarFile = jarConnection.getJarFile();
			ArrayList<JarEntry> list = Collections.list(jarFile.entries());
			ArrayList fullNames = new ArrayList();
			Class interfaceClass = Class.forName(interfaceName);
			System.out.println(interfaceClass.getName());
			// 遍历
			for (JarEntry e : list) {
			String n = e.getName();
			if (n.endsWith(".class")) {
			n = n.substring(0, n.length() - 6);
			n = n.replace('/', '.');
			fullNames.add(n);
			//System.out.println("add "+n);
			/*Class currentClass = Class.forName(n);
			if (interfaceClass.isAssignableFrom(currentClass) && false == n.equals(interfaceName)) {
			fullNames.add(n);
			}*/
			}
			}
			// 返回结果
			return (String[]) fullNames.toArray(new String[fullNames.size()]);
			} 
}
