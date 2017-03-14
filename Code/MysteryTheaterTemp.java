import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MysteryTheaterTemp implements Runnable {

	@Override
	public void run() {
		File file = new File("GameSettings.txt");
		try {
			FileReader reader = new FileReader(file);
			int resolution = reader.read();
			System.out.println(resolution);
			reader.close();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			GameLauncher gl = new GameLauncher();
			System.exit(-1);
		}


	}

}
