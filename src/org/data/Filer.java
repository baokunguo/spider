package org.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件读写，移动
 * @author Lius
 */
public class Filer {
	
	/**
	 * 写入文本内容
	 * @param file 存储地址+文件名
	 * @param info 文本内容
	 * @param isAppend 是否以追加的方式写入文本内容
	 * @return 是否成功写入
	 */
	static public boolean writer(String file,String info,boolean isAppend)
	{
		Filer.copyByte("", "");
		BufferedWriter bw=null;
		try{
			bw=new BufferedWriter(new FileWriter(file,isAppend));
			bw.write(info);
			bw.close();
			return true;
		}catch(IOException ex){
			System.out.println("My Writer.IOException :"+ex.toString());
			return false;
		}
	}
	
	/**
	 * 读取文本文件内容
	 * @param file 源文件
	 * @param firstLine 其实读取行
	 * @return 文本内容
	 */
	public static  String reader(String file,int firstLine)
	{
		StringBuilder sb=new StringBuilder();
		BufferedReader br=null;
		try{
			br=new BufferedReader(new FileReader(file));
			String line;
			for(int i=0;i<firstLine;i++)br.readLine();
			while((line=br.readLine())!=null)
			{
				sb.append(line+"\n");
			}
			br.close();
			return sb.toString();
		}catch(IOException ex){
			System.out.println("My Reader.IOException :"+ex.toString());
			return null;
		}
	}
	
	/**
	 * 读取文本文件内容
	 * @param file 源文件
	 * @return 一行为项的字符串数组
	 */
	public static String[] getList(String file)
	{
		ArrayList<String> lines=new ArrayList<String>();
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			String s;
			while((s=br.readLine())!=null)
			{
				lines.add(s);
			}
			br.close();
		}catch(Exception e){System.out.println("Filer.getList:"+e.toString());}
		String rs[]=new String[lines.size()];
		int i=0;
		for(String s:lines)rs[i++]=s;
		return rs;
	}
	
	/**
	 * 复制文本
	 * @param from 源文件
	 * @param to 目标地址
	 * @return 是否成功
	 */
	public static boolean copyText(String from,String to)
	{
		BufferedReader br=null;
		BufferedWriter bw=null;
		try{
			br=new BufferedReader(new FileReader(from));
			bw=new BufferedWriter(new FileWriter(to));
			String line;
			while((line=br.readLine())!=null)
			{
				bw.write(line+"\n");
			}
			br.close();
			bw.close();
			return true;
		}catch(IOException ex){
			System.out.println("Filer.copyChar IOException :"+ex.toString());
			return false;
		}
	}
	/**
	 * 复制所有格式的文件
	 * @param from 源文件
	 * @param to 目标地址
	 * @return 是否成功
	 */
	public static boolean copyByte(String from ,String to)
	{
		FileInputStream input=null;
		FileOutputStream output=null;
		try{
			int number;
			byte[] buffer=new byte[1024];
			input=new FileInputStream(from);
			output=new FileOutputStream(to);
			while((number=input.read(buffer))!=-1)
			{
				output.write(buffer, 0, number);
			}
			output.close();
			input.close();
			return true;
		}catch(IOException ex){
			System.out.println("Filer.copyByte.IOException :"+ex.toString());
			return false;
		}
	}

}
