package malicedevelopment.tardis.item;

import malicedevelopment.tardis.Tardis;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

public final class ModItems {
	private ModItems() {} // Makes it impossible to accidentally call 'new ModItems()'

	public static Item cinnabar;
	public static void init() {
		// TODO make numeric block ids configurable by the user
		// TODO actually use lang file to set names
		cinnabar = new ItemBuilder(Tardis.MOD_ID)
			.setIcon("tardis:item/cinnabar")
			.setStackSize(64)
			.build(new Item("Cinnabar",20000));

	}
}
