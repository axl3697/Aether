package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import AlterCommands.Crop;
import UndoRedo.BasicUndoRedoStack;

public class CropAction implements ActionListener{
	GUI g;
	public CropAction(GUI g){
		this.g = g;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Call crop stuff here
		Picture p = g.currentEditingPicture();
		BasicUndoRedoStack stack = g.getUndoRedoStack();
		Integer x = new Integer(0);
		Integer y = new Integer(0);
		Integer width = new Integer(0);
		Integer height = new Integer(0);
		x = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter x starting location"));
		y = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter y starting location"));
		width = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter width of area to crop"));
		height = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter height of area to crop"));
		Crop c = new Crop();
		Object[] args = new Object[]{x,y,width,height};
		c.execute(p, args);
		p.notifyObservers();
		stack.add(c);
	}	
}