package net.kemzino.cae.mixin;

import net.kemzino.cae.config.ModConfigs;
import net.kemzino.cae.global.ModVariables;
import net.kemzino.cae.service.EnchantmentService;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends HandledScreen<AnvilScreenHandler> {

    public AnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "drawForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"), cancellable = true)
    private void onDrawForeground(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        if(!ModVariables.getInstance().getIsNetheriteAnvilOpen()){
        AnvilScreenHandler handler = (AnvilScreenHandler) this.handler;
        int levelCost = handler.getLevelCost();
        ItemStack secondSlotStack = handler.getSlot(1).getStack();
        PlayerEntity player = MinecraftClient.getInstance().player;

        assert player != null;

        if (player.experienceLevel > levelCost || player.isCreative() && levelCost > ModConfigs.MIN_REQUIRED_LVL_TO_CURSE_ENCHANT && EnchantmentService.isEnchantedBook(secondSlotStack) || secondSlotStack.isEnchantable() || !secondSlotStack.getEnchantments().isEmpty()) {
            int chanceColor = ModConfigs.CHANCE_COLOR;
            int tinColor = ModConfigs.TIN_COLOR;

            double transparency = (double) (ModConfigs.CURSE_ENCHANT_CHANCE_MODIFICATION / 100.0) * levelCost * 1.5;
            if (transparency >= 1) {
                transparency = 1;
            }

            int greenR = (chanceColor >> 16) & 0xFF;
            int greenG = (chanceColor >> 8) & 0xFF;
            int greenB = chanceColor & 0xFF;

            int redR = (tinColor >> 16) & 0xFF;
            int redG = (tinColor >> 8) & 0xFF;
            int redB = tinColor & 0xFF;

            int mixedR = (int)((redR * transparency) + (greenR * (1 - transparency)));
            int mixedG = (int)((redG * transparency) + (greenG * (1 - transparency)));
            int mixedB = (int)((redB * transparency) + (greenB * (1 - transparency)));

            int mixedColor = (mixedR << 16) | (mixedG << 8) | mixedB;

            int originalX = 83 * 2 - this.textRenderer.getWidth(Text.translatable("container.repair.cost", levelCost));
            int originalY = 69;

            MutableText expCostText = Text.translatable("container.repair.cost", levelCost);

            expCostText.setStyle(Style.EMPTY.withColor(mixedColor));

            context.drawTextWithShadow(this.textRenderer, expCostText, originalX, originalY, mixedColor);
            context.drawText(this.textRenderer, expCostText, originalX, originalY, mixedColor, false);

            ci.cancel();
        }
    }}

}
