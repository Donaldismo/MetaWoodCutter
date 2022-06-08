package ru.donaldismo.meta.woodcutter;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;


public class TreeChecker {
    private List<Block> blocks = new LinkedList<Block>();
    private boolean isTree = false;

    public boolean checkTree(Location loc) {
        this.rec(loc.getBlock());
        return this.isTree;
    }

    public void rec(Block block) {
        if (this.isTree) {
            return;
        }
        if (!Utils.isTree(block.getType())) {
            return;
        }
        if (this.getBlocks().contains((Object)block)) {
            return;
        }
        this.blocks.add(block);
        if (Utils.isLeaves(block.getType())) {
            Leaves leaves = (Leaves) block.getBlockData();
            if (leaves.isPersistent()) return;
            this.isTree = true;
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
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}

