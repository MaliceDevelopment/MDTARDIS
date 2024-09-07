package malicedevelopment.tardis.block;

import malicedevelopment.tardis.Tardis;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import turniplabs.halplibe.helper.BlockBuilder;

public class tardisblock {
    public static Block TARDISfuncblock;

    public static void init() {
        TARDISfuncblock = new BlockBuilder(Tardis.MOD_ID).build(new Block("TARDIS", 3600, Material.metal));
    }
}