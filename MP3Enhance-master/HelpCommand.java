public class HelpCommand implements Command
{
   public void execute(String[] args, PlayList pl, String s)
   {
                System.out.println("+ = Play the file after the current one.");
                System.out.println("- = Play the file before the current one.");
                System.out.println("@ = Replay the current file.") ;
                System.out.println("h or H or ? = Print this help screen.") ;
                System.out.println("i [n] = Print information on file #'n'") ;
                System.out.println("        (or the current file if 'n' is omitted).") ;
                System.out.println("p [n] = Terminate any playback and start playing") ;
                System.out.println("        AudioSource #'n' (default 0).") ;
                System.out.println("P = Pause playback if any.") ;
                System.out.println("R = Resume playback if any.") ;
                System.out.println("t = Print the playback position in seconds.") ;
                System.out.println("s = Print number of playlist entries.") ;
                
                System.out.println("normal = Normal(default) mode of playing.") ;
                System.out.println("repeat = When the playlist finishes, it starts over from the beginning.") ;
                System.out.println("random = Next song is selected randomly.") ;
                
                System.out.println("q = Quit the player.") ;
   }
}