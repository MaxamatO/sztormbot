package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.ICommand;
import com.company.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        VoiceChannel voiceChannel = ctx.getMember().getVoiceState().getChannel();
        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.openAudioConnection(voiceChannel);
        List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        String link = args.get(0);

        try{
            Thread.sleep(3000);
            if (!isUrl(link)) {
                System.out.println("przeszlo ifa");
                link = "ytsearch: " + link;
            }

            final Member self = ctx.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();



            final Member member = ctx.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
                return;
            }

            PlayerManager.getInstance().loadAndPlay(channel, link, "");
        } catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }

    private boolean isUrl(String url){
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "Puszcza muzyke z youtube na kanale\n"+
                "Uzycie `!!play <tytul autor>`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("play", "graj", "yt", "song");
    }
}
