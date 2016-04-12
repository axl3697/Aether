package GUI;

import imageIO.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.util.List;
import imageIO.Component;
import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.BorderFactory;
import java.util.Observer;
import AlterCommands.Search;
import UndoRedo.BasicUndoRedoStack;
import java.io.File;



public class GUI implements Observer,KeyListener{
    private JFrame jf, jfResize;
    private JPanel jpGrid;
    private JMenuBar jmb;
    private JMenu jmFile, jmEdit, jmView, jmHelp;
    private JMenuItem jmiExit, jmiUndo, jmiRedo, jmiCrop, jmiResize, jmiRotateLeft, jmiRotateRight, jmiFlip, jmiMirror, jmiSave, jmiSaveAs, jmiView1, jmiView2, jmiViewA,jmiWeb,jmiExport;
    private JTextField jtfSearch, jtfHeight, jtfWidth;
    private JButton jbSubmit;
    private JPanel  jpSouth, jpEdit, jpMeta;
    private JPanel editControlPanel;
    //private String[][] items;
    private int height, width, gridSize, x, y, reference;
    private ArrayList<Image> images = new ArrayList<Image>();
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private Image icon;
    private JScrollPane treeView;

    private Collection<Picture> pictures;
    private List<BufferedImage> imageBuffers = new ArrayList<BufferedImage>();
    private Album allAlbums;
    private Picture masterPicture = null;
    //private Picture currentPicture=null;
    private Picture editingPicture = null;
    // private BufferedImage masterBuffer = null;
    // private BufferedImage editingBuffer = null;
    private Picture p = null;
    private Picture origionalPicture = null;
    private BasicUndoRedoStack stack;
    private JTree tree;
    private int test = 0;
    private boolean galleryLoaded = false;
    private JScrollPane listScroller ;
    private JScrollPane testScroller;
    private JPanel picturePanel;
    private JLabel pictureLabel;
    private JPopupMenu popup = new JPopupMenu();
    private PopupAction pa;
    private JMenuItem jmi1;
    private JTextField jtfMetaID, jtfMetaName, jtfMetaTags, jtfMetaAlbumList;


    public GUI(BasicUndoRedoStack stack, Album a) {
        // JFrame
        jf = new JFrame("PhotoBomb");
        jf.setLayout(new BorderLayout(1, 1));

        //set stack
        this.stack = stack;
        // JPanels
        allAlbums = a;

        // JMenuBar
        jmb = new JMenuBar();

        jmFile = new JMenu("File");
        
        //jmiWeb = new JMenuItem("Import From Web");
        //jmiWeb.addActionListener(new WebInputAction(this));
        
        jmiSave = new JMenuItem("Save");
        jmiSave.addActionListener(new SaveMenu(this));
        
        //jmiExport = new JMenuItem("Export Catalogue");
        //jmiExport.addActionListener(new ExportMenu(this));
        
        jmFile.add(jmiSave);
        //jmFile.add(jmiWeb);
        //jmFile.add(jmiExport);
        
        
        jmiExit = new JMenuItem("Exit");
        jmiExit.addActionListener(new ExitMenu());
        jmFile.add(jmiExit);

        jmEdit = new JMenu("Edit");

        jmiUndo = new JMenuItem("Undo");
        jmiUndo.addActionListener(new UndoAction(this));
        jmEdit.add(jmiUndo);

        jmiRedo = new JMenuItem("Redo");
        jmiRedo.addActionListener(new RedoAction(this));
        jmEdit.add(jmiRedo);

        jmiCrop = new JMenuItem("Crop");
        jmiCrop.addActionListener(new CropAction(this));
        jmEdit.add(jmiCrop);

        jmiResize = new JMenuItem("Resize");
        jmiResize.addActionListener(new ResizeAction(this));
        jmEdit.add(jmiResize);

        jmiRotateLeft = new JMenuItem("Rotate Left");
        jmiRotateLeft.addActionListener(new RotateClockwiseAction(this));
        jmEdit.add(jmiRotateLeft);

        jmiRotateRight = new JMenuItem("Rotate Right");
        jmiRotateRight.addActionListener(new RotateAnticlockwiseAction(this));
        jmEdit.add(jmiRotateRight);

        jmiMirror = new JMenuItem("Mirror");
        jmiMirror.addActionListener(new MirrorAction(this));
        jmEdit.add(jmiMirror);

        jmiFlip = new JMenuItem("Flip");
        jmiFlip.addActionListener(new FlipAction(this));
        jmEdit.add(jmiFlip);

        jmView = new JMenu("View");

        jmiView1 = new JMenuItem("1 Wide");
        jmiView1.addActionListener(new AlbumView(this));
        jmView.add(jmiView1);

        jmiView2 = new JMenuItem("2 Wide");
        jmiView2.addActionListener(new AlbumView(this));
        jmView.add(jmiView2);

        jmiViewA = new JMenuItem("4 Wide");
        jmiViewA.addActionListener(new AlbumView(this));
        jmView.add(jmiViewA);

        //jmHelp = new JMenu("Help");

        jmb.add(jmFile);
        jmb.add(jmEdit);
        jmb.add(jmView);
        //jmb.add(jmHelp);
        //jmb.add(new JLabel( "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         "));
        jtfSearch = new JTextField("Press Enter To Search", 15);
        jtfSearch.addKeyListener(this);
        jmb.add(jtfSearch);
        jf.setJMenuBar(jmb);

        // Get Resolution for Scaling
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double xx = screenSize.getWidth();
        double yy = screenSize.getHeight();
        width = (int) xx - 250; //-18
        height = (int) yy;

        gridSize = 4;
        //Display();

        // JFrame Properties
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setMinimumSize(new Dimension(600, 300));
        jf.setExtendedState(jf.MAXIMIZED_BOTH);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        
        pa = new PopupAction(this,popup);
        createTree();
        


    }
    public JTree getTree(){
    	return tree;
    }
    public JScrollPane getTreePane(){
    	return treeView;
    }
    public void createTree() {
    	tree = new JTree(createNodes(allAlbums));
        
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addMouseListener(pa);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(new TreeListener(this, tree));
         treeView = new JScrollPane(tree);
         
         //ova linija je bila iskomentirana
         //jf.add(treeView);
         ////
         
        treeView.setPreferredSize(new Dimension(200, 200));
        treeView.setMinimumSize(new Dimension(150, 150));
        treeView.setMaximumSize(new Dimension(500, 500));
        
        jf.add(treeView, BorderLayout.WEST);
        jf.revalidate();
    }

    public void buildSearchResults(ArrayList<Component> list){
        destroyPanel(listScroller);
        destroyPanel(jpGrid);
        destroyPanel(testScroller);
        setGridSize();
        for(Component item :list){
            Class albm = Album.class;
            Class pic = Picture.class;
            if(albm.isInstance(item)){
                jpGrid.add(loadAlbumImage((Album)item));
            }else if(pic.isInstance(item)){
                //add pictures to screen
                BufferedImage newPicture = null;
                final Picture currentPicture =(Picture)item;

                currentPicture.loadBufferedImage();
                newPicture = currentPicture.getImage();
                imageBuffers.add(newPicture);

                JLabel label = new JLabel();
                labels.add(label);
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        // boolean rightClick = ;
                        int count = evt.getClickCount();
                        if (count == 1) {
                            if(SwingUtilities.isLeftMouseButton(evt)){
                                editPanels(currentPicture);
                            }else if(SwingUtilities.isRightMouseButton(evt)){
                                metaData( currentPicture );
                            }
                        }

                    }
                });

                Image eePicture = (BufferedImage) newPicture;
                icon = eePicture.getScaledInstance(x, y, Image.SCALE_FAST);
                ImageIcon img = new ImageIcon(icon);
                label.setIcon(img);
                jpGrid.add(label);
                jpGrid.validate();
                jpGrid.revalidate();
            }

        }
        testScroller=null;
        testScroller = new JScrollPane(jpGrid);
        testScroller.setPreferredSize(new Dimension(width, height));
        testScroller.revalidate();
        jf.add(testScroller);
        //jf.add(jpGrid);
        jf.revalidate();
        //jf.repaint();
    }

    public JLabel loadAlbumImage(Album a){
        String filePath = "src/images/folder.png";
        FileAccessor newFileAccessor = new FileAccessor();
        BufferedImage folderImage = newFileAccessor.getBufferedImage(filePath);
        Image eePicture =  folderImage;
        icon = eePicture.getScaledInstance(x, y, Image.SCALE_FAST);
        ImageIcon img = new ImageIcon(icon);
        JLabel label = new JLabel();
        label.setIcon(img);
        final Album currentAlbum = a;
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                // boolean rightClick = ;
                System.out.println("Check mouse click");
                int count = evt.getClickCount();
                if (count == 1) {
                    if(SwingUtilities.isLeftMouseButton(evt)){
                        buildGallery(currentAlbum);
                    }
                }

            }
        });
        return label;
    }

    public void buildGallery(Album a) {
        destroyPanel(jpEdit);
        destroyPanel(editControlPanel);

        galleryLoaded = true;

        setGridSize();

        JLabel jl = new JLabel();
        labels.add(jl);
        try {
            BufferedImage newPicture = null;
            pictures = a.getPictureList();
            Iterator<Picture> picIterator = pictures.iterator();
            int i = 0;
            while (picIterator.hasNext()) {
                i++;
                final int temp = i;
                final Picture currentPicture = picIterator.next();

                currentPicture.loadBufferedImage();
                newPicture = currentPicture.getImage();
                imageBuffers.add(newPicture);

                JLabel label = new JLabel();
                labels.add(label);
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        int count = evt.getClickCount();
                        if (count == 1) {
                            if(SwingUtilities.isLeftMouseButton(evt)){
                                    editPanels( currentPicture );
                            }else if(SwingUtilities.isRightMouseButton(evt)){
                                metaData( currentPicture );
                            }
                        }

                    }
                });
                Image eePicture = (BufferedImage) newPicture;
                icon = eePicture.getScaledInstance(x, y, Image.SCALE_FAST);
                ImageIcon img = new ImageIcon(icon);
                label.setIcon(img);

                Font font = new Font("Sans Serif", Font.PLAIN, 24 );  

                picturePanel = new JPanel();
                picturePanel.setLayout(new BoxLayout(picturePanel,BoxLayout.Y_AXIS));
                JPanel jpPictureName = new JPanel( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
                picturePanel.add(label);
                pictureLabel = new JLabel( currentPicture.getName() );
                pictureLabel.setFont( font );
                jpPictureName.add( pictureLabel );
                picturePanel.add( jpPictureName );


                jpGrid.add(picturePanel);
            }

            //}
        } catch (NullPointerException e) {
            e.printStackTrace();
            String selectedString = "";
            JFileChooser chooser = new JFileChooser();
            // chooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            int choice = chooser.showOpenDialog(jf);

            if (choice == JFileChooser.APPROVE_OPTION) {
                // user selects a file
                File chosenFile = chooser.getSelectedFile();
            }

        }
        jpGrid.validate();
        jpGrid.revalidate();
        listScroller = new JScrollPane(jpGrid);
        listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScroller.setPreferredSize(new Dimension(width, height));
        jf.add(listScroller, BorderLayout.CENTER);
        jf.revalidate();
    }

    public DefaultMutableTreeNode createNodes(Album a) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(a);
        List<Picture> pictureList = a.getPictureList();
        List<Album> albumList = a.getChildAlbumList();

        for (Album album : albumList) {
            top.add(createNodes(album));
        }
        
        for (Picture p : pictureList) {
            top.add(new DefaultMutableTreeNode(p));
        }
        return top;
    }

    public void initFileCheck() {
        //Check the state of the ALBUM
        //If empty display a file browser to select a file

        //If not empty load images and build gallery
    }


    public void setGridSize() {
        if (gridSize == 1) {
            jpGrid = new JPanel(new GridLayout(0, 1, 0, 3));
            y = width / 16 * 9;
            x = width;
            jpGrid.setBorder(BorderFactory.createEmptyBorder(-5, -34, 0, -2));
        } else if (gridSize == 2) {
            jpGrid = new JPanel(new GridLayout(0, 2, 1, 3));
            y = width / 32 * 9;
            x = width / 2;
            jpGrid.setBorder(BorderFactory.createEmptyBorder(-5, -34, 0, -2));
        } else if (gridSize == 4) {
            jpGrid = new JPanel(new GridLayout(0, 4, 0, 3));
            y = (width-50)  / 64 * 9;
            x = (width-50) / 4;
            jpGrid.setBorder(BorderFactory.createEmptyBorder(-5, -64, 0, -34));
        }
        
        
    }


    public Picture currentEditingPicture() {
        return editingPicture;
    }

   public void activateButtons()
   {
      EditControls controls = new EditControls(this);
       editControlPanel = controls.buildPanel();
       jf.remove(treeView);
       jf.revalidate();
      jf.add(editControlPanel, BorderLayout.WEST );
      jf.revalidate();
   }

    public void Resize() {
        // JFrame
        jfResize = new JFrame("Resize");
        jfResize.setLayout(new BorderLayout(1, 1));

        // JPanel for Attributes
        JPanel jpResize = new JPanel(new GridLayout(0, 2, 5, 5));

        //
        jpResize.add(new JLabel(" Measurements ", JLabel.RIGHT));
        jpResize.add(new JLabel("in Pixels  ", JLabel.LEFT));

        // New Image Height
        jpResize.add(new JLabel(" New Height:  ", JLabel.RIGHT));
        jtfHeight = new JTextField("", 15);
        jpResize.add(jtfHeight);

        // New Image Width
        jpResize.add(new JLabel(" New Width:  ", JLabel.RIGHT));
        jtfWidth = new JTextField("", 15);
        jpResize.add(jtfWidth);

        // JPanel for Submit Button
        JPanel jpSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 3));

        // Submit Button
        jbSubmit = new JButton(" Submit ");
        jpSouth.add(jbSubmit);
        jbSubmit.addActionListener(new ResizeAction(this));

        // Add JPanels to JFrame
        jfResize.add(jpResize, BorderLayout.CENTER);
        jfResize.add(jpSouth, BorderLayout.SOUTH);

        // JFrame Properties
        jfResize.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jfResize.pack();
        jfResize.setLocationRelativeTo(null);
        jfResize.setVisible(true);
    }

    public void editPanels(Picture p) {
        destroyPanel(listScroller);
        destroyPanel(treeView);
        destroyPanel(testScroller);
        destroyPanel(jpGrid);

        if(jpGrid == null){
            p.loadBufferedImage();
        }


        //this.reference = reference;
        jpEdit = new JPanel(new GridLayout(0, 2, 5, 54));
        jpGrid.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        JLabel experiment = new JLabel();
        JLabel control = new JLabel();

        activateButtons();
        jf.revalidate();

        masterPicture = p.clone();
        editingPicture = p;
        editingPicture.addObserver(this);

        Image masterImage = masterPicture.getImage().getScaledInstance(width / 2 - 52, width / 32 * 9, Image.SCALE_FAST);
        Image editingImage = editingPicture.getImage().getScaledInstance(width / 2 - 52, width / 32 * 9, Image.SCALE_FAST);
        ImageIcon masterIcon = new ImageIcon(masterImage);
        ImageIcon editingIcon = new ImageIcon(editingImage);
        experiment.setIcon(masterIcon);
        control.setIcon(editingIcon);
        jpEdit.add(experiment);
        jpEdit.add(control);
        
        //ovo je bilo iskomentirano
        /*JPanel tagPanel = new JPanel();
        tagPanel.add(tags, BorderLayout.EAST);
        jf.add(tags, BorderLayout.NORTH);*/
        //////
        
        
        jpEdit.validate();
        jpEdit.revalidate();
        jf.add(jpEdit, BorderLayout.CENTER);
        jf.revalidate();

    }

    public BasicUndoRedoStack getUndoRedoStack() {
        return stack;
    }

    public void metaData( Picture p) {

        Picture meta = p;
        
        destroyPanel( jpGrid );
        destroyPanel( listScroller );
        destroyPanel( editControlPanel );
        destroyPanel( treeView );
        jpEdit = new JPanel( );
        
        jpEdit.setLayout(new BoxLayout( jpEdit, BoxLayout.Y_AXIS ) );
        JPanel jpMetaID = new JPanel( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
        JPanel jpMetaName = new JPanel( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
        JPanel jpMetaAlbumList = new JPanel( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
        JPanel jpMetaTagList = new JPanel( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
        JPanel jpMetaButtons = new JPanel( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
        
        jpEdit.setBorder(BorderFactory.createEmptyBorder(height/5, 0 , height/3, width/4 ));
         
        Font font = new Font("Sans Serif", Font.PLAIN, 18 );
        Font fName = new Font( "Sans Serif", Font.PLAIN, 24 );
        // Image ID
        JLabel jlMetaID = new JLabel("            ID:   ", JLabel.RIGHT );
        jtfMetaID = new JTextField( meta.getId() , 35);
        jtfMetaID.setEditable(false);
        jlMetaID.setFont( fName );
        jtfMetaID.setFont( font );
        jpMetaID.add( jlMetaID );
        jpMetaID.add( jtfMetaID );
        
        // Image Name
        JLabel jlMetaName = new JLabel("       Name:   ", JLabel.RIGHT );
        jtfMetaName = new JTextField( meta.getName(), 35);
        jtfMetaName.setEditable(false);
        jlMetaName.setFont( fName );
        jtfMetaName.setFont( font );
        jpMetaName.add(jlMetaName);
        jpMetaName.add(jtfMetaName);

        // Image Album List
        JLabel jlMetaAlbumList = new JLabel("    File Path:   ", JLabel.RIGHT );
        jtfMetaAlbumList = new JTextField( meta.getFilepath() , 35);
        jtfMetaAlbumList.setEditable(false);
        jlMetaAlbumList.setFont( fName );
        jtfMetaAlbumList.setFont( font );
        jpMetaAlbumList.add( jlMetaAlbumList );
        jpMetaAlbumList.add( jtfMetaAlbumList );

        // Image Tags 
        JLabel jlTags = new JLabel("     Tag List:   ", JLabel.RIGHT );
        jtfMetaTags = new JTextField( String.valueOf( meta.getTagList() ), 35);
        jtfMetaTags.setEditable(false);
        jlTags.setFont( fName );
        jtfMetaTags.setFont( font );
        jpMetaTagList.add( jlTags );
        jpMetaTagList.add( jtfMetaTags);
        
        //JButtons
        JButton jbEdit = new JButton( " Edit " );
        jbEdit.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                  jtfMetaID.setEditable( true );
                  jtfMetaName.setEditable( true );
                  jtfMetaTags.setEditable( true );
                  jtfMetaAlbumList.setEditable( true );
            }
        });
        JButton jbSave = new JButton( " Save" );
        jbEdit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jtfMetaID.setEditable( true );
                jtfMetaName.setEditable( true );
                jtfMetaTags.setEditable( true );
                jtfMetaAlbumList.setEditable( true );
            }
        });
        JButton jbBack = new JButton( " Back " );
        jbBack.addActionListener( new BackMenu( this ) );
        jbEdit.setFont( font );
        jbSave.setFont( font );
        jbBack.setFont( font );
        jpMetaButtons.add( jbBack ); 
        jpMetaButtons.add( new JLabel("      ") );
        jpMetaButtons.add( jbEdit );
        jpMetaButtons.add( new JLabel("      ") );
        jpMetaButtons.add( jbSave );
          
        jpEdit.add( jpMetaID );
        jpEdit.add( jpMetaName );
        jpEdit.add( jpMetaAlbumList );
        jpEdit.add( jpMetaTagList );
        jpEdit.add( jpMetaButtons);
        
        jpEdit.setPreferredSize(new Dimension(200, 300));
        jpEdit.setMinimumSize(new Dimension(150, 200));
        jpEdit.setMaximumSize(new Dimension(500, 600));

        jf.add( jpEdit, BorderLayout.CENTER );
        jf.revalidate();
    }

    public void Back() {
        destroyPanel(listScroller);
        destroyPanel(testScroller);
        destroyPanel(editControlPanel);
        destroyPanel(jpEdit);
        createTree();
        jf.revalidate();

    }

    public void update(Observable p, Object obj2){
        destroyPanel(jpGrid);
        destroyPanel(jpEdit);

        editingPicture = (Picture)p;

        jpEdit = new JPanel(new GridLayout(0, 2, 5, 54));
        JLabel experiment = new JLabel();
        JLabel control = new JLabel();

        Image masterImage = masterPicture.getImage().getScaledInstance(width / 2 - 52, width / 32 * 9, Image.SCALE_FAST);
        Image editingImage = editingPicture.getImage().getScaledInstance(width / 2 - 52, width / 32 * 9, Image.SCALE_FAST);
        ImageIcon masterIcon = new ImageIcon(masterImage);
        ImageIcon editingIcon = new ImageIcon(editingImage);
        experiment.setIcon(masterIcon);
        control.setIcon(editingIcon);
        jpEdit.add(experiment);
        jpEdit.add(control);

        jf.add(jpEdit, BorderLayout.CENTER);
        jf.revalidate();
    }
	public void keyPressed(KeyEvent e) {
		//If enter is typed, trigger sendButton actionListener
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!jtfSearch.getText().equals("")){
				ArrayList<Component> cs = new ArrayList<Component>();
				Search s = new Search();
				cs = s.doSearch(allAlbums, jtfSearch.getText());	
				
                buildSearchResults(cs);
			}
		}
	}

    public void destroyPanel(JPanel p){
        try{
            jf.remove(p);
            p = null;
            jf.revalidate();
            jf.repaint();
            System.gc();
        }catch (NullPointerException e){
            System.out.println("The panel doesn't exist");
        }

    }
    public void destroyPanel(JScrollPane p){
        try{
            jf.remove(p);
            p = null;
            jf.revalidate();
            jf.repaint();
            System.gc();
        }catch (NullPointerException e){
            System.out.println("The panel doesn't exist");
        }
   }
   
   public void changeGridSize( int gridSize )
   {
      this.gridSize = gridSize;
      setGridSize();
      destroyPanel(jpGrid);
   }
   public void setCurrentEditingPicture(Picture p) {
	   editingPicture = p;
   }
   
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return the jf
	 */
	public JFrame getJf() {
		return jf;
	}

}