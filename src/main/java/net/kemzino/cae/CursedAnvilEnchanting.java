package net.kemzino.cae;

import net.kemzino.cae.block.ModBlocks;
import net.kemzino.cae.event.ModEvents;
import net.kemzino.cae.item.ModItemGroups;
import net.kemzino.cae.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursedAnvilEnchanting implements ModInitializer {
	public static final String MOD_ID = "cursed_anvil_enchanting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModEvents.registerEvents();

		//ModNetworks.registerNetworks();
	}
}