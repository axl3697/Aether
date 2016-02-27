/*
 * Main driver class for the command line version of the audio player.
 * Creates a playlist from the command line arguments and then loops
 * looking for simple commands to control playback.
 */

import edu.rit.se.swen383.audio.AudioSource;
import java.util.*;

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
         
         
        Command cmd = null;
        Map<String, Command> commandMap = buildMap() ;
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
            /*
             * Arguments 1 .. N are things like the playlist
             * index to be used, etc.
             */ 
             
            cmd = commandMap.get(command);
            if( cmd != null ) {
               cmd.execute(arguments, pl, s) ;
            } 
         }
        
    }

    private static Map<String, Command> buildMap( ) {
       Command c ;
       Map<String, Command> result = new HashMap<String, Command>() ;
       
       c = new PlayNextCommand() ;
       result.put("+", c) ;
       result.put("next", c) ;
       
       c = new PlayPrevCommand() ;
       result.put("-", c) ;
       result.put("prev", c) ;
       
       c = new PlayAgainCommand() ;
       result.put("@", c) ;
       result.put("again", c) ;
       
       c = new HelpCommand() ;
       result.put("h", c) ;
       result.put("H", c) ;
       result.put("?", c) ;
       result.put("help", c) ;
       
       c = new InfoCommand(); 
       result.put("i", c) ;
       result.put("info", c) ;
       
       c = new PlayCommand() ;
       result.put("p", c) ;
       result.put("play", c) ;
       
       c = new PauseCommand() ;
       result.put("P", c) ;
       result.put("pause", c) ;
       
       c = new ResumeCommand() ;
       result.put("R", c) ;
       result.put("resume", c) ;
       
       c = new SizeCommand() ;
       result.put("s", c) ;
       result.put("size", c) ;
       
       c = new TimeCommand() ;
       result.put("t", c) ;
       result.put("time", c) ;
       
       c = new PlayNormalModeCommand() ;
       result.put("normal", c) ;
       
       c = new PlayRepeatModeCommand() ;
       result.put("repeat", c) ;
       
       c = new PlayRandomModeCommand() ;
       result.put("random", c) ;
       
       c = new QuitCommand() ;
       result.put("q", c) ;
       result.put("quit", c) ;
              
       return result ;
    }
     
    private static void println(String s) {
        System.out.println(s) ;
    }
}
