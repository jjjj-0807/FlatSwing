package com.mommoo.flat.frame;

import com.mommoo.flat.component.FlatPanel;
import com.mommoo.flat.frame.titlebar.TitleLabel;
import com.mommoo.flat.frame.titlebar.navigation.controller.NavigationControlPanel;
import com.mommoo.flat.frame.titlebar.navigation.listener.NavigationControlListener;
import com.mommoo.flat.image.FlatImagePanel;
import com.mommoo.flat.image.ImageOption;
import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.mommoo.util.ImageManager;

import javax.swing.*;
import java.awt.*;

class CommonTitleBar extends FlatPanel {
	private static final int TITLE_BAR_HEIGHT = 50;
	private static final int TITLE_BAR_SIDE_PADDING = 10;
	private static final int TITLE_TEXT_FONT_SIZE = TITLE_BAR_HEIGHT/3;

	private final TitleLabel TITLE_LABEL = new TitleLabel(TITLE_TEXT_FONT_SIZE);
	private final NavigationControlPanel controlPanel = new NavigationControlPanel(3*TITLE_BAR_HEIGHT/5);

	private FlatImagePanel mainIcon;

	CommonTitleBar() {
		setLayout(new LinearLayout(15));
		setBorder(BorderFactory.createEmptyBorder(0, TITLE_BAR_SIDE_PADDING,0,TITLE_BAR_SIDE_PADDING));
		createMainIcon();
		add(TITLE_LABEL, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		add(controlPanel, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
	}

	private void createMainIcon(){
		Dimension mainIconDimen = new Dimension(3*TITLE_BAR_HEIGHT/5, 3*TITLE_BAR_HEIGHT/5);
		mainIcon = new FlatImagePanel();
		mainIcon.setPreferredSize(mainIconDimen);
	}

	void setTitle(String title){
		TITLE_LABEL.setText(title);
	}
	
	String getTitle(){
		return TITLE_LABEL.getText();
	}
	
	void setIconImage(Image image){
		mainIcon.setImage(image, 3*TITLE_BAR_HEIGHT/5, 3*TITLE_BAR_HEIGHT/5);

		if (isComponentContained(mainIcon)){
			mainIcon.revalidate();
			mainIcon.repaint();
		}else {
			add(mainIcon, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT), 0);
		}
	}

	void removeIconImage(){
		if (isComponentContained(mainIcon)) remove(mainIcon);
	}

	@Override
	public void setBackground(Color color){
		super.setBackground(color);
		if (mainIcon != null) mainIcon.setBackground(color);
		if (controlPanel != null) controlPanel.setButtonColor(color);
	}

	void setButtonIconColor(Color color){
		controlPanel.setButtonIconColor(color);
	}

	Color getButtonIconColor(){
		return controlPanel.getButtonIconColor();
	}
	
	void setTitleColor(Color color){
		TITLE_LABEL.setForeground(color);
	}
	
	Color getTitleColor(){
		return TITLE_LABEL.getForeground();
	}

	void removeControlPanel(){
		remove(controlPanel);
	}

	void setControlListener(NavigationControlListener controlListener){
		controlPanel.setOnControlListener(controlListener);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(super.getPreferredSize().width , TITLE_BAR_HEIGHT);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public static int getTitleBarHeight(){
		return TITLE_BAR_HEIGHT;
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(()->{
			FlatFrame f = new FlatFrame();
			f.setSize(700,500);
			f.setProcessIconImage(ImageManager.TEST);
			f.setEnableSizeButton(true);
			f.setResizable(true);
			f.setTitle("A Beautiful Frame. You can customizing you want!");
			f.setLocationOnScreenCenter();
			f.show();
		});
	}
}