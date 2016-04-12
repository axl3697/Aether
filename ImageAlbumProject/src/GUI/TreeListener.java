package GUI;

import imageIO.Album;
import imageIO.Picture;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeListener implements TreeSelectionListener{
	private JTree tree;
	private GUI g;
	public TreeListener(GUI g, JTree tree){
		this.tree = tree;
		this.g = g;
	}
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		//This returns the tree node item that was last selected
	    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		if (node == null){
			//Nothing is selected.     
			return;
		}else{
			Object nodeInfo = node.getUserObject();
			if (node.isLeaf()) {
				//Then Item is either a picture or an empty album. (Basically the item has no children)	
				Class albm = Album.class;
				Class pic = Picture.class;
				if(albm.isInstance(nodeInfo)){
					//Item is an album if nothing is in it
					//System.out.println("Do i get here?");

					//Do something for an album
				}else if(pic.isInstance(nodeInfo)){
					//Item is a picture
					//Bring up editing panel for picture.
					g.editPanels((Picture)nodeInfo);
				}	
		    } else {
		        //Item has children. Item is an album that has pictures and or albums in it or it is the top top level album that contains all
		    	Class albm = Album.class;
		    	//Makes sure it is an album
		    	if(albm.isInstance(nodeInfo)){
		    		//Album is not empty
					Album currentAlbum = (Album)nodeInfo;
					g.buildGallery(currentAlbum);
		    	}
		    }
		}
	}
}