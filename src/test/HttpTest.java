package test;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 97825 on 2020/5/30.
 */
public class HttpTest {

    public static List<ResultModel> resultModelList;//200状态返回的列表
    public static int requestIndex;
    public static int responseIndex;
    public static StringBuffer stringBuffer;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");// HH:mm:ss
    public static void doGet(String httpurl, int index) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {


                HttpURLConnection connection = null;
                InputStream is = null;
                BufferedReader br = null;
                String result = null;// 返回结果字符串
                try {
                    // 创建远程url连接对象
                    URL url = new URL(httpurl);
                    // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
                    connection = (HttpURLConnection) url.openConnection();
                    // 设置连接方式：get
                    connection.setRequestMethod("GET");
                    // 设置连接主机服务器的超时时间：15000毫秒
                    connection.setConnectTimeout(1);
                    // 设置读取远程返回的数据时间：60000毫秒
                    connection.setReadTimeout(1);









                    // 发送请求
                    connection.connect();



                    String str1 = simpleDateFormat.format(new Date()) + ":第" + (index + 1) + "次请求" + "\n";
                    stringBuffer.append(str1);
                    System.out.print(str1);
                    requestIndex++;
                    if (requestIndex == count - 1) {
                        long end = System.currentTimeMillis();
                        StringBuffer stringBuffer1 = new StringBuffer();
                        String useTime = simpleDateFormat.format(new Date()) + ":请求" + count + "次访问用时" + (end - startTime) + "毫秒" + "\n";
                        System.out.print(useTime);
                        stringBuffer1.append(useTime);
                        WriteStringToFile(filePath, stringBuffer1, true);
                    }



                    // 通过connection连接，获取输入流
                    if (connection.getResponseCode() == 200) {
                        is = connection.getInputStream();
                        // 封装输入流is，并指定字符集
                        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        // 存放数据
                        StringBuffer sbf = new StringBuffer();
                        String temp = null;
                        while ((temp = br.readLine()) != null) {
                            sbf.append(temp);
                            sbf.append("\r\n");
                        }
                        result = sbf.toString();

                        ResultModel resultModel = JSON.parseObject(result, ResultModel.class);
                        resultModelList.add(resultModel);

                        stringBuffer.append(simpleDateFormat.format(new Date()) + ":第" + (index + 1) + "条请求，收到回复：" + result + "\n");
                        //resultCount++;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println(simpleDateFormat.format(new Date()) + ":第" + (index + 1) + "条请求，出错了：" + e.toString() + "\n");
                    stringBuffer.append(simpleDateFormat.format(new Date()) + ":第" + (index + 1) + "条请求，出错了：" + e.toString() + "\n");
                } finally {
                    connection.disconnect();// 关闭远程连接
                    responseIndex++;
                    if (responseIndex == count - 1) {
                        endTime = System.currentTimeMillis();
                        stringBuffer.append(simpleDateFormat.format(new Date()) + ":请求" + count + "次访问用时" + (endTime - startTime) + "毫秒" + "\n");
                        stringBuffer.append(simpleDateFormat.format(new Date()) + ":请求" + count + "次,成功（返回值200）次数：" + resultModelList.size() + "" + "\n");
                        //WriteStringToFile(filePath, stringBuffer, true);
                        System.out.println(resultModelList == null ? 0 : resultModelList.size());
                        System.out.println(simpleDateFormat.format(new Date()) + ":请求" + count + "次访问用时" + (endTime - startTime) + "毫秒" + "\n");
                    }


                    // 关闭资源
                    if (null != br) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (null != is) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t1.start();
    }

    public static String filePath = "D:\\httpResult.txt";
    public static long startTime;
    public static long endTime;
    public static int count = 1000;//循环次数

    public static void main(String[] args) {
        resultModelList = new ArrayList<>();
        stringBuffer = new StringBuffer();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            doGet("https://i.gtja.com/webpc/quotes/quote/minorfivedata.json?stkcode=sh600010&xdxrType=pre&type=1day", i);
        }
    }

    /**
     * 拼接的txt方法
     */
    public static void WriteStringToFile(String filePath, StringBuffer sb, boolean append) {
        try {

            File file = new File(filePath); //地址
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            FileOutputStream fos = new FileOutputStream(file, append);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            ps.println(new String(sb.toString().getBytes(), "utf-8"));
            try {
                osw.write(sb.toString());
                osw.flush();
                osw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空返回json对象列表
     */
    public static void clearResultModelList() {
        resultModelList.clear();
    }

    /**
     * 用于接收200状态返回值
     */
    public static class ResultModel {
        private String message;
        private String flag;
        private String stockCode;
        private List<ResultChild> mins;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getStockCode() {
            return stockCode;
        }

        public void setStockCode(String stockCode) {
            this.stockCode = stockCode;
        }

        public List<ResultChild> getMins() {
            return mins;
        }

        public void setMins(List<ResultChild> mins) {
            this.mins = mins;
        }
    }

    public static class ResultChild {
        private int volume;
        private double price;
        private long amount;
        private String time;
        private String ma;

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMa() {
            return ma;
        }

        public void setMa(String ma) {
            this.ma = ma;
        }
    }
}
