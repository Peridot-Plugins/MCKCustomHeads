package me.mckoxu.mckcustomheads.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mckoxu.mckcustomheads.MCKCustomHeads;
import me.mckoxu.mckcustomheads.methods.HeadsCreating;
import me.mckoxu.mckcustomheads.objects.Heads;

public class CustomHead implements CommandExecutor, Listener{
	
	public static ItemStack customp = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);{
		ItemMeta im = customp.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a♦ Click here to get custom player head!");
		im.setLore(lore);
		im.setDisplayName("§b§lCustom Players Skull");
		customp.setItemMeta(im);
	}
	public static ItemStack customs = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);{
		ItemMeta im = HeadsCreating.getHead("decorations-monitor").getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§c✘ This Command must be used on the chat !");
		lore.add("§c✘ This Command is for professionals !");
		lore.add("§a♦ Command: /ch give §f(texture)");
		im.setLore(lore);
		im.setDisplayName("§b§lCustom Skulls");
		customs.setItemMeta(im);
	} 
	public static ItemStack skullistitem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);{
		ItemMeta im = HeadsCreating.getHead("food-bread").getItemMeta();
		int amount = 0;
		for (Heads head : Heads.values()){
			amount++;
		}
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a♦ Click here to open skulls list");
		im.setLore(lore);
		im.setDisplayName("§3§lSkulls List - " + amount);
		skullistitem.setItemMeta(im);
	}
	
	public static Inventory maininv;
	public CustomHead(){
		maininv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("menuname")));
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
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefixtextcolor")) + "Correct Usage: /ch give [texture]");
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefixtextcolor")) + "Texture: {textures:[{Value:eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv§f§lOGQxOWM2ODQ2MTY2NmFhY2Q3NjI4ZTM0YTFlMmFkMzlmZTRmMmJkZTMyZTIzMTk2M2VmM2IzNTUzMyJ9fX0="+ ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefixtextcolor")) +"}]}");
								}
							}else{
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("noperm")));
							}
						} else{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefixtextcolor")) + "Correct Usage: /ch [give] [texture]");
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("prefixtextcolor")) + "[ ] - not required");
						}
					} else{
						p.openInventory(inv);
						for (int i = 0; i < 27; i++) {
							inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
						}
						inv.setItem(13, skullistitem);
						inv.setItem(11, customs);
						inv.setItem(15, customp); 
					}
				} else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("noperm")));
				}
			} else{
				s.sendMessage("[MCKCustomHeads] This command can be used only by players !");
			}
		}
		return false;
	}
	
	
	
}
