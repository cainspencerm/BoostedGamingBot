package com.cainspencerm;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


import java.util.Objects;

import static com.cainspencerm.Bot.boostedServer;
import static com.cainspencerm.Bot.delimiter;

public class RoleSetter extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (Bot.boostedServer == null || Bot.currentlyStreaming == null) {
            try {
                Bot.boostedServer = event.getGuild();
                Bot.currentlyStreaming = event.getGuild().getRolesByName("Currently Streaming", false).get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        if (event.getMessage().getContentRaw().contains(delimiter + "add")) {
            if (event.getMessage().getContentRaw().contains("rl")) {
                try {
                    boostedServer.addRoleToMember(Objects.requireNonNull(event.getMember()), boostedServer.getRolesByName("Boosted Rocket League", false).get(0)).queue();
                    event.getChannel().sendMessage("Added Boosted Rocket League role to <@" + event.getAuthor().getId() + ">!").queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        if (event.getMessage().getContentRaw().contains(delimiter + "remove")) {
            if (event.getMessage().getContentRaw().contains("rl")) {
                try {
                    boostedServer.removeRoleFromMember(Objects.requireNonNull(event.getMember()), boostedServer.getRolesByName("Boosted Rocket League", false).get(0)).queue();
                    event.getChannel().sendMessage("Removed Boosted Rocket League from to <@" + event.getAuthor().getId() + ">!").queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
