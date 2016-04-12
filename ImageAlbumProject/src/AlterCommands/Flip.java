package AlterCommands;

import imageIO.Picture;

import java.awt.image.BufferedImage;

import marvin.image.MarvinImage;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

/**
 * Flip.java - Class that Flips images for the GUI
 */
public class Flip implements AlterCommand{
	private BufferedImage obj;
	
	/**
	 * execute - Method that executes the change on the image for the GUI
	 * @param p - Picture object do to the change on
	 * @param args - Object array that contains arguments for change
	 */
	public void execute(Picture p, Object[] args) {
		// TODO Auto-generated method stub
		obj = p.getImage();
		MarvinImage	image = new MarvinImage(p.getImage());
	    MarvinImagePlugin imagePlugin;
	    
        imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.transform.flip.jar");
        imagePlugin.setAttribute("flip", "vertical");
        imagePlugin.process(image.clone(), image); 
        image.update();
        p.setImage(image.getBufferedImage());
	}
    /**
     * undo - Method to undo change
     * @param p - Picture objec to undo
     * @return A Picture object that has been changed
     */
	public Picture undo(Picture p){
		p.setImage(obj);
		return p;
	}
}
