package moe.satori.BakaAPI.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Test {
	public static HashMap getResult(HashMap<String, String> params) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("status", 200);
		map.put("online", Bukkit.getOnlinePlayers());
		return map;
	}
}
