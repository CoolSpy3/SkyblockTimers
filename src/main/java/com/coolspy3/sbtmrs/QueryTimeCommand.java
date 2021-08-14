package com.coolspy3.sbtmrs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.function.Supplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.util.text.TextFormatting;

public class QueryTimeCommand extends TimeCommand {
    
    public final String url;

    public QueryTimeCommand(String event, String eventName, String url) {
        super(event, eventName, new TimeQuery(url));
        this.url = url;
    }

    public static class TimeQuery implements Supplier<String> {

        public final String url;

        public TimeQuery(String url) {
            this.url = url;
        }

        @Override
        public String get() {
            JsonObject json;
            try(InputStreamReader reader = SBTmrs.openURL(url)) {
                json = new JsonParser().parse(reader).getAsJsonObject();
            } catch(IOException e) {
                e.printStackTrace(System.err);
                return TextFormatting.RED + "<ERROR>";
            }
            Duration timeUntil = Duration.ofMillis(json.get("estimate").getAsLong() - System.currentTimeMillis());
            return SBTmrs.formatTimeUntil(timeUntil);
        }

    }

}
