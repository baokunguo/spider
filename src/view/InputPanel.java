package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import mvc.IViewInput;
import mvc.Rule;


public class InputPanel extends JPanel implements IViewInput {
	private JLabel urlLabel=new JLabel("��ַ\t");
	private JLabel urlKeywordLabel=new JLabel("��ַ�ؼ���\t");
	private JLabel urlIllegalLabel=new JLabel("��ַ�Ƿ���\t");
	private JLabel imageKeywordLabel=new JLabel("ͼƬ��ַ�ؼ���\t");
	private JLabel imageIllegalLabel=new JLabel("ͼƬ��ַ�Ƿ���\t");
	private JLabel depthLabel=new JLabel("���\t");
	private JLabel threadCountLabel=new JLabel("�߳���\t");
	private JLabel widthLabel=new JLabel("���\t");
	private JLabel widthToLabel=new JLabel(" - ");
	private JLabel heightLabel=new JLabel("�߶�\t");
	private JLabel heightToLabel=new JLabel(" - ");
	private JLabel pathLabel=new JLabel("�����ļ���\t");
	
	private JTextField urlText=new JTextField(25);
	private JTextField urlKeywordText=new JTextField(21);
	private JTextField urlIllegalText=new JTextField("#,+,\"",21);
	private JTextField imageKeywordText=new JTextField(19);
	private JTextField imageIllegalText=new JTextField(".gif,+,\"",19);
	private JTextField depthText=new JTextField("1",10);
	private JTextField threadCountText=new JTextField("5",10);
	private JTextField widthMinText=new JTextField("100",10);
	private JTextField widthMaxText=new JTextField("0",10);
	private JTextField heightMinText=new JTextField("100",10);
	private JTextField heightMaxText=new JTextField("0",10);
	private JTextField pathText=new JTextField(20);
	
	private JButton threadIncreaseButton=new JButton("����");
	private JButton threadDecreaseButton=new JButton("����");
	private JButton openButton=new JButton("���...");
	private JButton switchButton=new JButton("����");
	private JButton cancelButton=new JButton("ȡ��������ʱ��");
	
	private String splitTag=",";
	private String noUrlInfo="��������ַ";

    public InputPanel() {
    	super();
    	setStuct();
    	setAction();
    	//!->test;
    	this.urlText.setText("");
    	this.depthText.setText("1");
    	this.threadCountText.setText("5");
    	
	}
    
    //�򿪱����ļ�
    public static String getLocalPath() {
    	JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		//fileChooser.setDialogTitle(dialogPathTitle);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// disable the "All files" option.
		fileChooser.setAcceptAllFileFilterUsed(false);	
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
		  //fileChooser.getCurrentDirectory());
			return fileChooser.getSelectedFile().toString();
		}else
			return "";
	}
    
    public void setThreadCount(int threadCount){
    	this.threadCountText.setText(threadCount+"");
    }
	public JButton getThreadIncreaseButton(){ return this.threadIncreaseButton; }
	public JButton getThreadDecreaseButton(){ return this.threadDecreaseButton; }
	public JButton getSwitchButton(){ return this.switchButton; }
	public JButton getCancelButton(){ return this.cancelButton; }
	// TODO ->url�ַ���֤
    public Rule getRule(){
    	Rule rule=null;
    	String httpStr="http://";
    	
    	String urlStr=urlText.getText();
    	String hrefKeywordStr=urlKeywordText.getText();
    	String hrefIllegalStr=urlIllegalText.getText();
    	String imageKeywordStr=imageKeywordText.getText();
    	String imageIllegalStr=imageIllegalText.getText();
    	String depthStr=depthText.getText();
    	String threadCountStr=threadCountText.getText();
    	String widthMinStr=widthMinText.getText();
    	String widthMaxStr=widthMaxText.getText();
    	String heightMinStr=heightMinText.getText();
    	String heightMaxStr=heightMaxText.getText();
    	String pathStr=pathText.getText();

       	if(urlStr.equals("")){
       		JOptionPane.showMessageDialog(this, noUrlInfo);
       		return null;	// url=="" -> false;
       	}
    	String[] urls=urlStr.split(this.splitTag);
    	for(int i=0;i<urls.length;i++){
			if(urls[i].indexOf("://")<0){
				urls[i] = httpStr + urls[i];
			}
    	}
		rule=new Rule(urls);
		
		if(hrefKeywordStr.length()>0){
			//""����󳤶�ҲΪ1
			String[] hrefKeywords=hrefKeywordStr.split(this.splitTag);
			rule.hrefKeywords=hrefKeywords;
		}
    	if(hrefIllegalStr.length()>0){
    		String[] hrefIllegals=hrefIllegalStr.split(this.splitTag);
    		rule.hrefIllegals=hrefIllegals;
    	}
    	if(imageKeywordStr.length()>0){
    		String[] imageKeywords=imageKeywordStr.split(this.splitTag);
    		rule.imageKeywords=imageKeywords;
    	}
    	if(imageIllegalStr.length()>0){
    		String[] imageIllegals=imageIllegalStr.split(this.splitTag);
    		rule.imageIllegals=imageIllegals;
    	}
 		if(pathStr.length()>0)rule.savePath=pathStr;
    	
    	try{
	    	int depth=Integer.parseInt(depthStr);
	    	if(depth>0)rule.depth=depth;
    	}catch(NumberFormatException ex){ }
    	try{
	    	int threadCount=Integer.parseInt(threadCountStr);
	    	if(threadCount>1)rule.threadCount=threadCount;
    	}catch(NumberFormatException ex){ }
    	try{
	    	int widthMin=Integer.parseInt(widthMinStr);
    		if(widthMin>0)rule.widthMin=widthMin;
    	}catch(NumberFormatException ex){ }
    	try{
	    	int widthMax=Integer.parseInt(widthMaxStr);
    		if(widthMax>=rule.widthMin)rule.widthMax=widthMax;
    	}catch(NumberFormatException ex){ }	    	
    	try{
    		int heightMin=Integer.parseInt(heightMinStr);
     		if(heightMin>0)rule.heightMin=heightMin;
    	}catch(NumberFormatException ex){ }	    	
    	try{
    		int heightMax=Integer.parseInt(heightMaxStr);
    		if(heightMax>=rule.heightMin)rule.heightMax=heightMax;
    	}catch(NumberFormatException ex){ }
     	return rule;
    }
    
    private void setAction(){
    	openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	pathText.setText(getLocalPath());
            }
        });
    }
   
    
	
	private void setStuct(){
    	//��Ŀ����ʱ����Ϊ׼��ˮƽ���10����ֱ���5
        this.setLayout(new GridLayout(12,1,10,5));
        //top, left, bottom, right
        this.setBorder(new EmptyBorder(30, 20, 10, 10)); 
		
		JPanel pt=null;
		FlowLayout layout=new FlowLayout(FlowLayout.LEFT,10,5);
		JLabel[] labels={urlLabel,urlKeywordLabel,urlIllegalLabel,
				imageKeywordLabel,imageIllegalLabel,depthLabel};
		JTextField[] texts={urlText,urlKeywordText,urlIllegalText,
				imageKeywordText,imageIllegalText,depthText};
		for(int i=0;i<labels.length;i++){
			pt=new JPanel(layout);
			pt.add(labels[i]);
			pt.add(texts[i]);
			this.add(pt);
		}
		
		pt=new JPanel(layout);
		pt.add(threadCountLabel);
		pt.add(threadCountText);
		pt.add(threadIncreaseButton);
		pt.add(threadDecreaseButton);
		this.add(pt);
		
		pt=new JPanel(layout);
		pt.add(widthLabel);
		pt.add(widthMinText);
		pt.add(widthToLabel);
		pt.add(widthMaxText);
		this.add(pt);
		
		pt=new JPanel(layout);
		pt.add(heightLabel);
		pt.add(heightMinText);
		pt.add(heightToLabel);
		pt.add(heightMaxText);
		this.add(pt);
		
		pt=new JPanel(layout);
		pt.add(pathLabel);
		pt.add(pathText);
		pt.add(openButton);
		this.add(pt);
		
		pt=new JPanel(layout);
		pt.add(switchButton);
		pt.add(cancelButton);
		this.add(pt);
	}
}
