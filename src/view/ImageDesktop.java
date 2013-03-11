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

//������Flow���̶���Grid
public class ImageDesktop extends JFrame {
    // ����µ�״̬��
    JLabel messageLine; 
    JMenuItem newMenuItem=null;
    static final String title="Hypiker 1.0";
    static int numBrowserWindows = 0;	// ��ǰ�Ѿ��򿪵������������
    
	JTextField fromText=new JTextField(10);
	JTextField toText=new JTextField(10);
	JLabel infoLabel=new JLabel("         ");


    public JMenuItem getMenuItem(){ return this.newMenuItem; }
 	public ImageDesktop(JPanel leftPanel,JPanel rightPanel){
        super(title);
        ImageDesktop.numBrowserWindows++;	// ����ǰ�򿪴���������1
        startAnimation("animation");
        
        JPanel dd=new JPanel(new GridLayout(1,0,20,80));
        dd.add(new JScrollPane(leftPanel));
        dd.add(new JScrollPane(rightPanel));
        this.add(dd);
        // ����״̬����ǩ�������������ڵײ�
        messageLine = new JLabel(" ");
        this.getContentPane().add(messageLine, BorderLayout.SOUTH);
        // ��ʼ���˵��͹�����
        this.initMenu();
        this.initToolbar();
       
        // ����close�����رմ��ڣ�������ϡ����޵�һ�����ã�����񣬴��ڽ��ᱻ��Ϊ���ɼ�
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
     * ��ʼ���˵���
     */
    private void initMenu(){
    	// �ļ��˵����������ĸ��˵���½����˳�����
    	JMenu fileMenu = new JMenu("�ļ�");
    	fileMenu.setMnemonic('F');
    	newMenuItem = new JMenuItem("�½�");
    	newMenuItem.setMnemonic('N');
    	// �����½���ʱ��һ�����������

    	JMenuItem exitMenuItem = new JMenuItem("�˳�����");
    	exitMenuItem.setMnemonic('E');
    	// �����˳���ʱ�˳�Ӧ�ó���
    	exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	close();
            }
        });
    	
    	fileMenu.add(newMenuItem);
    	fileMenu.add(exitMenuItem);
    	
    	//�����˵�����һ���˵������
    	JMenu helpMenu = new JMenu("����");
    	fileMenu.setMnemonic('H');
    	JMenuItem aboutMenuItem = new JMenuItem("����");
    	aboutMenuItem.setMnemonic('A');
    	helpMenu.add(aboutMenuItem);
    	
    	JMenuBar menuBar = new JMenuBar();
    	menuBar.add(fileMenu);
    	menuBar.add(helpMenu);
    	
    	// ���ò˵�����������
    	this.setJMenuBar(menuBar);
    }
    
    /**
     * ��ʼ��������
     */
    private void initToolbar(){
        // ǰ����ť������ǰһҳ�档��ʼ����¸ð�ť������
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
        // �����������������ڵı���
        this.getContentPane().add(toolbar, BorderLayout.NORTH);
    }
    
    private void encode(){
    	try{
	    	this.infoLabel.setText(" ��ѹ��ʼ ");
    		String toFile=toText.getText();
    		if(toFile.length()<5 || (!".spr".equals(toFile.substring(toFile.length()-4)))){
    			toFile+=".spr";
    		}
    		Zip.encode(fromText.getText(), toFile);
    		this.infoLabel.setText(" ��ѹ�ɹ� ");
    	}catch(Exception ex){
    		this.infoLabel.setText(" ��ѹʧ�� ");
    	}
    }
    private void decode(){
    	this.infoLabel.setText("        ");
		String strDate=JOptionPane.showInputDialog(this,"�������ļ�У����","�ļ�У��",JOptionPane.INFORMATION_MESSAGE);
        if(getDate().equals(strDate)){
	    	try{
		    	this.infoLabel.setText(" ��ѹ��ʼ ");
				Zip.decode(fromText.getText(), toText.getText());
	    		this.infoLabel.setText(" ��ѹ�ɹ� ");
	    	}catch(Exception ex){
	    		this.infoLabel.setText(" ��ѹʧ�� ");
	    	}
        }else{
        	this.infoLabel.setText(" У����� ");
        }
    }

    /**
     * �رյ�ǰ���ڣ�������д��ڶ��رգ������exitWhenLastWindowClosed���ԣ�
     * �ж��Ƿ��˳�Ӧ�ó���
     */
    public void close() {
    	/*
    	(JOptionPane.showConfirmDialog(this, "��ȷ���˳� "+title+" ��", "�˳�",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				*/
    	// �����Ի�������ȷ�ϣ����ȷ���˳������˳�Ӧ�ó���
		if (true){
	    	// �����ص�ǰ���ڣ����ٴ����е������
	        this.setVisible(false);
	        this.dispose();
	        // ����ǰ�򿪴�������1��
	        // ������д��ڶ��ѹرգ�����exitWhenLastWindowClosedΪ�棬���˳�
	        // ���������synchronized�ؼ��֣���֤�̰߳�ȫ
	        synchronized(ImageDesktop.class) {    
	        	ImageDesktop.numBrowserWindows--; 
	            if ((numBrowserWindows==0)){
	                System.exit(0);
	            }
	        }
		}
		return ;
    }
 
    // ������Ϣ����ʾ�������״̬����ǩ�ϣ����ڷ����������״̬
    String animationMessage;
    // ������ǰ��֡������
    int animationFrame = 0;
    // �������õ���֡����һЩ�ַ���
    String[] animationFrames = new String[] {
        "-", "\\", "|", "/", "-", "\\", "|", "/", 
        ",", ".", "o", "0", "O", "#", "*", "+"
    };

    /**
     * �½�һ��Swing�Ķ�ʱ����ÿ��125�������һ��״̬����ǩ���ı�
     */
    javax.swing.Timer animator =
        new javax.swing.Timer(125, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	animate(); 
                }
            });

    /**
     * ��ʾ�����ĵ�ǰ֡��״̬����ǩ�ϣ�����֡��������
     */
    private void animate() {
        String frame = animationFrames[animationFrame++];
        messageLine.setText(animationMessage + " " + frame);
        animationFrame = animationFrame % animationFrames.length;
    }

    /**
     * ��������
     */
    private void startAnimation(String msg) {
        animationMessage = msg;
        animationFrame = 0; 
        // ������ʱ��
        animator.start();
    }

    /**
     * ֹͣ����
     */
    private void stopAnimation() {  
    	// ֹͣ��ʱ��
        animator.stop();
        messageLine.setText(" ");
    }
    
    private String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//�������ڸ�ʽ
	    return df.format(new Date());
	}

}
