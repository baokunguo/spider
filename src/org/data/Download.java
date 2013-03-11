package org.data;

import java.io.*;
import java.net.URL;

public class Download {
	
	/**
	 * 从互联网下载数据
	 * @param src 网址
	 * @param file 地址+文件名，若为空则用文件原名称
	 * @return 是否成功下载
	 */
	public static boolean saveBinary(String src,String file)
	{
		final int SIZE=10240;
		InputStream in=null;
		FileOutputStream realFile=null;
		URL url=null;
		try{
			byte[] buffer=new byte[SIZE];
			int ch;
			url=new URL(src);
			in=url.openStream();
			realFile=new FileOutputStream(file);
			while((ch=in.read(buffer))!=-1){
				realFile.write(buffer,0,ch);
			}
			//System.out.println("success save:"+file);
			realFile.close();
			in.close();
			return true;
		}catch(IOException ex){
			System.out.println("Download.saveBinary :"+ex.toString());
			return false;
		}
	}
	
}
