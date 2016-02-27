public class TimeCommand implements Command
{
   public void execute(String[] args, PlayList pl)
   {
   int position = pl.getPosition() / 1000 ; // remove milliseconds
                int secs = position % 60 ;
                int mins = position / 60 ;
                System.out.printf("Source position: %d:%02d\n", mins, secs) ;
   }
}