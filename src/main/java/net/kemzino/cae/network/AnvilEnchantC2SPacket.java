package net.kemzino.cae.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.kemzino.cae.CursedAnvilEnchanting;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class AnvilEnchantC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if (player.currentScreenHandler instanceof AnvilScreenHandler) {
                AnvilScreenHandler anvilHandler = (AnvilScreenHandler) player.currentScreenHandler;
                ItemStack result = anvilHandler.getSlot(2).getStack();
                CursedAnvilEnchanting.LOGGER.info("Result1: " + result.getEnchantments());
                if (!result.isEmpty()) {
                    result.addEnchantment(Enchantments.UNBREAKING, 1);
                    CursedAnvilEnchanting.LOGGER.info("Result2: " + result.getEnchantments());
                    player.sendMessage(Text.literal("Unbreaking I додано до предмету!"), false);
                    anvilHandler.sendContentUpdates();
                    CursedAnvilEnchanting.LOGGER.info("Added Unbreaking I to item on server");
                }
            }
        });
    }
}