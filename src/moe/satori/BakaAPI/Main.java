package moe.satori.BakaAPI;

import org.bukkit.plugin.java.JavaPlugin;

import moe.satori.BakaAPI.App;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		int port = this.getConfig().getInt("port");
		Boolean auth = this.getConfig().getBoolean("auth");
		String password = this.getConfig().getString("password");
		App app = new App(this, port, password, auth);
		app.startService();
	}

	public static void main(String[] args) {

	}

	@Override
	public void onDisable() {

	}
}
