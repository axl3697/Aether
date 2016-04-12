package GUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import imageIO.FileAccessor;

import javax.swing.*;
public class OpenAction implements ActionListener{
	private GUI g;
	private String path;
	private File filePathgether;
	private JFrame jf;
	
	public OpenAction(GUI g){
		this.g = g;
		jf = new JFrame( "Open" );
		jf.setLayout(new BorderLayout( 1, 1 ) );
		jf.setDefaultCloseOperation( jf.EXIT_ON_CLOSE );
		jf.setExtendedState(jf.MAXIMIZED_BOTH);
		jf.setLocationRelativeTo( null );
		jf.setVisible( true );
	}
	@Override
	public void actionPerformed(ActionEvent e) {
      // Bring up file browser
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showOpenDialog(jf) == JFileChooser.APPROVE_OPTION){
	      //get the file and put it into a file place holder
	      filePathgether = fileChooser.getSelectedFile();
	      //get the file path
	      path = filePathgether.getPath();
	      //Create FileAccessor
	      FileAccessor fa = new FileAccessor();
	      // send the path to readInData
	      fa.readInData(path);
      }
      g.Back();
   }
}