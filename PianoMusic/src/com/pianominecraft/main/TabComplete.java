package com.pianominecraft.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a) {
		
		if (s.isOp()) {
			if (c.getName().equalsIgnoreCase("PianoMusic") || c.getName().equalsIgnoreCase("pw")) {
				if (a.length == 1) {
					return Arrays.asList("reload", "reloadAll", "play", "playAll", "repeatPlay", "repeatPlayAll", "stop", "stopAll", "list");
				} else if (a.length == 2) {
					switch (a[0]) {
					case "play":
					case "playAll":
					case "repeatPlay":
					case "repeatPlayAll":
					case "stop":
					case "stopAll":
						return new ArrayList<String>(Music.musics.keySet());
					}
				} else if (a.length == 3) {
					switch (a[0]) {
					case "play":
					case "repeatPlay":
					case "stop":
						List<String> value = new ArrayList<>();
						for (Player p : Bukkit.getOnlinePlayers()) {
							value.add(p.getName());
						}
						return value;
					}
				}
			}
		}
		
		return null;
	}
	
}
