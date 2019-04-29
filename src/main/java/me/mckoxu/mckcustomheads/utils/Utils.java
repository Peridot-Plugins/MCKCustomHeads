package me.mckoxu.mckcustomheads.utils;

import me.mckoxu.mckcustomheads.listeners.InventoryClickListener;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int removeFromNumber(int number, int what){
        int how = (int) number/what;
        return (number-(what*how));
    }

    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> list){
        List<String> listr = new ArrayList<>();
        for(String string : list){
            listr.add(color(string));
        }
        return listr;
    }

    public static List<String> color(ArrayList<String> list){
        List<String> listr = new ArrayList<>();
        for(String string : list){
            listr.add(color(string));
        }
        return listr;
    }

    public static List<String> replaceList(List<String> list){
        List<String> listr = new ArrayList<>();
        for(String string : list){
            listr.add(replaceString(string));
        }
        return listr;
    }


    public static List<String> replaceList(ArrayList<String> list){
        List<String> listr = new ArrayList<>();
        for(String string : list){
            listr.add(replaceString(string));
        }
        return listr;
    }

    public static String replaceString(String string){
        return string.replace("{AMOUNT}", String.valueOf(InventoryClickListener.heads.size()));
    }

    public static List<String> replaceListCurPage(ArrayList<String> list, int currentpage, int maxpage){
        List<String> listr = new ArrayList<>();
        for(String string : list){
            listr.add(replaceStringCurPage(string, currentpage, maxpage));
        }
        return listr;
    }

    public static List<String> replaceListCurPage(List<String> list, int currentpage, int maxpage){
        List<String> listr = new ArrayList<>();
        for(String string : list){
            listr.add(replaceStringCurPage(string, currentpage, maxpage));
        }
        return listr;
    }

    public static String replaceStringCurPage(String string, int currentpage, int maxpage){
        return string.replace("{CURRENTPAGE}", String.valueOf(currentpage)).replace("{MAXPAGES}", String.valueOf(maxpage));
    }
}
