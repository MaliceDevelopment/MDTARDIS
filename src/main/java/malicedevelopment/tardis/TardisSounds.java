package malicedevelopment.tardis;

import net.minecraft.core.sound.SoundTypes;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class TardisSounds  {

	public static void init(){
		SoundTypes.register("tardis.regen");
		SoundHelper.addSound(Tardis.MOD_ID, "regen.ogg");
	}

}
