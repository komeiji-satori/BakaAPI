package moe.satori.BakaAPI;

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
		// Test.getResult(params);
	}

	@Override
	public void onDisable() {

	}
}
