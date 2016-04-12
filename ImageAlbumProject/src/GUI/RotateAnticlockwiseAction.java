package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import AlterCommands.Rotate;
import UndoRedo.BasicUndoRedoStack;

public class RotateAnticlockwiseAction implements ActionListener{
	GUI g;
	public RotateAnticlockwiseAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Call anticlockwise rotate stuff here
		Picture p = g.currentEditingPicture();
		BasicUndoRedoStack stack = g.getUndoRedoStack();
		
		Rotate c = new Rotate();
		Object[] args = new Object[]{"anticlockwise90"};
		c.execute(p,args);
		p.notifyObservers();
		stack.add(c);
	}	
}