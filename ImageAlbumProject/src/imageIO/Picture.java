//Package Info
package imageIO;

//Imports
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * Picture - Stores information about a picture
 * Picture is also observable
 */
public class Picture extends Observable implements Component, Cloneable
{
	private String filepath;
	private List<String> parentAlbums;
    private BufferedImage image;
    private String name;
    private String id;
    private List<String> tagList;
   
    public Picture(BufferedImage image , String name)
    {
       this.image = image;
       this.name = name;


    }

   // used by the reader
	public Picture(String id,String fp, String name, List<String> tagList, List<String> albumList) {
		// TODO Auto-generated constructor stub
    	this.id = id;
		this.filepath = fp;
//		this.image =  needs to set the picture image using the image from the filepath
		this.name = name;
		this.tagList = tagList;
		this.parentAlbums = albumList;
	}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);//source.getType() 
       Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	public Picture clone(){
		Picture p= new Picture(copyImage(image),new String(name));
		
		p.setFilepath(new String(filepath));
		p.setTagList(new ArrayList<String>(tagList));
		p.setParentAlbums(new ArrayList<String>(parentAlbums));
		return p;
	}
   public void loadBufferedImage(){

      DataAccess newFileAccessor = new FileAccessor();
      image = newFileAccessor.getBufferedImage(filepath);
   }
   public void saveBufferedImage(){
	   DataAccess newFileAccessor = new FileAccessor();
	   newFileAccessor.setBufferedImage(filepath, image);
   }

	/**
    * Set the Buffered image of the picture
    * @param image
    */
   public void setImage(BufferedImage image)
   {
      this.image = image;
      this.setChanged();
      this.notifyObservers();
   }

   /**
    * return the Buffered image of the picture
    * @return
    */
   public BufferedImage getImage()
   {
      return image;
   }
   /**
    * Add a tag to the picture
    * @param tag
    */
   public void addTag(String tag)
   {
      tagList.add(tag);
   }

   /**
    * get the list of tags
    * @return
    */
   public List<String> getTags()
   {
      return tagList;
   }
   
   public void removeTag(String tag)
   {
	   tagList.remove(tag);
   }
   /**
    * Set the name of the picture
    * @param newName
    */
   public void setName(String newName)
   {
      name = newName;
   }

   /**
    * get the name of the picture
    * @return
    */
   public String getName()
   {
      return name;
   }

/**
 * @return the filepath
 */
public String getFilepath() {
	return filepath;
}

/**
 * @param filepath the filepath to set
 */
public void setFilepath(String filepath) {
	this.filepath = filepath;
}

/**
 * @return the parentAlbums
 */
public List<String> getParentAlbums() {
	return parentAlbums;
}

/**
 * @param parentAlbums the parentAlbums to set
 */
public void setParentAlbums(List<String> parentAlbums) {
	this.parentAlbums = parentAlbums;
}

/**
 * @return the tagList
 */
public List<String> getTagList() {
	return tagList;
}

/**
 * @param tagList the tagList to set
 */
public void setTagList(List<String> tagList) {
	this.tagList = tagList;
}
public String toString(){
	return name;
}

/**
 * @return the id
 */
public String getId() {
	return id;
}

/**
 * @param id the id to set
 */
public void setId(String id) {
	this.id = id;
}

   public String printableTagList() {
      String tags = "";
      for (String tag : tagList) {
         tag += ", ";
         tags += tag;
      }
      return tags;
   }
   
}