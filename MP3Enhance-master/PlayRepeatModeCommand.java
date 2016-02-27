public class PlayRepeatModeCommand implements Command
{
   public void execute(String[] args, PlayList pl, String s)
   {
      pl.setMode(PlayList.Mode.REPEAT);
   }
}