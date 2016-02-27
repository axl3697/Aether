public class PlayCommand implements Command
{
   public void execute(String[] arguments, PlayList pl, String s)
   {
   //Play a source. If an integer is given, play that list entry,
                //otherwise the first source in the list.
                int index ; 
                if( arguments.length == 1 ) 
                { 
                   index = 0 ;
                } 
                else 
                {
                   try 
                   {
                      index = Integer.parseInt(arguments[1]) ;
                   } 
                   catch(Exception e) 
                   {
                     index = 0 ; 
                   }
                }
                
                pl.play(index) ;
   }
}