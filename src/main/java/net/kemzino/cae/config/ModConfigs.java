package net.kemzino.cae.config;

import com.mojang.datafixers.util.Pair;
import net.kemzino.cae.CursedAnvilEnchanting;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;
// ______________CONFIG_VALUES
    public static double CURSE_ENCHANT_CHANCE_MODIFICATION;
    public static int MIN_REQUIRED_LVL_TO_CURSE_ENCHANT;

//_______________CONFIG_VALUES_END
    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(CursedAnvilEnchanting.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("curse.enchant.chance.modification", 1.0), "double");
        configs.addKeyValuePair(new Pair<>("min.required.lvl.to.curse.enchant", 5), "int");
    }

    private static void assignConfigs() {
        CURSE_ENCHANT_CHANCE_MODIFICATION = CONFIG.getOrDefault("curse.enchant.chance.modification", 1.0);
        MIN_REQUIRED_LVL_TO_CURSE_ENCHANT = CONFIG.getOrDefault("min.required.lvl.to.curse.enchant", 5);
        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}