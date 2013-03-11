package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.url.URLTool;

import controller.OutputController;

public class SaveManager {
	private String path=null;
	public int widthMin,heightMin,sizeMin;	//文件大小不等于长宽之积
	private Log log=null;
	private OutputController output=null;
	private Integer runCount=0;	//TODO 出现负数(-9,-44:高峰1707,)

	public SaveManager(Log log,OutputController output,String path,int width,int height){
		this.path=path;
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		//this.path=tpath; //debugged
		this.log=log;
		this.output=output;
		this.widthMin=width;
		this.heightMin=height;
		this.sizeMin=width*height;
	}

	public void addImages(Map<String,String> images){
		String src=null;
		String url=null;
		Entry<String, String> entry=null;
		Iterator<Entry<String, String>> iter = images.entrySet().iterator(); 
		while (iter.hasNext()) { 
			entry=iter.next(); 
			src=entry.getKey(); 
		    url=entry.getValue(); 
		    addImage(src , url);	//将图片传递到试图层处理
		    //System.out.println(src+" | "+url);
		} 
	}
	//注意次序
	public void addImage(String src ,String url) {
		//String filename=file.toString()+File.separator+URLBoy.getName(src);
		SaveSingle si=new SaveSingle(src,url);
		si.start();
	}
	
	/**
	 * 使用内部类：
	 *  1.SaveSingle 需要从 Manager 类中获取部分常量，
	 *  而这种常量须在Manager对象创建时初始化，且又不适合作为
	 *  SaveSingle的静态变量（当两个不同的Manager创建SaveSingle时）。
	 *  2.Thread构造Runnable匿名类的run()函数中不能调用含参量的外部函数
	 * */
	class SaveSingle extends Thread {
		//TODO 一个网页30张(10-100)，保证50个(5-100)线程运行内存不溢出
		private final static int BUFFER_SIZE=4096; //1KB
		private String src;
		private String url;
		//private boolean success=false;
		private int width=0,height=0; 
		private int size=0;
		public SaveSingle(String src , String url){
			this.src=src;
			this.url=url;
			//success=false;
		}
		public void run()
		{
			synchronized(runCount){
				runCount++;
				output.addStatus("imageRunCount",runCount.toString() );
			}
			try{
				String fileString=path.toString()+File.separator+URLTool.getDomain(src);
				String name=URLTool.getName(src);
				//windows支持的文件名长度有限，取后100字符
				if(name.length()>100)name=name.substring(name.length()-100);
				String address=fileString+"_"+name;
				if(!new File(address).exists()){
					boolean isSuccess=getImage(src,address,sizeMin);
					if(isSuccess){
						log.addSaveSuccessLog(src,url,this.size,address);
						output.increseSaveLog(true);
					}else{
						log.addSaveSmallLog(src,url,this.size); 
						output.increseSaveLog(false);
					}
				}
			}catch(Exception ex){ 
				log.addException("SaveImage.run|"+src, ex);
				output.increseException();
			}
			synchronized(runCount){
				runCount--;
				output.addStatus("imageRunCount",runCount.toString() );
			}
		}
		//！->内存消耗大大降低，但部分图片后部分数据丢失
		public boolean   getImage(String   URLName,String localAddress,int minSize) throws Exception { 
			URLConnection   urlconn=new URL(URLName).openConnection();  
			//System.out.println("outtime="+urlconn.getConnectTimeout());
			//System.out.println("readtime="+urlconn.getReadTimeout());
			this.size=urlconn.getContentLength(); 
			if(size<minSize){ return false; }
			InputStream input = urlconn.getInputStream();     
			OutputStream output = new FileOutputStream(localAddress);
			int ch;
			byte[]   buffer=new   byte[BUFFER_SIZE];
			while((ch=input.read(buffer))!=-1){
				output.write(buffer,0,ch);
			}
			output.flush();
			output.close(); 
			input.close(); 
			return true;
		}
		
		//内存消耗过大，但可以保证图片的完整性
		public void getImage(){
			try{
				//ImageIcon icon=new ImageIcon(new URL(src));
				/**
				 * JAVA 处理大图片(jpeg) 内存溢出的问题：
				 * @see http://topic.csdn.net/u/20081007/23/d3d9fade-84d1-4b09-b0b3-cdddddd11867.html
				 * @see http://bugs.sun.com/bugdatabase/log_bug.do?bug_id=6247845
				 * @advise 比较好的方法是自己实现ImageReader，用文件通道的读取图像数据，然后分块处理数据文件。
				 */
				BufferedImage image=ImageIO.read(new URL(src));
				this.width=image.getWidth();
				this.height=image.getHeight();
				//this.width=icon.getIconWidth();
				//this.height=icon.getIconHeight();
				if(width<widthMin||height<heightMin){
					//return ;
				}
				String name=URLTool.getName(src);
				//windows支持的文件名长度有限，取后100字符
				if(name.length()>100)name=name.substring(name.length()-100);
				String address=path.toString()+File.separatorChar+name;
				ImageIO.write(image, URLTool.getTail(src, '.'), new File(address));
			}catch(Exception ex){
				log.addException("SaveImage.getImage|"+src, ex);
				output.increseException();
			}
		}
	}
}
