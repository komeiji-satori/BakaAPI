package moe.satori.BakaAPI.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Players {
	public static HashMap getOnline(HashMap<String, String> params) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("status", 200);
		map.put("online", _getOnline());
		return map;
	}
	private static ArrayList<String> _getOnline(){
		ArrayList<String> playerlist = new ArrayList<>();
		Bukkit.getOnlinePlayers().forEach((player)->{
			playerlist.add(player.getName());
		});
		return playerlist;
	}
}
