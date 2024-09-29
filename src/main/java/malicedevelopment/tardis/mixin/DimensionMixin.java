package malicedevelopment.tardis.mixin;

import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = Dimension.class, remap = false)
	public abstract class DimensionMixin{
		@Inject(method = "<clinit>", at = @At("TAIL"))
		private static void addDimension(CallbackInfo ci){

		}

	}

