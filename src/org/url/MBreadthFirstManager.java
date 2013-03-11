package org.url;


public class MBreadthFirstManager extends MomeryManager {
	public MBreadthFirstManager(String[] urls){
		super(urls);
	}
	
	/**
	 * @return url和深度值
	 */
	public synchronized String[] next(){
		//System.out.println("next.size="+super.urlQueue.size());
		if(super.urlQueue.size()==0)return null;
		String[] rStrs=new String[2];
		rStrs[0]=urlQueue.removeFirst();
		
		Node dHead=dQueue.getFirst();
		if(dHead.count==0){
			dQueue.removeFirst();
			dHead=dQueue.getFirst();
		}
		//System.out.print("dCount1="+dQueue.getFirst().count);
		dHead.count--;
		//System.out.println("   dCount2="+dQueue.getFirst().count);
		rStrs[1]=dHead.value+"";
		return rStrs;
	}

}
