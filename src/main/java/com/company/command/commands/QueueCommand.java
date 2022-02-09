package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.ICommand;
import com.company.lavaplayer.GuildMusicManager;
import com.company.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("ConstantConditions")
public class QueueCommand implements ICommand {


    @Override
    public void handle(CommandContext ctx) {
        final Member self = ctx.getGuild().getSelfMember();
        TextChannel channel = ctx.getChannel();
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

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if(queue.isEmpty()){
            channel.sendMessage("Kolejka jest pusta").queue();
            return;
        }

        final int trackCount = Math.min(queue.size(), 20);
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        MessageAction messageAction = channel.sendMessage("**Teraz w kolejce:**\n");
        for(int i = 0; i < trackCount; i++){
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            messageAction.append("`#")
                    .append(String.valueOf(i+1))
                    .append(" ")
                    .append(" by ")
                    .append(info.author)
                    .append("(")
                    .append(info.title)
                    .append(")")
                    .append("`\n");
        }

        if(trackList.size()>trackCount){
            messageAction.append("Oraz `")
                    .append(String.valueOf(trackList.size() - trackCount))
                    .append("` wiecej...");
        }
        messageAction.queue();
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        return "Pokazuje piosenek w kolejce\n"+
                "Uzycie: `!!queue`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("q", "kolejka", "piosenki");
    }
}
