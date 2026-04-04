package com.xirosum.xiros.border.block.config;

import com.xirosum.xiros.border.block.XirosBorderBlock;
import com.xirosum.xiros.border.block.utils.BorderBlockStage;
import com.xirosum.xiros.border.block.utils.Reward;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private static final String CONFIG_DIR = "config/xiros-border-block";
    private static final String CONFIG_FILE = "xiros-border-block-config.yml";
    private static final String DEFAULT_CONFIG_RESOURCE = "defaults/xiros-border-block-config.yml";
    
    public static BorderBlockConfig loadConfig() {
        Path configDir = Path.of(CONFIG_DIR);
        Path configPath = configDir.resolve(CONFIG_FILE);
        
        try {
            // Create config directory if it doesn't exist
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
                XirosBorderBlock.LOGGER.info("Created config directory: {}", configDir);
            }
            
            // If config file doesn't exist, create it with defaults
            if (!Files.exists(configPath)) {
                XirosBorderBlock.LOGGER.info("Config file not found, creating default config at: {}", configPath);
                createDefaultConfig(configPath);
                return parseYamlConfig(configPath);
            }
            
            // Load and parse YAML
            return parseYamlConfig(configPath);
        } catch (Exception e) {
            XirosBorderBlock.LOGGER.error("Failed to load config, using defaults", e);
            return loadDefaultConfigResource();
        }
    }
    
    private static BorderBlockConfig parseYamlConfig(Path configPath) throws IOException {
        Yaml yaml = new Yaml();
        try (FileInputStream fis = new FileInputStream(configPath.toFile())) {
            Map<String, Object> data = asStringObjectMap(yaml.load(fis));
            
            if (data == null) {
                XirosBorderBlock.LOGGER.warn("Config file is empty, using defaults");
                return loadDefaultConfigResource();
            }

            BorderBlockConfig config = parseConfigData(data, true);
            
            XirosBorderBlock.LOGGER.info("Successfully loaded config from: {}", configPath);
            return config;
        }
    }

    private static BorderBlockConfig parseConfigData(Map<String, Object> data, boolean allowDefaultStageFallback) {
        BorderBlockConfig config = new BorderBlockConfig();
        config.stages = parseStages(getListOfMaps(data, "stages"), allowDefaultStageFallback);
        config.defaultReward = getStringValue(data, "defaultReward", config.defaultReward);
        return config;
    }

    private static List<BorderBlockStage> parseStages(List<Map<String, Object>> stagesData, boolean allowDefaultStageFallback) {
        if (stagesData == null || stagesData.isEmpty()) {
            XirosBorderBlock.LOGGER.warn("No stages found in config, using defaults");
            if (allowDefaultStageFallback) {
                return loadDefaultConfigResource().stages;
            }
            return new BorderBlockConfig().stages;
        }
        
        return stagesData.stream().map(stageData -> {
            int stageId = getInt(stageData, "stageId", 0);
            int startingBorderSize = getInt(stageData, "startingBorderSize", 0);
            int endingBorderSize = getInt(stageData, "endingBorderSize", 1000);
            String requiredItem = getStringValue(stageData, "requiredItem", "minecraft:diamond");
            int rewardMultiplier = getInt(stageData, "rewardMultiplier", 1);
            int expansionMultiplier = getInt(stageData, "expansionMultiplier", 1);

            List<Reward> lootTable = parseLootTable(getListOfMaps(stageData, "lootTable"));
            
            return new BorderBlockStage(
                stageId,
                startingBorderSize,
                endingBorderSize,
                requiredItem,
                rewardMultiplier,
                expansionMultiplier,
                lootTable
            );
        }).toList();
    }
    
    private static List<Reward> parseLootTable(List<Map<String, Object>> lootTableData) {
        if (lootTableData == null || lootTableData.isEmpty()) {
            return List.of(new Reward(List.of(loadDefaultConfigResource().defaultReward), 1));
        }
        
        return lootTableData.stream().map(rewardData -> {
            List<String> rewards = getListOfStrings(rewardData, "rewards", List.of("minecraft:dirt"));
            int weight = getInt(rewardData, "weight", 1);
            return new Reward(rewards, weight);
        }).toList();
    }
    
    private static void createDefaultConfig(Path configPath) throws IOException {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_RESOURCE)) {
            if (input == null) {
                throw new IOException("Default config resource not found: " + DEFAULT_CONFIG_RESOURCE);
            }

            Files.copy(input, configPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    private static BorderBlockConfig loadDefaultConfigResource() {
        Yaml yaml = new Yaml();

        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_RESOURCE)) {
            if (input == null) {
                throw new IOException("Default config resource not found: " + DEFAULT_CONFIG_RESOURCE);
            }

            Map<String, Object> data = asStringObjectMap(yaml.load(input));
            if (data == null) {
                return new BorderBlockConfig();
            }

            return parseConfigData(data, false);
        } catch (Exception resourceError) {
            XirosBorderBlock.LOGGER.error("Failed to load bundled default config resource", resourceError);
            return new BorderBlockConfig();
        }
    }

    private static Map<String, Object> asStringObjectMap(Object value) {
        if (!(value instanceof Map<?, ?> rawMap)) {
            return null;
        }

        for (Object key : rawMap.keySet()) {
            if (!(key instanceof String)) {
                return null;
            }
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> typed = (Map<String, Object>) rawMap;
        return typed;
    }

    private static List<Map<String, Object>> getListOfMaps(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (!(value instanceof List<?> rawList)) {
            return null;
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object entry : rawList) {
            Map<String, Object> typedEntry = asStringObjectMap(entry);
            if (typedEntry != null) {
                result.add(typedEntry);
            }
        }
        return result;
    }

    private static List<String> getListOfStrings(Map<String, Object> map, String key, List<String> defaultValue) {
        Object value = map.get(key);
        if (!(value instanceof List<?> rawList)) {
            return defaultValue;
        }

        List<String> result = new ArrayList<>();
        for (Object entry : rawList) {
            if (entry instanceof String text) {
                result.add(text);
            }
        }
        return result.isEmpty() ? defaultValue : result;
    }
    
    private static int getInt(Map<String, Object> map, String key, int defaultValue) {
        Object value = map.get(key);
        if (value instanceof Number num) {
            return num.intValue();
        }
        return defaultValue;
    }
    
    private static String getStringValue(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        if (value instanceof String str) {
            return str;
        }
        return defaultValue;
    }
}
