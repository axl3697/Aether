public class PlayRandomModeCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
      pl.setMode(PlayList.Mode.RANDOM);
   }
}