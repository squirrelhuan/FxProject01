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
import java.util.concurrent.CountDownLatch;

/**
 * Created by 97825 on 2020/6/1.
 */
public class LatchTest {


    public static void main(String[] args) throws InterruptedException {

        resultModelList = new ArrayList<>();
        stringBuffer = new StringBuffer();

        MyRunnable taskTemp = new MyRunnable() {
            @Override
            public void run() {
                // 发起请求
                    int index = iCounter++;
                    //System.out.println(System.nanoTime() + " [" + Thread.currentThread().getName() + "] iCounter = " + index);
                    doGet("https://i.gtja.com/webpc/quotes/quote/minorfivedata.json?stkcode=sh600010&xdxrType=pre&type=1day", index);
            }
        };

        LatchTest latchTest = new LatchTest();
        latchTest.startTaskAllInOnce(count, taskTemp);
    }

    public static class MyRunnable implements Runnable{

        public int iCounter;
        public void setiCounter(int iCounter) {
            this.iCounter = iCounter;
        }

        @Override
        public void run() {

        }
    }

    /**
     * 一次性开启
     * @param threadNums
     * @param task
     * @return
     * @throws InterruptedException
     */
    public long startTaskAllInOnce(int threadNums, final MyRunnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        for (int i = 0; i < threadNums; i++) {
            MyThread t = new MyThread(i) {
                public void run() {
                    try {
                        // 使线程在此等待，当开始门打开时，一起涌入门中
                        startGate.await();
                        try {
                            task.setiCounter(index);
                            task.run();
                        } finally {
                            // 将结束门减1，减到0时，就可以开启结束门了
                            endGate.countDown();
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long startTime = System.currentTimeMillis();// System.nanoTime();
        System.out.println(startTime + " [" + Thread.currentThread() + "] 线程准备就绪");
        // 因开启门只需一个开关，所以立马就开启开始门
        startGate.countDown();
        // 等等结束门开启
        endGate.await();
        long endTime = System.currentTimeMillis();
        System.out.println("请求" + count + "次访问用时" + (endTime - startTime) + "毫秒");
        System.out.println(endTime + " [" + Thread.currentThread() + "] 线程全部运行完成");
        return endTime - startTime;
    }

    public static class MyThread extends Thread{
        public int index;

        public MyThread(int index) {
            this.index = index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }


    public static String filePath = "D:\\httpResult.txt";
    public static long startTime;
    public static long endTime;
    public static int count = 1000;//循环次数

    public static List<HttpTest2.ResultModel> resultModelList;//200状态返回的列表
    public static int requestIndex;
    public static int responseIndex;
    public static StringBuffer stringBuffer;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日SSS HH:mm:ss:SSS");// HH:mm:ss
    public static void doGet(String httpurl, int index) {

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
            connection.setReadTimeout(0);

            int r= requestIndex++;
            String str1 =  "第" + r + "次请求"+"\n" ;
            System.out.print(str1);
            // 发送请求
            connection.connect();
            /*
           // stringBuffer.append(str1);
            System.out.print(str1);
            if (r == count - 1) {
                endTime = System.currentTimeMillis();
                StringBuffer stringBuffer1 = new StringBuffer();
                String useTime = simpleDateFormat.format(new Date()) + ":请求" + count + "次访问用时" + (endTime - startTime) + "毫秒" + "\n";
                System.out.print(useTime);
                stringBuffer1.append(useTime);
                WriteStringToFile(filePath, stringBuffer1, true);
            }*/


            // 通过connection连接，获取输入流
           /* if (connection.getResponseCode() == 200) {
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

                HttpTest2.ResultModel resultModel = JSON.parseObject(result, HttpTest2.ResultModel.class);
                resultModelList.add(resultModel);

                stringBuffer.append(simpleDateFormat.format(new Date()) + ":第" + (index + 1) + "条请求，收到回复：" + result + "\n");
                //resultCount++;
            }*/
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(":第" + (index + 1) + "条请求，出错了：" + e.toString() + "\n");
            //stringBuffer.append(simpleDateFormat.format(new Date()) + ":第" + (index + 1) + "条请求，出错了：" + e.toString() + "\n");
        } finally {
            responseIndex++;
            if (responseIndex == count - 1) {
                stringBuffer.append(simpleDateFormat.format(new Date()) + ":请求" + count + "次访问用时" + (endTime - startTime) + "毫秒" + "\n");
                stringBuffer.append(simpleDateFormat.format(new Date()) + ":请求" + count + "次,成功（返回值200）次数：" + resultModelList.size() + "" + "\n");
                WriteStringToFile(filePath, stringBuffer, true);
                System.out.println(resultModelList == null ? 0 : resultModelList.size());
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
            connection.disconnect();// 关闭远程连接
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

}
