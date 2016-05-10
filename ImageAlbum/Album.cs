using System;
using System.Collections.Generic;
using System.Text;

namespace ImageProcessing
{
    class Album
    {
        private static int id = 0;
        private string name;
        private List<Picture> pictureList = new List<Picture>();
        private List<Album> childAlbumList = new List<Album>();

        public Album(string name, List<Picture> pictureList, List<Album> childAlbumList)
        {
            Name = "Album: " + name;
            PictureList = pictureList;
            ChildAlbumList = childAlbumList;
            id++;
        }

        public int Id
        {
            get { return id; }
        }

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public List<Picture> PictureList
        {
            get { return pictureList; }
            set { pictureList = value; }
        }

        public List<Album> ChildAlbumList
        {
            get { return childAlbumList; }
            set { childAlbumList = value; }
        }
    }
}
