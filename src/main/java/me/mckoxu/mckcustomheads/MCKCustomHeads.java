package me.mckoxu.mckcustomheads;

import me.mckoxu.mckcustomheads.commands.CustomHead;
import me.mckoxu.mckcustomheads.listeners.InventoryClickListener;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MCKCustomHeads extends JavaPlugin{

	private static MCKCustomHeads instance;
	
	public void onEnable(){
		PluginDescriptionFile d = this.getDescription();
		if(getConfig().getBoolean("update.check")) {
			if(!d.getVersion().equalsIgnoreCase(getLatestVersion("https://raw.githubusercontent.com/McKoxu/MCKCustomHeads/master/VERSION"))) {
				Bukkit.getLogger().info("--------------------------------");
				Bukkit.getLogger().info("[MCKTools]> Plugin Version: "+this.getDescription().getVersion());
				Bukkit.getLogger().info("[MCKTools]> Newest Plugin Version: "+getLatestVersion("https://raw.githubusercontent.com/McKoxu/MCKCustomHeads/master/VERSION"));
				Bukkit.getLogger().info("--------------------------------");
			}
		}
		if(!d.getAuthors().get(0).equals("McKoxu") || !d.getName().equals("MCKCustomHeads")) {
			Bukkit.getLogger().info("--------------------------------");
			Bukkit.getLogger().info("[MCKCustomHeads]> Bad plugin.yml");
			Bukkit.getLogger().info("[MCKCustomHeads> The plugin will be turned off");
			Bukkit.getLogger().info("--------------------------------");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		instance = this;
		getCommand("customhead").setExecutor(new CustomHead());
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
		saveDefaultConfig();
	}
	
	public static MCKCustomHeads getInst(){
		return instance;
	}

	public static String getLatestVersion(String link){
		InputStream in = null;
		try {
			in = new URL(link).openStream();
		} catch (Exception e) {
			Bukkit.getLogger().info("Unable to check for updates!");
			e.printStackTrace();
		}

		try {
			return IOUtils.readLines(in).get(0);
		} catch (IOException e) {
			Bukkit.getLogger().info("Unable to determine update!");
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return null;
	}
    
}
