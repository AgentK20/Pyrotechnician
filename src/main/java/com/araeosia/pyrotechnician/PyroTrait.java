package com.araeosia.pyrotechnician;

import com.araeosia.pyrotechnician.utils.FireShow;
import com.araeosia.pyrotechnician.utils.FireShow.FireAct;
import com.araeosia.pyrotechnician.utils.FireShow.FireAct.CustomFirework;
import com.araeosia.pyrotechnician.utils.Utils;
import java.util.HashMap;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class PyroTrait extends Trait {

	private Pyrotechnician plugin;
	private Long currTime = 0L; // Separate from System.currentTimeMillis() to prevent lag from skipping start of a FireShow.
	private HashMap<Long, FireShow> shows = new HashMap<Long, FireShow>(); // Timestamp => Show
	private FireShow activeShow = null;
	private Integer showData = -1;
	private int sleep = 0;
	private boolean justSlept = false;
	private Location showStartPoint;

	public PyroTrait() {
		super("Pyrotechnician");
		plugin = (Pyrotechnician) Bukkit.getServer().getPluginManager().getPlugin("Pyrotechnician");
	}

	@Override
	public void load(DataKey key) {
		this.justSlept = key.getBoolean("justSlept");
		this.showData = key.getInt("showData");
		this.showStartPoint = new Location(
				plugin.getServer().getWorld(key.getString("location.world")),
				key.getInt("location.x"),
				key.getInt("location.y"),
				key.getInt("location.z"));
		this.sleep = key.getInt("sleep");
		this.currTime = key.getLong("currTime");
		for(int i = 0; key.keyExists("shows."+i); i++){
			this.shows.put(key.getLong("shows."+i+".time"), plugin.getShowHandler().getShow(key.getString("shows."+i+".show")));
		}
	}

	@Override
	public void save(DataKey key) {
		key.setBoolean("justSlept", justSlept);
		key.setInt("showData", showData);
		key.setString("location.world", showStartPoint.getWorld().getName());
		key.setInt("location.x", showStartPoint.getBlockX());
		key.setInt("location.y", showStartPoint.getBlockY());
		key.setInt("location.z", showStartPoint.getBlockZ());
		key.setInt("sleep", sleep);
		key.setLong("currTime", currTime);
		int i=0;
		for(Long l : shows.keySet()){
			key.setLong("shows."+i+".time", l);
			key.setString("shows."+i+".show", shows.get(l).getKey());
			i++;
		}
	}

	@Override
	public void run() { // Is it time for a fireworks show?
		if (activeShow == null) {
			currTime++;
			if (shows.containsKey(currTime)) {
				activeShow = shows.get(currTime);
				showStartPoint = npc.getBukkitEntity().getLocation();
				shows.remove(currTime);
			}
		} else {
			if (sleep != 0) {
				sleep = sleep - 1;
			} else {
				executeShow();
			}
		}
	}

	public void executeShow() {
		showData++;
		if (activeShow.getFireActs().containsKey(showData)) { // continue the show
			FireAct currentAct = activeShow.getFireActs().get(showData);
			if (currentAct.getDelay() != 0 && !justSlept) {
				sleep = currentAct.getDelay() * 20;
				showData = showData - 1; // Put us back where we were so we execute this act again.
				return;
			} else if (justSlept) {
				justSlept = false;
			}
			if (currentAct.getMessage() != null) {
				for (Player p : Utils.getPlayersInArea(showStartPoint, Math.pow(plugin.getConfig().getInt("Pyrotechnician.range"), 2))) {
					p.sendMessage(currentAct.getMessage());
				}
			}
			for (CustomFirework cf : currentAct.getFireworks()) {
				Location thisFirework = showStartPoint.add(cf.getLocation());
				Firework entity = (Firework) thisFirework.getWorld().spawnEntity(thisFirework, EntityType.FIREWORK);
				FireworkMeta meta = entity.getFireworkMeta();
				meta.addEffects(cf.getEffects());
				meta.setPower(cf.getPower());
				entity.setFireworkMeta(meta);
			}
		} else { // Show is over!
			activeShow = null;
			showData = -1;
		}
	}

	@Override
	public void onAttach() {
		plugin.debug(npc.getName() + "(" + npc.getId() + ") loaded as a Pyrotechnician");
		for (Player p : Utils.getPlayersByNPCSelection(npc.getId())) {
			p.sendMessage(npc.getFullName() + " has been set to a Pyrotechnician.");
		}
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

	public FireShow getActiveShow() {
		return activeShow;
	}

	public void getActiveShow(FireShow fs) {
		this.activeShow = fs;
	}
	
	public HashMap<Long, FireShow> getFireShows() {
		return shows;
	}
}