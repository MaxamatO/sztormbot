package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.ICommand;
import com.company.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final AudioManager audioManager = ctx.getGuild().getAudioManager();

        if (!ctx.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
            channel.sendMessage("I CAN'T JOOOOOIN").queue();
            return;
        }
        VoiceChannel voiceChannel = ctx.getMember().getVoiceState().getChannel();
        if (voiceChannel == null) {
            channel.sendMessage("Mordo gdzie ty").queue();
            return;
        }
        audioManager.openAudioConnection(voiceChannel);

        Member selfMember = ctx.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();

        Member member = ctx.getMember();

        if (selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Juz tu jestem").queue();
            return;
        }
        PlayerManager.getInstance().loadAndPlay(channel, System.getenv("czesc"), "siema byki");
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Bot dolacza do kanalu glsoowego\n"+
                "Uzycie: `!!join`";
    }
}
