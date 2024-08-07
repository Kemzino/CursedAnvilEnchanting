package net.kemzino.cae.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
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
                        if (random.nextDouble() < enchantmentChance && isEnchantedBook(lastItemStackSlot1) || lastItemStackSlot1.isEnchantable()) {

                            resultStack.addEnchantment(getRandomCurseEnchantment(), 1);
                        }
                    }
                }
            }
        });
    }

    private static boolean isEnchantedBook(ItemStack stack) {
        if (stack.getItem() instanceof EnchantedBookItem) {
            Map enchantments = EnchantmentHelper.get(stack);
            return !enchantments.isEmpty();
        }
        return false;
    }

    public static List<Enchantment> getAllCurseEnchantments() {
        List<Enchantment> curseEnchantments = new ArrayList<>();

        for (Enchantment enchantment : Registries.ENCHANTMENT) {
            if (isCurseEnchantment(enchantment)) {
                curseEnchantments.add(enchantment);
            }
        }

        return curseEnchantments;
    }

    private static boolean isCurseEnchantment(Enchantment enchantment) {
        return enchantment.isCursed();
    }

    public static Enchantment getRandomCurseEnchantment() {
        List<Enchantment> curseEnchantments = getAllCurseEnchantments();
        if (curseEnchantments.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(curseEnchantments.size());
        return curseEnchantments.get(index);
    }
}
