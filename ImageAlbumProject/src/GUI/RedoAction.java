package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UndoRedo.BasicUndoRedoStack;

public class RedoAction implements ActionListener{
	GUI g;
	public RedoAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//redo changes to image
		BasicUndoRedoStack stack = g.getUndoRedoStack();
		Picture p = g.currentEditingPicture();
		p = stack.redoPicture(p);
		p.notifyObservers();
	}	
}