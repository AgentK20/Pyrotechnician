package com.araeosia.pyrotechnician;

import java.util.logging.Logger;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class Pyrotechnician extends JavaPlugin {

	private Logger log;
	private boolean debugMode = false;
	private ShowHandler showHandler;

	@Override
	public void onEnable() {
		log = getLogger();
		if (!loadDependencies()) {
			getServer().getPluginManager().disablePlugin(this);
		}
		showHandler = new ShowHandler(this);
		this.getCommand("pyro").setExecutor(new CommandListener(this));
	}

	@Override
	public void onDisable() {
	}

	private boolean loadDependencies() {
		if (CitizensAPI.getPlugin() == null || !CitizensAPI.getPlugin().isEnabled()) {
			log.severe("Could not find CitizensNPCs! Is it properly loaded?");
			return false;
		}
		return true;
	}

	public void debug(String s) {
		if (debugMode) {
			log.info("Pyrotechnician debug: " + s);
		}
	}
	
	public ShowHandler getShowHandler(){
		return showHandler;
	}
}
