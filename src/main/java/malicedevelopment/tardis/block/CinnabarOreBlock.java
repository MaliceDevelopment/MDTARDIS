package malicedevelopment.tardis.block;

import malicedevelopment.tardis.Tardis;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSound;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public class CinnabarOreBlock {
	public static Block CinnabarOre;

	public static void init() {
		CinnabarOre = new BlockBuilder(Tardis.MOD_ID)
			.setTextures("tardis:block/cinnabar_ore")
			.setBlockSound(BlockSounds.SAND)
			.setHardness(5)
			.build(new Block("Cinnabar Ore",3601, Material.sand))
			.withTags(BlockTags.MINEABLE_BY_SHOVEL);

	}
}
