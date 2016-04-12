package GUI;
import imageIO.Album;
import imageIO.Component;
import imageIO.Picture;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class PopupAction extends MouseAdapter {
	private JPopupMenu popup;
	private GUI g;
	public PopupAction(GUI g,JPopupMenu popup){
		this.popup = popup;
		this.g = g;
	}
    public void mousePressed(MouseEvent e) {
    	if(SwingUtilities.isRightMouseButton(e)){	
    		maybeShowPopup(e);
    	}
    }

    public void mouseReleased(MouseEvent e) {
    	if(SwingUtilities.isRightMouseButton(e)){
    		maybeShowPopup(e);
    	}
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
        	JTree tree = g.getTree();
        	//get current selected object via right click
        	TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)selPath.getLastPathComponent();
        	Object obj = node.getUserObject();
			Class albm = Album.class;
			Class pic = Picture.class;
			popup.removeAll();
			if(albm.isInstance(obj)){
	        	//add menus to popup
				JMenuItem jmi1 = new JMenuItem("Add Album");
				jmi1.addActionListener(new AddAlbumAction(g,(Album)obj));
				JMenuItem jmi5 = new JMenuItem("Add Picture");
				jmi5.addActionListener(new AddPictureAction(g,(Album)obj));
	            JMenuItem jmi2 = new JMenuItem("View Details");
	            JMenuItem jmi3 = new JMenuItem("Add Tag");
	            jmi3.addActionListener(new AddTagAction((Component)obj));
	            JMenuItem jmi4 = new JMenuItem("Delete");
	            jmi4.addActionListener(new DeleteAction(g,selPath,(Component)obj));
	            popup.add(jmi1);
	            popup.add(jmi2);
	            popup.add(jmi3);
	            popup.add(jmi4);
	            popup.add(jmi5);
			}else if(pic.isInstance(obj)){
	        	//add menus to popup
				JMenuItem jmi1 = new JMenuItem("Edit");
	            JMenuItem jmi2 = new JMenuItem("View Details");
	            JMenuItem jmi3 = new JMenuItem("Add Tag");
	            jmi3.addActionListener(new AddTagAction((Component)obj));
	            JMenuItem jmi4 = new JMenuItem("Delete");
	            jmi4.addActionListener(new DeleteAction(g,selPath,(Component)obj));
	            popup.add(jmi1);
	            popup.add(jmi2);
	            popup.add(jmi3);
	            popup.add(jmi4);
			}	

        	//Add actionlisteners to menu items
            popup.show(e.getComponent(),e.getX(), e.getY());
        }
    }
}