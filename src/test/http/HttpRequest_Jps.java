package test.http;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import model.resource.StringModel2;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HttpRequest_Jps {

    /**
     * 以GET方式发送字符串
     *
     * @param params   要发送的数组
     * @param encoding 发送的编码
     * @return true返回成功，false返回失败
     * @throws Exception
     */
    public static boolean sendGETString(HashMap<String, String> params, String url1, String encoding) throws Exception {
        StringBuilder url = new StringBuilder(url1);
        url.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encoding));
            url.append("&");
        }
        url.deleteCharAt(url.length() - 1);
        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }

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
            connection.setConnectTimeout(8000);
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

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
            result = sendGet("https://omim.org/entry/" + param,
                    "search=" + param
                            + "&highlight=" + param);
        } else if (type == RequestType.POST) {
            // 发送 POST 请求
            result = sendPost("http://fanyi.baidu.com/v2transapi",
                    "from=zh&to=en&query=" + param
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

    public void translateAll(ObservableList<StringModel2> stringModels) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < stringModels.size(); i++) {
                    new Thread(new AddThread(HttpRequest_Jps.this, stringModels
                            .get(i))).start();
                }
            }
        });
    }

    /**
     * 放鸡蛋线程
     */
    class AddThread extends Thread {
        private HttpRequest_Jps plate;
        private StringModel2 egg;

        public AddThread(HttpRequest_Jps plate, StringModel2 stringModel) {
            this.plate = plate;
            this.egg = stringModel;
        }

        public void run() {
            plate.putEgg(egg);
        }
    }

    /**
     * 去除无用的html标签
     *
     * @param result
     * @return
     */
    public static String removeHtmlTag(String result) {
        result = result.replace(result.substring(result.indexOf("<a"), result.indexOf("</a>") + 4), "");
        return result;
    }

    /**
     * 移除括号
     *
     * @return
     */
    public static String removeBrackets(String result) {
        String tmp = "";
        if (result.endsWith(").")) {
            tmp = ").";
        } else if (result.endsWith(")")) {
            tmp = ")";
        }
        result = result.replace(result.substring(result.lastIndexOf("("), result.lastIndexOf(tmp) + 1), "");
        return result;
    }

    /**
     * 放鸡蛋 （加入子线程）
     */
    public void putEgg(StringModel2 egg) {
        try {
            if (egg != null && egg.getValueEN() == null) {
                String result = sendRequest(RequestType.GET, egg.getKey());
                if (result != null && result.contains("<title>")) {
                    String name = result.toString().substring(result.indexOf("<title>"), result.indexOf("</title>"));
                    if (name.contains("-")) {
                       // name = name.split("-")[name.split("-").length - 1];
                       // String tr = name.substring(0,name.indexOf("-",3));
                        name = name.substring(name.indexOf(egg.getKey()+" - ")+9,name.length());
                        egg.setName(name);
                    }
                }
                if (result != null && result.contains("id=\"descriptionFold\"")) {
                    result = result.split("id=\"descriptionFold\"")[1];
                    result = result.split("<p>")[1];
                    result = result.split("</p>")[0];
                    boolean hasA = true;
                    while (hasA) {
                        if (result.contains("<a")) {
                            result = removeHtmlTag(result);
                        } else {
                            hasA = false;
                        }
                    }
                    boolean hasB = true;
                    while (hasB) {
                        result = result.trim();
                        if (result.endsWith(").") || result.equalsIgnoreCase(")")) {
                            result = removeBrackets(result);
                        } else {
                            hasB = false;
                        }
                    }
                    egg.setValueEN(result);
                    eggs.put(egg);// 向盘子末尾放一个鸡蛋，如果盘子满了，当前线程阻塞
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 下面输出有时不准确，因为与put操作不是一个原子操作
        System.out.println("添加数据到线程");
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
        System.out.println("拿到数据");
        return egg;
    }

    public interface RequestType {
        int GET = 0;
        int POST = 1;
    }


    public static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                    + session.getPeerHost());
            return true;
        }
    };

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

}
