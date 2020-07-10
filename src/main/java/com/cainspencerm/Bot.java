package com.cainspencerm;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Bot {
    static Role currentlyStreaming;
    static Guild boostedServer;
    static String gameReactionMessageId;
    static Properties prop;
    static String delimiter = "?";

    public static void main(String[] args) {
        recursiveJDA(null);
    }

    private static void recursiveJDA(JDA jda) {
        // Open guild file.
        prop = new Properties();
        String fileName = "Guild.conf";
        InputStream is = null;

        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file.");
        }

        try {
            prop.load(is);
        } catch (IOException e) {
            System.err.println("Could not load properties.");
        }
        
        try {
            jda = JDABuilder
                    .create(
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.GUILD_PRESENCES,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MESSAGE_TYPING,
                            GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_EMOJIS,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                            GatewayIntent.DIRECT_MESSAGE_TYPING
                    ).setToken("Njk2MTM1MjE1OTkwNTcxMDY4.XokUhg.lFEXy-A8SohgzlSWlTL2O8m3fCc").setActivity(Activity.of(Activity.ActivityType.DEFAULT, "Are you streaming for Boosted?")).build();
            jda.addEventListener(new StreamListener());
            jda.addEventListener(new RoleSetter());
        } catch (LoginException e) {
            recursiveJDA(jda);
        }
        
        boostedServer = jda.getGuildById(prop.getProperty("boostedGuildId"));

        assert boostedServer != null;
        currentlyStreaming = boostedServer.getRolesByName("Currently Streaming", false).get(0);

        gameReactionMessageId = prop.getProperty("gameReactionMessageId");
    }
}
