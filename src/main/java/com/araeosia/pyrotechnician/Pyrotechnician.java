package com.araeosia.pyrotechnician;

import java.util.logging.Logger;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class Pyrotechnician extends JavaPlugin {
	
	private Logger log;
	
	@Override
	public void onEnable(){
		log = getLogger();
		if(!loadDependencies()){
			getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable(){
		
	}
	
	private boolean loadDependencies(){
		if(CitizensAPI.getPlugin()==null){
			log.severe("Could not find CitizensNPCs! Is it properly loaded?");
			return false;
		}
		return true;
	}
}
