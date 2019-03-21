package prometheus;

public class PlayerStats {
	
	public int kills;
	public int deaths;
	public int games;
	
	public PlayerStats(String username) {
		//TODO Load from file based on username
		kills = 0;
		deaths = 0;
		games = 0;
	}
}
