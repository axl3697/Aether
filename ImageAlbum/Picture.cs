using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace ImageProcessing
{
    public class Picture
    {
        private string name;
        private string path;
        private string[] labels;
        private Bitmap pictureBitmap;

        public Picture(string name, string path, string[] labels)
        {
            Name = name;
            Path = "../../images/" + path;
            Labels = labels;
            Bitmap img;
            using (Bitmap bmp = new Bitmap(Path))
            {
                img = new Bitmap(bmp);
            }
            PictureBitmap = img;
            /*
            Bitmap bm = (Bitmap)Bitmap.FromFile(Path);
            PictureBitmap = (Bitmap)bm.Clone();
            bm.Dispose();*/
            /*
            using (Bitmap bm = (Bitmap)Bitmap.FromFile(Path))
            {
                Bitmap bitmap = bm;
                PictureBitmap = bitmap;
                //bm.Save(Path);
                bm.Dispose();
            }*/
            /*
            using (FileStream stream = new FileStream(Path, FileMode.Open, FileAccess.Read))
            {
                PictureBitmap = (Bitmap)Image.FromStream(stream);
            }*/

        }

        public Picture(string name, string path, string[] labels, Bitmap bitmap)
        {
            Name = name;
            Path = "../../images/" + path;
            Labels = labels;
            PictureBitmap = bitmap;
        }

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public string Path
        {
            get { return path; }
            set { path = value; }
        }

        public string[] Labels
        {
            get { return labels; }
            set { labels = value; }
        }

        public Bitmap PictureBitmap
        {
            get { return pictureBitmap; }
            set { pictureBitmap = value; }
        }
    }
}
