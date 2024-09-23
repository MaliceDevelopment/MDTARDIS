package malicedevelopment.tardis.client;

import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//@Mixin(PlayerRenderer.class)
public class RenderRegenerationLayer {

	private static float primaryRed = 0.93f, primaryGreen = 0.61f, primaryBlue = 0.0f;
	private static float secondaryRed = 1f, secondaryGreen = 0.5f, secondaryBlue = 0.18f;

	public static void render(ModelPlayer modelPlayer, EntityPlayer entity, double xPos, double y, double z, float yaw, float partialTick, CallbackInfo ci) {
		// State manager changes
		GL11.glPushAttrib(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDepthMask(true);

		Vec3d primaryColor = Vec3d.createVector(primaryRed, primaryGreen, primaryBlue);
		Vec3d secondaryColor = Vec3d.createVector(secondaryRed, secondaryGreen, secondaryBlue);

		double x = 100;
		double p = 109.89010989010987;
		double r = 0.09890109890109888;
		double f = p * Math.pow(x, 2) - r;

		float cf = MathHelper.clamp((float) f, 0F, 1F);
		float primaryScale = cf * 4F;
		float secondaryScale = cf * 6.4F;

// Render head cone
		GL11.glPushMatrix();

		modelPlayer.bipedHead.postRender(0.0625F);


		GL11.glTranslatef(0f, 0.1f, 0f);
		GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);

		renderCone(entity, primaryScale / 1.6F, primaryScale * .75F, primaryColor);
		renderCone(entity, secondaryScale / 1.6F, secondaryScale / 1.5F, secondaryColor);
		GL11.glPopMatrix();


// Undo state manager changes
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopAttrib();
	}


	public static void renderConeAtArms(EntityPlayer player) {
		double x = 600;
		double p = 109.89010989010987;
		double r = 0.09890109890109888;
		double f = p * Math.pow(x, 2) - r;
		float cf = MathHelper.clamp((float) f, 0F, 1F);
		float primaryScale = cf * 4F;
		float secondaryScale = cf * 6.4F;

		Vec3d primaryColor = Vec3d.createVector(primaryRed, primaryGreen, primaryBlue);
		Vec3d secondaryColor = Vec3d.createVector(secondaryRed, secondaryGreen, secondaryBlue);

		// State manager changes
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		GL11.glDepthMask(true);

		renderCone(player, primaryScale, primaryScale, primaryColor);
		renderCone(player, secondaryScale, secondaryScale * 1.5f, secondaryColor);

		// Undo state manager changes
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

	public static void renderCone(EntityPlayer entityPlayer, float scale, float scale2, Vec3d color) {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		for (int i = 0; i < 8; i++) {
			GL11.glPushMatrix();
			GL11.glRotatef(entityPlayer.tickCount * 4 + i * 45, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0f, 1.0f, 0.65f);

			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glColor4f((float) color.xCoord, (float) color.yCoord, (float) color.zCoord, 55);
			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glVertex3d(-0.266D * scale, scale, -0.5F * scale);
			GL11.glVertex3d(0.266D * scale, scale, -0.5F * scale);

			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glVertex3d(0.266D * scale, scale, -0.5F * scale);
			GL11.glVertex3d(0.0D, scale2, 1.0F * scale);

			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glVertex3d(0.0D, scale2, 1.0F * scale);
			GL11.glVertex3d(-0.266D * scale, scale, -0.5F * scale);
			GL11.glEnd();

			GL11.glPopMatrix();
		}
	}

}
