package me.spaicygaming.autopotionstacker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PotionStackerListener implements Listener{
	
	AutoPotionStacker main;
	
	public PotionStackerListener(AutoPotionStacker APSPlugin) {
		this.main = APSPlugin;
	}
	
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event){
		
		if ((event.getAction().equals(Action.RIGHT_CLICK_AIR)) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			Player p = event.getPlayer();
			ItemStack item = p.getInventory().getItemInHand();
	      
			if ((item != null) && (item.getType().equals(Material.POTION)) && (!main.getConfig().getBoolean("AutoPotionStacker.drinkStackedPotions"))) {
				int amount = item.getAmount();
				if (amount >= 2) {
					event.setCancelled(true);
					item.setAmount(amount);
					p.setItemInHand(item);
					p.updateInventory();
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player && e.getInventory().equals(InventoryType.BREWING) && e.getRawSlot() < 4){
			Player p = (Player)e.getWhoClicked();
			ItemStack item = p.getItemOnCursor();
			
			if (item != null && !main.getConfig().getBoolean("AutoPotionStacker.brewStackedPotions") && item.getType().equals(Material.POTION)){
				if (item.getAmount() > 1){
					e.setCancelled(true);
					e.getInventory().clear(e.getRawSlot());
					item.setAmount(item.getAmount());
					p.setItemOnCursor(item);
					p.updateInventory();
				}
			}
		}
	}
	
	//Notify Updates
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (p.isOp() || p.hasPermission("autopotionstacker.notify") || p.hasPermission("*")){
			if(main.updates.length == 2){
				p.sendMessage(ChatColor.GREEN + main.Separatori(31));
				p.sendMessage("§6§l[§cConsoleOnly§6] New update available:");
				p.sendMessage("§6Current version: §e" + main.getDescription().getVersion());
				p.sendMessage("§6New version: §e" + main.updates[0]);
				p.sendMessage("§6What's new: §e" + main.updates[1]);
				p.sendMessage(ChatColor.GREEN + main.Separatori(31));
			}
		}
	}

}
