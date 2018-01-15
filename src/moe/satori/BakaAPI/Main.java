package moe.satori.BakaAPI;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import moe.satori.BakaAPI.App;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		int port = this.getConfig().getInt("port");
		App app = new App(this, port);
		app.startService();
	}

	public static void main(String[] args) {
//		HashMap<String, Object> map = new HashMap<>();
//		ArrayList<String> playerlist = new ArrayList<>();
//		playerlist.add("23333");
//		map.put("owo", "owo");
//		map.put("ovo", playerlist);
//		System.out.println(Utils.toJSON(map));
		// Test.getResult(params);
	}

	@Override
	public void onDisable() {

	}
}
