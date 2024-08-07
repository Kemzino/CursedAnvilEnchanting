package net.kemzino.cae.item;

import net.kemzino.cae.CursedAnvilEnchanting;
import net.kemzino.cae.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    private static void addItemsToFunctionalItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.NETHERITE_ANVIL);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CursedAnvilEnchanting.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CursedAnvilEnchanting.LOGGER.info("Registering Mod Items for " + CursedAnvilEnchanting.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItems::addItemsToFunctionalItemGroup);
    }
}