package malicedevelopment.tardis.mixin;

import malicedevelopment.tardis.access.ModelPlayerAccess;
import malicedevelopment.tardis.access.RegenerationDataAccess;
import malicedevelopment.tardis.client.RenderRegenerationLayer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.GetSkinUrlThread;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

	@Shadow
	@Final
	private ModelPlayer modelThick;

	@Shadow
	@Final
	private ModelPlayer modelSlim;

	@Shadow
	public abstract void loadEntityTexture(EntityPlayer entity);

	GetSkinUrlThread THREAD;


	@Inject(method = "renderSpecials(Lnet/minecraft/core/entity/player/EntityPlayer;F)V", at = @At("TAIL"), cancellable = true, remap = false)
	public void render(EntityPlayer entity, float partialTick, CallbackInfo ci) {
		RegenerationDataAccess playerRegenData = (RegenerationDataAccess) entity;

		ModelPlayerAccess modelPlayerAccessSlim = (ModelPlayerAccess) modelSlim;
		modelPlayerAccessSlim.setPlayer(entity);

		ModelPlayerAccess modelPlayerAccessThick = (ModelPlayerAccess) modelThick;
		modelPlayerAccessThick.setPlayer(entity);


		if (!playerRegenData.getSkin().isEmpty()) {
			if (playerRegenData.getRegenerationTicksElapsed() == 101 || entity.tickCount == 20) {
				entity.skinURL = playerRegenData.getSkin();
				loadEntityTexture(entity);
				entity.slimModel = playerRegenData.isSlim();
			}
		} else {
			if (playerRegenData.getRegenerationTicksElapsed() == 101 || entity.tickCount == 20) {
				if(THREAD == null){
					THREAD = new GetSkinUrlThread(entity);
				}
				THREAD.start();
				loadEntityTexture(entity);
			}
		}



		RenderRegenerationLayer.renderSpecials(entity.slimModel ? this.modelSlim : this.modelThick, entity, partialTick);
	}


}
