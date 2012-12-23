package com.araeosia.pyrotechnician;

import com.araeosia.pyrotechnician.utils.FireShow;
import com.araeosia.pyrotechnician.utils.Utils;
import java.util.HashMap;

public class ShowHandler {

	private HashMap<String, FireShow> shows = new HashMap<String, FireShow>();

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
}