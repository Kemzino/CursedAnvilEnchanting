package net.kemzino.cae.service;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnchantmentService {
    public static boolean isEnchantedBook(ItemStack stack) {
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

    public static List<Enchantment> getApplicableCurseEnchantments(ItemStack itemStack) {
        List<Enchantment> applicableCurseEnchantments = new ArrayList<>();
        List<Enchantment> curseEnchantments = getAllCurseEnchantments();

        for (Enchantment enchantment : curseEnchantments) {
            if (enchantment.isAcceptableItem(itemStack)) {
                applicableCurseEnchantments.add(enchantment);
            }
        }

        return applicableCurseEnchantments;
    }

    public static boolean isCurseEnchantment(Enchantment enchantment) {
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
    public static Enchantment getRandomCurseEnchantmentFromList(List<Enchantment> curseEnchantments) {
        if (curseEnchantments.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(curseEnchantments.size());
        return curseEnchantments.get(index);
    }
}
