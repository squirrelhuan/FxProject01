package util;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

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

	public static String findClassesImplementInterfaceFromJar2(String interfaceName, String jarPath)
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

		// 从jar文件MANIFEST.MF中读取Main-Class
		String mainClassName = null;
		Manifest manifest = jarFile.getManifest();
		if (manifest != null) {
			mainClassName = manifest.getMainAttributes().getValue("Main-Class");
		}else {
			System.out.println("not found main-class ，please refrence the main-class");
		}
		jarFile.close();
		// 返回结果
		return mainClassName;
	}
}
