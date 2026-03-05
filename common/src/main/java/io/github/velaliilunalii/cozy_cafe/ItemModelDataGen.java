package io.github.velaliilunalii.cozy_cafe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ItemModelDataGen {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String MOD_ID = "cozy_cafe";

    private static final String[] COLORS = {
            "default", "black", "blue", "brown", "cyan", "gray", "green",
            "light_blue", "light_gray", "lime", "magenta", "orange",
            "pink", "purple", "red", "white", "yellow"
    };

    private static final float[] COLOR_PREDICATES = {
            0.0f, 0.05882353f, 0.11764706f, 0.1764706f, 0.23529412f,
            0.29411766f, 0.3529412f, 0.4117647f, 0.47058824f, 0.5294118f,
            0.5882353f, 0.64705884f, 0.7058824f, 0.7647059f, 0.8235294f,
            0.88235295f, 0.9411765f
    };

    /**
     * Génère tous les fichiers pour une combinaison ingredient + contenant
     */
    public static void generate(Path outputDir, String ingredient, String contenant) throws IOException {
        generateMainModel(outputDir, ingredient, contenant);
        generateDefaultModel(outputDir, ingredient, contenant);
        for (int i = 1; i < COLORS.length; i++) {
            generateColorModel(outputDir, ingredient, contenant, COLORS[i]);
        }
    }

    /**
     * Génère tous les fichiers pour un item avec couleurs (ex: split_coconut, wooden_coaster)
     *
     * @param outputDir Le dossier de sortie
     * @param itemName  Ex: "split_coconut", "wooden_coaster"
     */
    public static void generateSimpleColoredItem(Path outputDir, String itemName) throws IOException {
        generateSimpleMainModel(outputDir, itemName);
        generateSimpleDefaultModel(outputDir, itemName);
        for (int i = 1; i < COLORS.length; i++) {
            generateSimpleColorModel(outputDir, itemName, COLORS[i]);
        }
    }

    public static void generateSimpleColoredItem(Path outputDir, String itemName, String folderName) throws IOException {
        generateSimpleMainModel(outputDir, itemName, folderName);
        generateSimpleDefaultModel(outputDir, itemName, folderName);
        for (int i = 1; i < COLORS.length; i++) {
            generateSimpleColorModel(outputDir, itemName, folderName, COLORS[i]);
        }
    }

    /**
     * Fichier principal pour item simple : <itemName>.json
     */
    private static void generateSimpleMainModel(Path outputDir, String itemName) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + itemName + "/default_" + itemName);
        json.add("textures", textures);

        JsonArray overrides = new JsonArray();
        for (int i = 0; i < COLORS.length; i++) {
            JsonObject override = new JsonObject();

            JsonObject predicate = new JsonObject();
            predicate.addProperty(MOD_ID + ":color", COLOR_PREDICATES[i]);
            override.add("predicate", predicate);

            String modelName = COLORS[i].equals("default")
                    ? "default_" + itemName
                    : COLORS[i] + "_" + itemName;
            override.addProperty("model", MOD_ID + ":item/" + modelName);

            overrides.add(override);
        }
        json.add("overrides", overrides);

        writeJson(outputDir.resolve(itemName + ".json"), json);
    }

    private static void generateSimpleMainModel(Path outputDir, String itemName, String folderName) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + folderName + "/default_" + itemName);
        json.add("textures", textures);

        JsonArray overrides = new JsonArray();
        for (int i = 0; i < COLORS.length; i++) {
            JsonObject override = new JsonObject();

            JsonObject predicate = new JsonObject();
            predicate.addProperty(MOD_ID + ":color", COLOR_PREDICATES[i]);
            override.add("predicate", predicate);

            String modelName = COLORS[i].equals("default")
                    ? "default_" + itemName
                    : COLORS[i] + "_" + itemName;
            override.addProperty("model", MOD_ID + ":item/" + modelName);

            overrides.add(override);
        }
        json.add("overrides", overrides);

        writeJson(outputDir.resolve(itemName + ".json"), json);
    }

    /**
     * Fichier default pour item simple : default_<itemName>.json
     */
    private static void generateSimpleDefaultModel(Path outputDir, String itemName) throws IOException {
        String fileName = "default_" + itemName;

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + itemName + "/" + itemName);
        textures.addProperty("layer1", MOD_ID + ":item/" + itemName + "/default_" + itemName);
        json.add("textures", textures);

        writeJson(outputDir.resolve(fileName + ".json"), json);
    }

    private static void generateSimpleDefaultModel(Path outputDir, String itemName, String folderName) throws IOException {
        String fileName = "default_" + itemName;

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + folderName + "/" + itemName);
        textures.addProperty("layer1", MOD_ID + ":item/" + folderName + "/default_" + itemName);
        json.add("textures", textures);

        writeJson(outputDir.resolve(fileName + ".json"), json);
    }

    /**
     * Fichier par couleur pour item simple : <color>_<itemName>.json
     */
    private static void generateSimpleColorModel(Path outputDir, String itemName, String color) throws IOException {
        String fileName = color + "_" + itemName;

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + itemName + "/" + itemName);
        textures.addProperty("layer1", MOD_ID + ":item/" + itemName + "/colors/" + color);
        json.add("textures", textures);

        writeJson(outputDir.resolve(fileName + ".json"), json);
    }

    private static void generateSimpleColorModel(Path outputDir, String itemName, String folderName, String color) throws IOException {
        String fileName = color + "_" + itemName;

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + folderName + "/" + itemName);
        textures.addProperty("layer1", MOD_ID + ":item/" + folderName + "/colors/" + color);
        json.add("textures", textures);

        writeJson(outputDir.resolve(fileName + ".json"), json);
    }

    // ========== MÉTHODES EXISTANTES ==========

    private static void generateMainModel(Path outputDir, String ingredient, String contenant) throws IOException {
        String baseName = ingredient + "_filled_" + contenant;

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + contenant + "/default_" + baseName);
        json.add("textures", textures);

        JsonArray overrides = getJsonElements(baseName);
        json.add("overrides", overrides);

        writeJson(outputDir.resolve(baseName + ".json"), json);
    }

    private static @NotNull JsonArray getJsonElements(String baseName) {
        JsonArray overrides = new JsonArray();
        for (int i = 0; i < COLORS.length; i++) {
            JsonObject override = new JsonObject();

            JsonObject predicate = new JsonObject();
            predicate.addProperty(MOD_ID + ":color", COLOR_PREDICATES[i]);
            override.add("predicate", predicate);

            String modelName = COLORS[i].equals("default")
                    ? "default_" + baseName
                    : COLORS[i] + "_" + baseName;
            override.addProperty("model", MOD_ID + ":item/" + modelName);

            overrides.add(override);
        }
        return overrides;
    }

    private static void generateDefaultModel(Path outputDir, String ingredient, String contenant) throws IOException {
        String fileName = "default_" + ingredient + "_filled_" + contenant;

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + contenant + "/" + ingredient + "_filled_" + contenant);
        textures.addProperty("layer1", MOD_ID + ":item/" + contenant + "/" + ingredient + "_default");
        json.add("textures", textures);

        writeJson(outputDir.resolve(fileName + ".json"), json);
    }

    private static void generateColorModel(Path outputDir, String ingredient, String contenant, String color) throws IOException {
        String fileName = color + "_" + ingredient + "_filled_" + contenant;

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":item/" + contenant + "/" + ingredient + "_filled_" + contenant);
        textures.addProperty("layer1", MOD_ID + ":item/" + contenant + "/colors/" + color);
        json.add("textures", textures);

        writeJson(outputDir.resolve(fileName + ".json"), json);
    }

    private static void writeJson(Path path, JsonObject json) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, GSON.toJson(json));
        System.out.println("Generated: " + path);
    }

    private static final String[] FLAVORS = {"bamboo", "cherry", "chorus", "glow_berries", "pumpkin", "melon", "sculk", "matcha", "sweet_berries", "apple", "spore_blossom", "potion"};
    private static final String[] CUPS = {"glass_tumbler", "split_coconut", "soda_bottle", "glass_can", "cocktail_glass", "glass_cup"};
    private static final String[] STANDALONE_ITEMS = {"chocolate_milk"};

    public static void main(String[] args) throws IOException {
        Path outputDir = Path.of("common/src/main/resources/assets/" + MOD_ID + "/models/item");

        for (String flavor : FLAVORS) {
            for (String cup : CUPS) {
                generate(outputDir, flavor, cup);
            }
        }

        for (String item : CUPS) {
            generateSimpleColoredItem(outputDir, item);
        }

        for (String item : STANDALONE_ITEMS) {
            generateSimpleColoredItem(outputDir, item, "glass_cup");
        }
    }
}