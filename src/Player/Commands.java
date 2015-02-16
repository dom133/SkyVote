package Player;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import Vote.VoteDelete;
import Vote.VoteInfo;
import pl.dom133.skyvote.SkyVote;

public class Commands implements CommandExecutor{
	
	private SkyVote plugin;
	public Commands(SkyVote plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean Glos(Player player, String arg)
	{
		if(arg.equals(plugin.vote.get("glos1").toString()))
		{
			plugin.playervote.put(player.getDisplayName(), 0);
			plugin.glosy[0] =1;
			player.sendMessage(ChatColor.GOLD+plugin.messages.get("g19").toString());
			plugin.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GREEN+plugin.vote.get("glos1")+": ").setScore(plugin.glosy[0]);
			return true;
		}
		else if(arg.equals(plugin.vote.get("glos2").toString()))
		{
			plugin.playervote.put(player.getDisplayName(), 1);
			plugin.glosy[1] =1;
			player.sendMessage(ChatColor.GOLD+plugin.messages.get("g19").toString());
			plugin.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GREEN+plugin.vote.get("glos2")+": ").setScore(plugin.glosy[1]);
			return true;
		}
		else
		{
			player.sendMessage(ChatColor.RED+plugin.messages.get("g20").toString());
			return true;
		}
	}
	
	public void Glosowani(Player player, String tresc, String glos1, String glos2, String czas, String vip)
	{
		tresc = tresc.replaceAll("&", " ");
		plugin.vote.put("tresc", tresc);
		plugin.vote.put("glos1", glos1);
		plugin.vote.put("glos2", glos2);
		plugin.vote.put("vip", vip);
		plugin.vote_last.put("tresc", tresc);
		plugin.vote_last.put("glos1", glos1);
		plugin.vote_last.put("glos2", glos2);
		plugin.vote.put("czas", czas);
		if(czas==null)
		{
			player.sendMessage(ChatColor.RED+plugin.messages.get("g30").toString());
		}
		else
		{
			int czas1 = Integer.parseInt(czas);
			plugin.vote_info = new VoteInfo(plugin, vip).runTaskTimer(plugin, 20, 20*czas1/3);
			plugin.vote_delete = new VoteDelete(plugin, vip).runTaskLater(plugin, 20*czas1);
			plugin.ScoreBoardCreate();
			for(Player player1 : Bukkit.getOnlinePlayers())
			{
				if(vip==null)
				{
					player1.setScoreboard(plugin.getScoreboard());
				}
				else if(vip.toLowerCase().equals("vip"))
				{
					if(player1.hasPermission("svote.vip"))
					{
						player1.setScoreboard(plugin.getScoreboard());
					}
				}
				else
				{
					player1.setScoreboard(plugin.getScoreboard());
				}
			}
			plugin.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GREEN+glos1+": ").setScore(0);
			plugin.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GREEN+glos2+": ").setScore(0);
			player.sendMessage(ChatColor.GOLD+plugin.messages.get("g2").toString());
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("svote"))
		{
			if(sender instanceof Player)
			{
				Player player = (Player) sender;
				if(args.length > 0)
				{
					switch(args[0])
					{
						case "help":
						{
							player.sendMessage(ChatColor.GOLD+plugin.messages.get("g21").toString());
							player.sendMessage(ChatColor.GOLD+plugin.messages.get("g22").toString());
							player.sendMessage(ChatColor.GOLD+plugin.messages.get("g23").toString());
							if(player.hasPermission("svote.add")==true)player.sendMessage(ChatColor.GOLD+plugin.messages.get("g24").toString());
							if(player.hasPermission("svote.remove")==true)player.sendMessage(ChatColor.GOLD+plugin.messages.get("g25").toString());
							return true;
						}
						case "add":
						{
							if(player.hasPermission("svote.add")==true)
							{
								if(plugin.vote.get("tresc")==null)
								{
									if(args.length == 5)
									{
										Glosowani(player, args[1], args[2], args[3], args[4], null);
										return true;
									}
									else if(args.length ==6)
									{
										Glosowani(player, args[1], args[2], args[3], args[4], args[5]);
										return true;
									}
									else
									{
										player.sendMessage(ChatColor.RED+plugin.messages.get("g26").toString());
										return true;
									}
								}
								else
								{
									player.sendMessage(ChatColor.RED+plugin.messages.get("g29").toString());
									return true;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED+plugin.messages.get("g14").toString());
								return true;
							}
						}
						case "last":
						{
							if(plugin.vote_last.get("tresc")!=null)
							{
								player.sendMessage(ChatColor.GOLD+plugin.messages.get("g10").toString()+":");
								player.sendMessage(ChatColor.GOLD+plugin.vote_last.get("glos1")+": "+plugin.glosy[0]+" "+plugin.messages.get("g8").toString());
								player.sendMessage(ChatColor.GOLD+plugin.vote_last.get("glos2")+": "+plugin.glosy[1]+" "+plugin.messages.get("g9").toString());
								return true;
							}
							else
							{
								player.sendMessage(ChatColor.RED+plugin.messages.get("g11").toString());
								return true;
							}
						}
						case "remove":
						{
							if(player.hasPermission("svote.remove")==true)
							{
								if(plugin.vote.get("tresc")!=null)
								{
									plugin.playervote.clear();
									plugin.vote.clear();
									plugin.vote_last.clear();
									plugin.vote_info.cancel();
									plugin.vote_delete.cancel();
									plugin.glosy[0]=0;
									plugin.glosy[1]=0;
									plugin.ScoreBoardClear();
									player.sendMessage(ChatColor.GOLD+plugin.messages.get("g12").toString());
									return true;
								}
								else
								{
									player.sendMessage(ChatColor.RED+plugin.messages.get("g13").toString());
									return true;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED+plugin.messages.get("g14").toString());
								return true;
							}
						}
						case "autor":
						{
							player.sendMessage(ChatColor.GOLD+plugin.messages.get("g15").toString()+" dom133");
							return true;
						}
						default: 
						{
							if(player.hasPermission("svote.glos")==true)
							{
								if(plugin.vote.get("tresc")!=null)
								{
									if(plugin.playervote.get(player.getDisplayName())==null)
									{
										if(plugin.vote.get("vip")==null)
										{
											Glos(player, args[0]);
											return true;
										}
										else if(plugin.vote.get("vip").toString().toLowerCase().equals("vip"))
										{
											if(player.hasPermission("svote.vip")==true)
											{
												Glos(player, args[0]);
												return true;
											}
											else
											{
												player.sendMessage(ChatColor.GOLD+plugin.messages.get("g16").toString());
												return true;
											}	
										}
										else 
										{
											Glos(player, args[0]);
											return true;
										}
									}
									else
									{
										player.sendMessage(ChatColor.RED+plugin.messages.get("g17").toString());
										return true;
									}
								}
								else
								{
									player.sendMessage(ChatColor.GOLD+plugin.messages.get("g5").toString());
									return true;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED+plugin.messages.get("g18").toString());
								return true;
							}
					    }	
					}
				}
				else
				{
					player.sendMessage(ChatColor.GOLD+plugin.messages.get("g5").toString());
					return true;
				}
			}
			else
			{
				System.out.println(plugin.messages.get("g6").toString());
				return true;
			}
		}
		return false;
	}

}