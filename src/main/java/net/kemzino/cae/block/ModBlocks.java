package net.kemzino.cae.block;

import net.kemzino.cae.CursedAnvilEnchanting;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlocks {

    public static final Block NETHERITE_ANVIL = registerBlock("netherite_anvil", new NetheriteAnvilBlock());

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(CursedAnvilEnchanting.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(CursedAnvilEnchanting.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        CursedAnvilEnchanting.LOGGER.info("Registering Mod Blocks for " + CursedAnvilEnchanting.MOD_ID);
    }
}
