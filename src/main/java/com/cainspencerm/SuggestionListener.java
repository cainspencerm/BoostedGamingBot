package com.cainspencerm;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class SuggestionListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        if (!event.getChannel().getName().equals("suggestions"))
            return;

        if (!event.getMessage().getContentRaw().contains("!anon"))
            return;

        // Anonymous message from user in #suggestions.
        String message = "Anonymous message: " + event.getMessage().getContentRaw().substring(6);
        event.getMessage().delete().queue();

        event.getGuild().getTextChannelsByName("anonymous-suggestions", true).get(0).sendMessage(message).queue();
    }
}
