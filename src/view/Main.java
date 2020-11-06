/**
 * 
 */
package view;

import controler.*;

/**
 * @author Eliantus De MICHEL
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window w = new Window();
		Controler c = new Controler(w);
		c.getWin().setVisible(true);

	}

}
