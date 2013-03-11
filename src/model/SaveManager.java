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
	public int widthMin,heightMin,sizeMin;	//�ļ���С�����ڳ���֮��
	private Log log=null;
	private OutputController output=null;
	private Integer runCount=0;	//TODO ���ָ���(-9,-44:�߷�1707,)

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
		    addImage(src , url);	//��ͼƬ���ݵ���ͼ�㴦��
		    //System.out.println(src+" | "+url);
		} 
	}
	//ע�����
	public void addImage(String src ,String url) {
		//String filename=file.toString()+File.separator+URLBoy.getName(src);
		SaveSingle si=new SaveSingle(src,url);
		si.start();
	}
	
	/**
	 * ʹ���ڲ��ࣺ
	 *  1.SaveSingle ��Ҫ�� Manager ���л�ȡ���ֳ�����
	 *  �����ֳ�������Manager���󴴽�ʱ��ʼ�������ֲ��ʺ���Ϊ
	 *  SaveSingle�ľ�̬��������������ͬ��Manager����SaveSingleʱ����
	 *  2.Thread����Runnable�������run()�����в��ܵ��ú��������ⲿ����
	 * */
	class SaveSingle extends Thread {
		//TODO һ����ҳ30��(10-100)����֤50��(5-100)�߳������ڴ治���
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
				//windows֧�ֵ��ļ����������ޣ�ȡ��100�ַ�
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
		//��->�ڴ����Ĵ�󽵵ͣ�������ͼƬ�󲿷����ݶ�ʧ
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
		
		//�ڴ����Ĺ��󣬵����Ա�֤ͼƬ��������
		public void getImage(){
			try{
				//ImageIcon icon=new ImageIcon(new URL(src));
				/**
				 * JAVA �����ͼƬ(jpeg) �ڴ���������⣺
				 * @see http://topic.csdn.net/u/20081007/23/d3d9fade-84d1-4b09-b0b3-cdddddd11867.html
				 * @see http://bugs.sun.com/bugdatabase/log_bug.do?bug_id=6247845
				 * @advise �ȽϺõķ������Լ�ʵ��ImageReader�����ļ�ͨ���Ķ�ȡͼ�����ݣ�Ȼ��ֿ鴦�������ļ���
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
				//windows֧�ֵ��ļ����������ޣ�ȡ��100�ַ�
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
