package controller;

import model.ImageTraverse;
import model.Log;
import model.SaveManager;
import mvc.IViewInput;
import mvc.IViewOutput;
import mvc.Rule;

import org.url.MBreadthFirstManager;
import org.url.MDepthFirstManager;
import org.url.URLManager;

/**
 * 配置model
 * @author Lius
 *
 */
public abstract class Controller {
	protected Rule rule=null;
	protected ImageTraverse model=null;
	protected SaveManager save=null;
	protected Log log=null;
	protected OutputController output=null;
	protected URLManager urlQueue=null;
	
	protected IViewInput inputView=null;
	protected IViewOutput outputView=null;

	/* 初始化inputView 和 outputView */	
	public Controller(){
	}

	public void start(){
    	rule=inputView.getRule();
    	if(rule==null){ return ; }
    	try{
    		log=new Log(rule.database);	
    		output=new OutputController(outputView);
        	save=new SaveManager(log,output,rule.savePath,rule.widthMin,rule.heightMin);
        	if(rule.isBreadthFirst){
        		urlQueue=new MBreadthFirstManager(rule.urls);
        	}else{
        		urlQueue=new MDepthFirstManager(rule.urls);
        	}
        	model=new ImageTraverse(rule,urlQueue,log,output,save);
        	model.start();
        	
        }catch(Exception ex){
        	System.out.println(ex.toString());
        	return ; }
	}
	
	public void cancel(){
    	/* 释放内存 */
     	model.setThreadCount(-1);
     	log=null;
    	save=null;
    	urlQueue=null;
    	model=null;
	}

}
