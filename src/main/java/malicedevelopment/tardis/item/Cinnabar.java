package malicedevelopment.tardis.item;

import malicedevelopment.tardis.Tardis;
import malicedevelopment.tardis.block.CinnabarOreBlock;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.ItemHelper;

public class Cinnabar {
	public static Item CinnabarOre;

	public static void init() {
		CinnabarOre = new ItemBuilder(Tardis.MOD_ID)
			.setIcon("tardis:item/cinnabar")
			.setStackSize(64)
			.build(new Item("Cinnabar",20000));

	}
}
