//Package Info
package imageIO;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;


public class Album extends Observable implements Component
{
   private List<Picture> pictureList = new ArrayList<Picture>();
   private List<Album> parentAlbumList;
   private List<Album> childAlbumList = new ArrayList<Album>();
   private List<String> albumTagList;
   private String name;
   private String id;
   private List<String> parentAlbums = null;
   private List<String> childAlbums = null;
   private List<String> pictures = null;
   public Album(String name)
   {
      this.name = name;
   }
   
   public Album(String id, String name, List<String> tags, List<String> parentAlbums, List<String> childAlbums, List<String> pictures){
	   this.id = id;
	   this.name = name;
	   this.albumTagList = tags;
	   this.parentAlbums = parentAlbums;
	   this.childAlbums = childAlbums;
	   this.pictures = pictures;
   }
   


/**
 * @param pictureList
 * @param parentAlbumList
 * @param childAlbumList
 * @param albumTagList
 * @param name
 */
public Album( List<Album> childAlbums, String name) {
	this.childAlbumList = childAlbums;
	this.name = name;
}

public void buildPictureList(HashMap<String, Picture> hm){
	   for(String pict : pictures){
		   addPicture(hm.get(pict));
	   }
   }
   public void buildParentAlbumList(HashMap<String, Album> hm ){
	   for(String parent : parentAlbums){
		   addParentAlbum(hm.get(parent));
	   }
   }
   public void buildChildAlbumList(HashMap<String, Album> hm){
	   for(String child : childAlbums){
		   addChildAlbum(hm.get(child));
	   }
   }
   public void addAlbum(Album a){
	   childAlbumList.add(a);
   }
   /**
    * Add a picture to the album
    * @param p
    */
   public void addPicture( Picture p)
   {
      pictureList.add(p);
   }

   /**
    * Return the pictures in the album
    * @return
    */
   public List<Picture> getPictureList()
   {
      return pictureList;
   }
   
   /**
    * removes a picture from the album. Does not delete the picture.
	 * @param p
	 */
	public void removePictue(Picture p)
   {
	   pictureList.remove(p);
   }
   
   /**
    * Add a tag to the tag list
    * @param tag
    */
   public void addTag(String tag){
      albumTagList.add(tag);
   }

   /**
    * retrun the taglist
    * @return
    */
   public List<String> getTags(){
      return albumTagList;
   }
   
   /**
    * removes a tag from the album. Does not delete the tag.
	 * @param p
	 */
	public void removeTag(String tag)
   {
	   albumTagList.remove(tag);
   }
   /**
    * Add a child Album to the album list
    * @param a
    */
   public void addChildAlbum(Album a){
      childAlbumList.add(a);
   }

   /**
    * Return the child album list
    * @return
    */
   public List<Album> getChildAlbumList(){
      return childAlbumList;
   }
   /**
    * removes a picture from the album. Does not delete the picture.
	 * @param p
	 */
	public void removeChildAlbum(Album a)
   {
	   childAlbumList.remove(a);
   }
   /**
    * Add a parent Album to this album
    * @param a
    */
   public void addParentAlbum(Album a){
      try{
    	  parentAlbumList.add(a);
      }catch(NullPointerException npe){
    	  
      }
   }

   /**
    * get the list of parent albums
    * @return
    */
   public List<Album> getParentAlbumList(){
      return parentAlbumList;
   }
   public void removeParentAlbum(Album a){
	   parentAlbumList.remove(a);
   }
   /**
    * Set the name of the album
    * @param newName
    */
   public void setName(String newName)
   {
      name = newName;
   }

   /**
    * Get the name of the Album
    * @return
    */
   public String getName()
   {
      return name;
   }

	/**
	 * @return the albumTagList
	 */
	public List<String> getAlbumTagList() {
		return albumTagList;
	}
	
	/**
	 * @return the parentAlbums
	 */
	public List<String> getParentAlbums() {
		return parentAlbums;
	}
	
	/**
	 * @return the childAlbums
	 */
	public List<String> getChildAlbums() {
		return childAlbums;
	}
	
	/**
	 * @return the pictures
	 */
	public List<String> getPictures() {
		return pictures;
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
}