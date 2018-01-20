package moe.satori.BakaAPI.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Players {
	public static HashMap<String, Object> getOnline(HashMap<String, String> params) {
		ArrayList<String> playerlist = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		Bukkit.getOnlinePlayers().forEach((player) -> {
			playerlist.add(player.getName());
		});
		map.put("status", 200);
		map.put("online", playerlist);
		return map;
	}

	public static HashMap<String, Object> addWhitelist(HashMap<String, String> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String playerName = params.get("username");
		Player player = Bukkit.getPlayer(playerName);
		player.setWhitelisted(true);
		result.put("status", 200);
		result.put("message", "ok");
		return result;
	}

	public static HashMap<String, Object> removeWhitelist(HashMap<String, String> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String playerName = params.get("username");
		Player player = Bukkit.getPlayer(playerName);
		player.setWhitelisted(false);
		result.put("status", 200);
		result.put("message", "ok");
		return result;
	}

	public static HashMap<String, Object> sendMessage(HashMap<String, String> params) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String playerName = params.get("username");
		String content = params.get("content");
		Player player = Bukkit.getPlayer(playerName);
		player.sendMessage(content);
		result.put("status", 200);
		result.put("message", "ok");
		return result;
	}

	public static HashMap<String, Object> getInfo(HashMap<String, String> params) {
		HashMap<String, Object> result = new HashMap<>();
		HashMap<String, Object> location = new HashMap<>();
		String playerName = params.get("username");
		Player player = Bukkit.getPlayer(playerName);
		location.put("x", player.getLocation().getX());
		location.put("y", player.getLocation().getY());
		location.put("z", player.getLocation().getZ());
		result.put("health", player.getHealth());
		result.put("location", location);
		return result;
	}

	public static HashMap<String, Object> getPlayerInventory(HashMap<String, String> params) {
		HashMap<String, Object> result = new HashMap<>();
		ArrayList<Object> itemlist = new ArrayList<>();
		Player player = Bukkit.getPlayer(params.get("username"));
		for (ItemStack item : player.getInventory().getContents()) {
			if (item != null && item.getType() != Material.AIR) {
				HashMap<Object, Object> temp_item = new HashMap<Object, Object>();
				int amount = item.getAmount();
				String name = item.getI18NDisplayName();
				temp_item.put("name", name);
				temp_item.put("count", amount);
				temp_item.put("id", item.getType().getId());
				itemlist.add(temp_item);
			}
		}
		result.put("status", 200);
		result.put("item", itemlist);
		return result;
	}
}
