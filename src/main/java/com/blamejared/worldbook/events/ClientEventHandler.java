package com.blamejared.worldbook.events;

import com.blamejared.worldbook.client.gui.GuiWorldBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {
    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.getGui() instanceof GuiWorldSelection && !(event.getGui() instanceof GuiWorldBook)) {
            event.setGui(new GuiWorldBook(Minecraft.getMinecraft().currentScreen));
        }
    }
}
