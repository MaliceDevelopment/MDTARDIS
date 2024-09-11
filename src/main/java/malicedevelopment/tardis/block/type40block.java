package malicedevelopment.tardis.block;

import static malicedevelopment.tardis.Tardis.MOD_ID;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import org.useless.dragonfly.model.block.DFBlockModelBuilder;
import turniplabs.halplibe.helper.BlockBuilder;

public class type40block {
    public static Block TARDIStype40funcblock;

    public static void init() {
        TARDIStype40funcblock = new BlockBuilder(MOD_ID)
            .setTextures("tardis:block/type40ttcapsule")
			.setBlockModel(block -> new DFBlockModelBuilder(MOD_ID)
                .setBlockModel("model.json")
                //.setBlockState("network_cable.json")
                .build(block)
            )
			.build(new Block("TARDIS", 3600, Material.metal));

	}
}
