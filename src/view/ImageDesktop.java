package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import model.Zip;

//浮动用Flow，固定用Grid
public class ImageDesktop extends JFrame {
    // 最底下的状态栏
    JLabel messageLine; 
    JMenuItem newMenuItem=null;
    static final String title="Hypiker 1.0";
    static int numBrowserWindows = 0;	// 当前已经打开的浏览器窗口数
    
	JTextField fromText=new JTextField(10);
	JTextField toText=new JTextField(10);
	JLabel infoLabel=new JLabel("         ");


    public JMenuItem getMenuItem(){ return this.newMenuItem; }
 	public ImageDesktop(JPanel leftPanel,JPanel rightPanel){
        super(title);
        ImageDesktop.numBrowserWindows++;	// 将当前打开窗口数增加1
        startAnimation("animation");
        
        JPanel dd=new JPanel(new GridLayout(1,0,20,80));
        dd.add(new JScrollPane(leftPanel));
        dd.add(new JScrollPane(rightPanel));
        this.add(dd);
        // 创建状态栏标签，并放在主窗口底部
        messageLine = new JLabel(" ");
        this.getContentPane().add(messageLine, BorderLayout.SOUTH);
        // 初始化菜单和工具栏
        this.initMenu();
        this.initToolbar();
       
        // 调用close方法关闭窗口，两者配合。若无第一个设置，点击否，窗口将会被设为不可见
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});

        
		this.setSize(1000, 650);
        this.setVisible(true); 
 	}

    /**
     * 初始化菜单栏
     */
    private void initMenu(){
    	// 文件菜单，下面有四个菜单项：新建、退出窗口
    	JMenu fileMenu = new JMenu("文件");
    	fileMenu.setMnemonic('F');
    	newMenuItem = new JMenuItem("新建");
    	newMenuItem.setMnemonic('N');
    	// 当“新建”时打开一个浏览器窗口

    	JMenuItem exitMenuItem = new JMenuItem("退出窗口");
    	exitMenuItem.setMnemonic('E');
    	// 当“退出”时退出应用程序
    	exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	close();
            }
        });
    	
    	fileMenu.add(newMenuItem);
    	fileMenu.add(exitMenuItem);
    	
    	//帮助菜单，就一个菜单项：关于
    	JMenu helpMenu = new JMenu("帮助");
    	fileMenu.setMnemonic('H');
    	JMenuItem aboutMenuItem = new JMenuItem("关于");
    	aboutMenuItem.setMnemonic('A');
    	helpMenu.add(aboutMenuItem);
    	
    	JMenuBar menuBar = new JMenuBar();
    	menuBar.add(fileMenu);
    	menuBar.add(helpMenu);
    	
    	// 设置菜单栏到主窗口
    	this.setJMenuBar(menuBar);
    }
    
    /**
     * 初始化工具栏
     */
    private void initToolbar(){
        // 前进按钮，进到前一页面。初始情况下该按钮不可用
    	JLabel fromLabel=new JLabel(" From  ");
    	JLabel toLabel=new JLabel("  To  ");
    	JButton encodeButton=new JButton("Encode");
    	JButton decodeButton=new JButton("Decode");
     	encodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	encode();
            }
        });      
     	decodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	decode();
            }
        });      
        JToolBar toolbar = new JToolBar();
        toolbar.add(fromLabel);
        toolbar.add(this.fromText);
        toolbar.add(toLabel);
        toolbar.add(this.toText);
        toolbar.add(encodeButton);
        toolbar.add(decodeButton);
        toolbar.add(this.infoLabel);
        // 将工具栏放在主窗口的北部
        this.getContentPane().add(toolbar, BorderLayout.NORTH);
    }
    
    private void encode(){
    	try{
	    	this.infoLabel.setText(" 加压开始 ");
    		String toFile=toText.getText();
    		if(toFile.length()<5 || (!".spr".equals(toFile.substring(toFile.length()-4)))){
    			toFile+=".spr";
    		}
    		Zip.encode(fromText.getText(), toFile);
    		this.infoLabel.setText(" 加压成功 ");
    	}catch(Exception ex){
    		this.infoLabel.setText(" 加压失败 ");
    	}
    }
    private void decode(){
    	this.infoLabel.setText("        ");
		String strDate=JOptionPane.showInputDialog(this,"请输入文件校验码","文件校验",JOptionPane.INFORMATION_MESSAGE);
        if(getDate().equals(strDate)){
	    	try{
		    	this.infoLabel.setText(" 解压开始 ");
				Zip.decode(fromText.getText(), toText.getText());
	    		this.infoLabel.setText(" 解压成功 ");
	    	}catch(Exception ex){
	    		this.infoLabel.setText(" 解压失败 ");
	    	}
        }else{
        	this.infoLabel.setText(" 校验错误 ");
        }
    }

    /**
     * 关闭当前窗口，如果所有窗口都关闭，则根据exitWhenLastWindowClosed属性，
     * 判断是否退出应用程序
     */
    public void close() {
    	/*
    	(JOptionPane.showConfirmDialog(this, "你确定退出 "+title+" 吗？", "退出",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				*/
    	// 弹出对话框，请求确认，如果确认退出，则退出应用程序
		if (true){
	    	// 先隐藏当前窗口，销毁窗口中的组件。
	        this.setVisible(false);
	        this.dispose();
	        // 将当前打开窗口数减1。
	        // 如果所有窗口都已关闭，而且exitWhenLastWindowClosed为真，则退出
	        // 这里采用了synchronized关键字，保证线程安全
	        synchronized(ImageDesktop.class) {    
	        	ImageDesktop.numBrowserWindows--; 
	            if ((numBrowserWindows==0)){
	                System.exit(0);
	            }
	        }
		}
		return ;
    }
 
    // 动画消息，显示在最底下状态栏标签上，用于反馈浏览器的状态
    String animationMessage;
    // 动画当前的帧的索引
    int animationFrame = 0;
    // 动画所用到的帧，是一些字符。
    String[] animationFrames = new String[] {
        "-", "\\", "|", "/", "-", "\\", "|", "/", 
        ",", ".", "o", "0", "O", "#", "*", "+"
    };

    /**
     * 新建一个Swing的定时器，每个125毫秒更新一次状态栏标签的文本
     */
    javax.swing.Timer animator =
        new javax.swing.Timer(125, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	animate(); 
                }
            });

    /**
     * 显示动画的当前帧到状态栏标签上，并将帧索引后移
     */
    private void animate() {
        String frame = animationFrames[animationFrame++];
        messageLine.setText(animationMessage + " " + frame);
        animationFrame = animationFrame % animationFrames.length;
    }

    /**
     * 启动动画
     */
    private void startAnimation(String msg) {
        animationMessage = msg;
        animationFrame = 0; 
        // 启动定时器
        animator.start();
    }

    /**
     * 停止动画
     */
    private void stopAnimation() {  
    	// 停止定时器
        animator.stop();
        messageLine.setText(" ");
    }
    
    private String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	    return df.format(new Date());
	}

}
