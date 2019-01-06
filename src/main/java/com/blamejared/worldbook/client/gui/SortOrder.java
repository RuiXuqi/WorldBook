package com.blamejared.worldbook.client.gui;

import net.minecraft.client.gui.GuiListWorldSelectionEntry;

import java.util.*;

public enum SortOrder {
    TIME_ASC(entries -> {
        Collections.sort(entries, (o1, o2) -> o1.summary.compareTo(o2.summary));
    }), TIME_DESC(entries -> {
        Collections.sort(entries, (o1, o2) -> o2.summary.compareTo(o1.summary));
    }), AZ(entries -> {
        entries.sort((o1, o2) -> o1.summary.getDisplayName().compareToIgnoreCase(o2.summary.getDisplayName()));
    }), ZA(entries -> {
        entries.sort((o1, o2) -> o2.summary.getDisplayName().compareToIgnoreCase(o1.summary.getDisplayName()));
    });
    
    private ISort sorter;
    
    SortOrder(ISort sorter) {
        this.sorter = sorter;
    }
    
    public SortOrder cycleUp() {
        int i = this.ordinal();
        if(i + 1 < SortOrder.values().length) {
            i++;
        } else {
            i = 0;
        }
        return SortOrder.values()[i];
    }
    
    public SortOrder cycleDown() {
        int i = this.ordinal();
        if(i - 1 >= 0) {
            i--;
        } else {
            i = SortOrder.values().length - 1;
        }
        return SortOrder.values()[i];
    }
    
    
    public void sort(List<GuiListWorldSelectionEntry> list) {
        List<GuiListWorldBookSelectionEntry> list1 = new ArrayList<>();
        for(GuiListWorldSelectionEntry entry : list) {
            if(entry instanceof GuiListWorldBookSelectionEntry) {
                list1.add((GuiListWorldBookSelectionEntry) entry);
            }
        }
        this.sorter.sort(list1);
        list.clear();
        list.addAll(list1);
    }
    
    public String getName() {
        switch(this) {
            default:
            case TIME_ASC:
                return "Time Asc";
            case TIME_DESC:
                return "Time Desc";
            case AZ:
                return "A->Z";
            case ZA:
                return "Z->A";
        }
    }
}
