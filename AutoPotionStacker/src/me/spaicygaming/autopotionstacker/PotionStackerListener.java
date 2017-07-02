package me.spaicygaming.autopotionstacker;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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

}
