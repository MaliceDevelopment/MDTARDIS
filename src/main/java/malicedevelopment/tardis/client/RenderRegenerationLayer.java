package malicedevelopment.tardis.client;

import malicedevelopment.tardis.access.RegenerationDataAccess;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import org.lwjgl.opengl.GL11;

//@Mixin(PlayerRenderer.class)
public class RenderRegenerationLayer {

	private static float primaryRed = 0.93f, primaryGreen = 0.61f, primaryBlue = 0.0f;
	private static float secondaryRed = 1f, secondaryGreen = 0.5f, secondaryBlue = 0.18f;

	public static void renderSpecials(ModelPlayer modelPlayer, EntityPlayer entity, float partialTick) {

		RegenerationDataAccess playerRegenData = (RegenerationDataAccess) entity;

		if (playerRegenData != null && playerRegenData.getRegenerationTicksElapsed() > 0) {
			// State manager changes
			GL11.glPushAttrib(0);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDepthMask(true);

			Vec3d primaryColor = Vec3d.createVector(primaryRed, primaryGreen, primaryBlue);
			Vec3d secondaryColor = Vec3d.createVector(secondaryRed, secondaryGreen, secondaryBlue);

			double x = playerRegenData.getRegenerationTicksElapsed();
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

			renderCone(entity, primaryScale / 1.6F, primaryScale * .75F, primaryColor, partialTick, false);
			renderCone(entity, secondaryScale / 1.6F, secondaryScale / 1.5F, secondaryColor, partialTick, false);
			GL11.glPopMatrix();


			// Render head cone
			GL11.glPushMatrix();
			modelPlayer.bipedLeftArm.postRender(0.0625F);
			renderCone(entity, primaryScale / 1.6F, primaryScale * .75F, primaryColor, partialTick, false);
			renderCone(entity, secondaryScale / 1.6F, secondaryScale / 1.5F, secondaryColor, partialTick, false);
			GL11.glPopMatrix();

			// Render head cone
			GL11.glPushMatrix();
			modelPlayer.bipedRightArm.postRender(0.0625F);
			renderCone(entity, primaryScale / 1.6F, primaryScale * .75F, primaryColor, partialTick, true);
			renderCone(entity, secondaryScale / 1.6F, secondaryScale / 1.5F, secondaryColor, partialTick, true);
			GL11.glPopMatrix();


// Undo state manager changes
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
	}

	public static void renderCone(EntityPlayer entityPlayer, float scale, float scale2, Vec3d color, float partialTicks, boolean mirrorRor) {
		Tessellator tessellator = Tessellator.instance;

		for (int i = 0; i < 8; i++) {
			GL11.glPushMatrix();
			// Adding a slight twist to the rotation for a dynamic look
			float rotationAngle = (entityPlayer.tickCount + partialTicks) * 4 * (mirrorRor ? -1 : 1) + i * 45 + (float)(Math.sin((entityPlayer.tickCount + partialTicks) * 0.1) * 10);
			GL11.glRotatef(rotationAngle, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0f, 1.0f, 0.65f);

			// Create a gradient effect
			for (int j = 0; j <= 1; j++) {
				float alpha = 0.65F * (1 - j * 0.5F); // Fade effect
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F((float) color.xCoord, (float) color.yCoord, (float) color.zCoord, alpha);

				// Central vertex
				tessellator.addVertex(0.0D, 0.0D, 0.0D);

				// Base vertices
				tessellator.addVertex(-0.266D * scale, scale, -0.5F * scale);
				tessellator.addVertex(0.266D * scale, scale, -0.5F * scale);
				tessellator.addVertex(0.0D, scale2, 1.0F * scale);

				tessellator.draw();
			}

			GL11.glPopMatrix();
		}

	}


}
