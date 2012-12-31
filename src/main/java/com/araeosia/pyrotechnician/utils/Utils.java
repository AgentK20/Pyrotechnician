package com.araeosia.pyrotechnician.utils;

import com.araeosia.pyrotechnician.Pyrotechnician;
import com.araeosia.pyrotechnician.utils.FireShow.FireAct;
import com.araeosia.pyrotechnician.utils.FireShow.FireAct.CustomFirework;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.util.YamlStorage;
import net.citizensnpcs.api.util.YamlStorage.YamlKey;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

public class Utils { // Static utilities class for utility methods.
	
	public static FireShow loadShow(String key) {
		Pyrotechnician plugin = (Pyrotechnician) Bukkit.getServer().getPluginManager().getPlugin("Pyrotechnician");
		String loc = plugin.getDataFolder().getAbsolutePath()+File.separator+"shows";
		plugin.debug("Location: "+loc);
		File storageLoc = new File(loc);
		if(!storageLoc.exists()){storageLoc.mkdir();}
		File actualFile = new File(storageLoc.getAbsolutePath()+File.separator+key+".yml");
		YamlStorage storage = new YamlStorage(actualFile);
		storage.load();
		plugin.debug("Loading file from "+actualFile.getAbsolutePath());
		if (storage.getKey(key) != null) {
			YamlKey data = storage.getKey(key); // This one FireShow.
			plugin.debug("Data: "+data.toString());
			if(data.keyExists("name")){
				FireShow output = new FireShow(key, data.getString("name"));
				plugin.debug("Looks like it is named "+data.getString("name"));
				if(data.keyExists("announce-5")){
					plugin.debug("5 minute announcement set to "+data.getString("announce-5"));
					output.getAnnouncements().put(5, data.getString("announce-5"));
				}
				if(data.keyExists("announce-1")){
					plugin.debug("1 minute announcement set to "+data.getString("announce-1"));
					output.getAnnouncements().put(2, data.getString("announce-1"));
				}
				if(data.keyExists("announce-lt1")){
					plugin.debug("lt1 minute announcement set to "+data.getString("announce-lt1"));
					output.getAnnouncements().put(1, data.getString("announce-lt1"));
				}
				if(data.keyExists("announce")){
					plugin.debug("Start announcement set to "+data.getString("announce"));
					output.getAnnouncements().put(0, data.getString("announce"));
				}
				YamlKey acts = data.getRelative("acts");
				for(DataKey dk : acts.getIntegerSubKeys()){
					plugin.debug("Working with next act.");
					FireAct thisAct = output.new FireAct();
					if(dk.keyExists("delay")){
						plugin.debug("This act's delay is "+dk.getInt("delay"));
						thisAct.setDelay(dk.getInt("delay"));
					}
					if(dk.keyExists("message")){
						plugin.debug("This act's message is "+dk.getString("message"));
						thisAct.setMessage(dk.getString("message"));
					}
					if(dk.keyExists("fireworks")){
						DataKey fireworks = dk.getRelative("fireworks");
						for(DataKey dk2 : fireworks.getIntegerSubKeys()){
							plugin.debug("Found another firework.");
							CustomFirework cf = thisAct.new CustomFirework();
							if(dk2.keyExists("power")){
								plugin.debug("Power set to "+dk2.getInt("power"));
								cf.setPower(dk2.getInt("power"));
							}
							if(dk2.keyExists("location")){
								Vector v = new Vector();
								if(dk2.keyExists("location.x")){
									v.setX(dk2.getInt("location.x"));
								}
								if(dk2.keyExists("location.y")){
									v.setY(dk2.getInt("location.y"));
								}
								if(dk2.keyExists("location.z")){
									v.setZ(dk2.getInt("location.z"));
								}
							}
							if(dk2.keyExists("effects")){
								DataKey effects = dk2.getRelative("effects");
								for(DataKey dk3 : effects.getIntegerSubKeys()){
									Builder builder = FireworkEffect.builder();
									if(dk3.keyExists("flicker")){
										builder = builder.flicker(dk3.getBoolean("flicker"));
									}
									if(dk3.keyExists("trail")){
										builder = builder.trail(dk3.getBoolean("trail"));
									}
									if(dk3.keyExists("specialEffect")){
										builder = builder.with(FireworkEffect.Type.valueOf(dk3.getString("specialEffect")));
									}
									if(dk3.keyExists("colors")){
										ArrayList<Color> colors = new ArrayList<Color>();
										String[] colordata = dk3.getString("colors").split(",");
										for(String s : colordata){
											colors.add(Color.fromRGB(Integer.parseInt(s)));
										}
										builder = builder.withColor(colors);
									}else{
										builder = builder.withColor(Color.WHITE);
									}
									if(dk3.keyExists("fadeColors")){
										ArrayList<Color> fadeColors = new ArrayList<Color>();
										String[] fadedata = dk3.getString("fadeColors").split(",");
										for(String s : fadedata){
											fadeColors.add(Color.fromRGB(Integer.parseInt(s)));
										}
										builder = builder.withFade(fadeColors);
									}
									FireworkEffect toAdd = builder.build();
									cf.getEffects().add(toAdd);
								}
							}
						}
					}
				}
				return output;
			}
		}
		return null;
	}
	
	public static ArrayList<String> getAvailableShows(){
		ArrayList<String> output = new ArrayList<String>();
		
		return output;
	}
	
	public static ArrayList<Player> getPlayersInArea(Location loc, Double rangeSquared){
		return Utils.getPlayersInArea(loc, rangeSquared.intValue());
	}
	
	public static ArrayList<Player> getPlayersInArea(Location loc, Integer rangeSquared){
		ArrayList<Player> output = new ArrayList<Player>();
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			if(p.getLocation().distanceSquared(loc)<=rangeSquared){
				output.add(p);
			}
		}
		return output;
	}
	
	public static ArrayList<Player> getPlayersByNPCSelection(Integer npcId){
		ArrayList<Player> output = new ArrayList<Player>();
		Pyrotechnician plugin = (Pyrotechnician) Bukkit.getServer().getPluginManager().getPlugin("Pyrotechnician");
		for(Player p : plugin.getServer().getOnlinePlayers()){
			if(p.hasMetadata("selected")){
				List<MetadataValue> values = p.getMetadata("selected");
				for(MetadataValue v : values){
					if(v.getOwningPlugin().getName().equals("Citizens")){
						if(v.asInt()==npcId){
							output.add(p);
						}
					}
				}
			}
		}
		return output;
	}
	
	public static NPC getSelectedNPC(Player p){
		if(p.hasMetadata("selected")){
			List<MetadataValue> values = p.getMetadata("selected");
			for(MetadataValue v : values){
				if(v.getOwningPlugin().getName().equals("Citizens")){
					int id = v.asInt();
					return CitizensAPI.getNPCRegistry().getById(id);
				}
			}
		}
		return null;
	}
	
	public static ArrayList<String> arrayToList(String[] input){
		ArrayList<String> output = new ArrayList<String>();
		for(String s : input){
			output.add(s);
		}
		return output;
	}
	
	public static String compressList(ArrayList<String> input){
		String output = "";
		int size = input.size();
		for(int i=0; i<size; i++){
			if(i>1){
				output = output+" "+input.get(i);
			}
		}
		return output.trim();
	}
}
