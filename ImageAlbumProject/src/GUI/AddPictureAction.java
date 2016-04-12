package GUI;

import imageIO.Album;
import imageIO.Picture;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddPictureAction implements ActionListener{
	private GUI g;
	private Album a;
	public AddPictureAction(GUI g,Album a){
		this.g = g;
		this.a = a;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name = "";
		
		while(name.equals("")){
			name = JOptionPane.showInputDialog(null,"Enter a Picture Name:");
		}
		Picture p;
		try{
		    JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "gif","png");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	String path =chooser.getSelectedFile().getAbsolutePath();
		    	List<String>parentAlbums = new ArrayList<String>();
		    	parentAlbums.add(a.getName());
		    	BufferedImage image = ImageIO.read(new File(path));
		        p = new Picture(image,name);
		        p.setParentAlbums(parentAlbums);
		    
				a.addPicture(p);
				g.destroyPanel(g.getTreePane());
				g.createTree();
		    }
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}