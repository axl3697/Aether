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
        
        while( true ) {
            
            /*
             * Read line and trim leading and trailing blanks.
             */
            String s = System.console().readLine() ;
            s.trim() ;
            /*
             * Split string into an array of strings, using
             * whitespace (spaces, tabs) as delimiters.
             */
            String arguments[] = s.split("\\s+") ;
            String command = arguments[0] ;
            Command cmd = null;
            /*
             * Arguments 1 .. N are things like the playlist
             * index to be used, etc.
             */ 
 
            if( command.equals("+") || command.equals("next") ) {
               cmd = new PlayNextCommand();
            }
            else if( command.equals("-") || command.equals("prev") ) {
               cmd = new PlayPrevCommand();
            }
            else if( command.equals("@") || command.equals("again") ) {
                cmd = new PlayAgainCommand();
            }
            else if( command.equals("h") || command.equals("H") || command.equals("?") || command.equals("help") ) {
                cmd = new HelpCommand();
            }
            else if( command.equals("i") || command.equals("info") ) {
               cmd = new InfoCommand(); 
               InfoCommand icmd = (InfoCommand) cmd;
               icmd.setConsoleStringReference(s);
            }               
            else if( command.equals("p") || command.equals("play") ) { 
               cmd = new PlayCommand();    
            }
            else if( command.equals("P") || command.equals("pause") ) {
               cmd = new PauseCommand(); 
            }
            else if( command.equals("R") || command.equals("resume") ) {
               cmd = new ResumeCommand(); 
            }
            else if( command.equals("s") || command.equals("size") ) {
               cmd = new SizeCommand(); 
            }
            else if( command.equals("t") || command.equals("time") ) {
               cmd = new TimeCommand(); 
            }
            else if( command.equals("normal") ) {
               cmd = new PlayNormalModeCommand(); 
            }
            else if( command.equals("repeat") ) {
               cmd = new PlayRepeatModeCommand(); 
            }
            else if( command.equals("random") ) {
               cmd = new PlayRandomModeCommand(); 
            }
            else if( command.equals("q") || command.equals("quit") ) 
            {
               cmd = new QuitCommand(); 
            }
            
            cmd.execute(arguments, pl);
        }
        
    }

    private static void println(String s) {
        System.out.println(s) ;
    }
}
