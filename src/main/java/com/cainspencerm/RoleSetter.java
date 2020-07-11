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
        if (!event.getMessageId().equals(gameReactionMessageId)) return;

        for (String game : games) {
            if (event.getReactionEmote().getName().equalsIgnoreCase(game)) {
                // Add role to member.
                try {
                    Member member = event.getMember();

                    System.out.println("Rolelist troubleshooting started.");

                    Role role = boostedServer.getRoleById(prop.getProperty("game." + game));
                    boostedServer.addRoleToMember(member, role).queue();

                    System.out.println("Rolelist troubleshooting completed.");

                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        if (event.getUser().isBot()) return;
        if (!event.getMessageId().equals(gameReactionMessageId)) return;

        for (String game : games) {
            if (event.getReactionEmote().getName().equalsIgnoreCase(game)) {
                // Remove role from member.
                try {
                    Member member = event.getMember();

                    System.out.println("Rolelist troubleshooting started.");

                    Role role = boostedServer.getRoleById(prop.getProperty("game." + game));
                    boostedServer.removeRoleFromMember(member, role).queue();

                    System.out.println("Rolelist troubleshooting completed.");

                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
