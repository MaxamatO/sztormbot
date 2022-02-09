//TODO: Granie z spotify
package com.company;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;


import javax.security.auth.login.LoginException;


public class Main {

    private Main() throws LoginException{
        JDABuilder jda = JDABuilder.createDefault(System.getenv("TOKEN"));
        jda.addEventListeners(new Listener())
            .setActivity(Activity.watching("!!"))
            .build();
    }

    public static void main(String[] args) throws LoginException {
        new Main();

    }
}
