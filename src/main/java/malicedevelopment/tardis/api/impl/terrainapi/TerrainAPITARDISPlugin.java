package malicedevelopment.tardis.api.impl.terrainapi;

import malicedevelopment.tardis.Tardis;
import useless.terrainapi.api.TerrainAPI;

public class TerrainAPITARDISPlugin implements TerrainAPI {
	@Override
	public String getModID() {
		return Tardis.MOD_ID;
	}
	@Override
	public void onInitialize(){
		new WorldGenTARDIS().init();
	}
}
