package moe.satori.BakaAPI;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import fi.iki.elonen.NanoHTTPD;
import moe.satori.BakaAPI.Utils;

public class App extends NanoHTTPD {
	Plugin plugin;

	public App(Plugin plugin, int Port) {
		super(Port);
		System.out.println("BakaAPI Port: " + Port);
		this.plugin = plugin;
	}

	public void startService() {
		Runnable rbq = new Runnable() {
			public void run() {
				try {
					System.out.println("BakaAPI Service Running..");
					App.super.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
				} catch (IOException ioe) {
					System.err.println("Couldn't start server:\n" + ioe);
				}
			}
		};
		Bukkit.getScheduler().runTaskAsynchronously(plugin, rbq);
	}

	@Override
	public Response serve(IHTTPSession session) {
		Map<String, String> parms = session.getParms();
		try {
			HashMap<String, Object> result = Utils.invokeController(parms.get("action"), parms.get("method"), parms);
			String msg = Utils.toJSON(result);
			return newFixedLengthResponse(msg);
		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
