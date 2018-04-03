package me.mckoxu.mckcustomheads.listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.cloutteam.samjakob.gui.ItemBuilder;
import com.cloutteam.samjakob.gui.buttons.GUIButton;
import com.cloutteam.samjakob.gui.types.PaginatedGUI;

import me.mckoxu.mckcustomheads.MCKCustomHeads;
import me.mckoxu.mckcustomheads.commands.CustomHead;
import me.mckoxu.mckcustomheads.methods.HeadsCreating;
import me.mckoxu.mckcustomheads.objects.Heads;
import net.wesjd.anvilgui.AnvilGUI;

public class InventoryClickListener implements Listener{
	
	FileConfiguration config = MCKCustomHeads.getInst().getConfig();
	PaginatedGUI skullist = new PaginatedGUI(ChatColor.translateAlternateColorCodes('&', config.getString("skullistpanel.name")));
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Inventory i = e.getInventory();
		if(i.equals(CustomHead.maininv)){
			Player p = (Player) e.getWhoClicked();
			ItemStack is = e.getCurrentItem();
			if(is.equals(CustomHead.customp)){
				new AnvilGUI(MCKCustomHeads.getInst(), p, config.getString("customplayerhead.maintext"), (player, reply) -> {
				    if (reply != null) {
				        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(short)3);
                        SkullMeta im = (SkullMeta)skull.getItemMeta();
                        im.setOwner(reply);
                        skull.setItemMeta(im);
						player.getInventory().addItem(skull);
				        return null;
				    }
				    return "Incorrect.";
				});
			} else if(is.equals(CustomHead.skullistitem)){
				ArrayList<String> lore = new ArrayList<String>();
				for (String s : config.getStringList("skullistpanel.items.back.lore")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', s));
				}
				int amount = 0;
				for (Heads head : Heads.values()){
					GUIButton skull = new GUIButton(
							ItemBuilder.start(Material.SKULL_ITEM).name("Head").data((short)3).itemmeta(head.getItemStack().getItemMeta()).build()
					);
					GUIButton back = new GUIButton(
							ItemBuilder.start(Material.EMPTY_MAP).name(config.getString(ChatColor.translateAlternateColorCodes('&', "skullistpanel.items.back.name"))).lore(lore).build()
					);
					skull.setListener(event -> {
						if(event.getCurrentItem().getType().equals(Material.SKULL_ITEM)){
							 p.getInventory().addItem(event.getCurrentItem());
							 event.setCancelled(true);
						}
					});
					back.setListener(event -> {
					    p.openInventory(CustomHead.maininv);
					    event.setCancelled(true);
					});
					skullist.setToolbarItem(0, back);
					skullist.setButton(amount, skull);
					amount++;
				}
				ConfigurationSection csl = MCKCustomHeads.getInst().getConfig().getConfigurationSection("heads");
				int csa = 0;
				for(String s : csl.getKeys(false)){
					ConfigurationSection cs = csl.getConfigurationSection(s);
					GUIButton skullc = new GUIButton(
							ItemBuilder.start(Material.SKULL_ITEM).name("Head").data((short) 3).itemmeta(HeadsCreating.createCustomSkull(cs.getString("texture")).getItemMeta()).build()
					);
					skullist.setButton(amount+csa, skullc);
					csa++;
				}
				p.openInventory(skullist.getInventory());	
			} else{
				p.openInventory(CustomHead.maininv);
				e.setCancelled(true);
			}
		} else{
			return;
		}
	}
}
