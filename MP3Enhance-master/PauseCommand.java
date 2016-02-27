public class PauseCommand implements Command
{
   public void execute(String[] args, PlayList pl, String s)
   {
      pl.pause() ;
   }
}