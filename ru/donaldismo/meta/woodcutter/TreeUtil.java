package ru.donaldismo.meta.woodcutter;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class TreeUtil {
    private List<Block> blocks = new LinkedList<Block>();
    private int count = 0;

    public Tree getTree(Location loc) {
        this.rec(loc.getBlock());
        return new Tree(this.getBlocks(), this.count);
    }

    public void rec(Block block) {
        if (this.getBlocks().contains((Object)block)) {
            return;
        }
        Material blockType = block.getType();

        if (Utils.isWood(blockType)) {
            ++this.count;
            this.blocks.add(block);
        }
        else{
            return;
        }
        Location loc = block.getLocation();
        
        int r = 1;
        for(int x = (r * -1); x <= r; x++) {
            for(int y = (r * -1); y <= r; y++) {
                for(int z = (r * -1); z <= r; z++) {
                    if(x == 0 && y == 0 && z == 0)
                    {}
                    else
                        this.rec(new Location(loc.getWorld(), (double)(loc.getBlockX() + x), (double)(loc.getBlockY() + y), (double)(loc.getBlockZ() + z)).getBlock());
                }
            }
        }

    /*
        this.rec(new Location(loc.getWorld(), (double)(loc.getBlockX() + 1), (double)loc.getBlockY(), (double)loc.getBlockZ()).getBlock());
        this.rec(new Location(loc.getWorld(), (double)(loc.getBlockX() - 1), (double)loc.getBlockY(), (double)loc.getBlockZ()).getBlock());
        this.rec(new Location(loc.getWorld(), (double)loc.getBlockX(), (double)(loc.getBlockY() + 1), (double)loc.getBlockZ()).getBlock());
        this.rec(new Location(loc.getWorld(), (double)loc.getBlockX(), (double)(loc.getBlockY() - 1), (double)loc.getBlockZ()).getBlock());
        this.rec(new Location(loc.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), (double)(loc.getBlockZ() + 1)).getBlock());
        this.rec(new Location(loc.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), (double)(loc.getBlockZ() - 1)).getBlock());*/


    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}

