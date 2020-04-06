package com.cainspencerm;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class StreamListener extends ListenerAdapter {

    // USER IS ONLINE OR IDLE AND STARTS STREAMING
    @Override
    public void onUserActivityStart(@NotNull UserActivityStartEvent event) {
        // Check the integrity of the boosted server guild and streaming role.
        if (Bot.boostedServer == null || Bot.currentlyStreaming == null) {
            try {
                Bot.boostedServer = event.getGuild();
                Bot.currentlyStreaming = event.getGuild().getRolesByName("Currently Streaming", false).get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Ignore bot events.
        if (event.getUser().isBot()) {
            return;
        }

        if (event.getNewActivity().getName().equalsIgnoreCase("Twitch")) {
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Currently Streaming", false).get(0)).queue();
        }
    }

    // USER IS ONLINE OR IDLE AND STOPS STREAMING
    @Override
    public void onUserActivityEnd(@NotNull UserActivityEndEvent event) {
        // Check the integrity of the boosted server guild and streaming role.
        if (Bot.boostedServer == null || Bot.currentlyStreaming == null) {
            try {
                Bot.boostedServer = event.getGuild();
                Bot.currentlyStreaming = event.getGuild().getRolesByName("Currently Streaming", false).get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Ignore bot events.
        if (event.getUser().isBot()) {
            return;
        }

        if (event.getOldActivity().getName().equalsIgnoreCase("Twitch")) {
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRolesByName("Currently Streaming", false).get(0)).queue();
        }
    }

    // USER IS ONLINE AND STREAMING, BUT GOES OFFLINE
    @Override
    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {
        // Check the integrity of the boosted server guild and streaming role.
        if (Bot.boostedServer == null || Bot.currentlyStreaming == null) {
            try {
                Bot.boostedServer = event.getGuild();
                Bot.currentlyStreaming = event.getGuild().getRolesByName("Currently Streaming", false).get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Ignore bot events.
        if (event.getUser().isBot()) {
            return;
        }

        if (event.getNewOnlineStatus() == OnlineStatus.OFFLINE ||
                event.getNewOnlineStatus() == OnlineStatus.DO_NOT_DISTURB ||
                event.getNewOnlineStatus() == OnlineStatus.INVISIBLE) {

            try {
                event.getMember().getRoles().remove(Bot.currentlyStreaming);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

}
