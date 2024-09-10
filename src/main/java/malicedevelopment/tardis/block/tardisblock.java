package malicedevelopment.tardis.block;

import static malicedevelopment.tardis.Tardis.MOD_ID;
import malicedevelopment.tardis.Tardis;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import org.useless.dragonfly.model.block.DFBlockModelBuilder;
import turniplabs.halplibe.helper.BlockBuilder;

public class tardisblock {
    public static Block TARDISfuncblock;

    public static void init() {
        TARDISfuncblock = new BlockBuilder(MOD_ID)
            .setTextures("tardis:block/cinnabar_ore")
			.setBlockModel(block -> new DFBlockModelBuilder(MOD_ID)
                .setBlockModel("model.json")
                //.setBlockState("network_cable.json")
                .build(block)
            )
			.build(new Block("TARDIS", 3600, Material.metal));

	}
}
