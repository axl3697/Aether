/*
 * Main driver class for the command line version of the audio player.
 * Creates a playlist from the command line arguments and then loops
 * looking for simple commands to control playback.
 */

import edu.rit.se.swen383.audio.AudioSource;

public class MP3Player {
    /*
     * Driver with a simple command language to control the
     * audio playback.
     */
    public static void main(String args[]) {
        /*
         * We need at least one file to play.
         */
        if( args.length < 1 ) {
            println("Usage: MP3Player mp3file ...") ;
            return ;
        }

        /*
           Team members: Marko Pancirov, Ana Hakstok, Ante Hakstok, Antonio Labriola
           
           This is the new code. It defines an Interable<String> object which is instantiated
           either with FromFile or FromArray object depending of how the user provided the mp3 files.
           If the user provided a String list of mp3 files or something like - *.mp3 - then we will use
           the FromFile class. If the user typed first "-d" and then the name of the text file, then we will
           use the FromFile class.
           
           How	would	we add additional	sources of MP3File names (from an XML file, a database, a URL, etc.)?
           For example if we wanted our program to be able to extract mp3 files from an XML file, we would
           make a class called FromXml. Its constructor would pass the name of the XML file, and in the class
           we would have a method which opens and reads the XML file and stores the mp3 names in a List<String>.
           This object would also be passed to the PlayList constructor.
        */
        Iterable<String> mp3names;
        if( args[0].equals("-d") ) 
        {
           String filename = args[1];
           mp3names = new FromFile(filename);
        } 
        else 
        {
           mp3names = new FromArray(args);
        }
        
        PlayList pl = new PlayList(mp3names); 
         
        /*
         * Command loop.
         * Unrecognized commands are ignored.
         */
        char command = ' ' ;
        while( command != 'q' ) {
            String s = System.console().readLine() + " " ;
            command = s.charAt(0) ;

            if( command == '+' ) {
                int nextIndex = pl.getSourceIndex() + 1 ;
                /*
                 * Don't move beyond the last play list element.
                 */
                if( nextIndex < pl.size() ) {
                    pl.play(nextIndex) ;
                }
            }
            if( command == '-' ) {
                int prevIndex = pl.getSourceIndex() - 1 ;
                /*
                 * Don't move before the first play list element.
                 */
                if( prevIndex >= 0 ) {
                    pl.play(prevIndex) ;
                }
            }
            if( command == '@' ) {
                pl.play(pl.getSourceIndex()) ;
            }
            if( command == 'h'  || command == 'H' || command == '?') {
                println("+ = Play the file after the current one.");
                println("- = Play the file before the current one.");
                println("@ = Replay the current file.") ;
                println("h or H or ? = Print this help screen.") ;
                println("i [n] = Print information on file #'n'") ;
                println("        (or the current file if 'n' is omitted).") ;
                println("p [n] = Terminate any playback and start playing") ;
                println("        AudioSource #'n' (default 0).") ;
                println("P = Pause playback if any.") ;
                println("R = Resume playback if any.") ;
                println("t = Print the playback position in seconds.") ;
                println("s = Print number of playlist entries.") ;
                println("q = Quit the player.") ;
            }
            if( command == 'i') {
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
                    println("Player is idle") ;
                } else if( as != null ) {
                    int duration = as.getDuration() ;
                    int secs = duration % 60 ;
                    int mins = duration / 60 ;

                    println("Index:    " + i) ;
                    println("File:     " + as.getFileName()) ;
                    println("Title:    " + as.getTitle()) ;
                    println("Artist:   " + as.getArtist()) ;
                    println("Album:    " + as.getAlbum()) ;
                    println("Genre:    " + as.getGenre()) ;
                    System.out.printf ("Duration: %d:%02d\n", mins, secs) ;
                }
            }
            if( command == 'p' ) {
                int i = 0 ;
                try {
                    String iv = s.substring(1).trim() ;
                    i = Integer.parseInt(iv) ;
                } catch(Exception e) {i = 0 ; }
                pl.play(i) ;
            }
            if( command == 'P' ) {
                pl.pause() ;
            }
            if( command == 'R' ) {
                pl.resume() ;
            }
            if( command == 's' ) {
                println("Playlist size: " + pl.size()) ;
            }
            if( command == 't' ) {
                int position = pl.getPosition() / 1000 ; // remove milliseconds
                int secs = position % 60 ;
                int mins = position / 60 ;
                System.out.printf("Source position: %d:%02d\n", mins, secs) ;
            }
        }
        /*
         * System.exit(0) rather than return as there is another thread
         * running and a return would only terminate the main thread.
         */
        System.exit(0) ;
    }

    private static void println(String s) {
        System.out.println(s) ;
    }
}
