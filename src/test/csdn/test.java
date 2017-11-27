package test.csdn;

import java.util.Scanner;
import java.util.regex.MatchResult;

public class test {

		public static void main(String[] args) {
			
			String input = "1 fish 2 fish red fish blue fish";
		     Scanner s = new Scanner(input).useDelimiter("\\s*fish\\s*");
		     System.out.println(s.nextInt());
		     System.out.println(s.nextInt());
		     System.out.println(s.next());
		     System.out.println(s.next());
		     s.close(); 
		     
		     
		    // 以下代码使用正则表达式同时解析所有的 4 个标记，并可以产生与上例相同的输出结果： 

		     Scanner s2 = new Scanner(input);
		     s2.findInLine("(\\d+) fish (\\d+) fish (\\w+) fish (\\w+)");
		     MatchResult result = s2.match();
		     for (int i=1; i<=result.groupCount(); i++)
		         System.out.println(result.group(i));
		     s2.close();
			
		    // TODO Auto-generated method stub
		    Scanner sc = new Scanner(System.in);
		    int T;
		    T=sc.nextInt();
		    if(T>=1|T<=100){    
		    String []str2 = new String[T] ;
		    str2[T]=sc.nextLine();
		    for(int i = 0;i<=T;i++){
		        System.out.println(T);
		        System.out.println(str2[2]);    
		    }
		    }
		    else{
		        System.out.println("输出错误");
		    }
		}
	
}
