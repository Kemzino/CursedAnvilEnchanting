package net.kemzino.cae.item;

import net.kemzino.cae.CursedAnvilEnchanting;
import net.kemzino.cae.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(CursedAnvilEnchanting.MOD_ID, "cursed_anvil_enchanting"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup"))
                    .icon(() -> new ItemStack(ModBlocks.NETHERITE_ANVIL)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.NETHERITE_ANVIL);
                    }).build());


    public static void registerItemGroups() {
        CursedAnvilEnchanting.LOGGER.info("Registering Item Groups for " + CursedAnvilEnchanting.MOD_ID);
    }
}