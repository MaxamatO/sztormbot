package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.ICommand;
import com.company.lavaplayer.GuildMusicManager;
import com.company.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class QuitCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        VoiceChannel voiceChannel = ctx.getMember().getVoiceState().getChannel();
        Member selfMember = ctx.getGuild().getSelfMember();
        GuildVoiceState selfStateMember = selfMember.getVoiceState();
        Member member = ctx.getMember();
        GuildVoiceState stateMember = member.getVoiceState();

        if (voiceChannel == null) {
            channel.sendMessage("Nie moge wyjsc").queue();
            return;
        }

        if (!selfStateMember.inVoiceChannel()) {
            channel.sendMessage("Musze byc z toba, zeby to wykonac").queue();
            return;
        }

        if (!stateMember.getChannel().equals(selfStateMember.getChannel())) {
            channel.sendMessage("Musisz byc tu ze mna, zeby to wykonac").queue();
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

        musicManager.audioPlayer.stopTrack();

        MessageAction message = channel.sendMessage("Opuszczanie");
        message.queue(message1 -> message1.delete().queueAfter(7, TimeUnit.SECONDS));
        PlayerManager.getInstance().loadAndPlay(channel,System.getenv("trzymajciesze"));

        try {
            Thread.sleep(2500);
            audioManager.closeAudioConnection();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String getName() {
        return "quit";
    }

    @Override
    public String getHelp() {
        return "Bot opuszcza kanal glosowy\n"+
                "Uzycie: `!!quit`";
    }

    @Override
    public List<String> getAliases() {
        return List.of( "quit", "leave", "wyjdz", "opusc");
    }
}
