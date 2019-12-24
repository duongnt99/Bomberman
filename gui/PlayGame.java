package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sound.GameSound;

import actor.Actor;
import actor.Bomber;
import actor.Manager;


public class PlayGame extends JPanel implements Runnable,ActionListener{
	public static boolean IS_RUNNING=true;
	private MyContainer mContainer;
	private BitSet traceKey = new BitSet();
	private Manager mMagager = new Manager();
	private int count=0;
	private int deadlineBomb=0;
	private int move=0;
	private int timeDead=0;
	private int timeLose=0;
	private int timeNext=0;
	private JButton btn_Menu;
	
	public PlayGame(MyContainer mContainer) {
		this.mContainer = mContainer;
		setBackground(Color.WHITE);
		setLayout(null);
		setFocusable(true);
		addKeyListener(keyAdapter);
		Thread mytheard = new Thread(this);
		mytheard.start();
		innitCompts();
	}
	
	private void innitCompts(){
		btn_Menu = new JButton();
		btn_Menu.setText("Menu");
		btn_Menu.setBounds(750, 520, 100, 30);
		btn_Menu.addActionListener(this);
		//add(btn_Menu);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new java.awt.BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		mMagager.draWBackground(g2d);
		mMagager.drawAllItem(g2d);
		mMagager.drawAllBomb(g2d);
		mMagager.drawAllBox(g2d);
		mMagager.drawAllMonster(g2d);
		mMagager.getmBomber().drawActor(g2d);		
		mMagager.drawAllShawDow(g2d);
		mMagager.drawInfo(g2d);
		mMagager.drawBoss(g2d);
		if(mMagager.getStatus()==1){
			mMagager.drawDialog(g2d, 1);
		}
		if(mMagager.getStatus()==2){
			mMagager.drawDialog(g2d, 2);
		}
		if(mMagager.getStatus()==3){
			mMagager.drawDialog(g2d, 3);
		}
	}
	
	private KeyAdapter keyAdapter = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			traceKey.set(e.getKeyCode());
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			traceKey.clear(e.getKeyCode());
		}
	};
	
	@Override
	public void run() {
		while(IS_RUNNING){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(traceKey.get(KeyEvent.VK_LEFT)){
				mMagager.getmBomber().changeOrient(Bomber.LEFT);
				mMagager.getmBomber().move(count,mMagager.getArrBomb(),mMagager.getArrBox());
				
			}
			if(traceKey.get(KeyEvent.VK_RIGHT)){
				mMagager.getmBomber().changeOrient(Bomber.RIGHT);
				mMagager.getmBomber().move(count,mMagager.getArrBomb(),mMagager.getArrBox());
			}
			if(traceKey.get(KeyEvent.VK_UP)){
				mMagager.getmBomber().changeOrient(Bomber.UP);
				mMagager.getmBomber().move(count,mMagager.getArrBomb(),mMagager.getArrBox());;
			}
			if(traceKey.get(KeyEvent.VK_DOWN)){
				mMagager.getmBomber().changeOrient(Bomber.DOWN);
				mMagager.getmBomber().move(count,mMagager.getArrBomb(),mMagager.getArrBox());
			}
			if(traceKey.get(KeyEvent.VK_SPACE)){
					mMagager.innitBomb();
					mMagager.getmBomber().setRunBomb(Bomber.ALLOW_RUN);

			}
			mMagager.setRunBomer();
			mMagager.deadLineAllBomb();
			mMagager.checkDead();
			mMagager.checkImpactItem();
			mMagager.checkWinAndLose();
			
			if(mMagager.getStatus()==1){
				timeLose++;
				if(timeLose == 3000){
					mMagager.innitManager();
					mContainer.setShowMenu();
					timeLose=0;
				}
			}
			
			if(mMagager.getStatus()==2){
				timeNext++;
				if(timeNext==3000){
					mMagager.innitManager();
					timeNext=0;
				}
			}
			
			if(mMagager.getStatus()==3){
				timeNext++;
				if(timeNext==3000){
					mMagager.innitManager();
					mContainer.setShowMenu();
					timeNext=0;
				}
			}
			
			if(mMagager.getmBomber().getStatus()==Bomber.DEAD){
				timeDead++;
				if(timeDead==3000){
					mMagager.setNewBomb();
					timeDead=0;
				}
			}
			
			if(move==0){
				mMagager.changeOrientAll();
				move=5000;
			}
			if(move>0){
				move--;
			}
			mMagager.moveAllMonster(count);
			repaint();
			count++;
			if(count==1000000){
				count=0;
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_Menu){
			mMagager.setRound(1);
			mMagager.innitManager();
			mContainer.setShowMenu();
		}	
		
	}
}
