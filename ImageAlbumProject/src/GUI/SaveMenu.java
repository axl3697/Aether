package GUI;

import imageIO.FileAccessor;
import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveMenu implements ActionListener{
	GUI g;
	public SaveMenu(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Write out changes to image
		FileAccessor fa = new FileAccessor();
		Picture p = g.currentEditingPicture();
		fa.setBufferedImage(p.getFilepath(), p.getImage());
//		g.Back();
		//fa.writeOutData();
	}	
}