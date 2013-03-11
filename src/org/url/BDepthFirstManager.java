package org.url;


public class BDepthFirstManager extends BufferManager {
	public BDepthFirstManager(String[] urls) {
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
