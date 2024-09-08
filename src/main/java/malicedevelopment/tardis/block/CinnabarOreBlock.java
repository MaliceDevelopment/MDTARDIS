package malicedevelopment.tardis.block;

import malicedevelopment.tardis.Tardis;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import turniplabs.halplibe.helper.BlockBuilder;

public class CinnabarOreBlock {
	public static Block CinnabarOre;

	public static void init() {
		CinnabarOre = new BlockBuilder(Tardis.MOD_ID)
			.setTextures("tardis:block/cinnabar_ore")
			.build(new Block("Cinnabar Ore",3601, Material.sand));

	}
}
