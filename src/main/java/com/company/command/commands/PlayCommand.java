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

@SuppressWarnings("ConstantConditions")
public class PlayCommand implements ICommand {


    @Override
    public void handle(CommandContext ctx) {
        VoiceChannel voiceChannel = ctx.getMember().getVoiceState().getChannel();
        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        audioManager.openAudioConnection(voiceChannel);
        List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        String user = ctx.getMember().toString();
        String link = args.toString();
        String name = link;

        try{
            Thread.sleep(3000);
            if (!isUrl(link)) {
                System.out.println("przeszlo ifa");
                name = link;
                link = "ytsearch: " + link;
            }
            System.out.println(name);

            final Member self = ctx.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();



            final Member member = ctx.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
                return;
            }


            PlayerManager.getInstance().loadAndPlay(channel, link, user);
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
