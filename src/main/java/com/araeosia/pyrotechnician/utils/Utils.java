package com.araeosia.pyrotechnician.utils;

import com.araeosia.pyrotechnician.Pyrotechnician;
import com.araeosia.pyrotechnician.utils.FireShow.FireAct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.util.YamlStorage;
import net.citizensnpcs.api.util.YamlStorage.YamlKey;
import org.bukkit.Bukkit;
import org.bukkit.entity.Firework;

public class Utils { // Static utilities class for utility methods.

	public static FireShow loadShow(String key) {
		Pyrotechnician plugin = (Pyrotechnician) Bukkit.getServer().getPluginManager().getPlugin("Pyrotechnician");
		File loc = new File(plugin.getDataFolder() + File.pathSeparator + key + ".yml");
		YamlStorage storage = new YamlStorage(loc);
		if (storage.getKey(key) != null) {
			YamlKey data = storage.getKey(key);
			// Okay, the show exists. Lets keep going.
			FireShow output = new FireShow(data.getString("name"));
			HashMap<Integer, FireAct> acts = output.getFireActs();
			YamlKey actKeys = data.getRelative("acts");
			for(DataKey dk : actKeys.getIntegerSubKeys()){
				FireAct toAdd = new FireAct(1);
				if(dk.keyExists("delay")){
					toAdd.setDelay(dk.getInt("delay"));
				}
				YamlKey fireworkKeys = dk.getRelative("fireworks");
				ArrayList<Firework> fireworks = new ArrayList<Firework>();
				for(DataKey fw : fireworkKeys.getIntegerSubKeys()){
					Firework e = new Firework();
					fireworks.add(e);
				}
			}
			return output;
		}
		return null;
	}
}
