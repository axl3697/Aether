/**
 * 
 */
package UndoRedo;
public interface MementoToOriginator {
	/**
	 * This is a generic setter for a given Memento. It allows creation of memento Objects 
	 * @param obj stores the partial state to be represented
	 */
	
	public MementoProxy getMemento(); // duplicate of java.lang.Object?
	public MementoProxy getValue();// gets the value of the memento
//	public Memento setMemento(Memento mem);
}
