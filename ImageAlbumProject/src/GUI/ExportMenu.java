/**
 * 
 */
package GUI;

import imageIO.FileAccessor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class ExportMenu implements ActionListener {
	GUI g;
	public ExportMenu(GUI g){
		this.g = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFileChooser chooser = new JFileChooser();
        // chooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int choice = chooser.showOpenDialog(g.getJf());

        if (choice == JFileChooser.APPROVE_OPTION) {
        
        	System.out.println("ticket 4061 : Specify data to fileAccessor at line 38 in ExportMenu.java");
        }
	}
	

}
