package com.araeosia.pyrotechnician;

import com.araeosia.pyrotechnician.utils.FireShow;
import com.araeosia.pyrotechnician.utils.Utils;
import java.io.File;
import java.util.HashMap;

public class ShowHandler {

	private Pyrotechnician plugin;
	private HashMap<String, FireShow> shows = new HashMap<String, FireShow>();

	public ShowHandler(Pyrotechnician plugin) {
		this.plugin = plugin;
	}

	public HashMap<String, FireShow> getShows() {
		return shows;
	}

	public FireShow getShow(String key) {
		if (shows.containsKey(key)) {
			return shows.get(key);
		} else {
			return Utils.loadShow(key);
		}
	}
	
	public void loadShows(){
		File directory = plugin.getDataFolder();
		String loc = directory.getAbsolutePath()+File.separator+"shows";
		File showStorage = new File(loc);
		if(!showStorage.exists()){
			showStorage.mkdir();
		}
		for(File f : showStorage.listFiles()){
			if(f.getName().endsWith(".yml")){
				String good = f.getName().substring(0, f.getName().length()-4);
				FireShow fs = Utils.loadShow(good);
				if(fs!=null){
					shows.put(good, fs);
				}
			}else if(f.getName().endsWith(".show")){
				String good = f.getName().substring(0, f.getName().length()-5);
				FireShow fs = Utils.loadShow(good);
				if(fs!=null){
					shows.put(good, fs);
				}
			}
		}
	}
}