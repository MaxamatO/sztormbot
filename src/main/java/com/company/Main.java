package com.company;

import jdk.jfr.Description;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDABuilder jda = JDABuilder.createDefault(System.getenv().get("TOKEN")).enableCache(CacheFlag.VOICE_STATE);
        jda.setActivity(Activity.watching("!!"));
        jda.addEventListeners(new Commands());
        jda.build();


    }
}
