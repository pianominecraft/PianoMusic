package com.pianominecraft.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	@Override
	public void onEnable() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for (String s : Music.musics.keySet()) {
					Music.musics.get(s).run();
				}
			}
			
		}, 1L, 1L);
		getCommand("pianomusic").setTabCompleter(new TabComplete());
		getCommand("pm").setTabCompleter(new TabComplete());
		if (!new File("plugins/PianoMusic").exists()) {
			new File("plugins/PianoMusic").mkdir();
			try {
				PrintWriter pw = new PrintWriter(new File("plugins/PianoMusic/Usage.txt"));
				pw.println("Usage of this plugin (Tutorial)");
				pw.println("In this plugin, all time concepts are counted in ticks (0.05s).");
				pw.println("First, write the length of the music on the first line.");
				pw.println("Then, from line 2, write in the form of [position] [instrument] [volume] [pitch].");
				pw.println("position : the position of note. For example, if the music is played 10 ticks after the start, it will be written as 10.");
				pw.println("instrument : the instrument of note. If you want to play bass, type \"bass\".");
				pw.println("volume : the volume of note.");
				pw.println("pitch : the pitch of note.");
				pw.println();
				pw.println("Example ----------");
				pw.println("16");
				pw.println("1 harp 1 0.707107");
				pw.println("3 harp 1 0.793701");
				pw.println("5 harp 1 0.890899");
				pw.println("7 harp 1 0.943874");
				pw.println("9 harp 1 1.059463");
				pw.println("11 harp 1 1.189207");
				pw.println("13 harp 1 1.334840");
				pw.println("15 harp 1 1.414214");
				pw.println("-------------------");
				pw.println();
				pw.println("pitchs ----------");
				pw.println("F# (1) : 0.5");
				pw.println("G (1) :  0.529732");
				pw.println("G# (1) : 0.561231");
				pw.println("A (1) :  0.594604");
				pw.println("A# (1) : 0.629961");
				pw.println("B (1) :  0.667420");
				pw.println("C (1) :  0.707107");
				pw.println("C# (1) : 0.749154");
				pw.println("D (1) :  0.793701");
				pw.println("D# (1) : 0.840896");
				pw.println("E (1) :  0.890899");
				pw.println("F (1) :  0.943874");
				pw.println("F# (2) : 1");
				pw.println("G (2) :  1.059463");
				pw.println("G# (2) : 1.122462");
				pw.println("A (2) :  1.189207");
				pw.println("A# (2) : 1.259921");
				pw.println("B (2) :  1.334840");
				pw.println("C (2) :  1.414214");
				pw.println("C# (2) : 1.498307");
				pw.println("D (2) :  1.587401");
				pw.println("D# (2) : 1.681793");
				pw.println("E (2) :  1.781797");
				pw.println("F (2) :  1.887749");
				pw.println("F# (3) : 2");
				pw.println("-----------------");
				pw.println();
				pw.println("Instruments --");
				pw.println("bass");
				pw.println("bit");
				pw.println("hat");
				pw.println("basedrum");
				pw.println("bell");
				pw.println("flute");
				pw.println("chime");
				pw.println("cow_bell");
				pw.println("didgeridoo");
				pw.println("guitar");
				pw.println("harp");
				pw.println("iron_xylophone");
				pw.println("pling");
				pw.println("snare");
				pw.println("banjo");
				pw.println("xylophone");
				pw.println("--------------");
				pw.println();
				pw.println("Commands ----------------------------------");
				pw.println("/pw reload [File name(Excluding extension)]");
				pw.println("    reload the music");
				pw.println("/pw reloadAll");
				pw.println("    reload all musics");
				pw.println("/pw play [Music name] [Player name]");
				pw.println("    play music to the player");
				pw.println("/pw playAll [Music name]");
				pw.println("    play music to all players");
				pw.println("/pw repeatPlay [Music name] [Player name]");
				pw.println("    play the music to the player repeatedly");
				pw.println("/pw repeatPlayAll [Music name]");
				pw.println("    play the music to all players repeatedly");
				pw.println("/pw stop [Music name] [Player name]");
				pw.println("    stop playing music to the player");
				pw.println("/pw stopAll [Music name]");
				pw.println("    stop playing music to all players");
				pw.println("/pw list");
				pw.println("    show list of all musics");
				pw.println("-------------------------------------------");
				pw.close();
			} catch (FileNotFoundException e) {
			}
		}
		reloadAll(Bukkit.getConsoleSender());
		Bukkit.getLogger().info("[PianoMusic] has been enabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		
		if (s.isOp()) {
			if (c.getName().equalsIgnoreCase("PianoMusic") || c.getName().equalsIgnoreCase("pm")) {
				if (a.length > 0) {
					if (a[0].equalsIgnoreCase("reload")) {
						if (a.length > 1) {
							reload(a[1], s);
						}
					} else if (a[0].equalsIgnoreCase("reloadAll")) {
						reloadAll(s);
					} else if (a[0].equalsIgnoreCase("play")) {
						if (a.length > 2) {
							if (Music.musics.containsKey(a[1])) {
								Music m = Music.musics.get(a[1]);
								if (Bukkit.getOfflinePlayer(a[2]).isOnline()) {
									Player p = Bukkit.getPlayer(a[2]);
									m.play(p);
								}
							}
						}
					} else if (a[0].equalsIgnoreCase("playAll")) {
						if (a.length > 1) {
							if (Music.musics.containsKey(a[1])) {
								Music m = Music.musics.get(a[1]);
								m.playAll();
							}
						}
					} else if (a[0].equalsIgnoreCase("repeatPlay")) {
						if (a.length > 2) {
							if (Music.musics.containsKey(a[1])) {
								Music m = Music.musics.get(a[1]);
								if (Bukkit.getOfflinePlayer(a[2]).isOnline()) {
									Player p = Bukkit.getPlayer(a[2]);
									m.repeatPlay(p);
								}
							}
						}
					} else if (a[0].equalsIgnoreCase("repeatPlayAll")) {
						if (a.length > 1) {
							if (Music.musics.containsKey(a[1])) {
								Music m = Music.musics.get(a[1]);
								m.repeatPlayAll();
							}
						}
					} else if (a[0].equalsIgnoreCase("stop")) {
						if (a.length > 2) {
							if (Music.musics.containsKey(a[1])) {
								Music m = Music.musics.get(a[1]);
								if (Bukkit.getOfflinePlayer(a[2]).isOnline()) {
									Player p = Bukkit.getPlayer(a[2]);
									m.stop(p);
								}
							}
						}
					} else if (a[0].equalsIgnoreCase("stopAll")) {
						if (a.length > 1) {
							if (Music.musics.containsKey(a[1])) {
								Music m = Music.musics.get(a[1]);
								m.stopAll();
							}
						}
					} else if (a[0].equalsIgnoreCase("list")) {
						s.sendMessage(ChatColor.GREEN + "Music List");
						int i = 1;
						for (String str : Music.musics.keySet()) {
							s.sendMessage(ChatColor.GREEN + "" + i++ + ". " + str);
						}
						s.sendMessage(ChatColor.GREEN + "Found " + --i + " Musics");
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean reload(String fileName, CommandSender s) {
		return reload(new File("plugins/PianoMusic/" + fileName + ".pm"), s);
	}
	
	public boolean reload(File file, CommandSender s) {
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				List<String> lines = new ArrayList<>();
				String l = null;
				while ((l = br.readLine()) != null) {
					lines.add(l);
				}
				int length = Integer.parseInt(lines.get(0));
				int dot = file.getName().lastIndexOf(".");
				String name = file.getName().substring(0, dot);
				Music m = new Music(name, length);
				for (int i = 1; i < lines.size(); i++) {
					String[] line = lines.get(i).split(" ");
					int pos = Integer.parseInt(line[0]);
					Sound type = Note.noteType(line[1]);
					double volume = Double.parseDouble(line[2]);
					double pitch = Double.parseDouble(line[3]);
					m.add(pos, new Note(pitch, volume, type));
				}
				s.sendMessage(ChatColor.GREEN + "Reload Success : loaded \"" + file.getName() + "\"");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				s.sendMessage(ChatColor.RED + "Reload Fail : Thrown an Exception");
				return false;
			}
		} else {
			s.sendMessage(ChatColor.RED + "Reload Fail : Invalied File Name");
			return false;
		}
	}
	
	public void reloadAll(CommandSender s) {
		for (File file : new File("plugins/PianoMusic").listFiles()) {
			if (file.getName().contains(".pm")) {
				reload(file, s);
			}
		}
	}
	
}
