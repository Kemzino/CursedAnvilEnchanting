package net.kemzino.cae.config;

import com.mojang.datafixers.util.Pair;
import net.kemzino.cae.CursedAnvilEnchanting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;
// ______________CONFIG_VALUES
    public static double CURSE_ENCHANT_CHANCE_MODIFICATION;
    public static int MIN_REQUIRED_LVL_TO_CURSE_ENCHANT;
    public static int CHANCE_COLOR;
    public static int TIN_COLOR;
    public static double NETHERITE_ANVIL_LVL_MODIFICATION;
    public static double ANVIL_LVL_MODIFICATION;
    public static List<String> GUARANTEE_CURSE_ENCHANTS;

//_______________CONFIG_VALUES_END
    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of("main_settings").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("curse.enchant.chance.modification", 1.5), "double");
        configs.addKeyValuePair(new Pair<>("min.required.lvl.to.curse.enchant", 5), "int");
        configs.addKeyValuePair(new Pair<>("tin.color", 0x000000), "int / color which visualize how high chance to get curse enchantment");
        configs.addKeyValuePair(new Pair<>("chance.color", 0xe5be5c), "int / color which visualize curse enchantment chance");
        configs.addKeyValuePair(new Pair<>("netherite.anvil.lvl.modification", 0.7), "double / netherite anvil ask less exp");
        configs.addKeyValuePair(new Pair<>("anvil.lvl.modification", 1.2), "double / anvil ask more exp");
        configs.addKeyValuePair(new Pair<>("guarantee.curse.enchants",
                "enchantment.minecraft.mending, " +
                "enchantment.minecraft.infinity, " +
                "enchantment.minecraft.protection, " +
                "enchantment.minecraft.sharpness, " +
                "enchantment.minecraft.fortune, "),
                "string / enchantment.[mod_id/minecraft].enchantment_name");

    }

    private static void assignConfigs() {
        CURSE_ENCHANT_CHANCE_MODIFICATION = CONFIG.getOrDefault("curse.enchant.chance.modification", 1.5);
        MIN_REQUIRED_LVL_TO_CURSE_ENCHANT = CONFIG.getOrDefault("min.required.lvl.to.curse.enchant", 5);
        TIN_COLOR = CONFIG.getOrDefault("tin.color", 0x000000);
        CHANCE_COLOR = CONFIG.getOrDefault("chance.color", 0xe5be5c);
        NETHERITE_ANVIL_LVL_MODIFICATION = CONFIG.getOrDefault("netherite.anvil.lvl.modification", 0.7);
        ANVIL_LVL_MODIFICATION = CONFIG.getOrDefault("anvil.lvl.modification", 1.2);
        GUARANTEE_CURSE_ENCHANTS = Arrays.stream(
                CONFIG.getOrDefault("guarantee.curse.enchants", "enchantment.minecraft.mending")
                        .split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}