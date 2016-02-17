import static java.util.Arrays.asList ;
import java.util.List ;
import java.util.Iterator ;
/*
   This class is used in case the user provided the mp3 files via an array of Strings
*/
public class FromArray implements Iterable<String> 
{
    private final List<String> list;
    
    public FromArray(String[] args) 
    {
       list = asList(args);//this will transform our args String[] array into a List<String> object and store it in the list attribute
    }
    
    public Iterator<String> iterator() 
    {
       return list.iterator() ;
    }
} 