package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
 
import javax.security.auth.login.FailedLoginException;
 
import starter.Wiki;
import starter.Wiki.Revision;
 
public class WikipediaConnector {
     
    Wiki wiki = null;
     
    public void connect(String user,String myString) throws FileNotFoundException, IOException, ClassNotFoundException{
     
    File f = new File("wiki.dat");
    if (f.exists()) // we already have a copy on disk
    {
       ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
       wiki = (Wiki)in.readObject();
    }
    else
    {
       try
       {
           wiki = new Wiki("en.wikipedia.org"); // create a new wiki connection to en.wikipedia.org
           wiki.setThrottle(5000); // set the edit throttle to 0.2 Hz
            
           char [] caratteri = new char[myString.length()];
           for (int i=0; i<caratteri.length; i++) {
              caratteri[i] = myString.charAt(i);
           }
           wiki.login(user, caratteri); // log in as user ft227, with the specified password Matteo
       }
       catch (FailedLoginException ex)
       {
           // tentativo di login fallito
       }
}
}
    public Revision[] getChanges() throws IOException{
         
            return wiki.recentChanges(500);
    }
     
}