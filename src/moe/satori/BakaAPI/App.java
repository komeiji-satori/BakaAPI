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
	String password;
	Boolean auth;

	public App(Plugin plugin, int Port, String password, Boolean auth) {
		super(Port);
		System.out.println("BakaAPI Port: " + Port);
		System.out.println("Use Authorize: " + auth.toString());
		this.plugin = plugin;
		this.password = password;
		this.auth = auth;
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
		Map<String, String> headers = session.getHeaders();
		Method method = session.getMethod();
		if (Method.POST.equals(method)) {
			try {
				session.parseBody(parms);
			} catch (IOException | ResponseException e) {
				e.printStackTrace();
			}
		}
		String response = "";
		HashMap<String, Object> map = new HashMap<>();
		try {
			if (!headers.containsKey("x-authorizetoken") && this.auth == true) {
				map.put("status", 401);
				map.put("msg", "Empty Token");
				response = Utils.toJSON(map);
			} else {
				if (Utils.checkToken((HashMap<String, String>) parms, this.password,
						(HashMap<String, String>) headers)) {
					HashMap<String, Object> result = Utils.invokeController(parms.get("action"), parms.get("method"),
							parms);
					response = Utils.toJSON(result);
				} else {
					map.put("status", 401);
					map.put("msg", "Token Verify Fail");
					response = Utils.toJSON(map);
				}
			}
			return newFixedLengthResponse(Response.Status.OK, "application/json", response);

		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
