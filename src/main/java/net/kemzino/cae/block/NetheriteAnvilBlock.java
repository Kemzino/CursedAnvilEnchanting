package net.kemzino.cae.block;

import net.kemzino.cae.CursedAnvilEnchanting;
import net.kemzino.cae.global.ModVariables;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetheriteAnvilBlock extends AnvilBlock {

    public NetheriteAnvilBlock() {
        super(FabricBlockSettings.copyOf(Blocks.ANVIL)
                .strength(100.0f, 2000.0f));
    }

    @Override
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(6.0F, 120);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ModVariables.getInstance().setIsNetheriteAnvilOpen(true);

        if (!world.isClient) {
            NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return Text.translatable("container.repair");
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
                    return new AnvilScreenHandler(syncId, inventory) {

                        @Override
                        public void onClosed(PlayerEntity player) {
                            super.onClosed(player);
                            ModVariables.getInstance().setIsNetheriteAnvilOpen(false);
                            player.giveItemStack(this.input.getStack(0));
                            player.giveItemStack(this.input.getStack(1));
                        }

                        @Override
                        protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
                            super.onTakeOutput(player, stack);

                            if (world != null && !world.isClient) {
                                world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            }
                        }
                    };
                }
            };

            if (player instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) player).openHandledScreen(factory);
            }
        }
        return ActionResult.SUCCESS;
    }
}