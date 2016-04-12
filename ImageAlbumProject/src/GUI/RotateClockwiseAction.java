package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import AlterCommands.Rotate;
import UndoRedo.BasicUndoRedoStack;

public class RotateClockwiseAction implements ActionListener{
	GUI g;
	public RotateClockwiseAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Call clockwise rotate stuff here
		Picture p = g.currentEditingPicture();
		BasicUndoRedoStack stack = g.getUndoRedoStack();
		
		Rotate c = new Rotate();
		Object[] args = new Object[]{"clockwise90"};
		c.execute(p,args);
		p.notifyObservers();
		stack.add(c);
	}	
}