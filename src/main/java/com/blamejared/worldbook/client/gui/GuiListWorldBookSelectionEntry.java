package com.blamejared.worldbook.client.gui;

import net.minecraft.client.gui.GuiListWorldSelectionEntry;
import net.minecraft.world.storage.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiListWorldBookSelectionEntry extends GuiListWorldSelectionEntry {
    
    public WorldSummary summary;
    
    public GuiListWorldBookSelectionEntry(GuiListWorldBookSelection listWorldSelIn, WorldSummary worldSummaryIn, ISaveFormat saveFormat) {
        super(listWorldSelIn, worldSummaryIn, saveFormat);
        this.summary = worldSummaryIn;
    }
    
}