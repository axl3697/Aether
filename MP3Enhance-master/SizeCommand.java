public class SizeCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
      System.out.println("Playlist size: " + pl.size()) ;
   }
}