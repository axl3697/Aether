package GUI;

import imageIO.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class AddTagAction implements ActionListener{
	private Component selectedItem;
	public AddTagAction(Component selectedItem){
		this.selectedItem = selectedItem;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String tag="";
		
		while(tag.equals("")){
			tag = JOptionPane.showInputDialog("Enter a tag name");
		}
		selectedItem.addTag(tag);
	}
}