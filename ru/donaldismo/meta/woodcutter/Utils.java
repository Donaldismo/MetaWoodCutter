package ru.donaldismo.meta.woodcutter;

import org.bukkit.Material;

public class Utils {

    public static boolean isWood(Material material) {
        return material.toString().toString().toLowerCase().contains("_log");
    }

    public static boolean isLeaves(Material material) {
        return material.toString().toString().toLowerCase().contains("_leaves");
    }

    public static boolean isTree(Material material) {
        return Utils.isLeaves(material) || Utils.isWood(material);
    }
}

