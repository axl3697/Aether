//Package Def.
package imageIO;

//Imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;


/**
 * FileAccessor - Invoked by GUI and StartUpController
 *
 */
public class FileAccessor implements DataAccess
{
	private List<Album> albumGroup = new Vector<Album>();
	private List<Picture> pictureGroup;
   public FileAccessor(){
//	   divideJSONReaders(fileName);
   }
   		/**
 * @param albumGroup
 * @param pictureGroup
 */
public FileAccessor(List<Album> albumGroup, List<Picture> pictureGroup) {
	this.albumGroup = albumGroup;
	this.pictureGroup = pictureGroup;
}
		public void writeOutData(String fileName){
   			
   			try {
   				File f = new File(fileName);
				JSONWriter jswt = new JSONWriter(f);
				jswt.write(albumGroup, pictureGroup);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		}
   // Method to read in JSON File : Returns JSON object 
	   public void readInData(String fileName){
		   JSONReader jsrd = new JSONReader();
		   marryObjects(jsrd.createAlbums(fileName), jsrd.createPictures(fileName));
	   }

	   private void marryObjects(Map<String, Album> albumMap, Map<String, Picture> pictureMap){
			for (Album activeAlbum : albumMap.values()) {
				for(String str : activeAlbum.getParentAlbums()){
					activeAlbum.addParentAlbum(albumMap.get(str));	
				}
				for(String str : activeAlbum.getChildAlbums()){
					activeAlbum.addChildAlbum(albumMap.get(str));	
				}
				for(String str : activeAlbum.getPictures()){
					activeAlbum.addPicture(pictureMap.get(str));
				}
				albumGroup.add(activeAlbum);
			}
			// minor inefficiency 
			pictureGroup = new Vector<Picture>(pictureMap.values());
	   }
	   

   /**
    * Method to make Buffered image from path from method above : Returns BufferedImage
    * @param path - String
    * @return BufferedImage
    */
   public BufferedImage getBufferedImage(String path)
   {
      File file = new File(path);
      try
      {
         BufferedImage image = ImageIO.read(file);
         return image;
      }
      catch (FileNotFoundException e){
         System.out.println("File not found - - - - ");
         e.printStackTrace();
      }
      catch(IOException e)
      {
         System.out.println("IO Exception- - - - ");
         e.printStackTrace();
      }
	return null;

   }
   public void setBufferedImage(String path, BufferedImage bi){
	   File file = new File(path);
	      try
	      {
	    	  String sectionedPath = path.split("/")[path.split("/").length-1].split("\\.")[1];
	    	  
	          ImageIO.write(bi, sectionedPath, file);
	      }
	      catch(ArrayIndexOutOfBoundsException e){
	    	  System.out.println(path.split("/")[path.split("/").length-1].split("\\.")[1]);
	      }
	      catch (FileNotFoundException e){
	         System.out.println("File not found - - - - ");
	         e.printStackTrace();
	      }
	      catch(IOException e)
	      {
	         System.out.println("IO Exception- - - - ");
	         e.printStackTrace();
	      }

   }
/**
 * @return the albumGroup
 */
public List<Album> getAlbumGroup() {
	return albumGroup;
}
/**
 * @return the pictureGroup
 */
public List<Picture> getPictureGroup() {
	return pictureGroup;
}
/**
 * @param albumGroup the albumGroup to set
 */
public void setAlbumGroup(List<Album> albumGroup) {
	this.albumGroup = albumGroup;
}
/**
 * @param pictureGroup the pictureGroup to set
 */
public void setPictureGroup(List<Picture> pictureGroup) {
	this.pictureGroup = pictureGroup;
}
public List<Album> buildAlbumCollection(Album a){
	ArrayList<Album> cs = new ArrayList<Album>();
    List<Album> albumList = a.getChildAlbumList();

    //Check all the sub albums recursively
    for (Album album : albumList) {
        cs.addAll(buildAlbumCollection(album));
    }
    return cs;
}
public List<Picture> buildPictureCollection(Album a){
	ArrayList<Picture> cs = new ArrayList<Picture>();
    List<Album> albumList = a.getChildAlbumList();
    List<Picture> pictureList = a.getPictureList();

    for(Picture p : pictureList){
    	cs.add(p);
    }
    //Check all the sub albums recursively
    for (Album album : albumList) {
        cs.addAll(buildPictureCollection(album));
    }
    return cs;
}


}