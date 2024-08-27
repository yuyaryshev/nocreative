package com.lomods.nocreativemod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class NoCreativeCommand {

    private static final String PASSWORD = ""; // Replace with your desired password

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("nocreative")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    toggleNoCreative(context.getSource(), player, null, "");
                    return 1;
                })
                .then(Commands.argument("lock", BoolArgumentType.bool())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            boolean lock = BoolArgumentType.getBool(context, "lock");
                            toggleNoCreative(context.getSource(), player, lock, "");
                            return 1;
                        })
                        .then(Commands.argument("player", StringArgumentType.string())
                                .executes(context -> {
                                    String playerName = StringArgumentType.getString(context, "player");
                                    ServerPlayer player = context.getSource().getServer().getPlayerList().getPlayerByName(playerName);
                                    if (player != null) {
                                        boolean lock = BoolArgumentType.getBool(context, "lock");
                                        toggleNoCreative(context.getSource(), player, lock, "");
                                    } else {
                                        context.getSource().sendFailure(Component.literal("Player not found!"));
                                    }
                                    return 1;
                                })
                                .then(Commands.argument("password", StringArgumentType.string())
                                        .executes(context -> {
                                            String playerName = StringArgumentType.getString(context, "player");
                                            ServerPlayer player = context.getSource().getServer().getPlayerList().getPlayerByName(playerName);
                                            if (player != null) {
                                                boolean lock = BoolArgumentType.getBool(context, "lock");
                                                String password = StringArgumentType.getString(context, "password");
                                                toggleNoCreative(context.getSource(), player, lock, password);
                                            } else {
                                                context.getSource().sendFailure(Component.literal("Player not found!"));
                                            }
                                            return 1;
                                        })))));
    }


    private static void toggleNoCreative(CommandSourceStack source, ServerPlayer player, Boolean lock, String password) {
        if (!PASSWORD.equals("") && !PASSWORD.equals(password)) {
            source.sendFailure(Component.literal("Incorrect password!"));
            return;
        }

        UUID playerId = player.getUUID();

        if (lock == null) {
            // Toggle lock if it's null (not provided)
            lock = !CommonClass.playerToggleMap.getOrDefault(playerId, true);
        }

        CommonClass.playerToggleMap.put(playerId, lock);

        // Create a final message variable to be used in the lambda
        final String message = "NoCreative mode " + (lock ? "enabled" : "disabled") + " for " + player.getName().getString();

        source.sendSuccess(() -> Component.literal(message), true);
    }
}
