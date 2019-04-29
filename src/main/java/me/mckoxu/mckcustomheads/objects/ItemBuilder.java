package me.mckoxu.mckcustomheads.objects;

import me.mckoxu.mckcustomheads.enums.XMaterial;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemBuilder {

    private Material material;
    private String smaterial;
    private ItemStack itemstack;
    private int amount;
    private short durability;
    private String name;
    private ArrayList<String> lore;
    private ArrayList<String> enchantments;
    private ItemMeta itemmeta;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(String material) {
        this.smaterial = material;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setStringMaterial(String material) {
        this.smaterial = material;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public String getStringMaterial() {
        return smaterial;
    }

    public ItemBuilder setItem(ItemStack itemstack) {
        this.itemstack = itemstack;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemBuilder setDurability(short durability) {
        this.durability = durability;
        return this;
    }

    public int getDurability() {
        return amount;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = (ArrayList<String>) lore;
        return this;
    }

    public ItemBuilder addLoreLine(String loreline) {
        this.lore.add(loreline);
        return this;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public ItemBuilder setEnchantments(ArrayList<String> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemBuilder addEnchantments(ArrayList<String> enchantments) {
        for (String enchantment : enchantments) {
            this.enchantments.add(enchantment);
        }
        return this;
    }

    public ItemBuilder setEnchantment(String enchantment) {
        this.enchantments.clear();
        this.enchantments.add(enchantment);
        return this;
    }

    public ItemBuilder addEnchantment(String enchantment) {
        this.enchantments.add(enchantment);
        return this;
    }

    public ItemBuilder removeEnchantment(String enchantment) {
        this.enchantments.remove(enchantment);
        return this;
    }

    public ItemBuilder removeEnchantments(ArrayList<String> enchantments) {
        for (String enchantment : enchantments) {
            this.enchantments.remove(enchantment);
        }
        return this;
    }

    public ArrayList<String> getEnchantments() {
        return enchantments;
    }

    public ItemBuilder setItemMeta(ItemMeta itemmeta) {
        this.itemmeta = itemmeta;
        return this;
    }

    public ItemMeta getItemMeta() {
        ItemMeta im = this.itemmeta;
        im.setLore(lore);
        return im;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        return this;
    }

    public ItemBuilder getItemBuilder() {
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStack toItemStack() {
        if (name == null) {
            name = "";
        }
        if (amount < 1) amount = 1;
        if (itemstack == null) {
            if (material == null && smaterial == null) {
                return XMaterial.fromString(String.valueOf("AIR")).parseItem();
            }
            ItemStack is;
            if (smaterial != null) {
                is = XMaterial.fromString(smaterial).parseItem();
            } else {
                is = XMaterial.fromString(String.valueOf(material)).parseItem();
            }
            is.setAmount(amount);
            if (XMaterial.isDamageable(XMaterial.fromString(String.valueOf(is.getType())))) {
                is.setDurability(durability);
            }
            ItemMeta im = is.getItemMeta();
            if (itemmeta != null) {
                im = itemmeta;
                if (XMaterial.isDamageable(XMaterial.fromString(String.valueOf(is.getType())))) {
                }
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null) {
                    im.setDisplayName(name);
                }
            } else {
                if (XMaterial.isDamageable(XMaterial.fromString(String.valueOf(is.getType())))) {
                }
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null && is.getType() != Material.AIR) {
                    im.setDisplayName(name);
                }
            }
            is.setItemMeta(im);
            HashMap<Enchantment, Integer> enchantmentIntegerMap = new HashMap<Enchantment, Integer>();
            if (enchantments != null) {
                for (String s : enchantments) {
                    String[] ss = s.split(":");
                    enchantmentIntegerMap.put(Enchantment.getByName(ss[0]), Integer.valueOf(Integer.parseInt(ss[1])));
                }
            }
            if (enchantmentIntegerMap != null) {
                is.addUnsafeEnchantments(enchantmentIntegerMap);
            }
            return is;
        } else {
            ItemStack is = this.itemstack;
            HashMap<Enchantment, Integer> enchantmentIntegerMap = new HashMap<Enchantment, Integer>();
            if (enchantments != null) {
                for (String s : enchantments) {
                    String[] ss = s.split(":");
                    enchantmentIntegerMap.put(Enchantment.getByName(ss[0]), Integer.valueOf(Integer.parseInt(ss[1])));
                }
            }
            if (enchantmentIntegerMap != null) {
                is.addUnsafeEnchantments(enchantmentIntegerMap);
            }
            is.setAmount(amount);
            if (XMaterial.isDamageable(XMaterial.fromString(String.valueOf(is.getType())))) {
                is.setDurability(durability);
            }
            ItemMeta im = is.getItemMeta();
            if (itemmeta != null) {
                im = itemmeta;
                if (XMaterial.isDamageable(XMaterial.fromString(String.valueOf(is.getType())))) {
                }
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null && is.getType() != Material.AIR) {
                    im.setDisplayName(name);
                }
            } else {
                if (XMaterial.isDamageable(XMaterial.fromString(String.valueOf(is.getType())))) {
                }
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null && is.getType() != Material.AIR) {
                    im.setDisplayName(name);
                }
            }
            is.setItemMeta(im);
            return is;
        }
    }

}
