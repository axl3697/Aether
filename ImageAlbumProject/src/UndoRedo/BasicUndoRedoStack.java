package UndoRedo;

import java.util.EmptyStackException;
import java.util.Stack;

import AlterCommands.AlterCommand;
import imageIO.Picture;

public class BasicUndoRedoStack implements GenericUndoRedoStack {
	
	// to hold the list of changes
	Stack<AlterCommand> undoList;
	Stack<AlterCommand> redoList;
	
	/**
	 * Initializes the stacks for changes 
	 */
	public BasicUndoRedoStack() {
		undoList = new Stack<AlterCommand>();
		redoList = new Stack<AlterCommand>();
	}

	/* (non-Javadoc)
	 * @see UndoRedo.GenericUndoRedoStack#undoPicture(imageIO.Picture)
	 */
	public Picture undoPicture(Picture picture){
		try{
			AlterCommand mp = undoList.pop();
			redoList.push(mp);
			return mp.undo(picture);
			
		}catch(EmptyStackException ese){
//			should pass warning to the gui			
		}catch(Exception e){
//			null object pattern	should go here
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see UndoRedo.GenericUndoRedoStack#redoPicture(imageIO.Picture)
	 */
	public Picture redoPicture(Picture picture) {
		try{
			AlterCommand mp = redoList.pop();
			undoList.push(mp);
			return mp.undo(picture);
			
		}catch(EmptyStackException ese){
//			should pass warning to the gui
		}catch(Exception e){
//		null object pattern	should go here
		}
		return null; // returns null if there is nothing to change or something unexpected happened
	}

	/* (non-Javadoc)
	 * @see UndoRedo.GenericUndoRedoStack#add(AlterCommands.AlterCommand)
	 */
	public void add(AlterCommand ai) {
		undoList.add(ai);
	}

}
