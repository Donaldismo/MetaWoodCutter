package ru.donaldismo.meta.woodcutter;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class EventListener
implements Listener {
    private WoodCutter plugin;

    public EventListener(WoodCutter plugin) {
        this.plugin = plugin;
    }

    public int getRandom(int lower, int upper) {
        Random rand = new Random();
        return rand.nextInt((upper - lower) + 1) + lower;
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();
            if(item.getItemStack().getType().toString().toLowerCase().contains("_sapling")){
                if(item.getItemStack().getAmount() > 1) {
                    return;
                }
                if(getRandom(0,100) > 25) {
                    return;
                }
                Location loc = item.getLocation();
                for(double y = loc.getY(); y >= loc.getY() - 30; y--) {
                    Location nloc = new Location(loc.getWorld(), loc.getX(), (double)y, loc.getZ());
                    Block b = nloc.getBlock();
                    if(b.getType() == Material.GRASS_BLOCK || b.getType() == Material.DIRT)
                    {       
                        Location sapplingLoc = new Location(loc.getWorld(), b.getLocation().getX(), (double)(y+1), b.getLocation().getZ());
                        if(sapplingLoc.getBlock().getType() == Material.AIR)
                        {
                            event.getEntity().remove();
                            sapplingLoc.getBlock().setType(item.getItemStack().getType());
                        }
                    break;
                    }
                }
            }  
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreakEvent(BlockBreakEvent e) {

        if (e.isCancelled()) {
            return;
        }

        int strength = 0;
        Block block = e.getBlock();
        Material mat = block.getType();
        Player pl = e.getPlayer();
        ItemStack tool = pl.getInventory().getItemInMainHand();


        if(!tool.getType().toString().toLowerCase().contains("_axe")) {
            return;
        }
        if(pl.isSneaking()) {
            return;
        }

        if (!Utils.isWood(block.getType())) {
            return;
        }

        TreeChecker treeChecker = new TreeChecker();
        if (!treeChecker.checkTree(block.getLocation())) {
            return;
        }

        e.setDropItems(false);

        TreeUtil treeUtil = new TreeUtil();
        Tree tree = null;
        List<Block> blocks = null;

        if(block.hasMetadata("strength")) {
            List<MetadataValue> metaDataValues = block.getMetadata("strength");
            for (MetadataValue value : metaDataValues) {
                strength = value.asInt() - 1;
                block.setMetadata("strength", new FixedMetadataValue(this.plugin, strength));
            }
            if(strength % 5 == 0 && strength > 5)
            {
                if(tree == null)
                {
                    tree = treeUtil.getTree(block.getLocation());
                    blocks = tree.getBlocks();
                }
                for(Block b : blocks)
                {
                    b.setMetadata("strength", new FixedMetadataValue(this.plugin, strength));
                }
            }
        }
        else{
            if(tree == null)
            {
                tree = treeUtil.getTree(block.getLocation());
                blocks = tree.getBlocks();
            }
            strength = tree.getStrenght() - 1;
            for(Block b : blocks)
            {
                b.setMetadata("strength", new FixedMetadataValue(this.plugin, strength));
            }
        }
 
        if (strength > 0) {
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&fОсталось ударить еще &e"+strength+"&f раз")));
        }
        else{
            if(tree == null)
            {
                tree = treeUtil.getTree(block.getLocation());
                blocks = tree.getBlocks();
            }
            pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "Дерево срублено!")));
            for(Block b : blocks)
            {
                b.removeMetadata("strength", this.plugin);
                b.breakNaturally();
            }
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> blockSetMaterial(block, mat));

    }
    void blockSetMaterial(Block b, Material m)
    {
        b.setType(m);
    }
}

