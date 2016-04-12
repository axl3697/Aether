package GUI;

import imageIO.Album;
import imageIO.Component;
import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class DeleteAction implements ActionListener{
	private Object selectedItem;
	private TreePath selectedPath;
	private GUI g;
	public DeleteAction(GUI g, TreePath selectedPath, Object selectedItem){
		this.selectedItem = selectedItem;
		this.selectedPath = selectedPath;
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int dialogType = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you wanted to delete this?","Warning",dialogType);
        
        if(dialogResult == JOptionPane.YES_OPTION){
			Class albm = Album.class;
			Class pic = Picture.class;
			TreePath parentPath = selectedPath.getParentPath();
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)parentPath.getLastPathComponent();
        	Album parentAlbum = (Album)node.getUserObject();
			if(albm.isInstance(selectedItem)){
				List<Album> childAlbums = parentAlbum.getChildAlbumList();
				childAlbums.remove((Album)selectedItem);
			}else if(pic.isInstance(selectedItem)){
				List<Picture> albumPictures = parentAlbum.getPictureList();
				albumPictures.remove((Picture)selectedItem);
			}
			g.destroyPanel(g.getTreePane());
			g.createTree();
			//Recreated tree;
        }
	}
	
}