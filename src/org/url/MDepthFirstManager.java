package org.url;


public class MDepthFirstManager extends MomeryManager {
	public MDepthFirstManager(String[] urls) {
		super(urls);
	}

	/**
	 * @return url和深度值
	 */
	public synchronized String[] next(){
		if(urlQueue.size()==0)return null;
		String[] rStrs=new String[2];
		rStrs[0]=urlQueue.removeLast();
		
		Node dHead=dQueue.getLast();
		if(dHead.count==0){
			dQueue.removeLast();
			dHead=dQueue.getLast();
		}
		//System.out.print("dCount1="+dQueue.getLast().count);
		dHead.count--;
		//System.out.println("   dCount2="+dQueue.getLast().count);
		rStrs[1]=dHead.value+"";
		return rStrs;
	}
}
