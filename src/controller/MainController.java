package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import view.ImageDesktop;
import view.InputPanel;
import view.OutputPanel;

/**
 * 配置view
 * @author Lius
 *
 */
public class MainController extends Controller{
	private JMenuItem newMenuItem=null;
	private JButton threadIncreaseButton=null;
	private JButton threadDecreaseButton=null;
	private JButton switchButton=null;
	private JButton cancelButton=null;
	
	private int switchCount=0;
	
	public MainController(){
		InputPanel ip=new InputPanel();
		OutputPanel op=new OutputPanel();
		ImageDesktop id=new ImageDesktop(ip,op);
    	this.threadIncreaseButton=ip.getThreadIncreaseButton();
    	this.threadDecreaseButton=ip.getThreadDecreaseButton();
    	this.switchButton=ip.getSwitchButton();
    	this.cancelButton=ip.getCancelButton();
    	this.newMenuItem=id.getMenuItem();
    	setAction();
    	
		this.inputView=ip;
    	this.outputView=op;
	}
	
	
	private void setAction(){
		// 创建一个新窗口
    	newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new MainController();
            }
        });

    	cancelButton.setEnabled(false);
    	threadIncreaseButton.setEnabled(false);
    	threadDecreaseButton.setEnabled(false);

    	switchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(switchCount==0){
            		start();
                	threadIncreaseButton.setEnabled(true);
                	threadDecreaseButton.setEnabled(true);
                 	switchButton.setText("暂停（有延时）");
                	cancelButton.setEnabled(true);
                	switchCount++;
            	}else if(rule!=null){
            		if(switchCount%2==1){
                	model.setThreadCount(0);
                	switchButton.setText("继续");
	            	}else{
	            		model.start();
	            		switchButton.setText("暂停（有延时）");
	            	}
            		switchCount++;
            	}
             }
        });
    	  	
    	cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	cancel();
            	switchButton.setEnabled(false);
            	threadIncreaseButton.setEnabled(false);
            	threadDecreaseButton.setEnabled(false);
            	cancelButton.setEnabled(false);
           }
        });
    	threadDecreaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(rule.threadCount>1){	
            		model.setThreadCount(--rule.threadCount);
            		inputView.setThreadCount(rule.threadCount);
            		threadIncreaseButton.setEnabled(true);
            		if(rule.threadCount==1){
            			threadDecreaseButton.setEnabled(false);
            		}
            	}
            }
        });
    	threadIncreaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(model.setThreadCount(rule.threadCount+1)){
             		inputView.setThreadCount(++rule.threadCount);
             		threadDecreaseButton.setEnabled(true);
            	}else{
            		threadIncreaseButton.setEnabled(false);
            	}
            }
        });
	}

}
