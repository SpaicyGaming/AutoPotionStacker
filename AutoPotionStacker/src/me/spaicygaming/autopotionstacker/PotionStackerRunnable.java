package me.spaicygaming.autopotionstacker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
//import org.bukkit.scheduler.BukkitRunnable;

public class PotionStackerRunnable implements Runnable {
	
	@Override
	public void run() {
			
		for (Player p : Bukkit.getServer().getOnlinePlayers()){			
			if (p.getInventory().contains(Material.POTION) && AutoPotionStacker.instance.getConfig().getBoolean("AutoPotionStacker.autostack.active")){
				AutoPotionStacker.instance.runPotionStacker(p);
			}
		}
		
	}
	

}
