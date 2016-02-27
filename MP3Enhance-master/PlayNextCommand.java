public class PlayNextCommand implements Command
{
   public void execute(String[] args, PlayList pl, String s)
   {
       int nextIndex = pl.getSourceIndex() + 1 ;
       /*
        * Don't move beyond the last play list element.
        */
       if( nextIndex < pl.size() ) {
           pl.play(nextIndex) ;
       }
   }
}