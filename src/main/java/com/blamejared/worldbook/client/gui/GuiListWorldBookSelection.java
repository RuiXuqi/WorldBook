package com.blamejared.worldbook.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.*;
import net.minecraftforge.fml.relauncher.*;
import org.apache.logging.log4j.*;

import java.util.*;

@SideOnly(Side.CLIENT)
public class GuiListWorldBookSelection extends GuiListWorldSelection {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    public List<GuiListWorldSelectionEntry> allEntries = new ArrayList<>();
    
    /**
     * Index to the currently selected world
     */
    private int selectedIdx = -1;
    
    public GuiListWorldBookSelection(GuiWorldBook p_i46590_1_, Minecraft clientIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(p_i46590_1_, clientIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        entries.clear();
        refreshList();
    }
    
    public void refreshList() {
        allEntries = new ArrayList<>();
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        List<WorldSummary> list;
        
        try {
            list = isaveformat.getSaveList();
        } catch(AnvilConverterException anvilconverterexception) {
            LOGGER.error("Couldn't load level list", (Throwable) anvilconverterexception);
            this.mc.displayGuiScreen(new GuiErrorScreen(I18n.format("selectWorld.unable_to_load"), anvilconverterexception.getMessage()));
            return;
        }
        
        Collections.sort(list);
        
        for(WorldSummary worldsummary : list) {
            GuiListWorldBookSelectionEntry e = new GuiListWorldBookSelectionEntry(this, worldsummary, this.mc.getSaveLoader());
            this.entries.add(e);
            this.allEntries.add(e);
        }
    }
    
    protected int getSize() {
        return this.entries.size();
    }
    
    protected int getScrollBarX() {
        return super.getScrollBarX() + 20;
    }
    
    
    /**
     * Gets the width of the list
     */
    public int getListWidth() {
        return super.getListWidth() + 50;
    }
    
}