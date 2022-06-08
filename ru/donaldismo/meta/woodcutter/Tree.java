package ru.donaldismo.meta.woodcutter;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.block.Block;

public class Tree {
    private List<Block> blocks = new LinkedList<Block>();
    private int strenght;

    public Tree() {
    }

    public Tree(List<Block> blocks, int strenght) {
        this.setBlocks(blocks);
        this.strenght = strenght;
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public int getStrenght() {
        return this.strenght;
    }

    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }
}

