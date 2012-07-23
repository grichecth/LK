package com.grichecth.blocks;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;

public class Blockmaker extends GenericCubeCustomBlock {
	public Blockmaker(Plugin plugin, String name, int blockid, Texture texture) {
		super(plugin, name, blockid, new GenericCubeBlockDesign(plugin, texture, 0));
	}
}
