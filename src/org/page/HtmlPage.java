package org.page;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

//->采用设置模式，函数的一致性，？但不高效
public class HtmlPage {
	protected String url=null;
	protected StringBuilder html=null;
	private InputStreamReader input=null;
	
	private int beginPoint=0;
	private String beginString=null;
	private String endString=null;
	public HtmlPage(String url,String charset)throws Exception
	{
		URLConnection conn=new URL(url).openConnection();
		conn.setConnectTimeout(10000);//10second to connect;
		conn.setRequestProperty("User-Agent","");
		if(charset==null||charset.equals(""))charset=getCharset(conn);
		if(charset!=null)this.input=new InputStreamReader(conn.getInputStream(),charset);
		else this.input=new InputStreamReader(conn.getInputStream());
		/* ！必须位于getInputStream()之后才有效，结合Socket尝试一个上午，下午上了一节课后发现 */
		/* google [URLConnection 302  跳转 url] */
		this.url=conn.getURL().toString();
	}

	public String getUrl(){return this.url;}
	
	public void setBeginPoint(int beginPoint){ 
		if(beginPoint>0) { this.beginPoint=beginPoint; }
	}
	public void setBeginString(String beginString){ 
		if(!"".equals(beginString)) { this.beginString=beginString; }
	}
	public void setEndString(String endString){ 
		if(!"".equals(beginString)) { this.endString=endString; }
	}
	
	/**
	 * @return 文本内容
	 * @throws Exception
	 */
	public StringBuilder getHtml()throws Exception{
		if(html!=null) return html;
		html=new StringBuilder();
		goBegin();
		goEnd();
		input.close();
		return html;
	}
	

	private void goBegin() throws IOException{
		int i=0;
		while(i++<beginPoint && input.read()!=-1);
		if(beginString==null)return ;
		
		// read to beginString: 请构思并阅读经典代码
		int index,beginLength=beginString.length();
		char cha=beginString.charAt(0);
		StringBuilder sb=new StringBuilder(beginLength);
		int tag=-1;
		do{
			//System.out.println("0 while");
			//读取beginString长度的字符串
			for(i=0;i<beginLength;i++){ 
				if((tag=input.read())!=-1){ 
					sb.append((char)tag);
				}
				else break;
			}
			//System.out.println("read:"+sb);

			while(true){
				if(sb.toString().equals(beginString)){
					//匹配成功则退出
					tag=-1;
					
					break;
				}else if((index=sb.toString().indexOf(cha,1))>0){
					//若含有beginString的首字符
					//System.out.println("before append:"+sb);
					sb.delete(0, index);	//则去掉sb前index个字符
					//并读入index字符，并追加在sb的尾部
					for(i=0;i<index;i++){ sb.append((char)input.read()); }
					//System.out.println("after append:"+sb);
				}else { 
					break;	//返回到重新读取beginString长度的字符串 
				}
			}
			sb.setLength(0);
		}while(tag!=-1);
	}
	
	private void goEnd() throws IOException{
		int rInt;
		if(endString==null){
			while((rInt=input.read())!=-1)html.append((char)rInt);
		}else{
			// append to endString
			int i,index,beginLength=endString.length();
			char cha=endString.charAt(0);
			StringBuilder sb=new StringBuilder(beginLength);
			int tag=-1;
			do{
				for(i=0;i<beginLength;i++){
					if((tag=input.read())!=-1){ 
						sb.append((char)tag);
					}
					else break;
				}
				while(true){
					if(sb.toString().equals(endString)){
						tag=-1;
						break;
					}else if((index=sb.toString().indexOf(cha,1))>0){
						html.append(sb.substring(0, index));	//不同1
						sb.delete(0, index);
						for(i=0;i<index;i++){ sb.append((char)input.read()); }
					}else { 
						html.append(sb);	//不同2
						break;	
					}
				}
				sb.setLength(0);
			}while(tag!=-1);
		}

	}
	private String getCharset(URLConnection conn) {
		try{
		  String s=conn.getContentType();
		  if(s.length()>9)
		  {
			  return s.substring(s.indexOf("charset=",9)+8);
		  }
		  return null;
		}catch(Exception ex){ return null; }
	}
	
	
	

}
