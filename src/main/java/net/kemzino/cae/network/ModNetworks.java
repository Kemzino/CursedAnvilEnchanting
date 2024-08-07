package net.kemzino.cae.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kemzino.cae.CursedAnvilEnchanting;
import net.minecraft.util.Identifier;

public class ModNetworks {
    public static final Identifier ANVIL_ENCHANT_PACKET_ID = new Identifier(CursedAnvilEnchanting.MOD_ID, "anvil_enchant");

    public static void registerNetworks() {
        ServerPlayNetworking.registerGlobalReceiver(ANVIL_ENCHANT_PACKET_ID, AnvilEnchantC2SPacket::receive);

        CursedAnvilEnchanting.LOGGER.info("Registering Mod Events for " + CursedAnvilEnchanting.MOD_ID);
    }
}
