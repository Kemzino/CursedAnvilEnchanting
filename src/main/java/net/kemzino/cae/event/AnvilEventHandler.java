package net.kemzino.cae.event;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.AnvilBlock;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class AnvilEventHandler {

    public static void register() {

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof AnvilBlock) {
                if (!world.isClient) {
                    player.sendMessage(Text.literal("You opened an anvil!"), false);
                }
            }
            return ActionResult.PASS;
        });
    }
}
