package prometheus;

import java.io.IOException;

import prometheus.utils.FileUtils;

public class PlayerStats {

	String name;
	public int kills;
	public int deaths;

	public PlayerStats(String username) { // create profile for username including kills and deaths
		name = username;
		kills = FileUtils.getPlayerKills(username);
		deaths = FileUtils.getPlayerDeaths(username);
	}

	public void addKills(int amount) { // add to kill count using server
		this.kills += amount;
		try {
			Prometheus.toServer.writeUTF(name);
			Prometheus.toServer.writeInt(kills);
			Prometheus.toServer.writeInt(deaths);
			Prometheus.toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDeaths(int amount) { // add to death count using server
		this.deaths += amount;
		try {
			Prometheus.toServer.writeUTF(name);
			Prometheus.toServer.writeInt(kills);
			Prometheus.toServer.writeInt(deaths);
			Prometheus.toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
