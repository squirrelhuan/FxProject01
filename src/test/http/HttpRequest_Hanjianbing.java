package test.http;

import com.alibaba.fastjson.JSON;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HttpRequest_Hanjianbing {

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


    public static String sendRequest(int type, String param) {
        String result = null;
        if (type == RequestType.GET) {
            // 发送 GET 请求
            result = sendGet("http://www.hanjianbing.org/disease!ajaxDiseaseDetail","id=" + param);
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

    public void translateAll( List<HanjianbingResult> stringModels) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < stringModels.size(); i++) {
                    new Thread(new AddThread(HttpRequest_Hanjianbing.this, stringModels
                            .get(i))).start();
                }
            }
       });
    }

    /**
     * 放鸡蛋线程
     */
    class AddThread extends Thread {
        private HttpRequest_Hanjianbing plate;
        private HanjianbingResult egg;

        public AddThread(HttpRequest_Hanjianbing plate, HanjianbingResult stringModel) {
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

    public static Map<String,HanjianbingResult> hanjianbingResultMap = new HashMap<>();

    /**
     * 放鸡蛋 （加入子线程）
     */
    public void putEgg(HanjianbingResult egg) {
        try {
            if (egg != null && egg.getInf().getName() != null) {
                String result = sendRequest(RequestType.GET, egg.getInf().getName());

                HanjianbingResult.InfBean infBean = egg.getInf();
                infBean.setName(result);
                HanjianbingResult hanjianbingResult = JSON.parseObject(result,HanjianbingResult.class);
                hanjianbingResultMap.put(hanjianbingResult.getInf().getName(),hanjianbingResult);
                System.out.println(hanjianbingResultMap.size()+"条疾病");
                egg.setInf(infBean);
                eggs.put(egg);// 向盘子末尾放一个鸡蛋，如果盘子满了，当前线程阻塞
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
        HttpsURLConnection.setDefaultSSLSocketFactory(sc
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

    @Test
    public  void main(){
        String str = new String("{\n" +
                "  \"inf\": {\n" +
                "    \"adImage\": [\n" +
                "      \n" +
                "    ],\n" +
                "    \"disease\": [\n" +
                "      {\n" +
                "        \"enTitle\": \"Abnormal cell proliferati\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"vhl综合征\",\n" +
                "            \"id\": \"f4be9bee5c494735015c72ebbaf6036b\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"朗格汉斯组织细胞增多症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c72f2e09d036c\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"淋巴管平滑肌增生症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c730167f9036d\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"视网膜母细胞瘤\",\n" +
                "            \"id\": \"f4be9bee5c494735015c7302faed036e\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5af3e6ec015af42fb491000a\",\n" +
                "        \"title\": \"不正常细胞增生（瘤）\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Congenital Metabolic Disorders\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"3-氢基-3-甲基戊二酸血症\",\n" +
                "            \"id\": \"f4be9bee5ca44ca5015cab27aca00088\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Beta硫解酶缺乏症\",\n" +
                "            \"id\": \"f4be9bee5c48bdc3015c48e91a8a0000\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Fabry氏症\",\n" +
                "            \"id\": \"f4be9bee5ca44ca5015cab0ee6d4007d\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"GM1神经节苷脂储积症\",\n" +
                "            \"id\": \"f4be9bee5ca44ca5015cab125dcb007f\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"GM2神经节苷脂储积症\",\n" +
                "            \"id\": \"f4be9bee5ca44ca5015cab107bbd007e\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5af3e6ec015af4324146000b\",\n" +
                "        \"title\": \"先天性代谢异常\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Brain / Nervous System Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Aicardi-Goutieres综合征\",\n" +
                "            \"id\": \"f4be9bee5c7b44e1015c7e6312e6008f\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Charcot Maire Tooth 氏症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c87c946e000ec\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Joubert氏综合征\",\n" +
                "            \"id\": \"f4be9bee5c807455015c87f6707600f3\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"下丘脑功能障碍综合征\",\n" +
                "            \"id\": \"f4be9bee5c7b44e1015c7e645ac80090\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"亚历山大症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c87ff3eea00f6\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5af3e6ec015af432b58b000c\",\n" +
                "        \"title\": \"脑部或神经系统病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Respiratory / Circulatory System Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Andersen氏综合征\",\n" +
                "            \"id\": \"f4be9bee5c807455015c8822a3190109\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Holt-Oram氏综合征\",\n" +
                "            \"id\": \"f4be9bee5c807455015c8824c462010a\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"原发性肺血铁质沉积症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c8816d1d40104\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"囊性纤维化症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c8834072f010c\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"特发性婴儿动脉硬化症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c884b2287010d\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5af3e6ec015af432e93e000d\",\n" +
                "        \"title\": \"呼吸循环系统病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Digestive System Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"α1-抗胰蛋白酶缺乏症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88a2bf0e0111\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性Cajal氏间质细胞增生合并肠道神经元发育异常\",\n" +
                "            \"id\": \"f4be9bee5c807455015c889f60910110\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性胆酸合成障碍\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88a5aa7e0112\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"克罗恩病\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88967398010e\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"进行性家族性肝内胆汁滞留症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88a846370113\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5af3e6ec015af4332db9000e\",\n" +
                "        \"title\": \"消化系统病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Renal / Urinary System Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Alport综合征\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88ac82820116\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Bartter氏综合征\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88afd9fa0118\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Lowe 氏综合征\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88b208530119\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"家族性低血钾症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88a95c7a0114\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"常染色体隐性多囊性肾脏疾病\",\n" +
                "            \"id\": \"f4be9bee5c807455015c88ae03070117\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d51469300a1\",\n" +
                "        \"title\": \"肾脏泌尿系统病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Skin Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Meleda岛病\",\n" +
                "            \"id\": \"f4be9bee5c494735015c717a69db030e\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Netherton综合征\",\n" +
                "            \"id\": \"f4be9bee5c494735015c7172df310309\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性角化不全症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c71771e6a030c\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"外胚层增生不良症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c717b7b0d030f\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"婴儿型全身性玻璃样变性\",\n" +
                "            \"id\": \"f4be9bee5c494735015c716eb19d0307\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d51de8e00a2\",\n" +
                "        \"title\": \"皮肤病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Muscular Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Nemaline线状肌肉病变\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c781033a0009f\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Schwartz Jampel 氏综合征\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c780f2702009d\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"三好氏远端肌肉病变\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c780581e5008c\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"弗里曼-谢尔登综合征\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7803af86008a\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"杜氏肌营养不良症\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7811651b00a2\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d52571900a3\",\n" +
                "        \"title\": \"肌肉病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Bone / Cartilage Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"假性软骨发育不全\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c781a758a00b2\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性胫骨假关节\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7816286800a9\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"多发性骨骺发育不全症\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c78193eef00b0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"多发性骨髓瘤\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7817b2d000ac\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"成骨不全症\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7821672100c0\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d52c4f600a4\",\n" +
                "        \"title\": \"骨及软骨病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Connective Tissue Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Loeys-Dietz综合征\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c78293a7c00c6\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性挛缩细长指（趾）\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7823c2a500c2\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天结缔组织异常Ⅳ型\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c782aba4600c7\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"瓦登伯格综合症\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7827381500c5\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"进行性骨化性肌炎\",\n" +
                "            \"id\": \"f4be9bee5c7693c9015c7824e4af00c3\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d53091300a5\",\n" +
                "        \"title\": \"结缔组织病变\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Blood Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"IPEX 综合症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94e02dbf0190\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Wiskott- Aldrich 氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94ea214b0193\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"低纤溶酶原症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94baf9d20183\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性纯红血球再生障碍性贫血\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94bfe23b0185\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"同基因合子蛋白质 C缺乏症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94c934470189\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d53537a00a6\",\n" +
                "        \"title\": \"血液疾病\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Immune System Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"严重复合型免疫缺乏症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94e4ffc10192\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性高免疫球蛋白 E综合症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94ee25cc0195\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"特发性慢性肉芽肿病\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94f015480196\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"白塞氏症\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94d25698018c\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"系统性红斑狼疮\",\n" +
                "            \"id\": \"f4be9bee5c807455015c94db45b4018e\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d53a01400a7\",\n" +
                "        \"title\": \"免疫疾病\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Endocrine System Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"1α-羟化酶缺乏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62c9de26013e\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Alsrtöm氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62baa9f00138\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Bardet-Biedl氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62b8beca0136\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Kenny-Caffey氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62a40203012c\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Laron 氏侏儒综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62b6f7d50134\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d53e35600a8\",\n" +
                "        \"title\": \"内分泌疾病\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Congenital Malformation\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Aarskog-Scott氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c6695068b018b\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Beckwith Wiedemann 氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c669de57b019b\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"CFC综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c66ced0cb01b7\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"CHARGE 联合征\",\n" +
                "            \"id\": \"f4be9bee5c494735015c66da0eaa01c2\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Conradi- Hünermann氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c66ae5fbe01ad\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d54258000a9\",\n" +
                "        \"title\": \"先天畸形综合征\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Chromosome Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"DiGeorge综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62f3ce5b014c\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Miller-Dieker 综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62fccb420150\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Prader-Willi氏综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62f5f226014d\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"Von Hippel\\u2013Lindau 综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c6302385c0155\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"天使综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c62f241b0014b\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d5464db00aa\",\n" +
                "        \"title\": \"染色体异常\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"enTitle\": \"Other Unclassified / Unknown Diseases\",\n" +
                "        \"diseases\": [\n" +
                "          {\n" +
                "            \"name\": \"Stargardt's氏症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c6330749a0164\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性水痘综合征\",\n" +
                "            \"id\": \"f4be9bee5c494735015c631cf46a015e\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"先天性虹膜缺损\",\n" +
                "            \"id\": \"f4be9bee5c494735015c6328e52d0162\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"克-特二氏综合征\",\n" +
                "            \"id\": \"f4be9bee5c494735015c63268e4d0161\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"发-肝-肠综合症\",\n" +
                "            \"id\": \"f4be9bee5c494735015c631021c30159\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"f4be9bee5c494735015c5d54abc700ab\",\n" +
                "        \"title\": \"其他未分类或不明原因\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"suspension\": [\n" +
                "      {\n" +
                "        \"imgUrl\": \"images/advertise/5b914f26-a962-4a36-bd27-05d96215bbf0.jpg\",\n" +
                "        \"linkUrl\": \"http://www.raredisease.cn/follow!detail?id=f4be9bee5dd05a02015e6c4f4cbd0030\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"res\": {\n" +
                "    \"errMsg\": \"\",\n" +
                "    \"status\": 0\n" +
                "  }\n" +
                "}");

        HanJanBing hanJanBing = JSON.parseObject(str,HanJanBing.class);

         List<HanjianbingResult> hanjianbingResults = new ArrayList<HanjianbingResult>();
        hanjianbingResults.clear();
         for (HanJanBing.InfBean.DiseaseBean diseaseBean : hanJanBing.getInf().getDisease()){
             for (HanJanBing.InfBean.DiseaseBean.DiseasesBean diseasesBean :diseaseBean.getDiseases()){
                 HanjianbingResult hanjianbingResult = new HanjianbingResult();
                 HanjianbingResult.InfBean infBean = new HanjianbingResult.InfBean();
                 infBean.setName(diseasesBean.getId());
                 hanjianbingResult.setInf(infBean);
                 hanjianbingResults.add(hanjianbingResult);
             }
         }

        translateAll(hanjianbingResults);

        System.out.println(hanjianbingResults.size()+"条");
    }


}
