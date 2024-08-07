package net.kemzino.cae.event;

import net.kemzino.cae.CursedAnvilEnchanting;

public class ModEvents {

    public static void registerEvents() {
        AnvilEventHandler.register();
        TestEventHandler.register();


        CursedAnvilEnchanting.LOGGER.info("Registering Mod Events for " + CursedAnvilEnchanting.MOD_ID);
    }
}
