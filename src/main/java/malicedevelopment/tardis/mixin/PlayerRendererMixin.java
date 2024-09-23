package malicedevelopment.tardis.mixin;

import malicedevelopment.tardis.client.RenderRegenerationLayer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {


	@Shadow
	private ModelBiped modelBipedMain;

	@Shadow
	@Final
	private ModelPlayer modelThick;

	@Shadow
	@Final
	private ModelPlayer modelSlim;

	@Inject(method = "render(Lnet/minecraft/core/entity/player/EntityPlayer;DDDFF)V", at = @At("TAIL"), cancellable = true, remap = false)
	public void render(EntityPlayer entity, double xPos, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
		RenderRegenerationLayer.render(modelSlim, entity, xPos, y, z, yaw, partialTick, ci);
	}

}
