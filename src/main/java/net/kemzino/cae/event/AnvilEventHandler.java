package net.kemzino.cae.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.kemzino.cae.CursedAnvilEnchanting;
import net.kemzino.cae.service.EnchantmentService;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class AnvilEventHandler {

    private static boolean enchantTryFlag = true;
    private static ItemStack lastItemStackSlot0 = ItemStack.EMPTY;
    private static ItemStack lastItemStackSlot1 = ItemStack.EMPTY;
    private static ItemStack lastItemStackSlot2 = ItemStack.EMPTY;
    private static ItemStack lastCursorStack = ItemStack.EMPTY;
    private static boolean enchantState = false;
    private static final double chanceModificator = 2.5;

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.currentScreenHandler instanceof AnvilScreenHandler) {
                    AnvilScreenHandler anvilScreenHandler = (AnvilScreenHandler) player.currentScreenHandler;

                    ItemStack currentItemStackSlot0 = anvilScreenHandler.getSlot(0).getStack();
                    ItemStack currentItemStackSlot1 = anvilScreenHandler.getSlot(1).getStack();
                    ItemStack resultStack = anvilScreenHandler.getSlot(2).getStack();
                    ItemStack cursorStack = player.currentScreenHandler.getCursorStack();

                    double enchantmentChance = (double) (anvilScreenHandler.getLevelCost() * chanceModificator) / 100;


                    if (resultStack.isEmpty() && enchantTryFlag && ItemStack.areItemsEqual(cursorStack, lastItemStackSlot2)) {

                        enchantTryFlag = false;
                        if (enchantState && EnchantmentService.isEnchantedBook(lastItemStackSlot1) || lastItemStackSlot1.isEnchantable()) {

                            Enchantment enchantmentToApply = EnchantmentService.getRandomCurseEnchantmentFromList(EnchantmentService.getApplicableCurseEnchantments(cursorStack));
                            cursorStack.addEnchantment(enchantmentToApply, 1);

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
                        Random random = new Random();
                        enchantState = random.nextDouble() < enchantmentChance;
                    }

                    if (!ItemStack.areEqual(resultStack, lastItemStackSlot2)) {
                        lastItemStackSlot2 = resultStack.copy();
                    }
                }
            }
        });
    }


}
