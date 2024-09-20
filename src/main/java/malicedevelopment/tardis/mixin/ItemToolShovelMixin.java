package malicedevelopment.tardis.mixin;

import malicedevelopment.tardis.block.ModBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShovel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = ItemToolShovel.class,remap = false)
public abstract class ItemToolShovelMixin extends Item {

	@Unique
	private static Map<Block, Integer> miningLevels = new HashMap<>();

	@SuppressWarnings("DataFlowIssue")
	@Unique
	private final ItemToolShovel thisAs = (ItemToolShovel)((Object)this);

	private ItemToolShovelMixin(int id) {
		super(id);
	}

	@Override
	public boolean canHarvestBlock(EntityLiving entityLiving, ItemStack itemStack, Block block) {
		Integer miningLevel = miningLevels.get(block);
		if (miningLevel != null) {
			return thisAs.getMaterial().getMiningLevel() >= miningLevel;
		} else {
			return true;
		}
	}

	static {
		miningLevels.put(ModBlocks.CinnabarOre,2);
	}

}
