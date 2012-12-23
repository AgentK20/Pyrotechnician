package com.araeosia.pyrotechnician.utils;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.FireworkEffect;

public class FireShow {

	private String name;
	private HashMap<Integer, String> announcements = new HashMap<Integer, String>();
	private HashMap<Integer, FireAct> fireacts = new HashMap<Integer, FireAct>();

	public FireShow(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getAnnouncement(Integer i) {
		if (announcements.containsKey(i)) {
			return announcements.get(i);
		}
		return "";
	}

	public FireAct getAct(Integer i) {
		if (fireacts.containsKey(i)) {
			return fireacts.get(i);
		}
		return null;
	}

	public HashMap<Integer, FireAct> getFireActs() {
		return fireacts;
	}

	public HashMap<Integer, String> getAnnouncements() {
		return announcements;
	}

	public class FireAct {

		private Integer id;
		private Integer delay = 0;
		private ArrayList<FireworkEffect> fireworks = new ArrayList<FireworkEffect>();

		public FireAct(Integer id) {
			this.id = id;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer i) {
			this.id = i;
		}

		public Integer getDelay() {
			return delay;
		}

		public void setDelay(Integer i) {
			this.delay = i;
		}

		public ArrayList<FireworkEffect> getFireworks() {
			return fireworks;
		}
	}
}