package net.kemzino.cae.block;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class NetheriteAnvilBlock extends AnvilBlock {
    public NetheriteAnvilBlock() {
        super(FabricBlockSettings.copyOf(Blocks.ANVIL));
    }
}
