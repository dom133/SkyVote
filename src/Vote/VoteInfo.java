package Vote;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import pl.dom133.skyvote.SkyVote;

public class VoteInfo extends BukkitRunnable{
	
	private SkyVote plugin;
	public String vip;
	public String Commands;
	
	public String Message(String message, String... var)
	{
		String esc = message;
        for(int i = 0; i < var.length; ++i){
            esc = esc.replaceAll("%"+i, var[i]);
        }
        return esc;
	}
	
	public VoteInfo(SkyVote plugin, String vip)
	{
		this.plugin = plugin;
		this.vip = vip;
	}
	public void run() {
		if(vip==null)
		{
			Bukkit.broadcastMessage(ChatColor.GOLD+"======================"+plugin.messages.get("g1").toString()+"=====================");
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.vote.get("tresc").toString());
			Bukkit.broadcastMessage(ChatColor.GOLD+"=====================================================");
			Bukkit.broadcastMessage(ChatColor.GOLD+Message(plugin.messages.get("g7").toString(), plugin.vote.get("glos1").toString(), plugin.vote.get("glos2").toString()));
			Bukkit.broadcastMessage(ChatColor.GOLD+"=====================================================");
		}
		else if(vip.toLowerCase().equals("vip"))
		{
			Bukkit.broadcast(ChatColor.GOLD+"======================"+plugin.messages.get("g1").toString()+"=====================", "svote.vip");
			Bukkit.broadcast(ChatColor.GOLD+plugin.vote.get("tresc").toString(), "svote.vip");
			Bukkit.broadcast(ChatColor.GOLD+"=====================================================", "svote.vip");
			Bukkit.broadcast(ChatColor.GOLD+Message(plugin.messages.get("g7").toString(), plugin.vote.get("glos1").toString(), plugin.vote.get("glos2").toString()), "svote.vip");
			Bukkit.broadcast(ChatColor.GOLD+"=====================================================", "svote.vip");
		}
		else
		{
			Bukkit.broadcastMessage(ChatColor.GOLD+"======================"+plugin.messages.get("g1").toString()+"=====================");
			Bukkit.broadcastMessage(ChatColor.GOLD+plugin.vote.get("tresc").toString());
			Bukkit.broadcastMessage(ChatColor.GOLD+"=====================================================");
			Bukkit.broadcastMessage(ChatColor.GOLD+Message(plugin.messages.get("g7").toString(), plugin.vote.get("glos1").toString(), plugin.vote.get("glos2").toString()));
			Bukkit.broadcastMessage(ChatColor.GOLD+"=====================================================");
		}
	}

}