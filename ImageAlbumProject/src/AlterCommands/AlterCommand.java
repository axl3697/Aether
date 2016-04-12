/**
 * 
 */
package AlterCommands;

import imageIO.Picture;
/**
 * AlterCommand - An Interface that creates the layout for the editing commands
 *
 */
public interface AlterCommand {
	public void execute(Picture p, Object[] args);
	public Picture undo(Picture picture);
}
