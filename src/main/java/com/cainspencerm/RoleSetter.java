package com.cainspencerm;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.cainspencerm.Bot.*;

public class RoleSetter extends ListenerAdapter {
    String[] roles = {"CS:GO", "Valorant", "PUBG", "GTA V", "Overwatch", "Sims",
            "Sea of Thieves", "Borderlands", "Scribbl.io", "GTFO", "Animal Crossing",
            "Destiny", "RPGs", "Rust", "Minecraft", "Rocket League", "Modern Warfare"};

    String[] games = {"csgo", "valorant", "pubg", "gtav", "overwatch", "sims",
            "seaofthieves", "borderlands", "scribblio", "gtfo", "animalcrossing",
            "destiny", "rpgs", "rust", "minecraft", "rl", "modernwarfare"};

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;

        for (int i = 0; i < games.length; i++) {
            if (event.getReactionEmote().getName().equalsIgnoreCase(games[i])) {
                // Add role to member.
                try {
                    Member member = event.getMember();
                    List<Role> roleList = null;
                    while (roleList == null || roleList.isEmpty()) {
                        try {
                            System.out.println("Rolelist troubleshooting.");
                            roleList = boostedServer.getRolesByName(roles[i], true);
                        } catch (Exception ignore) {
                            roleList = null;
                        }
                    }

                    Role role = roleList.get(0);
                    boostedServer.addRoleToMember(member, role).queue();
                    event.getChannel().sendMessage("Added role to " + member.getAsMention()).queue();

                    String messageId = event.getChannel().getLatestMessageId();

                    boostedServer.getTextChannelsByName("game-setter", true)
                            .get(0)
                            .deleteMessageById(messageId)
                            .queueAfter(1, TimeUnit.MINUTES);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Role not added to member.
        event.getChannel().sendMessage("Could not add role to " + event.getUser().getAsMention() + ". Try again in a few seconds.").queue();

        String messageId = event.getChannel().getLatestMessageId();

        boostedServer.getTextChannelsByName("game-setter", true)
                .get(0)
                .deleteMessageById(messageId)
                .queueAfter(1, TimeUnit.MINUTES);
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        if (event.getUser().isBot()) return;

        for (int i = 0; i < games.length; i++) {
            if (event.getReactionEmote().getName().equalsIgnoreCase(games[i])) {
                // Remove role from member.
                try {
                    Member member = event.getMember();
                    List<Role> roleList = null;
                    while (roleList == null || roleList.isEmpty()) {
                        try {
                            System.out.println("Rolelist troubleshooting.");
                            roleList = boostedServer.getRolesByName(roles[i], true);
                        } catch (Exception ignore) {
                            roleList = null;
                        }
                    }

                    Role role = roleList.get(0);
                    boostedServer.removeRoleFromMember(member, role).queue();
                    event.getChannel().sendMessage("Removed role from " + event.getUser().getAsMention()).queue();

                    String messageId = event.getChannel().getLatestMessageId();

                    boostedServer.getTextChannelsByName("game-setter", true)
                            .get(0)
                            .deleteMessageById(messageId)
                            .queueAfter(1, TimeUnit.MINUTES);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Role not removed from member.
        event.getChannel().sendMessage("Could not remove role from " + event.getUser().getAsMention() + ". Try again in a few seconds.").queue();

        String messageId = event.getChannel().getLatestMessageId();

        boostedServer.getTextChannelsByName("game-setter", true)
                .get(0)
                .deleteMessageById(messageId)
                .queueAfter(1, TimeUnit.MINUTES);
    }
}
