package prometheus.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileUtils {

	/**
	 * Method that return a file by a given name, if the file does not exist, create
	 * it
	 * 
	 * @param name username of the player
	 * @return File object for given file name
	 */
	public static File getOrCreateFile(String name) {
		// Create file object
		File f = new File("playerdata/" + name + ".txt");
		// If the file doesn't exist, create it
		if (!(f.exists())) {
			try {
				// Create new file
				f.createNewFile();

				PrintWriter outputWriter;

				try {
					// Add a starting value of 0 kills and 0 deaths to the file
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

		// Return file object
		return f;
	}

	/**
	 * Function that checks playerdata for the amount of kills a player has achieved
	 * 
	 * @param name username of player
	 * @return the number of kills a given user has
	 */
	public static int getPlayerKills(String name) {
		try {
			// Get or create a file for the user
			File f = getOrCreateFile(name);

			// Scan through the file line by line
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// If the line contains the string kills= return the integer value
				if (line.contains("kills=")) {
					return Integer.parseInt(line.substring(6));
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Function that checks playerdata for the amount of deaths a player has
	 * 
	 * @param name username of the player
	 * @return the number of deaths a given user has
	 */
	public static int getPlayerDeaths(String name) {
		try {
			// Get or create userdata file
			File f = getOrCreateFile(name);

			// Scan through every line
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				// Find the death line and parse the integer
				String line = scanner.nextLine();
				if (line.contains("deaths=")) {
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

	/**
	 * Function that takes kills, deaths and username and adds them to playerdata
	 * file
	 * 
	 * @param name   username of the player
	 * @param kills  the integer number of kills
	 * @param deaths the integer number of deaths
	 */
	public static void setPlayerStats(String name, int kills, int deaths) {
		PrintWriter outputWriter;

		try {
			// Get the player file
			File f = getOrCreateFile(name);

			// Write the kills and deaths overwriting old values
			outputWriter = new PrintWriter(f);
			outputWriter.write("kills=" + kills + "\n");
			outputWriter.write("deaths=" + deaths + "\n");
			outputWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A function that loops through all playerdata and sorts the users based on
	 * kills
	 * 
	 * @return
	 */
	public static HashMap<String, Integer> getHighscoresList() {
		// HashMap to store users
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// Loop through all files
		for (final File fileEntry : (new File("playerdata")).listFiles()) {
			if (!fileEntry.isDirectory()) {
				// Get rid of the .txt extension
				String name = fileEntry.getName().replace(".txt", "");
				// Add the user, use putIfAbsent to avoid double puts
				map.putIfAbsent(name, getPlayerKills(name));
			}
		}

		// Sort the HashMap by converting it to a LinkedHashMap, comparing the entry
		// values in reverse order
		return map.entrySet().stream().sorted(Collections.reverseOrder(Entry.comparingByValue()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
}
