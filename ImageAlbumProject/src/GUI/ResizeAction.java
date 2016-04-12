package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import AlterCommands.Resize;
import UndoRedo.BasicUndoRedoStack;

public class ResizeAction implements ActionListener{
	GUI g;
	public ResizeAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Call resize stuff here
		Picture p = g.currentEditingPicture();
		BasicUndoRedoStack stack = g.getUndoRedoStack();
		Integer newWidth = new Integer(0);
		Integer newHeight = new Integer(0);
		newWidth = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter new image width"));
		newHeight = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter new image height"));
		Resize c = new Resize();
		Object[] args = new Object[]{newWidth,newHeight};
		c.execute(p, args);
		p.notifyObservers();
		stack.add(c);
	}	
}