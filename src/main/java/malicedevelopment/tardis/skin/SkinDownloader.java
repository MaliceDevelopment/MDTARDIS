package malicedevelopment.tardis.skin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import malicedevelopment.tardis.Tardis;

public class SkinDownloader {

	private static final String SKINS_API_URL = "https://api.jeryn.dev/mc/skins"; // API endpoint

	// This map will store skins with their type (slim or default) and their metadata.
	public static Map<String, List<SkinData>> skinMap = new HashMap<>();

	public static SkinData getRandomSkin() {
		if (skinMap.isEmpty()) {
			System.err.println("Skin map is empty. Please collect skin data first.");
			return null;
		}

		// Randomly choose between "slim" and "default"
		String[] skinTypes = skinMap.keySet().toArray(new String[0]);
		String randomSkinType = skinTypes[Tardis.MASTER_RANDOM.nextInt(skinTypes.length)];

		// Get the list of skins for the randomly chosen skin type
		List<SkinData> skins = skinMap.get(randomSkinType);
		if (skins == null || skins.isEmpty()) {
			System.err.println("No skins found for the chosen type: " + randomSkinType);
			return null;
		}

		// Randomly select a skin from the list
		SkinData randomSkin = skins.get(Tardis.MASTER_RANDOM.nextInt(skins.size()));
		System.out.println("Randomly selected skin: " + randomSkin);
		return randomSkin;
	}

	public static void collectSkinData() {
		try {
			// Fetch the skin data from the API
			List<SkinData> skinDataList = fetchSkinData(SKINS_API_URL);

			// Process each skin without downloading
			for (SkinData skinData : skinDataList) {
				// Check the type (slim or default) based on the image pixels (metadata only)
				boolean isSlim = checkIfSlim(skinData.link);
				skinData.setSlim(isSlim);
				// Add to the map (key: skin type, value: list of SkinData)
				String skinType = isSlim ? "slim" : "default";
				skinMap.computeIfAbsent(skinType, k -> new ArrayList<>()).add(skinData);
			}

			System.out.println("Collected skin data and classified by type.");
		} catch (IOException e) {
			System.err.println("Error fetching skin data from API.");
			e.printStackTrace();
		}
	}

	private static List<SkinData> fetchSkinData(String apiUrl) throws IOException {
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			return parseSkinData(response.toString());
		} else {
			throw new IOException("Failed to fetch skin data from API: " + apiUrl);
		}
	}

	private static List<SkinData> parseSkinData(String jsonResponse) {
		List<SkinData> skinDataList = new ArrayList<>();

		JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
		for (JsonElement element : jsonArray) {
			JsonObject skinObject = element.getAsJsonObject();
			String name = skinObject.get("name").getAsString();
			String link = skinObject.get("link").getAsString();
			skinDataList.add(new SkinData(name, link));
		}

		return skinDataList;
	}

	// Instead of downloading the image, we'll just simulate checking if it's slim using the URL.
	private static boolean checkIfSlim(String skinUrl) {
		// Simulate image check based on URL or predefined logic.
		// For the sake of example, we assume every third URL corresponds to a slim skin.
		return skinUrl.hashCode() % 3 == 0;
	}

	// SkinData class to hold skin information
	public static class SkinData {
		String name;
		String link;
		boolean isSlim;

		SkinData(String name, String link) {
			this.name = name;
			this.link = link;
		}

		public String getLink() {
			return link;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return "SkinData{" +
				"name='" + name + '\'' +
				", link='" + link + '\'' +
				'}';
		}

		public void setSlim(boolean slim) {
			isSlim = slim;
		}

		public boolean isSlim() {
			return isSlim;
		}
	}

}
