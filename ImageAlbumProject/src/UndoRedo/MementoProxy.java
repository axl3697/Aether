package UndoRedo;

import imageIO.Component;

public interface MementoProxy { 
	public Component mergeWithOriginator(Component comp);
}
