package com.pianominecraft.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Music {
	
	public static HashMap<String, Music> musics = new HashMap<>();
	
	private HashMap<Player, Integer> playTime = new HashMap<>();
	private HashMap<Player, Boolean> playing = new HashMap<>();
	private HashMap<Player, Boolean> repeating = new HashMap<>();
	
	private HashMap<Integer, List<Note>> notes = new HashMap<>();
	private int length;
	
	public Music(String name, int _length) {
		length = _length;
		musics.put(name, this);
	}
	
	public void add(Integer pos, Note note) {
		if (notes.containsKey(pos)) {
			List<Note> temp = new ArrayList<>(notes.get(pos));
			temp.add(note);
			notes.put(pos, temp);
		} else {
			notes.put(pos, Arrays.asList(note));
		}
	}
	
	public void play(Player p) {
		playTime.put(p, 1);
		playing.put(p, true);
		repeating.put(p, false);
	}
	
	public void playAll() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			playTime.put(p, 1);
			playing.put(p, true);
			repeating.put(p, false);
		}
	}
	
	public void stop(Player p) {
		playing.remove(p);
	}
	
	public void stopAll() {
		for (Player p : playing.keySet()) {
			playing.remove(p);
		}
	}
	
	public void repeatPlay(Player p) {
		playTime.put(p, 1);
		playing.put(p, true);
		repeating.put(p, true);
	}
	
	public void repeatPlayAll() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			playTime.put(p, 1);
			playing.put(p, true);
			repeating.put(p, true);
		}
	}
	
	public void run() {
		for (Player p : playing.keySet()) {
			if (playTime.get(p) > length) {
				if (repeating.get(p)) {
					playTime.put(p, 1);
				} else {
					playing.remove(p);
				}
			}
			if (notes.containsKey(playTime.get(p))) {
				for (Note n : notes.get(playTime.get(p))) {
					p.playSound(p.getLocation(), n.type, (float)n.volume, (float)n.pitch);
				}
			}
			playTime.put(p, playTime.get(p) + 1);
		}
	}
	
}

class Note {
	
	private static HashMap<String, String> noteType = new HashMap<String, String>() {{
		put("bass", "BASS");
		put("bit", "BIT");
		put("hat", "HAT");
		put("basedrum", "BASEDRUM");
		put("bell", "BELL");
		put("flute", "FLUTE");
		put("chime", "CHIME");
		put("cow_bell", "COW_BELL");
		put("didgeridoo", "DIDGERRIDOO");
		put("guitar", "GUITAR");
		put("harp", "HARP");
		put("iron_xylophone", "IRON_XYLOPHONE");
		put("pling", "PLING");
		put("snare", "SNARE");
		put("banjo", "BANJO");
		put("xylophone", "XYLOPHONE");
	}};
	
	public double pitch;
	public double volume;
	public Sound type;
	
	public Note (double _pitch, double _volume, Sound _type) {
		pitch = _pitch;
		volume = _volume;
		type = _type;
	}
	
	public static Sound noteType(String _type) {
		if (noteType.containsKey(_type)) {
			return Sound.valueOf("BLOCK_NOTE_BLOCK_" + noteType.get(_type));
		}
		return null;
	}
}