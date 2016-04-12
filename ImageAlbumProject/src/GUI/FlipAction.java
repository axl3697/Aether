package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import AlterCommands.Flip;
import UndoRedo.BasicUndoRedoStack;

public class FlipAction implements ActionListener{
	GUI g;
	public FlipAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Call Flip stuff here
		Picture p = g.currentEditingPicture();
		BasicUndoRedoStack stack = g.getUndoRedoStack();
		
		Flip c = new Flip();
		c.execute(p ,new Object[0]);
		stack.add(c);
		p.notifyObservers();
	}	
}