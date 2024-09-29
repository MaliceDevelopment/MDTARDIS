package malicedevelopment.tardis.mixin;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import malicedevelopment.tardis.dim.ModDimensions;
import malicedevelopment.tardis.block.ModBlocks;


@Mixin(value = Dimension.class, remap = false)
	public abstract class DimensionMixin{
		@Inject(method = "<clinit>", at = @At("TAIL"))
		private static void addDimension(CallbackInfo ci){
			ModDimensions.dimensionTardis = new Dimension("tardis", Dimension.overworld, 1f, ModBlocks.portalTardis.id).setDefaultWorldType(WorldTypes.EMPTY);
			Dimension.registerDimension(3, ModDimensions.dimensionTardis);
		}

	}

