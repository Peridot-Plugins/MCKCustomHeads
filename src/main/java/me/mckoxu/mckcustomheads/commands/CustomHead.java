package me.mckoxu.mckcustomheads.commands;

import me.mckoxu.mckcustomheads.MCKCustomHeads;
import me.mckoxu.mckcustomheads.objects.ItemBuilder;
import me.mckoxu.mckcustomheads.utils.HeadsCreating;
import me.mckoxu.mckcustomheads.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class CustomHead implements CommandExecutor{

	public static ItemStack customp = new ItemBuilder("PLAYER_HEAD")
			.setName(Utils.color(MCKCustomHeads.getInst().getConfig().getString("mainpanel.items.customplayerskull.name")))
			.setLore(Utils.color(MCKCustomHeads.getInst().getConfig().getStringList("mainpanel.items.customplayerskull.lore")))
			.toItemStack();

	public static ItemStack customs = new ItemBuilder("PLAYER_HEAD")
			.setItemMeta(HeadsCreating.getHead("decorations-monitor").getItemMeta())
			.setName(Utils.color(MCKCustomHeads.getInst().getConfig().getString("mainpanel.items.customskull.name")))
			.setLore(Utils.color(MCKCustomHeads.getInst().getConfig().getStringList("mainpanel.items.customskull.lore")))
			.toItemStack();

	public static ItemStack skullistitem = new ItemBuilder("PLAYER_HEAD")
			.setItemMeta(HeadsCreating.getHead("food-bread").getItemMeta())
			.setName(Utils.color(Utils.replaceString(MCKCustomHeads.getInst().getConfig().getString("mainpanel.items.skullist.name"))))
			.setLore(Utils.color(Utils.replaceList(MCKCustomHeads.getInst().getConfig().getStringList("mainpanel.items.skullist.lore"))))
			.toItemStack();
	
	public static Inventory maininv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("mainpanel.name")));

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("customhead")){
			if(s instanceof Player){
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
						p.openInventory(maininv);
						for (int i = 0; i < 27; i++) {
							maininv.setItem(i, new ItemBuilder("BLACK_STAINED_GLASS_PANE").toItemStack());
						}
						maininv.setItem(13, skullistitem);
						maininv.setItem(11, customs);
						maininv.setItem(15, customp);
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
