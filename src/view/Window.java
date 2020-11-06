/**
 * 
 */
package view;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Eliantus De MICHEL
 *
 */
public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextArea ecran1, ecran2;
	private JLabel time;
	private JComboBox<String> list1, list2;
	private JButton translate, play, record, read, send;
	
	public Window() {
		
		// Custom side left
		list1 = new JComboBox<String>();
		list1.addItem("English");
		list1.addItem("French");
		list1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		ecran1 = new JTextArea();
		ecran1.setEditable(false);
		ecran1.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		ecran1.setMargin(new Insets(5, 10, 10, 10));
		ecran1.setWrapStyleWord(true);
	    ecran1.setLineWrap(true);
	    
	    JScrollPane j1 = new JScrollPane(ecran1);
		j1.setPreferredSize(new Dimension(240,170));
				
		record = new JButton();
		record.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		play = new JButton();
		play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		time = new JLabel(" Time: 00:00:00 ");
		time.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		
		send = new JButton();
		send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
		
		p.add(record);
		p.add(time);
		p.add(play);
		p.add(send);
		
		JPanel pane1 = new JPanel();
		pane1.setLayout(new BorderLayout());
		pane1.add(list1, BorderLayout.NORTH);
		pane1.add(j1, BorderLayout.CENTER);
		pane1.add(p, BorderLayout.SOUTH);
		
		
		
		// Custom Center
		translate = new JButton();
		translate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JPanel pane2 = new JPanel();
		pane2.setLayout(new BoxLayout(pane2, BoxLayout.LINE_AXIS));
		pane2.add(translate);	
		
		
		// Custom side Right
		list2 = new JComboBox<String>();
		list2.addItem("French");
		list2.addItem("English");
		list2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		ecran2 = new JTextArea();
		ecran2.setEditable(false);
		ecran2.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		ecran2.setMargin(new Insets(5, 10, 10, 10));
		ecran2.setWrapStyleWord(true);
	    ecran2.setLineWrap(true);
		
	    JScrollPane j2 = new JScrollPane(ecran2);
		j2.setPreferredSize(new Dimension(240,170));
				
		read = new JButton();
		read.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		
		JPanel pane3 = new JPanel();
		pane3.setLayout(new BorderLayout());
		pane3.add(list2, BorderLayout.NORTH);
		pane3.add(j2, BorderLayout.CENTER);
		pane3.add(read, BorderLayout.SOUTH);
		
			
		
		//Main Pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(pane1, BorderLayout.WEST);
		pane.add(pane2,BorderLayout.CENTER);
		pane.add(pane3,BorderLayout.EAST);
		this.setContentPane(pane);
		
		
		//Custom Frame
		this.setMinimumSize(new Dimension(605,205));
		this.pack();
		this.setTitle("Speech to text translator");
		ImageIcon img = new ImageIcon(Main.class.getResource("/speech.png"));
		this.setIconImage(img.getImage());
		this.setLocationRelativeTo(null);
		
	}
	
	public JTextArea ecran1() {
		return this.ecran1;
	}
	
	public JTextArea ecran2() {
		return this.ecran2;
	}
	
	public JComboBox<String> list1() {
		return this.list1;
	}
	
	public JComboBox<String> list2() {
		return this.list2;
	}
	
	public JLabel time() {
		return this.time;
	}
	
	public JButton translate() {
		return this.translate;
	}
	
	public JButton play() {
		return this.play;
	}
	
	public JButton record() {
		return this.record;
	}
	
	public JButton read() {
		return this.read;
	}
	
	public JButton send() {
		return this.send;
	}
	
	public void Torecord() {
		record.setToolTipText("Record a paragraph");
		
		try {
		    Image rec = ImageIO.read(getClass().getResource("/Record.gif"));
		    record.setIcon(new ImageIcon(rec));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	
	public void Toplay() {
		play.setToolTipText("Verify the paragraph");
		
		try {
		    Image img_p = ImageIO.read(getClass().getResource("/Play.gif"));
		    play.setIcon(new ImageIcon(img_p));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	
	public void Tostop(JButton b, String s, String text) {
		b.setText(text);
		b.setToolTipText(s);
		
		try {
		    Image rec = ImageIO.read(getClass().getResource("/Stop.gif"));
		    b.setIcon(new ImageIcon(rec));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	
	public void Tosend() {
		send.setToolTipText("Transform paragraph to text");
		
		try {
		    Image img1 = ImageIO.read(getClass().getResource("/next.png"));
		    send.setIcon(new ImageIcon(img1));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	
	public void Totranslate(String s) {
		translate.setToolTipText("Translate into "+s);
		translate.setText("Translate");
		try {
		    Image img = ImageIO.read(getClass().getResource("/translate.png"));
		    translate.setIcon(new ImageIcon(img));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	
	public void Toread() {
		read.setToolTipText("Read the translation");
		read.setText("Read");
		try {
		    Image img_p = ImageIO.read(getClass().getResource("/sound.png"));
		    read.setIcon(new ImageIcon(img_p));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	
	public void enButton(JButton b, boolean a1, boolean a2) {
		
		this.list1.setEnabled(a2);
		this.list2.setEnabled(a2);
		
		if(a1) {
			this.record().setEnabled(true);
			this.play().setEnabled(true);
			this.send().setEnabled(true);
			this.translate().setEnabled(true);
			this.read().setEnabled(true);
		}
		else {
			this.record().setEnabled(false);
			this.play().setEnabled(false);
			this.send().setEnabled(false);
			this.translate().setEnabled(false);
			this.read().setEnabled(false);
			b.setEnabled(true);
			
		}
	}
	
	
}
