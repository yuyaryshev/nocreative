// NoCreativeMod.java
package com.lomods.nocreativemod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(NoCreativeMod.MOD_ID)
public class NoCreativeMod {

    public static final String MOD_ID = "nocreativemod";
    public static final Logger LOGGER = LogManager.getLogger();

    public NoCreativeMod() {
        LOGGER.info("nocreativemod started!");

        // Register the event handler
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }
}
