
package AlterCommands;

import imageIO.Picture;

import java.awt.image.BufferedImage;

import marvin.image.MarvinImage;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;


/**
 * Crop.java - Class that handles the cropping of images for the GUI
 */
public class Crop implements AlterCommand  {
    MarvinImagePlugin imagePlugin;
    private BufferedImage obj;
    /**
     * execute - Method That executes the change on the image
     * @param p - A picture object to crop
     * @param args - Contains the string arguments for where to crop and edit image (x,y,width,height)
     */
    public void execute(Picture p, Object[] args){
    	obj = p.getImage();
    	MarvinImage	image = new MarvinImage(p.getImage());
    	imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.segmentation.crop.jar");
        imagePlugin.setAttributes("x",args[0],"y",args[1],"width",args[2],"height",args[3]);
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
