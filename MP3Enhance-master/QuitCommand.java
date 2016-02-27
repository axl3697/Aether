public class QuitCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
   //System.exit(0) rather than return as there is another thread
                //running and a return would only terminate the main thread.
                System.exit(0) ;
   }
}