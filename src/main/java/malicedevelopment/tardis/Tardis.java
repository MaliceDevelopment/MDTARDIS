package malicedevelopment.tardis;

import malicedevelopment.tardis.block.ModBlocks;
import malicedevelopment.tardis.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class Tardis implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "tardis";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
		LOGGER.info("Tardis initialized.");
	}
	@Override
	public void beforeGameStart() {
		ModBlocks.init(); // Makes sure all of our block are initialized
		ModItems.init(); // Makes sure all of our items are initialized
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}
}
