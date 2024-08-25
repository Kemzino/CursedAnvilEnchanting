package net.kemzino.cae.mixin;

import net.kemzino.cae.config.ModConfigs;
import net.kemzino.cae.global.ModVariables;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {


    @Shadow @Final
    private Property levelCost;

    @Inject(method = "updateResult", at = @At("TAIL"))
    private void onUpdateResult(CallbackInfo ci) {
        if(ModVariables.getInstance().getIsNetheriteAnvilOpen()){
            this.levelCost.set((int) (this.levelCost.get() * ModConfigs.NETHERITE_ANVIL_LVL_MODIFICATION));
        }
        else {
            this.levelCost.set((int) (this.levelCost.get() * ModConfigs.ANVIL_LVL_MODIFICATION));
        }
    }
}
