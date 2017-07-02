package me.spaicygaming.autopotionstacker;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoPotionStacker extends JavaPlugin {
	
	public static AutoPotionStacker instance;
	
	public void onEnable(){
		instance = this;
		getServer().getPluginManager().registerEvents(new PotionStackerListener(this), this);
		
		getLogger().info("AutoPotionStacker has been Enabled!");
		saveDefaultConfig();
		
		getCommand("potionstacker").setExecutor(new APotionStackerCommand(this));
		getCommand("potionstack").setExecutor(new APotionStackerCommand(this));
		
		//se attivo
		if (getConfig().getBoolean("AutoPotionStacker.autostack.active")){
			getServer().getScheduler().runTaskTimer(this, new PotionStackerRunnable(), 0, getConfig().getInt("AutoPotionStacker.autostack.delay"));
		}
		
		
		if (!getConfig().getString("ConfigVersion").equals("1.0")) {
			getLogger().warning("########################################################");
	        getLogger().warning("OUTDATED CONFIG FILE DETECTED, PLEASE DELETE THE OLD ONE!");
	        getLogger().warning("########################################################");
	    }
	}
	
	public void onDisable(){
		getLogger().info("AutoPotionStacker has been Disabled! :(");
	}
	
	
	public void runPotionStacker(Player p){ //, boolean send_msg, String noperms

		short[] potion_types = new short[2866];
		short k = 0; short l = 0;
		do {
			potion_types[(l++)] = (k++);
			if (k == 100)
				k = 8000;
				
			if (k == 9000) 
				k = 16000;
				
			if (k == 17000) 
				k = 32000; 
				
		} while (k != 32766);

		
		int[] potion_amt = new int[potion_types.length];
		//boolean success = false;

		ItemStack[] inventory_contents = p.getInventory().getContents();
		
		for (int i = 0; i < inventory_contents.length; i++) {
			ItemStack item = inventory_contents[i];
			if ((item != null) && (item.getType().equals(Material.POTION))) {
	        	short potion_type = item.getDurability();
	        	for (int j = 0; j < potion_types.length; j++) {
	        		if (potion_type == potion_types[j]) {
	        			potion_amt[j] += item.getAmount();
	        			p.getInventory().clear(i);
	        		}
	        	}
	        }
		}
		
		for (int j = 0; j < potion_types.length; j++) {
        	if (potion_amt[j] > 0) {
        		ItemStack potions = new ItemStack(Material.POTION, potion_amt[j], potion_types[j]);
        		p.getInventory().addItem(new ItemStack[] { potions });
        		//success = true;
        	}
        }
		
	}

}
