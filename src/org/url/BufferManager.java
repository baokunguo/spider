package org.url;

import java.util.List;

/**
 * TODO 遍历较深时，缓存机制降低内存使用
 * @author Lius
 *
 */
public abstract class BufferManager implements URLManager  {

	protected final int size=1000;
	protected int head=-1,tail=-1;
	protected int[] depths=new int[size];
	protected String[] urls=new String[size];
	
	public BufferManager(String[] ss){
		for(int i=0;i<ss.length;i++){
			if(++tail%size==head){
				if(head==-1)head=0;
				writeBuffer();
			}
			urls[tail]=ss[i];
			depths[tail]=0;
			if(head==-1)head=0;
		}
	}
	
	protected abstract boolean writeBuffer();
	protected abstract boolean readBuffer();
	public synchronized void add(String url,int depth){
		if(++tail%size==head){
			writeBuffer();
		}
		urls[tail]=url;
		depths[tail]=depth;
	}
	public synchronized void addAll(List<String> list,int depth){
		for(String s:list){
			if(++tail%size==head){
				writeBuffer();
			}
			urls[tail]=s;
			depths[tail]=0;
		}
	}
	

}
