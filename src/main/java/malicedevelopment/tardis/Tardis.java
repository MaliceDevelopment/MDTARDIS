package malicedevelopment.tardis;

import malicedevelopment.tardis.block.ModBlocks;
import malicedevelopment.tardis.item.ModItems;
import malicedevelopment.tardis.skin.SkinDownloader;
import net.fabricmc.api.ModInitializer;
import org.lwjgl.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Random;

public class Tardis implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "tardis";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Random MASTER_RANDOM = new Random();

    @Override
    public void onInitialize() {
		LOGGER.info("Tardis initialized.");

	}
	@Override
	public void beforeGameStart() {
		SkinDownloader.collectSkinData();
		SkinDownloader.skinMap.forEach((s, skinData) -> {
			for (SkinDownloader.SkinData skinDatum : skinData) {
				System.out.println(skinDatum);
			}
		});

		ModBlocks.init(); // Makes sure all of our block are initialized
		ModItems.init(); // Makes sure all of our items are initialized
		TardisSounds.init();
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
