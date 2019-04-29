package me.mckoxu.mckcustomheads.listeners;

import me.mckoxu.mckcustomheads.MCKCustomHeads;
import me.mckoxu.mckcustomheads.commands.CustomHead;
import me.mckoxu.mckcustomheads.enums.XMaterial;
import me.mckoxu.mckcustomheads.objects.ItemBuilder;
import me.mckoxu.mckcustomheads.utils.HeadsCreating;
import me.mckoxu.mckcustomheads.utils.Utils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryClickListener implements Listener{
	
	FileConfiguration config = MCKCustomHeads.getInst().getConfig();
    public static final List<ItemStack> heads = HeadsCreating.getHeads();
    private final int pages = (int) Math.ceil(((double) heads.size()/45));
    final ItemStack next = new ItemBuilder("MAP")
            .setName(Utils.color(MCKCustomHeads.getInst().getConfig().getString("skullistpanel.items.nextpage.name")))
            .setLore(Utils.color(MCKCustomHeads.getInst().getConfig().getStringList("skullistpanel.items.nextpage.lore")))
            .toItemStack();
    final ItemStack previous = new ItemBuilder("MAP")
            .setName(Utils.color(MCKCustomHeads.getInst().getConfig().getString("skullistpanel.items.previouspage.name")))
            .setLore(Utils.color(MCKCustomHeads.getInst().getConfig().getStringList("skullistpanel.items.previouspage.lore")))
            .toItemStack();
    final ItemStack back = new ItemBuilder("RED_WOOL")
            .setName(Utils.color(MCKCustomHeads.getInst().getConfig().getString("skullistpanel.items.back.name")))
            .setLore(Utils.color(MCKCustomHeads.getInst().getConfig().getStringList("skullistpanel.items.back.lore")))
            .toItemStack();
    final ItemStack air = new ItemBuilder("AIR")
            .toItemStack();
    final ItemBuilder pagenum = new ItemBuilder("PAPER").setName("Aktualna strona: 0")
            .setName(Utils.color(MCKCustomHeads.getInst().getConfig().getString("skullistpanel.items.currentpage.name")))
            .setLore(Utils.color(MCKCustomHeads.getInst().getConfig().getStringList("skullistpanel.items.currentpage.lore")));
    public static Map<Player, Integer> openedgui = new HashMap<Player, Integer>();
    public static Map<Player, Inventory> skullListInventoryMap = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Inventory i = e.getInventory();
        ItemStack is = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
		if(i.equals(CustomHead.maininv)){
			if(is != null) {
			    e.setCancelled(true);
                if (is.equals(CustomHead.customp)) {
                    new AnvilGUI(MCKCustomHeads.getInst(), p, config.getString("customplayerhead.maintext"), (player, reply) -> {
                        if (reply != null) {
                            ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
                            SkullMeta im = (SkullMeta) skull.getItemMeta();
                            im.setOwner(reply);
                            skull.setItemMeta(im);
                            player.getInventory().addItem(skull);
                            return null;
                        }
                        return "Incorrect.";
                    });
                } else if (is.equals(CustomHead.skullistitem)) {
                    openedgui.put(p, 1);
                    openGui(p, 1);
                } else {
                    p.openInventory(CustomHead.maininv);
                }
            }
		} else if(i.equals(skullListInventoryMap.get(p))) {
		    if(is != null){
		        e.setCancelled(true);
		        if(is.getType().equals(XMaterial.PLAYER_HEAD.parseMaterial())){
                    p.getInventory().addItem(e.getCurrentItem());
                } else if(is.equals(next)){
                    if((openedgui.get(p)+1) <= pages) {
                        openedgui.put(p, openedgui.get(p) + 1);
                        openGui(p, openedgui.get(p));
                    }
                } else if(is.equals(previous)){
                    if((openedgui.get(p)-1) >= 1) {
                        openedgui.put(p, openedgui.get(p) - 1);
                        openGui(p, openedgui.get(p));
                    }
                } else if(is.equals(back)){
                    p.openInventory(CustomHead.maininv);
                }
		    }
		}
	}

	private void openGui(Player p, int page){
        Inventory skullist = Bukkit.createInventory(null, 54,"Glupie Inventory");
        skullListInventoryMap.put(p, skullist);
	    for (int i = 0; i < 44; i++) {
	        skullist.setItem(i, back);
	    }
	    int emptyslots = 45-Utils.removeFromNumber(heads.size(), 45);
	    int firstemptyslot = Utils.removeFromNumber(heads.size(), 45);
        int ii = 0;
        for (int i = 0; i < heads.size();) {
            if (ii > 44) {
                ii = 0;
            }
            if (i < page * 45) {
                skullist.setItem(ii++, heads.get(i));
            }
            i++;
        }
        if(page >= pages) {
            for (int i = 0; i < emptyslots; i++) {
                skullist.setItem(firstemptyslot+i, air);
            }
        }
        ItemStack pagenum = this.pagenum
                .setName(Utils.color(Utils.replaceStringCurPage(MCKCustomHeads.getInst().getConfig().getString("skullistpanel.items.currentpage.name"), page, pages)))
                .setLore(Utils.color(Utils.replaceListCurPage(MCKCustomHeads.getInst().getConfig().getStringList("skullistpanel.items.currentpage.lore"), page, pages)))
                .toItemStack();
        if((page-1) >= 1) {
            skullist.setItem(48, previous);
        } else{
            skullist.setItem(48, null);
        }
        if((page+1) <= pages) {
            skullist.setItem(50, next);
        } else{
            skullist.setItem(50, null);
        }
        skullist.setItem(49, pagenum);
        skullist.setItem(53, back);
        p.openInventory(skullist);
    }
}
