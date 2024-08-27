package com.lomods.nocreativemod;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod(NoCreativeMod.MOD_ID)
public class NoCreativeMod {

    public static final String MOD_ID = "nocreativemod";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Map<UUID, Boolean> playerToggleMap = new HashMap<>();

    public NoCreativeMod() {
        LOGGER.info("nocreativemod started!");
        CommonClass.init();

        // Register the event handler
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        NoCreativeCommand.register(dispatcher);
    }
}
