using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using System.Net;
using System.Threading;

namespace ImageProcessing
{
    public partial class AlbumGallery : Form
    {
        private Album rootAlbum;
        private List<Album> allAlbums = new List<Album>();
        private List<Picture> allPictures = new List<Picture>();

        Album selectedAlbum = null;
        Picture selectedPicture = null;

        private List<Picture> currentPictures = new List<Picture>();
        List<Bitmap> currentBitmapList = new List<Bitmap>();
        int i = 0;
        bool mode = false;//true for 4, false for 1

        OpenFileDialog oDlg;
        public AlbumGallery()
        {
            InitializeComponent();
            prev.FlatStyle = FlatStyle.Flat;
            prev.FlatAppearance.BorderSize = 0;
            next.FlatStyle = FlatStyle.Flat;
            next.FlatAppearance.BorderSize = 0;
            toggleButton.FlatStyle = FlatStyle.Flat;
            toggleButton.FlatAppearance.BorderSize = 0;

            oDlg = new OpenFileDialog(); // Open Dialog Initialization
            oDlg.RestoreDirectory = true;
            oDlg.InitialDirectory = "C:\\";
            oDlg.FilterIndex = 1;
            oDlg.Filter = "jpg Files (*.jpg)|*.jpg|gif Files (*.gif)|*.gif|png Files (*.png)|*.png |bmp Files (*.bmp)|*.bmp";
        }

        private void AlbumGallery_Load(object sender, EventArgs e)
        {
            List<Picture> picList1 = new List<Picture>();
            List<Picture> picList2 = new List<Picture>();
            List<Picture> picList3 = new List<Picture>();
            List<Picture> picList4 = new List<Picture>();

            Picture pic1 = new Picture("Abstract", "abstract.jpg", new[] { "crazy", "summer", "pipe" });
            Picture pic2 = new Picture("Avengers", "avengers.png", new[] { "fight", "hulk", "kapetan", "novi sad", "pipe" });
            Picture pic3 = new Picture("Diablo", "diablo.gif", new[] { "blizzard", "chaos", "anarchy", "peace" });
            Picture pic4 = new Picture("Futuristic", "futuristic123.jpg", new[] { "tommorow" });
            Picture pic5 = new Picture("Solar", "solar_sistem.png", new[] { "Luca", "Mads", "Luke", "Dinesh", "pipe" });
            Picture pic6 = new Picture("Spaceships", "spaceships.jpg", new[] { "kirk", "harp", "david", "tommorow" });
            Picture pic7 = new Picture("Staw Wars", "star_wars.png", new[] { "icke", "history", "war", "pipe" });
            Picture pic8 = new Picture("StarCraft", "starcraft.gif", new[] { "strategy", "protos", "minion", "tommorow", "pipe" });
            Picture pic9 = new Picture("Super Mario", "super_mario.jpg", new[] { "luigi", "tree", "pipe" });

            allPictures.Add(pic1);
            allPictures.Add(pic2);
            allPictures.Add(pic3);
            allPictures.Add(pic4);
            allPictures.Add(pic5);
            allPictures.Add(pic6);
            allPictures.Add(pic7);
            allPictures.Add(pic8);
            allPictures.Add(pic9);

            JSON.JSONWrite(allPictures);

            currentPictures.Add(pic1);
            currentPictures.Add(pic2);
            currentPictures.Add(pic3);
            currentPictures.Add(pic4);
            currentPictures.Add(pic5);
            currentPictures.Add(pic6);
            currentPictures.Add(pic7);
            currentPictures.Add(pic8);
            currentPictures.Add(pic9);

            picList1.Add(pic1);
            picList1.Add(pic2);
            picList1.Add(pic3);

            picList2.Add(pic4);
            picList2.Add(pic5);

            picList3.Add(pic6);
            picList3.Add(pic7);
            picList3.Add(pic8);

            picList4.Add(pic9);

            Album work = new Album("work", picList4, new List<Album>());
            allAlbums.Add(work);

            Album fun = new Album("fun", picList3, new List<Album>());
            allAlbums.Add(fun);

            List<Album> vacChildren = new List<Album>();
            vacChildren.Add(work);
            vacChildren.Add(fun);

            Album vacation = new Album("vacation", picList2, vacChildren);
            allAlbums.Add(vacation);

            Album summer = new Album("summer", picList1, new List<Album>());
            allAlbums.Add(summer);

            List<Album> allist = new List<Album>();
            allist.Add(summer);
            allist.Add(vacation);

            rootAlbum = new Album("root", new List<Picture>(), allist);
            allAlbums.Add(rootAlbum);
            PopulateTree(rootAlbum, null);

            setCurrentBitmapList();

            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
        }

        private void setCurrentBitmapList()
        {
            currentBitmapList.Clear();
            foreach (Picture picture in currentPictures)
            {
                currentBitmapList.Add(picture.PictureBitmap);
            }

            setPictures1();
        }

        private void DeleteAlbum(Album albumToDelete)
        {
            for(int i = 0; i < allAlbums.Count; i++)
            {
                if (allAlbums[i].Equals(albumToDelete))
                {
                    allAlbums.Remove(allAlbums[i]);
                }
            }

            DeleteAlbumRec(albumToDelete, rootAlbum);
        }

        private void DeleteAlbumRec(Album albumToDelete, Album parentAlbum)
        {
            for (int i = 0; i < parentAlbum.ChildAlbumList.Count; i++)
            {
                if (parentAlbum.ChildAlbumList[i].Equals(albumToDelete))
                {
                    parentAlbum.ChildAlbumList.Remove(parentAlbum.ChildAlbumList[i]);
                }
                else
                {
                    DeleteAlbumRec(albumToDelete, parentAlbum.ChildAlbumList[i]);
                }
                
            }
        }

        private void DeletePicture(Picture pictureToDelete)
        {
            for (int i = 0; i < allPictures.Count; i++)
            {
                if (allPictures[i].Equals(pictureToDelete))
                {
                    allPictures.Remove(allPictures[i]);
                }
            }

            DeletePictureRec(pictureToDelete, rootAlbum);
            JSON.JSONWrite(allPictures);
            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();
        }

        private void DeletePictureRec(Picture pictureToDelete, Album parentAlbum)
        {
            for (int i = 0; i < parentAlbum.ChildAlbumList.Count; i++)
            {
                for (int j = 0; j < parentAlbum.ChildAlbumList[i].PictureList.Count; j++)
                {
                    if (parentAlbum.ChildAlbumList[i].PictureList[j].Equals(pictureToDelete))
                    {
                        parentAlbum.ChildAlbumList[i].PictureList.Remove(parentAlbum.ChildAlbumList[i].PictureList[j]);
                    }
                    else
                    {
                        DeletePictureRec(pictureToDelete, parentAlbum.ChildAlbumList[i]);
                    }
                }
            }
        }

        private void PopulateTree(Album album, TreeNode parentNode)
        {
            if (parentNode == null)
            {
                TreeNode newNode = new TreeNode(album.Name);
                parentNode = newNode;
                treeView1.Nodes.Add(parentNode);
            }

            if (album.PictureList.Count > 0)
            {
                foreach (Picture pic in album.PictureList)
                {
                    TreeNode newNode = new TreeNode(pic.Name);
                    parentNode.Nodes.Add(newNode);
                }
            }

            if (album.ChildAlbumList.Count > 0)
            {
                foreach (Album a in album.ChildAlbumList)
                {
                    TreeNode newNode = new TreeNode(a.Name);
                    parentNode.Nodes.Add(newNode);
                    PopulateTree(a, newNode);
                }
            }
        }

        private void treeView1_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                string node = e.Node.Text;
                if (node.Contains("Album: "))
                {
                    panel1.Visible = true;
                    panel2.Visible = false;
                    string albumName = node;
                    foreach (Album album in allAlbums)
                    {
                        if (album.Name.Equals(albumName))
                        {
                            selectedAlbum = album;
                            changeAlbumNameTextBox.Text = selectedAlbum.Name.Substring(7);
                        }
                    }



                    //PopulateTree(rootAlbum, null);
                    /*
                    string albumName = node;
                    foreach (Album album in allAlbums)
                    {
                        if (album.Name.Equals(albumName))
                        {
                            foreach (Picture pic in album.PictureList)
                            {
                                Console.Write(pic.Name);
                            }
                        }
                    }*/
                }
                else
                {
                   
                    panel1.Visible = false;
                    panel2.Visible = true;

                    string pictureName = node;
                    
                    foreach (Picture picture in allPictures)
                    {
                        if (picture.Name.Equals(pictureName))
                        {
                            selectedPicture = picture;
                            changePictureNameTextBox.Text = selectedPicture.Name;

                            updateLabelsTextBox.Text = "";
                            for (int i = 0; i < selectedPicture.Labels.Length; i++)
                            {
                                if (i < selectedPicture.Labels.Length - 1)
                                {
                                    updateLabelsTextBox.Text += selectedPicture.Labels[i] + ",";
                                }  
                                else
                                {
                                    updateLabelsTextBox.Text += selectedPicture.Labels[i];
                                }
                            }
                            
                        }
                    }
                    
                }
            }
        }

        private void next_Click(object sender, EventArgs e)
        {
            i++;

            if (mode)
            {
                if (i > currentBitmapList.Count / 4)
                {
                    i--;
                    return;
                }
                setPictures4();
            }
            else
            {
                if (i >= currentBitmapList.Count)
                {
                    i--;
                    return;
                }
                setPictures1();
            }
        }

        private void prev_Click(object sender, EventArgs e)
        {
            i--;
            if (i < 0)
            {
                i = 0;
                return;
            }

            if (mode)
            {
                setPictures4();
            }
            else
            {
                setPictures1();
            }

        }

        private void setPictures4()
        {
            pictureBox5.Visible = false;
            pictureBox5Labels.Visible = false;

            pictureBox1.Visible = true;
            pictureBox2.Visible = true;
            pictureBox3.Visible = true;
            pictureBox4.Visible = true;

            pictureBox1Labels.Visible = true;
            pictureBox2Labels.Visible = true;
            pictureBox3Labels.Visible = true;
            pictureBox4Labels.Visible = true;

            int length = currentBitmapList.Count;
            try
            {
                int j = i * 4;

                if (j < length)
                {
                    pictureBox1.Image = currentBitmapList[j];
                    pictureBox1Labels.Text = ConvertStringArrayToString(currentPictures[j].Labels);
                }
                else
                {
                    pictureBox1.Image = null;
                    pictureBox1Labels.Text = "";
                }
                j++;
                if (j < length)
                {
                    pictureBox2.Image = currentBitmapList[j];
                    pictureBox2Labels.Text = ConvertStringArrayToString(currentPictures[j].Labels);
                }
                else
                {
                    pictureBox2.Image = null;
                    pictureBox2Labels.Text = "";
                }
                j++;
                if (j < length)
                {
                    pictureBox3.Image = currentBitmapList[j];
                    pictureBox3Labels.Text = ConvertStringArrayToString(currentPictures[j].Labels);
                }
                else
                {
                    pictureBox3.Image = null;
                    pictureBox3Labels.Text = "";
                }
                j++;
                if (j < length)
                {
                    pictureBox4.Image = currentBitmapList[j];
                    pictureBox4Labels.Text = ConvertStringArrayToString(currentPictures[j].Labels);
                }
                else
                {
                    pictureBox4.Image = null;
                    pictureBox4Labels.Text = "";
                }
            }
            catch (ArgumentOutOfRangeException)
            {
                MessageBox.Show("Error");
            }
        }

        private void setPictures1()
        {
            pictureBox1.Visible = false;
            pictureBox2.Visible = false;
            pictureBox3.Visible = false;
            pictureBox4.Visible = false;

            pictureBox1Labels.Visible = false;
            pictureBox2Labels.Visible = false;
            pictureBox3Labels.Visible = false;
            pictureBox4Labels.Visible = false;

            pictureBox5.Visible = true;
            pictureBox5Labels.Visible = true;

            if (currentBitmapList.Count > 0)
            {
                pictureBox5.Image = currentBitmapList[i];
                pictureBox5Labels.Text = ConvertStringArrayToString(currentPictures[i].Labels);
            }
            else
            {
                pictureBox5.Image = null;
                pictureBox5Labels.Text = "";
                MessageBox.Show("No pictures in this album");
            }
        }

        private string ConvertStringArrayToString(string[] array)
        {
            //
            // Concatenate all the elements into a StringBuilder.
            //
            StringBuilder builder = new StringBuilder();
            foreach (string value in array)
            {
                builder.Append(value);
                builder.Append(',');
            }
            return builder.ToString();
        }

        private void toggleButton_Click(object sender, EventArgs e)
        {
            i = 0;
            if (mode)
            {
                mode = false;
            }
            else
            {
                mode = true;
            }

            if (mode)
            {
                setPictures4();
                toggleButton.Image = (Bitmap)Bitmap.FromFile("../../Resources/toggle1.png");
            }
            else
            {
                setPictures1();
                toggleButton.Image = (Bitmap)Bitmap.FromFile("../../Resources/toggle4.png");
            }
        }

        private void deleteAlbumButton_Click(object sender, EventArgs e)
        {
            DeleteAlbum(selectedAlbum);
            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();
            panel1.Visible = false;
        }

        private void changeAlbumNameButton_Click(object sender, EventArgs e)
        {
            selectedAlbum.Name = "Album: " + changeAlbumNameTextBox.Text;
            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();
        }

        private void addAlbumButton_Click(object sender, EventArgs e)
        {
            Album newAlbum = new Album(addAlbumTextBox.Text, new List<Picture>(), new List<Album>());
            selectedAlbum.ChildAlbumList.Add(newAlbum);
            allAlbums.Add(newAlbum);

            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();

            addAlbumTextBox.Text = "";
        }

       

        

        private void AlbumGallery_MouseDown(object sender, MouseEventArgs e)
        {
            if (!panel1.Bounds.Contains(e.Location))
            {
                panel1.Visible = false;
            }

            if (!panel2.Bounds.Contains(e.Location))
            {
                panel2.Visible = false;
            }
        }

        string path = "";
        Bitmap bitmap;
        private void addPictureLocalButton_Click(object sender, EventArgs e)
        {
            panel1.Visible = false;
            if (DialogResult.OK == oDlg.ShowDialog())
            {   
                string[] str = oDlg.FileName.Split('\\');
               
                path = str[str.Length - 1];
                
                if (System.IO.File.Exists("../../images/" + path))
                    System.IO.File.Delete("../../images/" + path);

                //MessageBox.Show(oDlg.FileName);
                bitmap = (Bitmap)Bitmap.FromFile(oDlg.FileName);
                bitmap.Save("../../images/" + path);

                definePicturePanel.Visible = true;
                
                /*
                imageHandler.CurrentBitmap = (Bitmap)Bitmap.FromFile(oDlg.FileName);
                imageHandler.BitmapPath = oDlg.FileName;
                this.AutoScroll = true;
                this.AutoScrollMinSize = new Size(Convert.ToInt32(imageHandler.CurrentBitmap.Width * zoomFactor), Convert.ToInt32(imageHandler.CurrentBitmap.Height * zoomFactor));
                this.Invalidate();
                menuItemImageInfo.Enabled = true;
                ImageInfo imgInfo = new ImageInfo(imageHandler);
                imgInfo.Show();*/
            }
        }

        private void definePictureOKButton_Click(object sender, EventArgs e)
        {
            string name = pictureNameTextBox.Text;
            string[] labels = pictureLabelsTextBox.Text.Split(',');

            //
            Picture newPicture = new Picture(name, path, labels, bitmap);
            
            selectedAlbum.PictureList.Add(newPicture);
            //MessageBox.Show(selectedAlbum.Name);
            allPictures.Add(newPicture);
            JSON.JSONWrite(allPictures);
            definePicturePanel.Visible = false;

            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();
        }

        private void addPictureWebButton_Click(object sender, EventArgs e)
        {
            panel1.Visible = false;
            string input = Microsoft.VisualBasic.Interaction.InputBox("Copy url of the picture you want to import", "Import from web", "www.somewebpage.com/myPicture.jpg");

            string[] str = input.Split('/');

            path = str[str.Length - 1];
            //MessageBox.Show(path);

            string localPath = "";
            if (System.IO.File.Exists("../../images/" + path))
            {
                localPath = "../../images/" + "(1)" + path;
            }
            else
            {
                localPath = "../../images/" + path;
            }

            using (WebClient client = new WebClient())
            {
                client.DownloadFile(input, localPath);
            }

            //bitmap = (Bitmap)Bitmap.FromFile(localPath);

            Bitmap img;
            using (Bitmap bmp = new Bitmap(localPath))
            {
                img = new Bitmap(bmp);
            }
            bitmap = img;

            definePicturePanel.Visible = true;

            

        }

        private void displayPictureButton_Click(object sender, EventArgs e)
        {
            currentPictures.Clear();
            currentPictures.Add(selectedPicture);
            setCurrentBitmapList();
            panel2.Visible = false;
        }

        private void displayAlbumPicturesButton_Click(object sender, EventArgs e)
        {
            currentPictures.Clear();
            foreach (Picture picture in selectedAlbum.PictureList)
            {
                currentPictures.Add(picture);
            }

            setCurrentBitmapList();

            panel1.Visible = false;
        }

        private void editPictureButton_Click(object sender, EventArgs e)
        {
            ImageProcessing ip = new ImageProcessing(selectedPicture);
            ip.ShowDialog();
            selectedPicture = ip.returnCurrentEditingPicture();
            bool isSame = ip.returnIsSame();


            if (isSame)
            {
                selectedPicture.PictureBitmap.Save(selectedPicture.Path);
            }
            else
            {
                string format = ip.returnPictureFormat();
                string str = selectedPicture.Path.Remove(selectedPicture.Path.Length - 4);
                
                string newPath = str + "." + format;
                selectedPicture.Path = newPath;
                //MessageBox.Show(newPath);
                if (System.IO.File.Exists(selectedPicture.Path))
                    System.IO.File.Delete(selectedPicture.Path);

                if (format.Equals("png"))
                {
                    selectedPicture.PictureBitmap.Save(selectedPicture.Path, System.Drawing.Imaging.ImageFormat.Png);
                }
                else if (format.Equals("gif"))
                {
                    selectedPicture.PictureBitmap.Save(selectedPicture.Path, System.Drawing.Imaging.ImageFormat.Gif);
                }
                else if (format.Equals("jpg"))
                {
                    selectedPicture.PictureBitmap.Save(selectedPicture.Path, System.Drawing.Imaging.ImageFormat.Jpeg);
                }
            }

            
    
                

            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();

            setCurrentBitmapList();
        }

        private void changePictureNameButton_Click(object sender, EventArgs e)
        {
            selectedPicture.Name = changePictureNameTextBox.Text;
            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();
        }

        private void deletePIctureButton_Click(object sender, EventArgs e)
        {
            DeletePicture(selectedPicture);
            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();
            panel2.Visible = false;
        }

        private void updateLabelsButton_Click(object sender, EventArgs e)
        {
            selectedPicture.Labels = updateLabelsTextBox.Text.Split(',');
            treeView1.Nodes.Clear();
            PopulateTree(rootAlbum, null);
            treeView1.ExpandAll();
        }

        private void seacrhByLabelTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                string label = seacrhByLabelTextBox.Text;
                currentPictures.Clear();
                foreach (Picture picture in allPictures)
                {
                    string[] labels = picture.Labels;
                    for (int i = 0; i < labels.Length; i++)
                    {
                        if (labels[i].Equals(label))
                        {
                            currentPictures.Add(picture);
                        }
                    }
                }

                setCurrentBitmapList();
                MessageBox.Show("Found " + currentPictures.Count.ToString() + " pictures with label: " + label);
            }
        }
    }
}

       