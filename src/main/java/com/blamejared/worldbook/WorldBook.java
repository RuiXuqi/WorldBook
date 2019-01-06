package com.blamejared.worldbook;

import com.blamejared.worldbook.events.ClientEventHandler;
import com.blamejared.worldbook.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, clientSideOnly = true)
public class WorldBook {
    
    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}
