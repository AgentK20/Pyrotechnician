package com.araeosia.pyrotechnician.utils;

import com.araeosia.pyrotechnician.Pyrotechnician;
import com.araeosia.pyrotechnician.utils.FireShow.FireAct;
import com.araeosia.pyrotechnician.utils.FireShow.FireAct.CustomFirework;
import java.io.File;
import java.util.ArrayList;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.util.YamlStorage;
import net.citizensnpcs.api.util.YamlStorage.YamlKey;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.util.Vector;

public class Utils { // Static utilities class for utility methods.

	public static FireShow loadShow(String key) {
		Pyrotechnician plugin = (Pyrotechnician) Bukkit.getServer().getPluginManager().getPlugin("Pyrotechnician");
		File loc = new File(plugin.getDataFolder() + File.pathSeparator + key + ".yml");
		YamlStorage storage = new YamlStorage(loc);
		if (storage.getKey(key) != null) {
			YamlKey data = storage.getKey(key); // This one FireShow.
			if(data.keyExists("name")){
				FireShow output = new FireShow(data.getString("name"));
				if(data.keyExists("announce-5")){
					output.getAnnouncements().put(5, data.getString("announce-5"));
				}
				if(data.keyExists("announce-1")){
					output.getAnnouncements().put(2, data.getString("announce-1"));
				}
				if(data.keyExists("announce-lt1")){
					output.getAnnouncements().put(1, data.getString("announce-lt1"));
				}
				if(data.keyExists("announce")){
					output.getAnnouncements().put(0, data.getString("announce"));
				}
				YamlKey acts = data.getRelative("acts");
				for(DataKey dk : acts.getIntegerSubKeys()){
					
					FireAct thisAct = output.new FireAct();
					if(dk.keyExists("delay")){
						thisAct.setDelay(dk.getInt("delay"));
					}
					if(dk.keyExists("message")){
						thisAct.setMessage(dk.getString("message"));
					}
					if(dk.keyExists("fireworks")){
						DataKey fireworks = dk.getRelative("fireworks");
						for(DataKey dk2 : fireworks.getIntegerSubKeys()){
							CustomFirework cf = thisAct.new CustomFirework();
							if(dk2.keyExists("power")){
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
}
