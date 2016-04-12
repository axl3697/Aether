/**
 * 
 */
package UndoRedo;

import imageIO.Picture;
import AlterCommands.AlterCommand;

/**
 * <p>Two Stacks passing AlterCommands between them.
 *  The AlterCommands each hold a piece of the objects state</p>
 *  <p>On call to undo() the old state is returned</p>
 *  <p>On call to undo() the old state is returned</p>
 */
public interface GenericUndoRedoStack {
	
	/**
	 * Called on any change to a Picture object
	 * @param ai the AlterCommand to add to the UndoStack
	 */
	void add(AlterCommand ai);
	
	/**
	 * <p>Any undo method for the Picture object to revert to state before last change.</p> 
	 * @param picture a Picture whose change needs to be undone
	 * @return the passed in Picture with it's last change undone
	 */
	Picture undoPicture(Picture picture);
	
	/**
	 * <p>Any redo method for the Picture object to revert to state before the last undo change.</p>
	 * @param picture a Picture whose change needs to be redone
	 * @return the passed in Picture with its last undo redone
	 */
	Picture redoPicture(Picture picture);
}
