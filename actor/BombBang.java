package actor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BombBang {
	private int x,y,size,timeLine;
	private Image img_left, img_right, img_up, img_down;
	
	public BombBang(int x, int y, int size, ArrayList<Box> arrBox) {
		this.x=x;
		this.y=y;
		this.size=size;
		this.timeLine = 250;
		img_left = new ImageIcon(getClass().getResource("/Images/bombbang_left_1.png")).getImage();
		img_right = new ImageIcon(getClass().getResource("/Images/bombbang_right_1.png")).getImage();
		img_up = new ImageIcon(getClass().getResource("/Images/bombbang_up_1.png")).getImage();
		img_down = new ImageIcon(getClass().getResource("/Images/bombbang_down_1.png")).getImage();
		for(int i=1;i<size;i++){
			int tmp_left=0, tmp_right=0 ,tmp_up=0 ,tmp_dow=0 ;
			for(int j=0;j<arrBox.size();j++){
				if(isImpactBox(x-(i)*45, y, (i+1)*45, 45, arrBox.get(j))){
					tmp_left=1;
				}
				if(isImpactBox(x, y, (i+1)*45, 45, arrBox.get(j))){
					tmp_right=1;
				}
				if(isImpactBox(x, y-(i*45), 45, (i+1)*45, arrBox.get(j))){
					tmp_up=1;
				}
				if(isImpactBox(x, y, 45, (i+1)*45, arrBox.get(j))){
					tmp_dow=1;
				}
			}
			if(tmp_left==0){
				setImage(Bomber.LEFT, i+1);
			}
			if(tmp_right==0){
				setImage(Bomber.RIGHT, i+1);
			}
			if(tmp_up==0){
				setImage(Bomber.UP, i+1);
			}
			if(tmp_dow==0){
				setImage(Bomber.DOWN, i+1);
			}
		}
		
	}
	
	public void drawBongBang(Graphics2D g2d){
		g2d.drawImage(img_left, x+45-img_left.getWidth(null), y,null);
		g2d.drawImage(img_right, x, y,null);
		g2d.drawImage(img_up, x, y+45-img_up.getHeight(null),null);
		g2d.drawImage(img_down, x, y,null);
	}
	
	private boolean isImpactBox(int x, int y, int width, int height, Box box){
		Rectangle rec1 = new Rectangle(x, y, width, height);
		Rectangle rec2 = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
		return rec1.intersects(rec2);
	}
	
	public boolean isImpactBombBangVsActor(Actor actor){
		Rectangle rec1 = new Rectangle(x+45-img_left.getWidth(null)+5, y+5, img_left.getWidth(null)-5, img_left.getHeight(null)-10);
		Rectangle rec2 = new Rectangle(x, y+5, img_right.getWidth(null)-5, img_right.getHeight(null)-10);
		Rectangle rec3 = new Rectangle(x+5, y+45-img_up.getHeight(null)+5, img_up.getWidth(null)-5, img_up.getHeight(null)-10);
		Rectangle rec4 = new Rectangle(x+5, y, img_down.getWidth(null)-10, img_down.getHeight(null)-5);
		Rectangle rec5 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		if(rec1.intersects(rec5) || rec2.intersects(rec5) || rec3.intersects(rec5) || rec4.intersects(rec5)){
			return true;
		}
		return false;
	}
	
	public boolean isImpactBombBangvsBomb(Bomb bomb){
		Rectangle rec1 = new Rectangle(x+45-img_left.getWidth(null), y, img_left.getWidth(null), img_left.getHeight(null));
		Rectangle rec2 = new Rectangle(x, y, img_right.getWidth(null), img_right.getHeight(null));
		Rectangle rec3 = new Rectangle(x, y+45-img_up.getHeight(null), img_up.getWidth(null), img_up.getHeight(null));
		Rectangle rec4 = new Rectangle(x, y, img_down.getWidth(null), img_down.getHeight(null));
		Rectangle rec5 = new Rectangle(bomb.getX(), bomb.getY(), bomb.getWidth(), bomb.getHeight());
		if(rec1.intersects(rec5) || rec2.intersects(rec5) || rec3.intersects(rec5) || rec4.intersects(rec5)){
			return true;
		}
		return false;
	}
	
	public boolean isImpactBombBangvsBox(Box box){
		if(box.getType()==Box.DISALLROW_BANG){
			return false;
		}
		Rectangle rec1 = new Rectangle(x+45-img_left.getWidth(null), y, img_left.getWidth(null), img_left.getHeight(null));
		Rectangle rec2 = new Rectangle(x, y, img_right.getWidth(null), img_right.getHeight(null));
		Rectangle rec3 = new Rectangle(x, y+45-img_up.getHeight(null), img_up.getWidth(null), img_up.getHeight(null));
		Rectangle rec4 = new Rectangle(x, y, img_down.getWidth(null), img_down.getHeight(null));
		Rectangle rec5 = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
		if(rec1.intersects(rec5) || rec2.intersects(rec5) || rec3.intersects(rec5) || rec4.intersects(rec5)){
			return true;
		}
		return false;
	}
	
	public boolean isImpactBombBangvsItem(Item item){
		Rectangle rec1 = new Rectangle(x+45-img_left.getWidth(null), y, img_left.getWidth(null), img_left.getHeight(null));
		Rectangle rec2 = new Rectangle(x, y, img_right.getWidth(null), img_right.getHeight(null));
		Rectangle rec3 = new Rectangle(x, y+45-img_up.getHeight(null), img_up.getWidth(null), img_up.getHeight(null));
		Rectangle rec4 = new Rectangle(x, y, img_down.getWidth(null), img_down.getHeight(null));
		Rectangle rec5 = new Rectangle(item.getX(), item.getY(), item.getWidth(), item.getHeight());
		if(rec1.intersects(rec5) || rec2.intersects(rec5) || rec3.intersects(rec5) || rec4.intersects(rec5)){
			if(item.getTimeLine()>0){
				item.setTimeLine(item.getTimeLine()-1);
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	
	public void setImage(int orient, int size){
		switch (orient) {
		case Bomber.LEFT:
			if(size==2){
				img_left = new ImageIcon(getClass().getResource("/Images/bombbang_left_2.png")).getImage();
			}
			break;
		case Bomber.RIGHT:
			if(size==2){
				img_right = new ImageIcon(getClass().getResource("/Images/bombbang_right_2.png")).getImage();
			}
			break;
		case Bomber.UP:
			if(size==2){
				img_up = new ImageIcon(getClass().getResource("/Images/bombbang_up_2.png")).getImage();
			}
			break;
		case Bomber.DOWN:
			if(size==2){
				img_down = new ImageIcon(getClass().getResource("/Images/bombbang_down_2.png")).getImage();
			}
			break;

		default:
			break;
		}
	}
	
	public void deadlineBomb(){
		if(timeLine>0){
			timeLine--;
		}
	}

	public int getTimeLine() {
		return timeLine;
	}
	
	
}
