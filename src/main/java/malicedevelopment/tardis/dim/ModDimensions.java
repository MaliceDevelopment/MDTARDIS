package malicedevelopment.tardis.dim;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;

public class ModDimensions {
	public static Dimension dimensionTardis;

	public static void register() {}

	public static void DimensionShift(int targetDimension){
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;

		Dimension lastDim = Dimension.getDimensionList().get(player.dimension);
		Dimension newDim = Dimension.getDimensionList().get(targetDimension);
		System.out.println("Switching to dimension \"" + newDim.getTranslatedName() + "\"!!");
		world.setEntityDead(player);
		player.removed = false;
		double x = player.x;
		double z = player.z;

	}
}
