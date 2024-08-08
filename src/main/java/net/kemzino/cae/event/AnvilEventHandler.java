package net.kemzino.cae.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
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

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.currentScreenHandler instanceof AnvilScreenHandler) {
                    AnvilScreenHandler anvilScreenHandler = (AnvilScreenHandler) player.currentScreenHandler;

                    ItemStack currentItemStackSlot0 = anvilScreenHandler.getSlot(0).getStack();
                    ItemStack currentItemStackSlot1 = anvilScreenHandler.getSlot(1).getStack();

                    if (!ItemStack.areEqual(currentItemStackSlot0, lastItemStackSlot0) || !ItemStack.areEqual(currentItemStackSlot1, lastItemStackSlot1)) {
                        enchantTryFlag = true;
                        lastItemStackSlot0 = currentItemStackSlot0.copy();
                        lastItemStackSlot1 = currentItemStackSlot1.copy();
                    }

                    ItemStack resultStack = anvilScreenHandler.getSlot(2).getStack();
                    double enchantmentChance = (double) (anvilScreenHandler.getLevelCost() * 2) / 100;

                    if (!resultStack.isEmpty() && enchantTryFlag) {
                        Random random = new Random();
                        enchantTryFlag = false;
                        if (random.nextDouble() < enchantmentChance || true && EnchantmentService.isEnchantedBook(lastItemStackSlot1) || lastItemStackSlot1.isEnchantable()) {
                            Enchantment enchantmentToApply = EnchantmentService.getRandomCurseEnchantmentFromList(EnchantmentService.getApplicableCurseEnchantments(resultStack));
                            resultStack.addEnchantment(enchantmentToApply, 1);
                        }
                    }
                }
            }
        });
    }


}
