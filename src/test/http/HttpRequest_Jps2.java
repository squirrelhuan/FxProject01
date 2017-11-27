package test.http;

import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import model.resource.StringModel2;
import org.junit.Test;
import util.MySystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HttpRequest_Jps2 {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // conn.setRequestProperty("content-length", "74");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            getEgg();
        }
        return result;
    }

    @Test
    public static String sendRequest(int type, String param) {
        String result = null;
        if (type == RequestType.GET) {
            // 发送 GET 请求
            // String
            // s=HttpRequest.sendGet("http://localhost:6144/Home/RequestString",
            // "key=123&v=456");
            // System.out.println(s);
        } else if (type == RequestType.POST) {
            String translateType = type == 0 ? "from=zh&to=en" : "from=en&to=zh";
            // 发送 POST 请求
            result = sendPost("http://fanyi.baidu.com/v2transapi",
                    translateType + "&query=" + param
                            + "&transtype=enter&simple_means_flag:3");
            System.out.println(result);
        }
        return result;
    }

    /**
     * 装鸡蛋的盘子，大小为5
     */
    private static BlockingQueue<Object> eggs = new ArrayBlockingQueue<Object>(
            1);

    private int type = 0;

    public void translateAll(ObservableList<StringModel2> stringModels, int type) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HttpRequest_Jps2.this.type = type;
                for (int i = 0; i < stringModels.size(); i++) {
                    new Thread(new AddThread(HttpRequest_Jps2.this, stringModels
                            .get(i))).start();
                }
            }
        });
    }

    /**
     * 放鸡蛋线程
     */
    class AddThread extends Thread {
        private HttpRequest_Jps2 plate;
        private StringModel2 egg;

        public AddThread(HttpRequest_Jps2 plate, StringModel2 stringModel) {
            this.plate = plate;
            this.egg = stringModel;
        }

        public void run() {
            plate.putEgg(egg);
        }
    }

    /**
     * 放鸡蛋 （加入子线程）
     */
    public void putEgg(StringModel2 egg) {
        try {
            if (egg != null && egg.getValueCH() == null) {
                String result = null;
                if (HttpRequest_Jps2.this.type == 0) {//中文到英文
                   result = sendRequest(RequestType.POST, egg.getValueCH());
                } else {//英文到中文
                   result = sendRequest(RequestType.POST, egg.getValueEN());
                }

                JSONObject reObject = JSONObject.parseObject(result);
                if (reObject != null) {
                    if (reObject.containsKey("trans_result")) {
                        JSONObject a1 = reObject.getJSONObject("trans_result");
                        if (a1.containsKey("data")) {
                            String name = a1.getJSONArray("data").getJSONObject(0)
                                    .getString("dst").toString();
                            if (result != null) {
                                if (HttpRequest_Jps2.this.type == 0) {//中文到英文
                                    egg.setValueEN(name);
                                } else {
                                    egg.setValueCH(name);
                                }
                            }
                        } else {
                            MySystem.out.println("data为空");
                        }
                    } else {
                        MySystem.out.println("trans_result为空");
                    }
                } else {
                    MySystem.out.println("返回值为空");
                }
                eggs.put(egg);// 向盘子末尾放一个鸡蛋，如果盘子满了，当前线程阻塞
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 下面输出有时不准确，因为与put操作不是一个原子操作
        System.out.println("放入鸡蛋");
    }

    /**
     * 取鸡蛋 (从线程中移除子线程)
     */
    public static Object getEgg() {
        Object egg = null;
        try {
            if (eggs.size() > 0)
                egg = eggs.take();// 从盘子开始取一个鸡蛋，如果盘子空了，当前线程阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 下面输出有时不准确，因为与take操作不是一个原子操作
        System.out.println("拿到鸡蛋");
        return egg;
    }

    public interface RequestType {
        int GET = 0;
        int POST = 1;
    }
}
