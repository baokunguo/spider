package org.http;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Browser extends SConnection {
	private static String CONTENT_TYPE = "Content-Type";
	private static String CHARSET = "charset=";
	public Browser(String domain,String path) throws UnknownHostException, IOException{
		super(domain,path);
		getHeader();
	}
	public void getFile() throws Exception{
		int t;
		FileOutputStream realFile = new FileOutputStream("G:/2326.pdf");
		byte[] buffer=new byte[1024];
		while((t=input.read(buffer))!=-1){
			realFile.write(buffer,0,t);
		}
		realFile.close();
		input.close();
	}
	
	public StringBuilder getHtml() throws UnknownHostException, IOException{
		InputStreamReader ir;
		String charset = getHeaderField(CONTENT_TYPE);
		
		if(charset!=null && charset.length()>9){
			charset = charset.substring(charset.indexOf(CHARSET,9)+8);
			ir=new InputStreamReader(input,charset);
		}
		else
			ir=new InputStreamReader(input);
		
		StringBuilder html = new StringBuilder();
		int t;
		while((t=ir.read())!=-1) { html.append((char)t); }		
		return html;
	}
	
	//必须调用getInputStream()后才能有效
	public String getHeaderField(String key){ return this.gets.get(key); }
}
