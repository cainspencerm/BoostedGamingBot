package com.cainspencerm;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


import java.util.Objects;

import static com.cainspencerm.Bot.*;

public class RoleSetter extends ListenerAdapter {
    String[] roles = {"CS:GO", "Valorant", "PUBG", "GTA V", "Overwatch", "Sims",
            "Sea of Thieves", "Borderlands", "Scribbl.io", "GTFO", "Animal Crossing",
            "Destiny", "RPGs", "Rust", "Minecraft", "Rocket League", "Modern Warfare"};

    String[] games = {"csgo", "valorant", "pubg", "gtav", "overwatch", "sims", "seaofthieves", "borderlands", "scribblio", "gtfo", "animalcrossing", "destiny", "rpgs", "rust", "minecraft", "rl", "modernwarfare"};

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if (Bot.gameReactionMessageId.isEmpty() && event.getMember().getUser().getAsTag().equalsIgnoreCase("BG Mint#8834")) {
            gameReactionMessageId = event.getMessageId();
            Bot.prop.replace("gameReactionMessageId", event.getMessageId());
        }

        if (event.getUser().isBot()) return;

        for (int i = 0; i < games.length; i++) {
            if (event.getReactionEmote().getAsCodepoints().equalsIgnoreCase(":" + games[i] + ":")) {
                // Add role to member.
                boostedServer.addRoleToMember(event.getMember(), boostedServer.getRolesByName(roles[i], true).get(0));
                break;
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        if (Bot.gameReactionMessageId.isEmpty() && event.getMember().getUser().getAsTag().equalsIgnoreCase("BG Mint#8834")) {
            gameReactionMessageId = event.getMessageId();
            Bot.prop.replace("gameReactionMessageId", event.getMessageId());
        }

        if (event.getUser().isBot()) return;

        for (int i = 0; i < games.length; i++) {
            if (event.getReactionEmote().getAsCodepoints().equalsIgnoreCase(":" + games[i] + ":")) {
                // Add role to member.
                boostedServer.removeRoleFromMember(event.getMember(), boostedServer.getRolesByName(roles[i], true).get(0));
                break;
            }
        }
    }
}
