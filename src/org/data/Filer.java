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
 * �ļ���д���ƶ�
 * @author Lius
 */
public class Filer {
	
	/**
	 * д���ı�����
	 * @param file �洢��ַ+�ļ���
	 * @param info �ı�����
	 * @param isAppend �Ƿ���׷�ӵķ�ʽд���ı�����
	 * @return �Ƿ�ɹ�д��
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
	 * ��ȡ�ı��ļ�����
	 * @param file Դ�ļ�
	 * @param firstLine ��ʵ��ȡ��
	 * @return �ı�����
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
	 * ��ȡ�ı��ļ�����
	 * @param file Դ�ļ�
	 * @return һ��Ϊ����ַ�������
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
	 * �����ı�
	 * @param from Դ�ļ�
	 * @param to Ŀ���ַ
	 * @return �Ƿ�ɹ�
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
	 * �������и�ʽ���ļ�
	 * @param from Դ�ļ�
	 * @param to Ŀ���ַ
	 * @return �Ƿ�ɹ�
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
