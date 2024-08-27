// PlayerEventHandler.java
package com.lomods.nocreativemod;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NoCreativeMod.MOD_ID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // Only run on the server side
        if (!player.level().isClientSide && player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;

            // Check if the player is not in survival mode
            if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) {
                // Set the player back to survival mode
                serverPlayer.setGameMode(GameType.SURVIVAL);
            }
        }
    }
}
