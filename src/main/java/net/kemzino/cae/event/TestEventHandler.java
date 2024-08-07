package net.kemzino.cae.event;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.kemzino.cae.CursedAnvilEnchanting;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;

public class TestEventHandler {

    public static void register() {

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof AnvilScreen) {
                CursedAnvilEnchanting.LOGGER.info("Inside anvil");
                ScreenMouseEvents.afterMouseClick(screen).register((screen1, mouseX, mouseY, button) -> {
                    if (client.player != null && client.player.currentScreenHandler instanceof AnvilScreenHandler) {
                        AnvilScreenHandler handler = (AnvilScreenHandler) client.player.currentScreenHandler;
                        ItemStack result = handler.getSlot(2).getStack();
                        if (!result.isEmpty()) {
                            result.addEnchantment(Enchantments.UNBREAKING, 1);
                            if (client.player.getWorld().isClient) {
                                client.player.sendMessage(Text.literal("Unbreaking I have been added!"), false);
                            }
                        }
                    }
                });
            }
        });

    }
}
