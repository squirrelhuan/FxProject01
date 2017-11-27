package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties _prop = new Properties();

	/**
	 * 读取配置文件
	 * 
	 * @param fileName
	 */
	public static void readProperties(String fileName) {
		try {
			InputStream in = PropertiesUtil.class.getResourceAsStream("/"
					+ fileName);
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			_prop.load(bf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key读取对应的value
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertySet(String key) {
		for (Object s : _prop.keySet()) {
			System.out.println(s);
		}
		return _prop.getProperty(key);
	}

	/**
	 * 根据key读取对应的value
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return _prop.getProperty(key);
	}
	/**
	 * 根据key读取对应的value
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key,String defValue) {
		return _prop.getProperty(key,defValue);
	}

	/**
	 * 根据key修改对应的value
	 * 
	 * @param key
	 * @return
	 */
	public static void setProperty(String key, String value) {
		MySystem.out.println(key+_prop.getProperty(key));
		_prop.setProperty(key, value);
		MySystem.out.println(key+_prop.getProperty(key));
	}

	/**
	 * 传递键值对的Map，更新properties文件
	 * 
	 * @param fileName
	 *            文件名(放在resource源包目录下)，需要后缀
	 * @param keyValueMap
	 *            键值对Map
	 */
	/*
	 * public static void updateProperties(String fileName,Map<String, String>
	 * keyValueMap) {
	 * //getResource方法使用了utf-8对路径信息进行了编码，当路径中存在中文和空格时，他会对这些字符进行转换，这样，
	 * //得到的往往不是我们想要的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的中文及空格路径。 String
	 * filePath =
	 * PropertiesUtil.class.getClassLoader().getResource(fileName).getFile();
	 * Properties props = null; BufferedWriter bw = null;
	 * 
	 * try { filePath = URLDecoder.decode(filePath,"utf-8");
	 * MySystem.out.println("updateProperties propertiesPath:" + filePath);
	 * props = PropertiesLoaderUtils.loadProperties(new
	 * ClassPathResource(fileName));
	 * MySystem.out.println("updateProperties old:"+props);
	 * 
	 * // 写入属性文件 bw = new BufferedWriter(new OutputStreamWriter(new
	 * FileOutputStream(filePath)));
	 * 
	 * props.setProperty(arg0, arg1) props.clear();// 清空旧的文件
	 * 
	 * for (String key : keyValueMap.keySet()) props.setProperty(key,
	 * keyValueMap.get(key));
	 * 
	 * MySystem.out.println("updateProperties new:"+props); props.store(bw, "");
	 * } catch (IOException e) { MySystem.out.println(e.getMessage()); } finally
	 * { try { bw.close(); } catch (IOException e) { e.printStackTrace(); } } }
	 */

}