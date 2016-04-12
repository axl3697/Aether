package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UndoRedo.BasicUndoRedoStack;

public class UndoAction implements ActionListener{
	GUI g;
	public UndoAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		BasicUndoRedoStack stack = g.getUndoRedoStack();
		Picture p = g.currentEditingPicture();
		p = stack.undoPicture(p);
		p.notifyObservers();
	}	
}