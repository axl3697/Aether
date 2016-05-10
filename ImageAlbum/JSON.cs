using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
namespace ImageProcessing
{
    class JSON
    {

        public static void JSONWrite(List<Picture> pictures)
        {
            // serialize JSON directly to a file
            using (StreamWriter file = File.CreateText("../../Resources/pictures.json"))
            {
                JsonSerializer serializer = new JsonSerializer();
                serializer.Serialize(file, pictures);
            }
        }

        public static List<Picture> JSONRead()
        {
            List<Picture> pictures = null;
            using (StreamReader r = new StreamReader("../../Resources/pictures.json"))
            {
                string json = r.ReadToEnd();
                pictures = Newtonsoft.Json.JsonConvert.DeserializeObject<List<Picture>>(json);
            }

            return pictures;
        }
    }
}
