package me.mckoxu.mckcustomheads.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mckoxu.mckcustomheads.MCKCustomHeads;
import me.mckoxu.mckcustomheads.enums.Heads;
import me.mckoxu.mckcustomheads.enums.XMaterial;
import me.mckoxu.mckcustomheads.methods.HeadsCreating;

@SuppressWarnings("unused")
public class CustomHead implements CommandExecutor, Listener{
	
	public static ItemStack customp = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);{
		ItemMeta im = customp.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		for (String s : MCKCustomHeads.getInst().getConfig().getStringList("mainpanel.items.customplayerskull.lore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		im.setLore(lore);
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("mainpanel.items.customplayerskull.name")));
		customp.setItemMeta(im);
	}
	public static ItemStack customs = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);{
		ItemMeta im = HeadsCreating.getHead("decorations-monitor").getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		for (String s : MCKCustomHeads.getInst().getConfig().getStringList("mainpanel.items.customskull.lore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		im.setLore(lore);
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("mainpanel.items.customskull.name")));
		customs.setItemMeta(im);
	} 
	public static ItemStack skullistitem = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);{
		ItemMeta im = HeadsCreating.getHead("food-bread").getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		int amount = 0;
		int csa = 0;
		for (Heads head : Heads.values()){
			amount++;
		}
		ConfigurationSection csl = MCKCustomHeads.getInst().getConfig().getConfigurationSection("heads");
		for(String s : csl.getKeys(false)){
			csa++;
		}
		for (String s : MCKCustomHeads.getInst().getConfig().getStringList("mainpanel.items.skullist.lore")) {
			lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("{AMOUNT}", String.valueOf(amount+csa))));
		}
		im.setLore(lore);
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("mainpanel.items.skullist.name")).replace("{AMOUNT}", String.valueOf(amount+csa)));
		skullistitem.setItemMeta(im);
	}
	
	public static Inventory maininv;
	public CustomHead(){
		maininv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("mainpanel.name")));
	}
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("customhead")){
			if(s instanceof Player){
				Inventory inv = maininv;
				Player p = (Player) s;
				if(p.hasPermission("mck.headsmenu")){
					if(args.length >= 1){
						if(args[0].equalsIgnoreCase("give")){
							if(p.hasPermission("mck.headgive")){					
								if(args.length >= 2){
									String msg = "";
									for(int i = 1; i<args.length; i++){
										msg += " " + args[i];
									}
									p.getInventory().addItem(HeadsCreating.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv"+msg));
								} else{
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("messages.correctusage")));
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("messages.texture")));
								}
							}else{
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("error.noperm")));
							}
						} else{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("messages.correctusage")));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("messages.notrequired")));
						}
					} else{
						p.openInventory(inv);
						for (int i = 0; i < 27; i++) {
							inv.setItem(i, new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short) 15));
						}
						inv.setItem(13, skullistitem);
						inv.setItem(11, customs);
						inv.setItem(15, customp); 
					}
				} else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("messages.noperm")));
				}
			} else{
				s.sendMessage("[MCKCustomHeads] This command can be used only by players !");
			}
		}
		return false;
	}
	
	
	
}
