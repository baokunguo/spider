package controller;

import mvc.IViewOutput;

public class OutputController {
	private IViewOutput output=null;
	private Integer imageSuccessCount=0;
	private Integer imageSmallCount=0;
	private Integer visitedCount=0;
	private Integer exceptionCount=0;
	public OutputController(IViewOutput output){
		this.output=output;
		
	}
	
	public void increseVisited() {	//http://www.baidu.com/
		synchronized(visitedCount){
			visitedCount++;
			output.setVisitedCount(visitedCount.toString());
		}
	}
	public void increseException() {
		synchronized(exceptionCount){
			exceptionCount++;
			output.setExceptionCount(exceptionCount.toString());
		}
	}
	public void increseSaveLog(boolean isSuccess){
		if(isSuccess){
			synchronized(imageSuccessCount){
				imageSuccessCount++;
				output.setImageSuccessCount(imageSuccessCount.toString());
			}
		}else {
			synchronized(imageSmallCount){
				imageSmallCount++;
				output.setImageSmallCount(imageSmallCount.toString());
			}
		}		
	}

	//runCount,activeCount,depth
	public void addStatus(String tag,String value) {
		if(tag.equals("runCount")){
			output.setRunCount(value);
		}else if(tag.equals("imageRunCount")){
			output.setImageRunCount(value);
		}else if(tag.equals("depth")){
			output.setDepth(value);
		}
	}

}
