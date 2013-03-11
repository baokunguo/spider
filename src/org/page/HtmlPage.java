package org.page;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

//->��������ģʽ��������һ���ԣ���������Ч
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
		/* ������λ��getInputStream()֮�����Ч�����Socket����һ�����磬��������һ�ڿκ��� */
		/* google [URLConnection 302  ��ת url] */
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
	 * @return �ı�����
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
		
		// read to beginString: �빹˼���Ķ��������
		int index,beginLength=beginString.length();
		char cha=beginString.charAt(0);
		StringBuilder sb=new StringBuilder(beginLength);
		int tag=-1;
		do{
			//System.out.println("0 while");
			//��ȡbeginString���ȵ��ַ���
			for(i=0;i<beginLength;i++){ 
				if((tag=input.read())!=-1){ 
					sb.append((char)tag);
				}
				else break;
			}
			//System.out.println("read:"+sb);

			while(true){
				if(sb.toString().equals(beginString)){
					//ƥ��ɹ����˳�
					tag=-1;
					
					break;
				}else if((index=sb.toString().indexOf(cha,1))>0){
					//������beginString�����ַ�
					//System.out.println("before append:"+sb);
					sb.delete(0, index);	//��ȥ��sbǰindex���ַ�
					//������index�ַ�����׷����sb��β��
					for(i=0;i<index;i++){ sb.append((char)input.read()); }
					//System.out.println("after append:"+sb);
				}else { 
					break;	//���ص����¶�ȡbeginString���ȵ��ַ��� 
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
						html.append(sb.substring(0, index));	//��ͬ1
						sb.delete(0, index);
						for(i=0;i<index;i++){ sb.append((char)input.read()); }
					}else { 
						html.append(sb);	//��ͬ2
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
