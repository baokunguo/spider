package model;

import java.util.List;

import org.url.Tree;
import org.url.URLManager;

import controller.OutputController;

import mvc.Rule;


/**
 * TODO 深度未达到，由于连接异常大量地址作废时没有线程运行时就停止，线程控制程序应独立调度
 * TODO Java heap space，线程卡死
 * synchronized在静态方法上表示调用前要获得类的锁，而在非静态方法上表示调用此方法前要获得对象的锁。
 * @author Lius
 */
public class ImageTraverse {
	private static final int MAX_THREAD_COUNT=100;	//最大线程数
	private URLManager urlQueue=null;	//入口地址
	private Log log=null;
	private OutputController output=null;
	private SaveManager save=null;

	private int depth;	//遍历深度
	private int threadCount=1;
	
	private Integer runCount=0;
	private Integer continuousCount=0;	//动态线程补给标志
	public boolean isRunning=true;
	
	public String[] hrefKeywords=null;	//网址关键字符
	public String[] hrefIllegals=null;	//网址非法字符
	public String[] imgIllegals=null;	//图片路径非法字符
	
	private Tree hrefTree=null;	//网址是否访问标记树
	private Tree imgTree=null;	//图片是否访问过


	/*TODO 返回全部结束标志 */
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
	 * @return true:成功设置,false:操作或是越界
	 */
	public boolean setThreadCount(int threadCount){
		if(threadCount>0 && threadCount<=MAX_THREAD_COUNT){
			this.threadCount=threadCount; 
			return true;
		}else if(threadCount==0){	//暂停，保护threadCount
			isRunning=false;
		}else if(threadCount==-1){	//停止
			isRunning=false;
			threadCount=0;
			/* TODO 结束命令释放内存 */
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

	// TODO runCount计数错误：退出不能回到0
	public void singleRun(){
		//System.out.println("singleRun");
		synchronized(runCount){ 
			runCount++;
			output.addStatus("runCount",runCount.toString());
		}
		String[] urlAndDepth=null;
		// 不能通过activeCount==0进行判断是否全部结束，可能else获取到url但还未来得及运行其中的程序
		while(isRunning && (urlAndDepth=urlQueue.next())!=null){
			//无延时则只要未退出线程就一直为active状态
			int tNowDepth=Integer.parseInt(urlAndDepth[1]);
			output.addStatus("depth", ""+tNowDepth);
			//System.out.println("depth="+tNowDepth+" activeCount="+activeCount+" runCount="+runCount);
			List<String> links=doSingle(urlAndDepth[0]);
			
			if(links!=null){
				log.addVisitedLog(urlAndDepth[0],links.size());
				output.increseVisited();
				if(tNowDepth<depth){
					urlQueue.addAll(links,tNowDepth+1);	//深度递增，将网址添加到访问队列
				}
			}
			
			/* 动态线程数目控制，但频繁增加，函数调用栈开销较大。建议移至外部检查 */
			synchronized(runCount){
				if(runCount<this.threadCount){
					//当连续2次获取url且运行线程小于要求线程时，增加一个线程
					synchronized(continuousCount){
						if(++continuousCount>2){ this.start(); }
					}
				}else if(runCount>this.threadCount){
					//当外部控制减少线程数时，退出此线程
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
	 * 配置数据来源，保存网页中的图片
	 * @param pageUrl
	 * @param hasNextDepth
	 * @return 网页中可访问的网址
	 */
	public List<String> doSingle(String pageUrl){
		try{
			Page page=new Page(pageUrl,null);
			ImageParser parser=new ImageParser(page,hrefTree,imgTree);

			/* 保证超连接中的图片被提取 */
			List<String> links=parser.getLinks(hrefKeywords,hrefIllegals);
			// TODO !-> , rule.widthMin , rule.heightMin
			this.save.addImages(parser.getText(imgIllegals));	//将信息保存
			return links;
		}catch(Exception ex){
			this.log.addException("ImageModel.doSingle|"+pageUrl, ex);
			output.increseException();
			return null;
		}
	}
}
