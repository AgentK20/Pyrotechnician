package com.araeosia.pyrotechnician;

import java.util.logging.Logger;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class Pyrotechnician extends JavaPlugin {

	private Logger log;
	private ShowHandler showHandler;
	
	private boolean debugMode=false;

	@Override
	public void onEnable() {
		loadConfig();
		log = getLogger();
		if (!loadDependencies()) {
			getServer().getPluginManager().disablePlugin(this);
		}
		showHandler = new ShowHandler(this);
		showHandler.loadShows();
		this.getCommand("pyro").setExecutor(new CommandListener(this));
		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(PyroTrait.class).withName("Pyrotechnician"));
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
	
	public void loadConfig(){
		if(getConfig().getDouble("Pyrotechnician.technical.version")!=0.1){
			// Save default config to disk?
		}
		debugMode = getConfig().getBoolean("Pyrotechnician.technical.debugMode");
	}
}
