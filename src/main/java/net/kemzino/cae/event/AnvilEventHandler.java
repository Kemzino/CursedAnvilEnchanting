package net.kemzino.cae.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.kemzino.cae.CursedAnvilEnchanting;
import net.kemzino.cae.config.ModConfigs;
import net.kemzino.cae.global.ModVariables;
import net.kemzino.cae.service.EnchantmentService;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;

import java.util.*;

public class AnvilEventHandler {

    private static boolean enchantTryFlag = true;
    private static ItemStack lastItemStackSlot0 = ItemStack.EMPTY;
    private static ItemStack lastItemStackSlot1 = ItemStack.EMPTY;
    private static ItemStack lastItemStackSlot2 = ItemStack.EMPTY;
    private static ItemStack lastCursorStack = ItemStack.EMPTY;
    private static int lastLevelCost = 0;
    private static boolean enchantState = false;
    public static boolean guarateeCurse = false;

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.currentScreenHandler instanceof AnvilScreenHandler && !ModVariables.getInstance().getIsNetheriteAnvilOpen()) {
                    AnvilScreenHandler anvilScreenHandler = (AnvilScreenHandler) player.currentScreenHandler;
                        ItemStack currentItemStackSlot0 = anvilScreenHandler.getSlot(0).getStack();
                        ItemStack currentItemStackSlot1 = anvilScreenHandler.getSlot(1).getStack();
                        ItemStack resultStack = anvilScreenHandler.getSlot(2).getStack();
                        ItemStack cursorStack = player.currentScreenHandler.getCursorStack();

                        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(currentItemStackSlot1);



                        double enchantmentChance = (double) (anvilScreenHandler.getLevelCost() * ModConfigs.CURSE_ENCHANT_CHANCE_MODIFICATION) / 100;

                        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                            Enchantment enchantment = entry.getKey();
                            int level = entry.getValue();

                            if(ModConfigs.GUARANTEE_CURSE_ENCHANTS.contains(enchantment.getTranslationKey())) {
                                enchantmentChance = 1;
                                guarateeCurse = true;
                            }
                            else {
                                guarateeCurse = false;
                            }

                        }

                        if (resultStack.isEmpty() && enchantTryFlag && ItemStack.areItemsEqual(cursorStack, lastItemStackSlot2)) {

                            enchantTryFlag = false;
                            if (enchantState && EnchantmentService.isEnchantedBook(lastItemStackSlot1) || !lastItemStackSlot1.getEnchantments().isEmpty()) {

                                Enchantment enchantmentToApply = EnchantmentService.getRandomCurseEnchantmentFromList(EnchantmentService.getApplicableCurseEnchantments(cursorStack));
                                if (lastLevelCost > ModConfigs.MIN_REQUIRED_LVL_TO_CURSE_ENCHANT || enchantmentChance == 1) {
                                    cursorStack.addEnchantment(enchantmentToApply, 1);
                                }

                                player.currentScreenHandler.setCursorStack(cursorStack);
                                player.playerScreenHandler.sendContentUpdates();
                            }
                        }

                        if (!ItemStack.areEqual(currentItemStackSlot0, lastItemStackSlot0)
                                || !ItemStack.areEqual(currentItemStackSlot1, lastItemStackSlot1)
                                || !ItemStack.areEqual(cursorStack, lastCursorStack)) {
                            enchantTryFlag = true;
                            lastItemStackSlot0 = currentItemStackSlot0.copy();
                            lastItemStackSlot1 = currentItemStackSlot1.copy();
                            lastCursorStack = cursorStack.copy();
                            lastLevelCost = anvilScreenHandler.getLevelCost();
                            Random random = new Random();
                            enchantState = random.nextDouble() < enchantmentChance;
                        }

                        if (!ItemStack.areEqual(resultStack, lastItemStackSlot2)) {
                            lastItemStackSlot2 = resultStack.copy();
                        }
                    }
                }
            if (ModVariables.getInstance().getIsNetheriteAnvilOpen()) {
                lastLevelCost = (int) (lastLevelCost * 0.5);
            }
        });
    }


}
