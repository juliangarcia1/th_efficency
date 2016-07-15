import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.teamtreehouse.KaraokeMachine;
import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;

import java.io.IOException;


public class Karaoke{
	public static void main( String [] args) throws IOException {
		SongBook songBook = new SongBook();
		songBook.importFrom("songs.txt");
		KaraokeMachine machine = new KaraokeMachine(songBook);
		machine.run();
		System.out.println("Saving book...");
		songBook.exportTo("songs.txt");
	}
}