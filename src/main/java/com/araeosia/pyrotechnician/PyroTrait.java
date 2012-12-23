package com.araeosia.pyrotechnician;

import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Bukkit;

public class PyroTrait extends Trait {

	private Pyrotechnician plugin;

	public PyroTrait() {
		super("Pyrotechnician");
		plugin = (Pyrotechnician) Bukkit.getServer().getPluginManager().getPlugin("Pyrotechnician");
	}

	@Override
	public void load(DataKey key) {
	}

	@Override
	public void save(DataKey key) {
	}

	@Override
	public void run() { // Is it time for a fireworks show?
	}

	@Override
	public void onAttach() {
		plugin.debug(npc.getName()+"("+npc.getId()+") loaded as a Pyrotechnician");
	}

	@Override
	public void onDespawn() {
	}

	@Override
	public void onSpawn() {
	}

	@Override
	public void onRemove() {
	}
}