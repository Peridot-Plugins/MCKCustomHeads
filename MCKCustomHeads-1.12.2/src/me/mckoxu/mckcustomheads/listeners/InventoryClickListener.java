package me.mckoxu.mckcustomheads.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.cloutteam.samjakob.gui.ItemBuilder;
import com.cloutteam.samjakob.gui.buttons.GUIButton;
import com.cloutteam.samjakob.gui.types.PaginatedGUI;

import me.mckoxu.mckcustomheads.MCKCustomHeads;
import me.mckoxu.mckcustomheads.commands.CustomHead;
import me.mckoxu.mckcustomheads.methods.AnvilGui;
import me.mckoxu.mckcustomheads.methods.AnvilGui.AnvilClickEvent;
import me.mckoxu.mckcustomheads.objects.Heads;

public class InventoryClickListener implements Listener{
	
	PaginatedGUI skullist = new PaginatedGUI(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("skullsmemulist")));
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Inventory i = e.getInventory();
		if(i.equals(CustomHead.maininv)){
			Player p = (Player) e.getWhoClicked();
			ItemStack is = e.getCurrentItem();
			if(is.equals(CustomHead.customp)){
				final Player player = p;
				AnvilGui gui = new AnvilGui(player, new AnvilGui.AnvilClickEventHandler() {
					@Override
					public void onAnvilClick(AnvilClickEvent event) {
						if(event.getSlot() == AnvilGui.AnvilSlot.OUTPUT){
								event.setWillClose(true);
								event.setWillDestroy(true);
								ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(short)3);
		                        SkullMeta im = (SkullMeta)skull.getItemMeta();
		                        im.setOwner(event.getName());
		                        skull.setItemMeta(im);
								player.getInventory().addItem(skull);
						} else{
							event.setWillClose(false);
							event.setWillDestroy(false);
						}
					}
				});
                ItemStack ii = new ItemStack(Material.PAPER);
                ItemMeta im = (ItemMeta)ii.getItemMeta();
                im.setDisplayName(MCKCustomHeads.getInst().getConfig().getString("maintextinanvil"));
                ii.setItemMeta(im);
               
                gui.setSlot(AnvilGui.AnvilSlot.INPUT_LEFT, ii);
               
                gui.open();
			} else if(is.equals(CustomHead.skullistitem)){
				int amount = 0;
				for (Heads head : Heads.values()){
					GUIButton skull = new GUIButton(
							ItemBuilder.start(Material.SKULL_ITEM).name("name").data((short)3).itemmeta(head.getItemStack().getItemMeta()).build()
					);
					GUIButton back = new GUIButton(
							ItemBuilder.start(Material.EMPTY_MAP).name("&3&lBack To Main Skulls Menu").lore("&b♦ Click here to back").build()
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
				skullist.setDisplayName(ChatColor.translateAlternateColorCodes('&', MCKCustomHeads.getInst().getConfig().getString("skullsmemulist")));	
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
