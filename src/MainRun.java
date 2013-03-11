
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.data.Download;
import org.page.HtmlPage;

import app.wanfang.Wanfang;

import controller.MainController;

public class MainRun{

	public static void main(String [] agr)throws Exception{
		//long bs = java.util.Calendar.getInstance().getTimeInMillis();
		//new MainController();
		//Zip.encode("apps\\backup\\small", "apps\\zip\\small.dog");
		//Zip.decode("apps\\zip\\small.dog", "apps\\zip\\small_decode");
		//new Wanfang();
		//ETag: "0-2253-4f541fad"
		hans("grsinfo.zju.edu.cn" , "/search_master2012.html");
		//hans("grsinfo.zju.edu.cn" , "/kscjcx/grxxcx.jsp?zkzh=103352000001794&sfzh=&bmh=&xm=朱新艳");

	}

	public static void hans(String address , String filepath) throws Exception{
		Socket client=new Socket(address,80);
		PrintWriter result=new PrintWriter(client.getOutputStream(),true);
		
		//发送请求
		//"referer:http://www.baidu.com/";	//请求时当前的网址
		result.println("GET "+ filepath +" HTTP/1.1");
		result.println("Host:"+address);//请求头必须规范，以免造成不必要的麻烦
		result.println("referer:http://10.1.13.49/phpProject/a.php");
		//result.println("Accept-Charset:UTF-8");
		result.println("Connection:Close");
		result.println();
		
		int rInt;
		InputStreamReader input=new InputStreamReader(client.getInputStream(),"UTF-8");
		while((rInt=input.read())!=-1)System.out.print((char)rInt);
	}
	public static void renren() throws Exception{
		String address="renren.com";
		Socket client=new Socket(address,80);
		PrintWriter result=new PrintWriter(client.getOutputStream(),true);
		
		//发送请求
		String filepath="/";
		//"referer:http://www.baidu.com/";	//请求时当前的网址
		result.println("HEAD "+ filepath +" HTTP/1.1");
		result.println("Host:"+address);//请求头必须规范，以免造成不必要的麻烦
		result.println("Connection:Close");
		result.println();
		int rInt;
		//StringBuilder sb=new StringBuilder("");
		InputStreamReader input=new InputStreamReader(client.getInputStream(),"UTF-8");
		while((rInt=input.read())!=-1)System.out.print((char)rInt);
		//++c<1000&&
		String url="http://"+address+filepath;
		URLConnection conn=new URL(url).openConnection();
        for (int i=0; ; i++) {
            String headerName = conn.getHeaderFieldKey(i);
            String headerValue = conn.getHeaderField(i);
    
            if (headerName == null && headerValue == null) {
                // No more headers
                break;
            }
            if (headerName == null) {
            	continue;
                // The header value contains the server's HTTP version
            }
            //System.out.println(headerName+"="+headerValue);
        }
		
		//System.out.println("URLConnection: "+con.getHeaderField("Location"));
	}


}
