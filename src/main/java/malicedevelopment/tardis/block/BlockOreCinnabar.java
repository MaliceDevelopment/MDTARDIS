package malicedevelopment.tardis.block;

import malicedevelopment.tardis.item.ModItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class BlockOreCinnabar extends Block {
	public BlockOreCinnabar(String key, int id, Material material) {
		super(key, id, material);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case IMPROPER_TOOL: // When broken with the wrong tool drop nothing (in this case null is a stand in for nothing)
				return null;
			case PICK_BLOCK: // When using pick block or silk touch drop the ore block itself
			case SILK_TOUCH:
				return new ItemStack[]{new ItemStack(this)}; // Makes a new itemstack array that only contains a single stack with a single instance of the block (1x cinnabar ore)
			default: // When broken with the right tool, or by the world (explosions) drop 1 cinnabar
				return new ItemStack[]{new ItemStack(ModItems.cinnabar, 1)};
		}
	}
}
