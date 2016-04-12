package AlterCommands;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import imageIO.Album;
import imageIO.Component;
import imageIO.Picture;

/**
 * Search.java  - A class for handling searching of albums and pictures
 */
public class Search{
	/**
	 * doSearch - Method that searches all albums for the searchParams. Recursive.
	 * @param a - A variable of type Album that contains all of the Albums in the program
	 * @param searchParams - A variable of type String that is what you are searching for
	 * @return An ArrayList of Components that matched the searchParams
	 */
	public ArrayList<Component> doSearch(Album a , String searchParams){
		ArrayList<Component> cs = new ArrayList<Component>();
		List<Picture> pictureList = a.getPictureList();
        List<Album> albumList = a.getChildAlbumList();
        searchParams = searchParams.toLowerCase();
        
        //Check current working album's name
        if(a.getName().toLowerCase().contains(searchParams)){
        	//cool it fits our search terms, add to list
        	cs.add(a);
        }else{//album name doesn't match, check album tags for match
        	List<String> albumTags = a.getTags();
        	//Loop through tags. If a tag contains the search term, add to list
        	if(albumTags != null){
	        	for(String tag : albumTags){
	        		if(tag.toLowerCase().contains(searchParams)){
	        			cs.add(a);
	        			//We don't want to add the same album more than once
	        			break;
	        		}
	        	}
        	}
        }
        //Check all the sub albums recursively
        for (Album album : albumList) {
        	//Add the results of those searches to our list
            cs.addAll(doSearch(album,searchParams));
        }
        //Check all pictures in our album for matches
        if(pictureList !=null){
	        for (Picture p : pictureList) {
	        	//If their name matches our search terms, add to list
	            if(p.getName().toLowerCase().contains(searchParams)){
	            	cs.add(p);
	            }else{//Name doesn't match so search through tags
	            	List<String> tags = p.getTags();
	            	for(String tag : tags){
	            		if(tag.toLowerCase().contains(searchParams)){
	            			cs.add(p);
	            			//We don't want to add the same picture more than once.
	            			break;
	            		}
	            	}
	            }
	        }
        }
		return cs;	
	}
}