package model;

import java.util.List;

import org.url.Tree;
import org.url.URLManager;

import controller.OutputController;

import mvc.Rule;


/**
 * TODO ���δ�ﵽ�����������쳣������ַ����ʱû���߳�����ʱ��ֹͣ���߳̿��Ƴ���Ӧ��������
 * TODO Java heap space���߳̿���
 * synchronized�ھ�̬�����ϱ�ʾ����ǰҪ�������������ڷǾ�̬�����ϱ�ʾ���ô˷���ǰҪ��ö��������
 * @author Lius
 */
public class ImageTraverse {
	private static final int MAX_THREAD_COUNT=100;	//����߳���
	private URLManager urlQueue=null;	//��ڵ�ַ
	private Log log=null;
	private OutputController output=null;
	private SaveManager save=null;

	private int depth;	//�������
	private int threadCount=1;
	
	private Integer runCount=0;
	private Integer continuousCount=0;	//��̬�̲߳�����־
	public boolean isRunning=true;
	
	public String[] hrefKeywords=null;	//��ַ�ؼ��ַ�
	public String[] hrefIllegals=null;	//��ַ�Ƿ��ַ�
	public String[] imgIllegals=null;	//ͼƬ·���Ƿ��ַ�
	
	private Tree hrefTree=null;	//��ַ�Ƿ���ʱ����
	private Tree imgTree=null;	//ͼƬ�Ƿ���ʹ�


	/*TODO ����ȫ��������־ */
	public ImageTraverse(Rule rule,URLManager urlQueue,Log log,OutputController output,SaveManager save){
		this.log=log;
		this.output=output;
		this.save=save;
		this.urlQueue=urlQueue;		
		if(rule.depth>=0){ this.depth=rule.depth; }
		if(rule.threadCount>1 && rule.threadCount<=MAX_THREAD_COUNT){ this.threadCount=rule.threadCount; }
		this.hrefKeywords=rule.hrefKeywords;
		this.hrefIllegals=rule.hrefIllegals;
		this.imgIllegals=rule.imageIllegals;
		
		hrefTree=new Tree("http:");
		imgTree=new Tree("http:");

	}
	
	/**
	 * @return true:�ɹ�����,false:��������Խ��
	 */
	public boolean setThreadCount(int threadCount){
		if(threadCount>0 && threadCount<=MAX_THREAD_COUNT){
			this.threadCount=threadCount; 
			return true;
		}else if(threadCount==0){	//��ͣ������threadCount
			isRunning=false;
		}else if(threadCount==-1){	//ֹͣ
			isRunning=false;
			threadCount=0;
			/* TODO ���������ͷ��ڴ� */
		} 
		return false;

	}
	public boolean start(){
		isRunning=true;
		Thread t=new Thread(new Runnable(){
			public void run(){
				singleRun();
			}
		});
		t.start();
		//rule.threadCount++;
		return true;
	}

	// TODO runCount���������˳����ܻص�0
	public void singleRun(){
		//System.out.println("singleRun");
		synchronized(runCount){ 
			runCount++;
			output.addStatus("runCount",runCount.toString());
		}
		String[] urlAndDepth=null;
		// ����ͨ��activeCount==0�����ж��Ƿ�ȫ������������else��ȡ��url����δ���ü��������еĳ���
		while(isRunning && (urlAndDepth=urlQueue.next())!=null){
			//����ʱ��ֻҪδ�˳��߳̾�һֱΪactive״̬
			int tNowDepth=Integer.parseInt(urlAndDepth[1]);
			output.addStatus("depth", ""+tNowDepth);
			//System.out.println("depth="+tNowDepth+" activeCount="+activeCount+" runCount="+runCount);
			List<String> links=doSingle(urlAndDepth[0]);
			
			if(links!=null){
				log.addVisitedLog(urlAndDepth[0],links.size());
				output.increseVisited();
				if(tNowDepth<depth){
					urlQueue.addAll(links,tNowDepth+1);	//��ȵ���������ַ��ӵ����ʶ���
				}
			}
			
			/* ��̬�߳���Ŀ���ƣ���Ƶ�����ӣ���������ջ�����ϴ󡣽��������ⲿ��� */
			synchronized(runCount){
				if(runCount<this.threadCount){
					//������2�λ�ȡurl�������߳�С��Ҫ���߳�ʱ������һ���߳�
					synchronized(continuousCount){
						if(++continuousCount>2){ this.start(); }
					}
				}else if(runCount>this.threadCount){
					//���ⲿ���Ƽ����߳���ʱ���˳����߳�
					break;
				}
			}	//synchronized(runCount)
		}	//while
		
		synchronized(continuousCount){ continuousCount=0; }

		synchronized(runCount){
			runCount--;
			output.addStatus("runCount",runCount.toString());
		}
	}

	/**
	 * ����������Դ��������ҳ�е�ͼƬ
	 * @param pageUrl
	 * @param hasNextDepth
	 * @return ��ҳ�пɷ��ʵ���ַ
	 */
	public List<String> doSingle(String pageUrl){
		try{
			Page page=new Page(pageUrl,null);
			ImageParser parser=new ImageParser(page,hrefTree,imgTree);

			/* ��֤�������е�ͼƬ����ȡ */
			List<String> links=parser.getLinks(hrefKeywords,hrefIllegals);
			// TODO !-> , rule.widthMin , rule.heightMin
			this.save.addImages(parser.getText(imgIllegals));	//����Ϣ����
			return links;
		}catch(Exception ex){
			this.log.addException("ImageModel.doSingle|"+pageUrl, ex);
			output.increseException();
			return null;
		}
	}
}
