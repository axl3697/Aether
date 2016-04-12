package GUI;

import javax.swing.*;

import java.awt.*;

/**
 * Created by Nick on 4/25/15.
 */
public class EditControls {
    private JButton jbCrop, jbRotateLeft, jbRotateRight, jbMirror, jbRedo, jbUndo, jbResize, jbSave, jbFlip;
    private JPanel  editControlPanel;
	private JButton jbBack;


    public EditControls(GUI g){
        editControlPanel = new JPanel( new GridLayout( 0, 1, 5, 5 ) );

        jbUndo = new JButton("Undo");
        editControlPanel.add(jbUndo);
        jbUndo.addActionListener( new UndoAction(g) );

        jbRedo = new JButton("Redo");
        editControlPanel.add(jbRedo);
        jbRedo.addActionListener( new RedoAction(g) );

        jbCrop = new JButton("Crop");
        editControlPanel.add(jbCrop);
        jbCrop.addActionListener( new CropAction(g) );

        jbResize = new JButton("Resize");
        editControlPanel.add(jbResize);
        jbResize.addActionListener( new ResizeAction(g) );

        jbRotateLeft = new JButton("Rotate Left");
        editControlPanel.add(jbRotateLeft);
        jbRotateLeft.addActionListener( new RotateClockwiseAction(g) );

        jbRotateRight = new JButton("Rotate Right");
        editControlPanel.add(jbRotateRight);
        jbRotateRight.addActionListener( new RotateAnticlockwiseAction(g) );

        jbMirror = new JButton("Mirror");
        editControlPanel.add(jbMirror);
        jbMirror.addActionListener( new MirrorAction(g) );

        jbFlip = new JButton("Flip");
        editControlPanel.add(jbFlip);
        jbFlip.addActionListener( new FlipAction(g) );

        jbSave = new JButton("Save");
        editControlPanel.add(jbSave);
        jbSave.addActionListener( new SaveMenu(g) );
        
        jbBack = new JButton("Back");
        editControlPanel.add(jbBack);
        jbBack.addActionListener( new BackMenu(g) );
    }

    public JPanel buildPanel(){
        return editControlPanel;
    }
}
