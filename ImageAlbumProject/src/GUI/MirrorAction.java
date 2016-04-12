package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import AlterCommands.Mirror;
import UndoRedo.BasicUndoRedoStack;

public class MirrorAction implements ActionListener{
	GUI g;
	public MirrorAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Call mirror stuff here
		Picture p = g.currentEditingPicture();
		BasicUndoRedoStack stack = g.getUndoRedoStack();
		
		Mirror c = new Mirror();
		c.execute(p, new Object[0]);
		stack.add(c);
		p.notifyObservers();
	}	
}