package moe.satori.BakaAPI.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Players {
	public static HashMap getOnline(HashMap<String, String> params) {
		ArrayList<String> playerlist = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		
		Bukkit.getOnlinePlayers().forEach((player)->{
			playerlist.add(player.getName());
		});
		map.put("status", 200);
		map.put("online", playerlist);
		return map;
	}
}
