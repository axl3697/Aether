package AlterCommands;
import imageIO.Picture;

import java.awt.image.BufferedImage;

import marvin.image.MarvinImage;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

/**
 * Resize.java - A class that resizes images for the GUI
 */
public class Resize implements AlterCommand{
	private BufferedImage obj;

	/**
	 * execute - Method that executes the change on the image for the GUI
	 * @param p - Picture object do to the change on
	 * @param args - Object array that contains arguments for change (newWidth,newHeight)
	 */
	public void execute(Picture p,Object[] args) {
		obj = p.getImage();
		MarvinImage	image = new MarvinImage(p.getImage());
	    MarvinImagePlugin imagePlugin;
	    
        imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.transform.scale.jar");
        imagePlugin.setAttributes("newWidth",args[0],"newHeight",args[1]);
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
