
import java.util.List;
import imageIO.*;
import GUI.*;
import UndoRedo.BasicUndoRedoStack;

/**
 * StartUpController.java - A class that sets up the file IO and the GUI
 */
public class StartUpController {
	/**
	 * @param args - Arguments sent into program
	 */
	public static void main(String[] args) {
		//Sets up stack
		BasicUndoRedoStack stack = new BasicUndoRedoStack();
		FileAccessor newFileAccessor = new FileAccessor();
		newFileAccessor.readInData("demo.json");
		
		List<Album> albums = newFileAccessor.getAlbumGroup();
		//Creates master album
		Album a = new Album( albums, "Visual Media" );
		GUI startGui = new GUI(stack,a);
	}

}
