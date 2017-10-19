package com.mommoo.flat.frame;

import com.mommoo.animation.AnimationAdapter;
import com.mommoo.animation.Animator;
import com.mommoo.animation.timeInterpolator.AccelerateInterpolator;
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
import java.util.List;

class CommonTitleBar extends FlatPanel {
	private static final int TITLE_BAR_HEIGHT = 50;
	private static final int TITLE_BAR_SIDE_PADDING = 10;
	private static final int TITLE_TEXT_FONT_SIZE = TITLE_BAR_HEIGHT/3;

	private final TitleLabel TITLE_LABEL = new TitleLabel(TITLE_TEXT_FONT_SIZE);
	private final NavigationControlPanel controlPanel = new NavigationControlPanel(3*TITLE_BAR_HEIGHT/5);

	private FlatImagePanel mainIcon;

	CommonTitleBar() {
		init();
		createMainIcon();
		add(TITLE_LABEL, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		add(controlPanel, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
	}

	private void init(){
		setOpaque(true);
		setLayout(new LinearLayout(15));
		setBorder(BorderFactory.createEmptyBorder(0, TITLE_BAR_SIDE_PADDING,0,TITLE_BAR_SIDE_PADDING));
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

	public static void main(String[] args){
		SwingUtilities.invokeLater(()->{
			FlatFrame f = new FlatFrame();
			f.setSize(700,500);
			f.setProcessIconImage(ImageManager.TEST);
			f.setEnableSizeButton(true);
			f.setResizable(true);
//			f.getContainer().setBackground(Color.RED);
			f.getContainer().setOpaque(true);
			f.getContainer().setLayout(null);
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JButton("GOODFOSDFSO"));
			f.getContainer().add(new JButton("COOL")).setBounds(0,0,200,200);
			f.getContainer().add(panel).setBounds(200,200,200,200);
			f.setTitle("A Beautiful Frame. You can customizing you want!");
			f.setLocationOnScreenCenter();
			f.show();

			new Animator()
					.setTimeInterpolator(new AccelerateInterpolator())
					.setDuration(1000)
					.setAnimationListener(new AnimationAdapter(){
						@Override
						public void onAnimation(List<Double> resultList) {
							super.onAnimation(resultList);
//							try {
//								Thread.sleep(100);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
							f.getContainer().getComponent(1).setLocation(200 - resultList.get(0).intValue(), 200 - resultList.get(0).intValue());

						}
					})
					.start(200);
			((JPanel)f.getJFrame().getContentPane()).setDoubleBuffered(true);
			new Animator()
					.setTimeInterpolator(new AccelerateInterpolator())
					.setDuration(1000)
					.setAnimationListener(new AnimationAdapter(){
						@Override
						public void onAnimation(List<Double> resultList) {
							super.onAnimation(resultList);
							f.getJFrame().setSize(new Dimension(700, 500 - resultList.get(0).intValue()));
//							f.getJFrame().repaint();
							f.getJFrame().revalidate();
//							f.getJFrame().setSize(700, 500 - resultList.get(0).intValue());
						}
					})
					.start(200);
		});
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

	void setIconImage(Image image){
		mainIcon.setImage(image, ImageOption.MATCH_PARENT);

		if (isComponentContained(mainIcon)){
			mainIcon.revalidate();
			mainIcon.repaint();
		}else {
			add(mainIcon, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT), 0);
		}
	}
}