import java.util.*;
import java.util.ArrayList ;

import java.io.InputStream ;
import java.io.Reader ;
import java.io.FileInputStream ;
import java.io.InputStreamReader ;
import java.io.BufferedReader ; 
/*
   This is class is used in case the user provided the mp3 files via a text file.
*/
public class FromFile implements Iterable<String> 
{
   private List<String> list = new ArrayList<String>();
   
   public FromFile(String fileName) 
   {
      loadFromFile(fileName);
   }
   
   public Iterator<String> iterator() 
   {
      return list.iterator();
   }
   
   private void loadFromFile(String fileName) 
   {
      try 
      {
         InputStream fis = new FileInputStream(fileName);
         Reader isr = new InputStreamReader(fis) ;
         BufferedReader reader = new BufferedReader(isr);
         
         String line = reader.readLine();
         while( line != null ) 
         {
            list.add(line) ;
            line = reader.readLine();
         }
      } 
      catch(Exception ex) 
      {
         list.clear(); // empty list on exception.
      }
      return ; 
   }
} 