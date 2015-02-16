package Vote;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import pl.dom133.skyvote.SkyVote;

public class VoteDelete extends BukkitRunnable{
	private SkyVote plugin;
	public String vip;
	
	public VoteDelete(SkyVote plugin, String vip)
	{
		this.plugin = plugin;
		this.vip = vip;
		
	}
	@Override
	public void run() {
		if(vip==null)
		{
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.messages.get("g27").toString());
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.messages.get("g28").toString()+":");
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.vote.get("glos1").toString()+": "+plugin.glosy[0]+" "+plugin.messages.get("g8").toString());
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.vote.get("glos2").toString()+": "+plugin.glosy[1]+" "+plugin.messages.get("g9").toString());
		}
		else if(vip.toLowerCase().equals("vip"))
		{
			Bukkit.broadcast(ChatColor.GOLD+plugin.messages.get("g27").toString(), "svote.vip");
			Bukkit.broadcast(ChatColor.GOLD+plugin.messages.get("g28").toString()+":", "svote.vip");
			Bukkit.broadcast(ChatColor.GOLD+plugin.vote.get("glos1").toString()+": "+plugin.glosy[0]+" "+plugin.messages.get("g8").toString(), "svote.vip");
			Bukkit.broadcast(ChatColor.GOLD+plugin.vote.get("glos2").toString()+": "+plugin.glosy[1]+" "+plugin.messages.get("g9").toString(), "svote.vip");		
		}
		else
		{
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.messages.get("g27").toString());
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.messages.get("g28").toString()+":");
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.vote.get("glos1").toString()+": "+plugin.glosy[0]+" "+plugin.messages.get("g8").toString());
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.vote.get("glos2").toString()+": "+plugin.glosy[1]+" "+plugin.messages.get("g9").toString());
		}
		plugin.playervote.clear();
		plugin.vote.clear();
		plugin.vote_info.cancel();
		plugin.glosy[0]=0;
		plugin.glosy[1]=0;
		plugin.ScoreBoardClear();
	}

}