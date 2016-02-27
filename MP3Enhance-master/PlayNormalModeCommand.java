public class PlayNormalModeCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
      pl.setMode(PlayList.Mode.NORMAL);
   }
}