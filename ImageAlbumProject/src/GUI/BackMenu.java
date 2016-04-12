/**
 * 
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackMenu implements ActionListener {
	GUI g;
	public BackMenu(GUI g){
		this.g = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		g.Back();
	}

}
