package com.araeosia.pyrotechnician.utils;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.FireworkEffect;
import org.bukkit.util.Vector;

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
		private ArrayList<CustomFirework> fireworks = new ArrayList<CustomFirework>();
		private String message;

		public FireAct() {
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
		
		public void setMessage(String m){
			this.message = m;
		}
		
		public String getMessage(){
			return message;
		}

		public ArrayList<CustomFirework> getFireworks() {
			return fireworks;
		}
		
		public class CustomFirework {
			private Vector location;
			private ArrayList<FireworkEffect> effects = new ArrayList<FireworkEffect>();
			private int power;
			
			public Vector getLocation(){
				return location;
			}
			public void setLocation(Vector v){
				this.location = v;
			}
			public ArrayList<FireworkEffect> getEffects(){
				return effects;
			}
			public void setPower(int i){
				this.power = i;
			}
			public int getPower(){
				return power;
			}
		}
	}
}