package malicedevelopment.tardis;

import malicedevelopment.tardis.block.type40block;
import malicedevelopment.tardis.block.CinnabarOreBlock;
import malicedevelopment.tardis.item.Cinnabar;
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
		type40block.init();
		CinnabarOreBlock.init();
		Cinnabar.init();
	}
	@Override
	public void beforeGameStart() {

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
