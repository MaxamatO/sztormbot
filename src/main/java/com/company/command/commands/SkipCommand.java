package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.ICommand;
import com.company.lavaplayer.GuildMusicManager;
import com.company.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

@SuppressWarnings("ConstantConditions")
public class SkipCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();

        Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        Member member = ctx.getMember();
        final GuildVoiceState guildVoiceState = member.getVoiceState();

        if(!selfVoiceState.inVoiceChannel()){
            channel.sendMessage("Nie jestem na kanale").queue();
            return;
        }

        if(!guildVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Nie jestem z toba na kanale").queue();
            return;
        }

        if(!guildVoiceState.inVoiceChannel()){
            channel.sendMessage("Nie jestes na zadnym kanale").queue();
            return;
        }

        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
        AudioPlayer audioPlayer = guildMusicManager.audioPlayer;

        if(audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("Nic nie jest grane").queue();
            return;
        }

        guildMusicManager.scheduler.nextTrack();
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "Przewija piosenke\n"+
                "Uzycie: `!!skip`";
    }
}
