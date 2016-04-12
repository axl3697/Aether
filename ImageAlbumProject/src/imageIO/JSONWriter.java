package imageIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * A writer class that formats metadata about Component objects into JSON.
 */
public class JSONWriter {
	private FileWriter fe; // writer of where the data should be written to
	
	/**
	 * Constructor to create a FileWriter from a file object
	 * @param fe a JSON file to be  written to
	 * @throws IOException if reading the file fails
	 */
	public JSONWriter(File fe) throws IOException {
        this.fe = new FileWriter(fe);
	}
	
	/**
	 * Writes out the data of Album and Pictures passed into it to the specified JSON file marked in the constructor.
	 * @param alblist Album objects data is extracted from
	 * @param piclist Picture objects data is exacted from
	 * @throws JsonProcessingException If an exception occurred created the JSON objects
	 * @throws IOException If an output exception occurred
	 */
	public void write(Collection<Album> alblist, Collection<Picture> piclist)throws JsonProcessingException, IOException{
		// instantiations for objects responsible for creating JSON objects
		ObjectMapper mapper = new ObjectMapper(); // maps the program's object to the file system object
        JsonNodeFactory factory = new JsonNodeFactory(false); // creates nodes in the JSON Object

//        rework this may be able to be condensed
        JsonFactory jsonFactory = new JsonFactory(); // creates the generator for where and how to write the data
        JsonGenerator generator = jsonFactory.createGenerator(fe); // could write out to different places
        
        // node declaration for the overall object
        // takes the form of
        //  {
        //    	albums : {"albumid" : ""}
        //    	pictures : {}
        //  }
        ObjectNode wholeTing = factory.objectNode();
        
        // adds nodes to the album array each of which represents an album from the list passed in
        wholeTing.set("albums",  albumJson(alblist)); 
        
        // adds nodes to the pictures array each of which represents a picture from the list passed in
        wholeTing.set("pictures", pictureJson(piclist));
        
        // writes everything into the file
        mapper.writeTree(generator, wholeTing);
        
         // cleans and closes the file writer
         fe.flush();
         fe.close();
	}
	
	/**
	 * Translates a group of Album objects data into JSON Objects stored in a List object
	 * @param group the group of Albums to be translated
	 * @return list of JSON Objects each representing an Album object
	 */
	public ObjectNode albumJson(Collection<Album> group){
//		why is this factory here?
		// factory for creating JSON nodes 
		JsonNodeFactory factory = new JsonNodeFactory(false);
		// overall node for albums
		ObjectNode albumsNode = factory.objectNode();
        for(Album alb :  group){
        	
        	// factory creation, have the ObjectNode recreated each time to prevent data from being overwritten
        	ObjectNode album = factory.objectNode();
        	
        	// arrays added to the overall ObjectNode album
        	ArrayNode tags = factory.arrayNode();
			ArrayNode parent = factory.arrayNode();
			ArrayNode child = factory.arrayNode();
			ArrayNode pictures = factory.arrayNode();
	        
			// adds information to the Album node Objects
			album.put("id", alb.getId());
			album.put("name", alb.getName());
			album.putArray("tagList").addAll(listToJson(tags, alb.getAlbumTagList()));
			album.putArray("parentAlbumList").addAll(listToJson(parent, alb.getParentAlbums()));
			album.putArray("childAlubmList").addAll(listToJson(child, alb.getChildAlbums()));
			album.putArray("picture").addAll(listToJson(pictures, alb.getPictures()));
			
			// adds the populated object node to the return list
			albumsNode.set(alb.getId(),album);
		}
        return albumsNode;
	}
	
	/**
	 * Translates a group of Album objects data into JSON Objects stored in a List object.
	 * @param group the group of Albums to be translated
	 * @return list of JSON Objects each representing an Album object
	 */
	public ObjectNode pictureJson(Collection<Picture> piclist){

		// factory for creating JSON nodes 
		JsonNodeFactory factory = new JsonNodeFactory(false);
		// overall node for pictures
		ObjectNode picturesNode = factory.objectNode();
        
        for(Picture pic :  piclist){
        	// factory creation, have the ObjectNode recreated each time to prevent data from being overwritten
        	ObjectNode picture = factory.objectNode();

        	// arrays added to the overall object Picture node
        	ArrayNode tags = factory.arrayNode();
			ArrayNode albums = factory.arrayNode();

			// adds information to the Picture node Objects
			picture.put("id", pic.getId());
			picture.put("name", pic.getName());
			picture.put("fpToImage", pic.getFilepath());
			picture.putArray("tagList").addAll(listToJson(tags, pic.getTags()));
			picture.putArray("albumList").addAll(listToJson(albums, pic.getParentAlbums()));
			
			// adds the populated object node to containing object node
			picturesNode.set("id",picture);
		}
        return picturesNode;
	}
	
	/**
	 * Utility method for populating ArrayNodes with strings.
	 * @param arrNode ArrayNode to be populated
	 * @param objList list of strings to be added to the ArrayNode
	 * @return the populated arrayNode
	 */
	public ArrayNode listToJson(ArrayNode arrNode, Iterable<String> objList){ // check if can allow multiple types of iterable
		Iterator<String> iter = objList.iterator();
		while(iter.hasNext()){
			arrNode.add(iter.next());
		}
		return arrNode;
	}
	
	/**
	 * Utility method for populating an ArrayNode with ObjectNodes. 
	 * @param arrNode ArrayNode to be populated
	 * @param list list of ObjectNodes to be added to the ArrayNode
	 * @return the populated ArrayNode
	 */
	public ArrayNode nodeListToJson(ArrayNode arrNode, Iterable<ObjectNode> list){
		Iterator<ObjectNode> iter = list.iterator();
		while(iter.hasNext()){
			arrNode.add(iter.next());
		}
		return arrNode;
	}
}