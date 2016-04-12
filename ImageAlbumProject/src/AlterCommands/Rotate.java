package AlterCommands;

import imageIO.Picture;

import java.awt.image.BufferedImage;

import marvin.image.MarvinImage;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

/**
 * Rotate.java - A class that resizes images for the GUI
 */
public class Rotate implements AlterCommand{
	private BufferedImage obj;
    MarvinImagePlugin inmagePlugin;

	/**
	 * execute - Method that executes the change on the image for the GUI
	 * @param p - Picture object do to the change on
	 * @param args - Object array that contains arguments for change (rotate direction: clockwise90 or anticlockwise90)
	 */
    public void execute(Picture p, Object[] args){
		obj = p.getImage();
    	MarvinImage	image = new MarvinImage(p.getImage());
	    MarvinImagePlugin imagePlugin;
        imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.transform.rotate.jar");
        if(args[0].equals("clockwise90")){
            System.out.println("Do i get into the rotate ?");
            imagePlugin.setAttribute("rotate", "clockwise90");
            imagePlugin.process(image.clone(), image);
        }else if(args[0].equals("anticlockwise90")){
            imagePlugin.setAttribute("rotate", "anticlockwise90");
            imagePlugin.process(image.clone(), image);
        }
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