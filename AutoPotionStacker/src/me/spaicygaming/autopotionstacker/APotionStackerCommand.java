package me.spaicygaming.autopotionstacker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class APotionStackerCommand implements CommandExecutor{

	AutoPotionStacker main;
	
	public APotionStackerCommand(){
	}
	
	public APotionStackerCommand(AutoPotionStacker APSPlugin) {
		this.main = APSPlugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		
		String prefix = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("AutoPotionStacker.Messages.prefix")) + " " + ChatColor.RESET;
		String noconsole = "This command can only be run by a player.";
		String noperms = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("AutoPotionStacker.Messages.noPerms"));
		
		String projectlink = "http://bit.ly/AutoPotionStacker";
		String sourcelink = "http://bit.ly/AutoPotionStackerSource";
		String ver = main.getDescription().getVersion();
		
		if (alias.equalsIgnoreCase("potionstacker")){
			
			if (!(sender instanceof Player)){
				main.getLogger().info(noconsole);
				return true;
			}
			
			Player p = (Player)sender;
			
			if (args.length == 0){	
				p.sendMessage("");
				p.sendMessage(ChatColor.RED + "     --=" + ChatColor.GOLD  + " AutoPotionStacker " + ChatColor.GRAY + "v" + ver + ChatColor.RED + " =--");
				p.sendMessage(ChatColor.AQUA + "   /potion [/pot]" + ChatColor.GREEN + "->" + ChatColor.GRAY + " Stacks Potions.");
				p.sendMessage(ChatColor.AQUA + "   /potionstacker info " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Shows Info.");
				p.sendMessage(ChatColor.AQUA + "   /potionstacker reload " + ChatColor.GREEN + "->" + ChatColor.GRAY + " Reloads the Config.");
				p.sendMessage(ChatColor.RED + "          --=-=-=-=-=-=--");
				p.sendMessage("");
				
			}
			else if (args.length == 1){	
				if (args[0].equalsIgnoreCase("reload")){
					if  (!p.hasPermission("autopotionstacker.reload")){
						p.sendMessage(prefix + noperms);
						return true;
					}
					main.reloadConfig();
					p.sendMessage(prefix + "§cConfig Reloaded");
				}
				else if (args[0].equalsIgnoreCase("info")){
					p.sendMessage(ChatColor.DARK_GREEN + "    --=-=-=-=-=-=-=-=-=--");
					p.sendMessage(ChatColor.AQUA + "       AutoPotion" + ChatColor.RED + "Stacker" + ChatColor.GRAY + "v" + ver);
					p.sendMessage(ChatColor.GOLD + "    Project: " + ChatColor.DARK_RED + ChatColor.ITALIC + projectlink);
					p.sendMessage(ChatColor.GOLD + "    SourceCode: " + ChatColor.DARK_RED + ChatColor.ITALIC + sourcelink);
					p.sendMessage(ChatColor.DARK_GREEN + "      --=-=-=-=-=-=-=--");
					p.sendMessage("");	
				}
				else{
					p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("AutoPotionStacker.Messages.unkCommand")));
				}
			}
			else{
				p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("AutoPotionStacker.Messages.tooManyArgs")));
			}
		}
		
		
		if (alias.equalsIgnoreCase("potionstack") || alias.equals("pot")){
			
			if (!(sender instanceof Player)){
				main.getLogger().info(noconsole);
				return true;
			}
			
			Player p = (Player)sender;
			if (!p.hasPermission("autopotionstacker.use.command")){
				p.sendMessage(prefix + noperms);
				return true;
			}
			/*
			 * stack potions
			 */
			p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("AutoPotionStacker.Messages.potionStacked")));
			main.runPotionStacker(p);
			
		}
		
		
		return false;
	}
	
}
