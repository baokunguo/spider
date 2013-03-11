package org.url;

import java.util.LinkedList;
import java.util.List;

/**
 * 两个链表实现队列便于深度控制
 * @author Lius
 * 采用""只能是单线程的深度控制，无法进行多线程
 */

/**
	Integer a = 100; 
	Integer b = 100; 
	System.out.println(a==b); 
	结果为:true，但是如果换成 128 > var >= -128 之外的整数为false。 
	1.java在编译的时候 Integer a = 100; 被翻译成-> Integer a = Integer.valueOf(100); 
	2.比较的时候仍然是对象的比较 
	3.但a++是新创建了一个对象，不是以前的对象。
 */

public abstract class MomeryManager implements URLManager {

	/* AbstractQueue的子类不能创建新Node */
	public class Node{
		Node(int v,int c){
			this.value=v;
			this.count=c;
		}
		public int value;
		public int count;
	}	
	
	//个例：1619页3MB内存;
	//1758-5MB.
	//608-16MB,tree.size= 10MB
	//701-7MB(38280个),tree.size=7MB,
	//网址长度：100-200；
	protected LinkedList<String> urlQueue=new LinkedList<String>();
	protected LinkedList<Node> dQueue=new LinkedList<Node>();
	
	public MomeryManager(String[] urls){
		for(String s:urls){ this.urlQueue.add(s); }
		Node node=new Node(0,urls.length);
		dQueue.add(node);
		//System.out.println("size="+this.urlQueue.size());
	}
	
	public synchronized void add(String url,int depth){
		urlQueue.add(url);
		Node node=null;
		if(dQueue.size()>0 && (node=dQueue.getLast()).value==depth){
			//当新的深度值与最后一次添加进来的深度值相等时合并，辅助队列增加记录值
			node.count++;
		}else{	//否则创建一条新记录
			node=new Node(depth,1);
			dQueue.add(node);
		}
	}
	public synchronized void addAll(List<String> list,int depth){
		urlQueue.addAll(list);
		Node node=null;
		if(dQueue.size()>0 && (node=dQueue.getLast()).value==depth){
			//当新的深度值与最后一次添加进来的深度值相等时合并，辅助队列增加记录值
			node.count+=list.size();
		}else{	//否则创建一条新记录
			node=new Node(depth,list.size());
			dQueue.add(node);
		}
	}

}
