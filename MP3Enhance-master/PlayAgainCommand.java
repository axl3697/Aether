public class PlayAgainCommand implements Command
{
   public void execute(String[] args, PlayList pl, String s)
   {
      pl.play(pl.getSourceIndex()) ;
   }
}