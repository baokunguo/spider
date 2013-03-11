package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.IViewOutput;

public class OutputPanel extends JPanel implements IViewOutput {

	private JLabel depthLabel=new JLabel("�������\t");
	private JLabel runCountLabel=new JLabel("�����߳�\t");
	private JLabel imageRunCountLabel=new JLabel("ͼƬ�߳�\t");
	private JLabel imageSuccessCountLabel=new JLabel("�ɹ�������\t");
	private JLabel imageSmallCountLabel=new JLabel("С���޶���\t");
	private JLabel visitedCountLabel=new JLabel("������ҳ��\t");
	private JLabel exceptionCountLabel=new JLabel("�����쳣��\t");

	private JLabel depthValueLabel=new JLabel("0");
	private JLabel runCountValueLabel=new JLabel("0");
	private JLabel imageRunCountValueLabel=new JLabel("0");
	private JLabel imageSuccessCountValueLabel=new JLabel("0");
	private JLabel imageSmallCountValueLabel=new JLabel("0");
	private JLabel visitedCountValueLabel=new JLabel("0");
	private JLabel exceptionCountValueLabel=new JLabel("0");

	public OutputPanel() {
		setStruct();
		
	}
	
	private void setStruct(){
    	//��Ŀ����ʱ����Ϊ׼��ˮƽ���10����ֱ���5
        this.setLayout(new GridLayout(12,1,10,5));
        //top, left, bottom, right
        this.setBorder(new EmptyBorder(30, 20, 10, 10)); 
		JPanel pt=null;
		FlowLayout layout=new FlowLayout(FlowLayout.LEFT,10,5);
		
		JLabel[] tags={depthLabel,runCountLabel,imageRunCountLabel,
				imageSuccessCountLabel,imageSmallCountLabel,visitedCountLabel,exceptionCountLabel};
		JLabel[] values={depthValueLabel,runCountValueLabel,imageRunCountValueLabel,
				imageSuccessCountValueLabel,imageSmallCountValueLabel, visitedCountValueLabel,exceptionCountValueLabel};
		for(int i=0;i<tags.length;i++){
			pt=new JPanel(layout);
			pt.add(tags[i]);
			pt.add(values[i]);
			this.add(pt);
		}
		/*
		// ��ʾHTML�����
		JEditorPane textPane; 
        textPane = new JEditorPane(); 
        textPane.setEditable(false); 
        textPane.setText("textPane");
        this.add(new JScrollPane(textPane));
        textPane.setPage(url); 
		 */

	}
	
	public void setDepth(String depthStr){
		if(!depthValueLabel.getText().equals(depthStr)){
			this.depthValueLabel.setText(depthStr);
		}
	}
	public void setRunCount(String runCounuStr){
		if(!runCountValueLabel.getText().equals(runCounuStr)){
			this.runCountValueLabel.setText(runCounuStr);
		}
	}
	public void setImageRunCount(String imageRunCountStr){
		this.imageRunCountValueLabel.setText(imageRunCountStr);
	}
	public void setImageSuccessCount(String imageSuccessCountStr){
		this.imageSuccessCountValueLabel.setText(imageSuccessCountStr);
	}
	public void setImageSmallCount(String imageSmallCountStr){
		this.imageSmallCountValueLabel.setText(imageSmallCountStr);
	}
	public void setVisitedCount(String visitedCountStr){
		this.visitedCountValueLabel.setText(visitedCountStr);
	}
	public void setExceptionCount(String exceptionCountStr){
		this.exceptionCountValueLabel.setText(exceptionCountStr);
	}

}
