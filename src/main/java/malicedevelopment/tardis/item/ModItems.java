package malicedevelopment.tardis.item;

import malicedevelopment.tardis.Tardis;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

public final class ModItems {
	private ModItems() {} // Makes it impossible to accidentally call 'new ModItems()'

	public static Item cinnabar;
	public static Item fob_watch;


	public static void init() {
		// TODO make numeric block ids configurable by the user
		cinnabar = new ItemBuilder(Tardis.MOD_ID)
			.setIcon("tardis:item/cinnabar")
			.setStackSize(64)
			.build(new Item("cinnabar",20000));

		fob_watch = new ItemBuilder(Tardis.MOD_ID)
			.setIcon("tardis:item/fob_watch")
			.setStackSize(1)
			.build(new FobWatchItem("fob_watch",20001));

	}
}
