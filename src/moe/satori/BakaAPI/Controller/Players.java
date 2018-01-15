package moe.satori.BakaAPI.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Players {
	public static HashMap getOnline(HashMap<String, String> params) {
		ArrayList<String> playerlist = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		Bukkit.getOnlinePlayers().forEach((player) -> {
			playerlist.add(player.getName());
		});
		map.put("status", 200);
		map.put("online", playerlist);
		return map;
	}

	public static HashMap getPlayerInventory(HashMap<String, String> params) {
		HashMap<String, Object> result = new HashMap<>();
		ArrayList<Object> itemlist = new ArrayList<>();
		Player player = Bukkit.getPlayer(params.get("username"));
		for (ItemStack item : player.getInventory().getContents()) {
			if (item != null && item.getType() != Material.AIR) {
				HashMap<Object, Object> temp_item = new HashMap();
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
