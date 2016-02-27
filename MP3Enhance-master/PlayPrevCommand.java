public class PlayPrevCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
         int prevIndex = pl.getSourceIndex() - 1 ;
                /*
                 * Don't move before the first play list element.
                 */
                if( prevIndex >= 0 ) {
                    pl.play(prevIndex) ;
                }
   }
}