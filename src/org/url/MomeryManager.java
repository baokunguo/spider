package org.url;

import java.util.LinkedList;
import java.util.List;

/**
 * ��������ʵ�ֶ��б�����ȿ���
 * @author Lius
 * ����""ֻ���ǵ��̵߳���ȿ��ƣ��޷����ж��߳�
 */

/**
	Integer a = 100; 
	Integer b = 100; 
	System.out.println(a==b); 
	���Ϊ:true������������� 128 > var >= -128 ֮�������Ϊfalse�� 
	1.java�ڱ����ʱ�� Integer a = 100; �������-> Integer a = Integer.valueOf(100); 
	2.�Ƚϵ�ʱ����Ȼ�Ƕ���ıȽ� 
	3.��a++���´�����һ�����󣬲�����ǰ�Ķ���
 */

public abstract class MomeryManager implements URLManager {

	/* AbstractQueue�����಻�ܴ�����Node */
	public class Node{
		Node(int v,int c){
			this.value=v;
			this.count=c;
		}
		public int value;
		public int count;
	}	
	
	//������1619ҳ3MB�ڴ�;
	//1758-5MB.
	//608-16MB,tree.size= 10MB
	//701-7MB(38280��),tree.size=7MB,
	//��ַ���ȣ�100-200��
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
			//���µ����ֵ�����һ����ӽ��������ֵ���ʱ�ϲ��������������Ӽ�¼ֵ
			node.count++;
		}else{	//���򴴽�һ���¼�¼
			node=new Node(depth,1);
			dQueue.add(node);
		}
	}
	public synchronized void addAll(List<String> list,int depth){
		urlQueue.addAll(list);
		Node node=null;
		if(dQueue.size()>0 && (node=dQueue.getLast()).value==depth){
			//���µ����ֵ�����һ����ӽ��������ֵ���ʱ�ϲ��������������Ӽ�¼ֵ
			node.count+=list.size();
		}else{	//���򴴽�һ���¼�¼
			node=new Node(depth,list.size());
			dQueue.add(node);
		}
	}

}
