import edu.rit.se.swen383.audio.AudioSource;

public class InfoCommand implements Command
{
   private String s;
   
   public void setConsoleStringReference(String s)
   {
      this.s = s;
   }
   
   public void execute(String[] args, PlayList pl)
   {
      AudioSource as = null ;
                int i = -1 ;

                try {
                    String iv = s.substring(1).trim() ;
                    i = Integer.parseInt(iv) ;
                } catch(Exception e) {
                    i = -1 ; // no integer argument.
                } ;
                if( i < 0 ) {
                    i = pl.getSourceIndex() ;
                }
                as = pl.getSource(i) ;

                if( i == (-1) ) {
                    System.out.println("Player is idle") ;
                } else if( as != null ) {
                    int duration = as.getDuration() ;
                    int secs = duration % 60 ;
                    int mins = duration / 60 ;

                    System.out.println("Index:    " + i) ;
                    System.out.println("File:     " + as.getFileName()) ;
                    System.out.println("Title:    " + as.getTitle()) ;
                    System.out.println("Artist:   " + as.getArtist()) ;
                    System.out.println("Album:    " + as.getAlbum()) ;
                    System.out.println("Genre:    " + as.getGenre()) ;
                    System.out.printf ("Duration: %d:%02d\n", mins, secs) ;
                }

   }
}