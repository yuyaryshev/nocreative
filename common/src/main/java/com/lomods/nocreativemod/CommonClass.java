package com.lomods.nocreativemod;

import com.lomods.nocreativemod.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {
    public static final Map<UUID, Boolean> playerToggleMap = new HashMap<>();

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This exam ple has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this exam ple
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        if (Services.PLATFORM.isModLoaded("nocreativemod")) {

            Constants.LOG.info("Hello to nocreativemod");
        }
    }

    public static void onPlayerTick(Player player) {
        // Only run on the server side
        if (!player.level().isClientSide && player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;

            // Check if the player is in the toggle map and if the behavior is enabled
            if (playerToggleMap.getOrDefault(serverPlayer.getUUID(), true)) {
                // Check if the player is not in survival mode
                if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) {
                    // Set the player back to survival mode
                    serverPlayer.setGameMode(GameType.SURVIVAL);
                }
            }
        }
    }
}