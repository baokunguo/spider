package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Zip {
	private static int SIZE=1024;
	public static void encode(String fromDir,String toFile) throws Exception{
		File file=new File(fromDir);
		String[] list=file.list();
		RandomAccessFile fromRaf;
		RandomAccessFile toRaf=new RandomAccessFile(toFile,"rw");
		for(String s:list) {
			file=new File(fromDir+File.separator+s);
			if(file.isDirectory())continue ;
			fromRaf=new RandomAccessFile(file,"r");
			byte buffer[]=new byte[SIZE];
			int ch;
			long length=0;
			try{
				toRaf.writeInt(s.length());	//0
				toRaf.writeUTF(s);	//1
				toRaf.writeLong(fromRaf.length());	//2
				while((ch=fromRaf.read(buffer))!=-1){	//3
					toRaf.write(buffer, 0, ch);
					length+=ch;
				}
				fromRaf.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		toRaf.writeInt(0);	//4,Ω· ¯±Í÷æ
		toRaf.close();
	}
	public static void decode(String fromFile,String toDir) throws FileNotFoundException{
		new File(toDir).mkdirs();
		System.out.println(toDir);
		RandomAccessFile fromRaf=new RandomAccessFile(fromFile,"rw");
		
		FileOutputStream output=null;
		while(true){
			try{
				byte[] buffer =new byte[SIZE];
				int nameLength=fromRaf.readInt();
				if(nameLength==0) break;
				String name=fromRaf.readUTF();
				long leftLength=fromRaf.readLong();
				output=new FileOutputStream(toDir+File.separator+name);
				int bufferSize = SIZE<leftLength ? SIZE : (int)leftLength;
	
				while(leftLength>0){
					bufferSize=fromRaf.read(buffer,0,bufferSize);
					output.write(buffer,0,bufferSize);
					leftLength-=bufferSize;
					if(leftLength<SIZE)bufferSize=(int)leftLength;
				}
				output.close();
			}
			catch(FileNotFoundException ex){ex.printStackTrace();break;}
			catch(IOException ex){ex.printStackTrace();break;}
		}
	}

}
