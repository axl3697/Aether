public class PlayAgainCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
      pl.play(pl.getSourceIndex()) ;
   }
}