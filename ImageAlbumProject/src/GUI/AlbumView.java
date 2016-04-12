package GUI;

import imageIO.Picture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import AlterCommands.Resize;
import UndoRedo.BasicUndoRedoStack;

public class AlbumView implements ActionListener{
	GUI g;
	public AlbumView(GUI g ){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if( ae.getActionCommand().equals( "1 Wide" ) )
      {
         g.changeGridSize( 1 );
      }
      else if( ae.getActionCommand().equals( "2 Wide" ) )
      {
         g.changeGridSize( 2 );
         
      }
      else if( ae.getActionCommand().equals( "4 Wide" ) )
      {
         g.changeGridSize( 4 );
         
      }
      
	}	
}