package pl.dom133.skyvote;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import Player.Commands;

public class SkyVote extends JavaPlugin implements Listener{
	
	public Map<String, String> vote = new HashMap<String, String>();
	public Map<String, String> messages = new HashMap<String, String>();
	public Map<String, String> vote_last = new HashMap<String, String>();
	public Map<String, Number> playervote = new HashMap<String, Number>();
	public int[] glosy = new int[2];
	public BukkitTask vote_info;
	public BukkitTask vote_delete;
	public String lang;
	Scoreboard sb;
	Objective objective;
	ScoreboardManager manager;
	
	public void ScoreBoardCreate()
	{
		manager = Bukkit.getScoreboardManager();
		sb = manager.getNewScoreboard();
		objective = sb.registerNewObjective("glosowanie", "title");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(messages.get("g1").toString());
	}
	
	public Scoreboard getScoreboard()
	{
		return this.sb;
	}
		
	public void ScoreBoardClear()
	{
		for(Player player : Bukkit.getOnlinePlayers())
		{
			player.setScoreboard(manager.getNewScoreboard());
		}
	}
		
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("svote").setExecutor(new Commands(this));
		Config.registerConfig("config", "config.yml", this);
		Config.load("config");
		String lang = Config.getConfig("config").getString("DataSource.lang");
		Config.registerConfig("message", "messages_"+lang.toLowerCase()+".yml", this);
		Config.load("message");
		for(int i=1; i<=30; ++i)
		{
			messages.put("g"+i, Config.getConfig("message").getString("g"+i));
		}
	}
	
	@Override
	public void onDisable()
	{
		Config.saveAll();
	}
}