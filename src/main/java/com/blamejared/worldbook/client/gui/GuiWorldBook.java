package com.blamejared.worldbook.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiWorldBook extends GuiWorldSelection {
    
    
    private GuiTextField search;
    private SortOrder sortOrder = SortOrder.TIME_ASC;
    private String lastSearch = "";
    
    
    public GuiWorldBook(GuiScreen screenIn) {
        super(screenIn);
        this.prevScreen = screenIn;
    }
    
    
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        super.initGui();
        this.selectionList = new GuiListWorldBookSelection(this, this.mc, this.width, this.height, 60, this.height - 64, 36);
        search = new GuiTextField(0, mc.fontRenderer, width / 2 - (width / 3 / 2), 35, width / 3, 20);
        search.setMaxStringLength(Integer.MAX_VALUE);
        this.buttonList.add(new GuiButton(2906, search.x + search.width + 5, search.y, 80, 20, "Sort: " + sortOrder.getName()) {
            @Override
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                boolean b = super.mousePressed(mc, mouseX, mouseY);
                if(b) {
                    if(Mouse.isButtonDown(0))
                        sortOrder = sortOrder.cycleUp();
                    else if(Mouse.isButtonDown(1)){
                        sortOrder = sortOrder.cycleDown();
                    }
                    filter();
                    displayString = "Sort: " + sortOrder.getName();
                }
                return b;
                
            }
        });
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        fontRenderer.drawString("Search:", search.x - 5 - fontRenderer.getStringWidth("Search:"), search.y + search.height / 2 - fontRenderer.FONT_HEIGHT / 2, 0xFFFFFF);
        search.drawTextBox();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        search.updateCursorCounter();
        if(!lastSearch.equals(search.getText())) {
            filter();
        }
    }
    
    public void filter() {
        lastSearch = search.getText();
        GuiListWorldBookSelection list = getList();
        if(lastSearch.isEmpty() && sortOrder == SortOrder.TIME_ASC) {
            list.entries.clear();
            list.entries.addAll(list.allEntries);
            return;
        }
        list.entries.clear();
        for(GuiListWorldSelectionEntry entry : list.allEntries) {
            if(entry instanceof GuiListWorldBookSelectionEntry) {
                GuiListWorldBookSelectionEntry ent = (GuiListWorldBookSelectionEntry) entry;
                if(ent.summary.getDisplayName().toLowerCase().contains(lastSearch.toLowerCase())) {
                    list.entries.add(ent);
                }
            }
        }
        sortOrder.sort(list.entries);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if(search.isFocused()) {
            search.textboxKeyTyped(typedChar, keyCode);
        }
    }
    
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        search.mouseClicked(mouseX, mouseY, mouseButton);
        if(search.isFocused()) {
            if(mouseButton == 1) {
                search.setText("");
            }
        }
        
        if(mouseButton == 1) {
            for(GuiButton button : buttonList) {
                if(button.id == 2906) {
                    if(button.mousePressed(this.mc, mouseX, mouseY)) {
                        net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, button, this.buttonList);
                        if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                            break;
                        button = event.getButton();
                        this.selectedButton = button;
                        button.playPressSound(this.mc.getSoundHandler());
                        this.actionPerformed(button);
                        if(this.equals(this.mc.currentScreen))
                            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), this.buttonList));
                    }
                }
            }
        }
    }
    
    
    public GuiListWorldBookSelection getList() {
        return (GuiListWorldBookSelection) this.selectionList;
    }
    
}
