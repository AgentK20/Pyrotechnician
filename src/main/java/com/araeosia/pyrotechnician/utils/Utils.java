package com.araeosia.pyrotechnician.utils;

import com.araeosia.pyrotechnician.Pyrotechnician;
import java.io.File;
import java.util.HashMap;
import net.citizensnpcs.api.util.YamlStorage;
import org.bukkit.Bukkit;

public class Utils { // Static utilities class for utility methods.
	public static FireShow loadShow(String key) {
		Pyrotechnician plugin = (Pyrotechnician) Bukkit.getServer().getPluginManager().getPlugin("Pyrotechnician");
		File loc = new File(plugin.getDataFolder()+File.pathSeparator+key+".yml");
		YamlStorage storage = new YamlStorage(loc);
		if(storage.getKey(key)!=null){
			YamlStorage.YamlKey data = storage.getKey(key);
			// Okay, the show exists. Lets keep going.
			FireShow output = new FireShow(data.getString("name"));
			HashMap<Integer, FireShow.FireAct> acts = output.getFireActs(); 
			
			return output;
		}
		return null;
	}
}
