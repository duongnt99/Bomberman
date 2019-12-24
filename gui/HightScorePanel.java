package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import actor.HightScore;


public class HightScorePanel extends JPanel implements ActionListener{
	private MyContainer mContainer;
	private Image backgroundImage;
	private JButton btn_ok;
	private ArrayList<HightScore> arrHightScore;
	
	public HightScorePanel(MyContainer mContainer) {
		this.mContainer = mContainer;
		setBackground(Color.YELLOW);
		setLayout(null);
		initCompts();
	}
	
	public void initCompts(){
		ReadFileHightScore();
		
		btn_ok = new JButton();
		btn_ok.setText("OK");
		btn_ok.setBounds(400, 520, 100, 50);
		btn_ok.addActionListener(this);
		add(btn_ok);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D )g;
		drawImage(g2d);
		drawHightSore(g2d);
	}
	
	public void drawImage(Graphics2D g2d){
		Image background = new ImageIcon(getClass().getResource("/Images/background_hightscore.png")).getImage();
		g2d.drawImage(background, 0, 0, null);
	}
	
	public void drawHightSore(Graphics2D g2d){
		g2d.setStroke(new java.awt.BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(new Font("Arial", Font.BOLD, 30));
		g2d.setColor(Color.RED);	
		g2d.drawRect(250, 50, 400, 450);
		g2d.setColor(Color.YELLOW);
		int y=100;
		for(int i=0;i<arrHightScore.size();i++){
			g2d.drawString(""+(i+1), 270, y);
			g2d.drawString(""+arrHightScore.get(i).getName(), 380, y);
			g2d.drawString(""+arrHightScore.get(i).getScore(), 600, y);
			y=y+40;
		}
		
	}
	
	public void ReadFileHightScore(){
		arrHightScore = new ArrayList<HightScore>();
		try {
			FileReader file = new FileReader("src/hightscore/HightScore.txt");
			BufferedReader input = new BufferedReader(file);
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				String name = str[0];
				int score = Integer.parseInt(str[1]);
				HightScore hightScore = new HightScore(name, score);
				arrHightScore.add(hightScore);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_ok){
			mContainer.setShowMenu();
		}	
	}
}
