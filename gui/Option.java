package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Option extends JPanel implements ActionListener{
	private MyContainer mContainer;
	private JLabel lbbackground;
	private ImageIcon backgroundIcon;
	private JButton btn_ok;
	
	public Option(MyContainer mContainer) {
		this.mContainer = mContainer;
		setBackground(Color.YELLOW);
		setLayout(null);
		initCompts();
	}
	
	public void initCompts(){
		lbbackground = new JLabel();
		lbbackground.setBounds(95, -40, GUI.WIDTHJF, GUI.HEIGHTJF);
		lbbackground.setBackground(Color.BLACK);
		backgroundIcon = new ImageIcon(getClass().getResource("/Images/background_option.png"));
		lbbackground.setIcon(backgroundIcon);
		add(lbbackground);
		
		btn_ok = new JButton();
		btn_ok.setText("OK");
		btn_ok.setBounds(400, 520, 100, 50);
		btn_ok.addActionListener(this);
		add(btn_ok);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn_ok){
			mContainer.setShowMenu();
		}	
	}
	
}
