package com.coolspy3.sbtmrs;

import java.util.function.Supplier;

import com.coolspy3.util.ModUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TimeCommand {
    
    public final String regex;
    public final String eventName;
    protected final Supplier<String> timeSupplier;

    public TimeCommand(String event, String eventName, Supplier<String> timeSupplier) {
        regex = "/" + event + "tmr( .*)?";
        this.eventName = eventName;
        this.timeSupplier = timeSupplier;
    }

    @SubscribeEvent
    public void register(ClientChatEvent event) {
        if(event.getMessage().matches(regex)) {
            event.setCanceled(true);
            Minecraft.getInstance().gui.getChat().addRecentChat(event.getMessage());
            ModUtil.executeAsync(() -> {
                ModUtil.sendMessage(TextFormatting.AQUA + eventName + " in " + timeSupplier.get());
            });
        }
    }

}
