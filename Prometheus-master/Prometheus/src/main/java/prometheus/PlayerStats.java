package prometheus;

import prometheus.utils.FileUtils;

public class PlayerStats {
	
	String name;
	public int kills;
	public int deaths;
	
	public PlayerStats(String username) {
		name = username;
		kills = FileUtils.getPlayerKills(username);
		deaths = FileUtils.getPlayerDeaths(username);
	}
	
	public void addKills(int amount) {
		this.kills += amount;
		FileUtils.setPlayerStats(name, this.kills, this.deaths);
	}
	
	public void addDeaths(int amount) {
		this.deaths += amount;
		FileUtils.setPlayerStats(name, this.kills, this.deaths);
	}
}
