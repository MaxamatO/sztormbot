package com.company;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDABuilder jda = JDABuilder.createDefault(System.getenv("TOKEN")).enableCache(CacheFlag.VOICE_STATE);
        jda.setActivity(Activity.watching("!!"));
        jda.addEventListeners(new Commands());
        jda.build();


    }
}
