package org.url;


public class BBreadthFirstManager extends BufferManager {
	
	public BBreadthFirstManager(String[] urls){
		super(urls);
	}

	public synchronized String[] next(){
		return null;
	}
	
	protected boolean writeBuffer(){
		return true;
	}
	
	protected boolean readBuffer(){
		return true;		
	}

}
