package prometheus.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtils {
	
	public static File getOrCreateFile(String name) {
		File f = new File("playerdata/"+name+".txt");
		if(!(f.exists())) {
			try {
				f.createNewFile();
				
				PrintWriter outputWriter;

				try {
					outputWriter = new PrintWriter(f);
					outputWriter.write("kills=" + 0 + "\n");
					outputWriter.write("deaths=" + 0 + "\n");
					outputWriter.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return f;
	}
	
	public static int getPlayerKills(String name) {
		try {
			File f = getOrCreateFile(name);
			Scanner scanner = new Scanner(f);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.contains("kills=")) {
					return Integer.parseInt(line.substring(6));
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static int getPlayerDeaths(String name) {
		try {
			File f = getOrCreateFile(name);
			Scanner scanner = new Scanner(f);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.contains("deaths=")) {
					return Integer.parseInt(line.substring(7));
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void setPlayerStats(String name, int kills, int deaths) {
		PrintWriter outputWriter;

		try {
			File f = getOrCreateFile(name);

			outputWriter = new PrintWriter(f);
			outputWriter.write("kills=" + kills + "\n");
			outputWriter.write("deaths=" + deaths + "\n");
			outputWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
