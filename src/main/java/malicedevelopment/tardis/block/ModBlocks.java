package malicedevelopment.tardis.block;

import malicedevelopment.tardis.Tardis;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import org.useless.dragonfly.model.block.DFBlockModelBuilder;
import turniplabs.halplibe.helper.BlockBuilder;

import static malicedevelopment.tardis.Tardis.MOD_ID;

public class ModBlocks {
	public static Block TARDIStype40funcblock;
	public static Block CinnabarOre;

	public static void init() {
		// TODO make numeric block ids configurable by the user
		// TODO actually use lang file to set names
		TARDIStype40funcblock = new BlockBuilder(MOD_ID)
			.setTextures("tardis:block/type40ttcapsule")
			.setBlockModel(block -> new DFBlockModelBuilder(MOD_ID)
				.setBlockModel("model.json")
				//.setBlockState("network_cable.json")
				.build(block)
			)
			.build(new Block("tardis", 3600, Material.metal));

		CinnabarOre = new BlockBuilder(Tardis.MOD_ID)
			.setTextures("tardis:block/cinnabar_ore")
			.setBlockSound(BlockSounds.SAND)
			.setHardness(5)
			.build(new BlockOreCinnabar("cinnabar_ore",3601, Material.sand))
			.withTags(BlockTags.MINEABLE_BY_SHOVEL);

	}
}
