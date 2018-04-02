package me.mckoxu.mckcustomheads;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.cloutteam.samjakob.gui.types.PaginatedGUI;

import me.mckoxu.mckcustomheads.commands.CustomHead;
import me.mckoxu.mckcustomheads.listeners.InventoryClickListener;

public class MCKCustomHeads extends JavaPlugin{

	private static MCKCustomHeads instance;
	
	public void onEnable(){
		instance = this;
		getCommand("customhead").setExecutor(new CustomHead());
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getPluginManager().registerEvents(new CustomHead(), this);
		PaginatedGUI.prepare(this);
		saveDefaultConfig();
	}
	
	public static MCKCustomHeads getInst(){
		return instance;
	}
    
}
