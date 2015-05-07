package pl.dom133.skyvote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
import org.mcstats.Metrics;

import Lib.Config;
import Lib.HttpFile;
import Lib.VersionCheckerSV;
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
	public boolean checker;
	Scoreboard sb;
	Objective objective;
	ScoreboardManager manager;
	public String updatemsg;
	private VersionCheckerSV updatechecker;
	
	public void ScoreBoardCreate()
	{
		manager = Bukkit.getScoreboardManager();
		sb = manager.getNewScoreboard();
		objective = sb.registerNewObjective("glosowanie", "title");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(messages.get("g1").toString());
	}
	
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	@SuppressWarnings("resource")
	public void Download(String url, String filename)
	{
		try {
			HttpFile hf = new HttpFile(
			        new URL(url));
			File pdf = new File(filename);
			FileChannel ch = new FileOutputStream(pdf).getChannel();
			ch.write(ByteBuffer.wrap(hf.getData()));
			ch.close();
		} catch (MalformedURLException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

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
		updatechecker = new VersionCheckerSV(this);
		File message = new File("plugins/SkyVote/messages_"+lang.toLowerCase()+".yml");
				
		if(updatechecker.rawMessage("https://raw.githubusercontent.com/dom133/SkyVote/master/messages_"+lang.toLowerCase()+".yml")==null)
		{
			lang = "en";
			Config.getConfig("config").set("DataSource.lang", lang);
			Download("https://raw.githubusercontent.com/dom133/SkyVote/master/messages_"+lang.toLowerCase()+".yml", "plugins/SkyVote/messages_"+lang.toLowerCase()+".yml");
			Config.saveAll();
		}
		else if(message.exists()==false)
		{
			Download("https://raw.githubusercontent.com/dom133/SkyVote/master/messages_"+lang.toLowerCase()+".yml", "plugins/SkyVote/messages_"+lang.toLowerCase()+".yml");
		}
		else if(updatechecker.rawMessage("https://rawgit.com/dom133/SkyVote/master/version.txt")!=getDescription().getVersion())
		{
			Download("https://raw.githubusercontent.com/dom133/SkyVote/master/messages_"+lang.toLowerCase()+".yml", "plugins/SkyVote/messages_"+lang.toLowerCase()+".yml");			
		}
		
		Config.registerConfig("message", "messages_"+lang.toLowerCase()+".yml", this);
		Config.load("message");
		
		int i = 1;
		while(true)
		{
			if(Config.getConfig("message").getString("g"+i)==null) break;
			else messages.put("g"+i, Config.getConfig("message").getString("g"+i)); i++;
		}
		updatemsg = messages.get("g31").toString();
		updatechecker.startUpdateCheck();
		
		try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        // Failed to submit the stats :-(
	    }
	}
	
	@Override
	public void onDisable()
	{
		Config.saveAll();
	}
}