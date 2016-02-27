public class ResumeCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
      pl.resume() ;
   }
}