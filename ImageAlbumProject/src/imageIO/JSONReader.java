/**
 * 
 */
package imageIO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONReader {
	private ObjectMapper mapper;
	public JSONReader(){
		mapper = new ObjectMapper();
	}
	
	public Map<String, Album> createAlbums(String fileName){
		JsonNode root;
		Map<String, Album> albumGroup = new HashMap<String, Album>();
		
		try {
			root = mapper.readTree(new File(fileName)); // creates a node from the JSON file
		
			Iterator<Entry<String, JsonNode>> group = root.get("albums").fields(); // gets all the elements in the array keyed to "albums"
			while(group.hasNext()){ // iterates through the JSON's array of all albums
				Entry<String, JsonNode> e = group.next();
				Album temp = buildAlbum(e.getValue()); 
				albumGroup.put(temp.getId(), temp); // adds album, keyed to its name to a map which it will return
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return albumGroup;

	}
//	public void buildBlankJSON(){
//		try{
//			List<Album> emptyList = new Vector<Album>();
//			List<Picture> emptyPictureList = new Vector<Picture>();
//
//			JSONWriter newWriter = new JSONWriter(new File("src/demo.json"));
//			newWriter.write(emptyList,emptyPictureList);
//
//
//		}catch (IOException e){
//			e.printStackTrace();
//		}
//	}

	public Album buildAlbum(JsonNode element){
		List<String> tagList = new Vector<String>();
		List<String> parentAlbumList = new Vector<String>();
		List<String> childAlbumList = new Vector<String>();
		List<String> pictureList = new Vector<String>();
		String name = element.get("name").asText(); // gets values from the JSON and then uses this to create an album Object
		String id = element.get("id").asText();
		
			tagList.addAll(loop((element.findValue("tagList").elements())));
			parentAlbumList.addAll(loop((element.findValue("parentAlbumList")).elements()));
			System.out.println(loop((element.findValue("parentAlbumList")).elements()));
			childAlbumList.addAll(loop((element.findValue("childAlbumList")).elements()));
			pictureList.addAll(loop((element.findValue("pictureList")).elements())); // filepaths to these pictues
	
		return new Album(id, name, tagList, parentAlbumList, childAlbumList, pictureList); 
	}
	public List<String> loop(Iterator<JsonNode> jn){
		List<String> list = new Vector<String>();
		while(jn.hasNext()){
			list.add((jn.next()).asText());
		}
		return list;
	}
	
	public Map<String, Picture> createPictures(String fileName){
		JsonNode root;
		Map<String, Picture> pictureGroup = new HashMap<String, Picture>();
		
		try {
			root = mapper.readTree(new File(fileName)); // creates a node from the JSON file
		
			Iterator<JsonNode> group = root.get("pictures").elements(); // gets all the elements in the array keyed to "pictures"
			while(group.hasNext()){
				Picture temp = buildPicture(group.next());
				pictureGroup.put(temp.getId(), temp);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pictureGroup;

	}
	/**
	 * @param element a node containing the information for a picture object.
	 * @return stored picture object
	 */
	public Picture buildPicture(JsonNode element){
		// read in values
		String fp = element.get("fpToImage").asText();
		String name = element.get("name").asText();
		String id = element.get("id").asText();
		List<String> tagList = (loop((element.findValue("tagList")).elements()));
		List<String> albumList = (loop((element.findValue("albumList")).elements()));
		return new Picture( id, fp, name, tagList, albumList); 
	}
}