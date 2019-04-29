package me.mckoxu.mckcustomheads.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.mckoxu.mckcustomheads.MCKCustomHeads;
import me.mckoxu.mckcustomheads.enums.Heads;
import me.mckoxu.mckcustomheads.enums.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HeadsCreating {
	public static ItemStack getHead(String name) {
        for (Heads head : Heads.values()) {
            if (head.getName().equalsIgnoreCase(name)) {
                return head.getItemStack();
            }
        }
        return null;
    }

    public static List<ItemStack> getHeads(){
	    List<ItemStack> heads = new ArrayList<>();
	    for(Heads head : Heads.values()){
	        heads.add(head.getItemStack());
        }
        ConfigurationSection csl = MCKCustomHeads.getInst().getConfig().getConfigurationSection("heads");
        for (String ss : csl.getKeys(false)) {
            ConfigurationSection cs = csl.getConfigurationSection(ss);
            heads.add(createSkull(cs.getString("texture"), cs.getString("name")));
        }
	    return heads;
    }

    public static ItemStack createSkull(String url, String name) {
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short)3);
        if (url.isEmpty()) return head;
        ItemMeta im = head.getItemMeta();
        String what = name;
        String nameset = "";
        if (what != null) {
            String[] list = what.split("-");
            List<String> listStr = Arrays.asList(list);
            for (String s : listStr) {
                nameset+=("-" + s.substring(0, 1).toUpperCase() + s.substring(1));
            }
            nameset = nameset.replaceFirst("-", "");
        }
        im.setDisplayName(nameset);
        SkullMeta headMeta = (SkullMeta) im;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
           
        }
        catch (IllegalArgumentException|NoSuchFieldException|SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack createCustomSkull(String url) {
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short)3);
        if (url.isEmpty()) return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        }
        catch (IllegalArgumentException|NoSuchFieldException|SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }
}
