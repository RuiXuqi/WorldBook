package com.blamejared.worldbook.client.gui;

import net.minecraft.client.gui.GuiListWorldSelectionEntry;

import java.util.List;

public interface ISort {
    
    void sort(List<GuiListWorldBookSelectionEntry> entries);
}
