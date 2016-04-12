package GUI;

import imageIO.Album;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class AddAlbumAction implements ActionListener{
	private GUI g;
	private Album a;
	public AddAlbumAction(GUI g,Album a){
		this.g = g;
		this.a = a;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name = "";
		
		while(name.equals("")){
			name = JOptionPane.showInputDialog(null,"Enter an Album Name:");
		}
		a.addAlbum(new Album(name));
		g.destroyPanel(g.getTreePane());
		g.createTree();
	}
	
}