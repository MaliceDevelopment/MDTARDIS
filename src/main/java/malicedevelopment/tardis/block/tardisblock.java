package malicedevelopment.tardis.block;
import malicedevelopment.tardis.Tardis;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import turniplabs.halplibe.helper.BlockBuilder;


public static Block TARDISfuncblock;

	TARDISfuncblock = new BlockBuilder(MOD_ID).build(new Block("TARDIS",3600, Material.metal));

