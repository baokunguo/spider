package org.http;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class SConnection {
	private static String LOCATION_STRING = "Location";

	protected String domain;
	protected String path;
	protected String cookie;
	protected String state;
	protected HashMap<String,String> sets;
	protected HashMap<String,String> gets;	//getContent ��ʼ��
	protected InputStream input;
	
	public SConnection(String domain,String path) throws UnknownHostException, IOException{
		this.domain = domain;
		this.path = path;
		cookie = "";
		sets = new HashMap<String,String>();
	}
	public void setHeader(String key,String value){
		this.sets.put(key, value);
		//result.println("referer:http://10.1.13.49/phpProject/a.php");
		//result.println("Accept-Charset:UTF-8");
	}

	protected void getHeader() throws UnknownHostException, IOException{
		/* �Զ���ת�µ�ַ */
		while(true){
			//System.out.println("domain="+domain);
			//System.out.println("path="+path);
			getCurrentHeader("GET");
			String location = gets.get(LOCATION_STRING);
			if(location=="" ||location==null)
				break;
			else{
				//��cookie��ӵ�sets��
				this.cookie += gets.get("Set-Cookie");
				if(cookie!=""&&cookie!=null)
					sets.put("Cookie", cookie.substring(0,cookie.length()));

				//����domain������path
				int i=0,j=0;
				if(location.length()>4 && location.substring(0, 4).equals("http"))
					i=location.indexOf("://")+3;
				j=location.indexOf("/",i);
				if(j>=0){
					this.domain = location.substring(i, j);
					i = j;
					j = location.indexOf(" ", i);
					if(j>i)
						this.path = location.substring(i,j);
					else
						this.path = location.substring(i);
				}
				else{
					this.domain = location.substring(i);
					this.path = "/";
				}
			}
		}
	}

	private void getCurrentHeader(String method) throws IOException{
		/* accept http header */
		sendHeader(method);
		//InputStreamReader input=new InputStreamReader(getInputStream(method));
		
		StringBuilder line = new StringBuilder();
		int t;
		//��Ӧͷ/r/n����������ӡֻ��ʾһ�����У�������ӡ�������
		//http://blog.csdn.net/fallinsky/article/details/5969512
		//TODO ?��ӡ(t+" ")�޽��������ӡ(t+"|")OK
		while((t=this.input.read())!=-1 && t!='\n') { line.append((char)t); }	//readLine
		line.deleteCharAt(line.length()-1);	//line end with '\r'
		state = line.toString();
		//System.out.println("response="+state);
		//bugged:���gets
		gets = new HashMap<String,String>();
		while(true){
			line.setLength(0);
			while((t=this.input.read())!=-1 && t!='\n') { line.append((char)t); }	//readLine
			line.deleteCharAt(line.length()-1);	//line end with '\r'
			int i;
			if(line.length()==0 || (i=line.indexOf(":"))<0)break;	
			
			String key,value;
			key = line.substring(0 , i);
			i++;
			while(line.charAt(i)==' ')i++;
			value = line.substring(i);
			gets.put(key, value);
			//TODO ���Set-Cookie
			System.out.println(key+":"+value);
		}
		System.out.println("--------------------");
	}
	
	private void sendHeader(String method)throws UnknownHostException, IOException{
		/* send http header request */
		Socket client=new Socket(this.domain,80);
		PrintWriter headSender=new PrintWriter(client.getOutputStream(),true);
		//ֻ����һ�����н���������������Ϣ��Ч
		headSender.println(method + " " + this.path +" HTTP/1.1");	//debugged:path end with '\r' before
		headSender.println("Host:" + this.domain);	//����ͷ����淶��������ɲ���Ҫ���鷳

		Iterator<Entry<String, String>>  iter = sets.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Entry<String, String> entry = (Entry<String, String>) iter.next(); 
		    String s = entry.getKey()+":"+entry.getValue(); 
		    //System.out.println("req "+s);
			headSender.println(s);	//����û��Զ���ͷ��Ϣ
		}
		headSender.println("Connection:Close");	//������ֹ���ӣ��޷���������
		headSender.println();
		
		this.input = client.getInputStream();
	}
}
