package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.ICommand;
import com.company.lavaplayer.GuildMusicManager;
import com.company.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import sun.jvm.hotspot.opto.Block;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("ConstantConditions")
public class StopCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final GuildMusicManager guildMusicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = guildMusicManager.audioPlayer;
        final BlockingQueue<AudioTrack> queue =  guildMusicManager.scheduler.queue;

        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }
        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        if(queue.isEmpty()){
            channel.sendMessage("Nie ma niczego w kolejce").queue();
            return;
        }
        if(audioPlayer.getPlayingTrack()==null){
            channel.sendMessage("Nic nie jest grane").queue();
            return;
        }

        queue.clear();
        audioPlayer.stopTrack();

        channel.sendMessage("Zatrzymano i wyczyszczono").queue();

    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "Zatrzymuje i czysci playliste\n"+
                "Uzycie: `!!stop`";
    }
}
