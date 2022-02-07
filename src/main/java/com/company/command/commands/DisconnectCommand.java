package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class DisconnectCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();
        final Member target = message.getMentionedMembers().get(0);

        if(message.getMentionedMembers().isEmpty()){
            channel.sendMessage("Nie wiem kogo rozlaczyc").queue();
            return;
        }

        if(!member.canInteract(target) || !member.hasPermission(Permission.VOICE_MOVE_OTHERS)){
            channel.sendMessage("Nie mozesz go rozlaczyc").queue();
            return;
        }
        final Member selfMember = ctx.getSelfMember();

        /*if(!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.VOICE_MOVE_OTHERS)){
            channel.sendMessage("Nie moge  go rozlaczyc").queue();
            return;
        }*/

         ctx.getGuild()
                 .kickVoiceMember(target)
                 .queue(
                         (__) -> channel.sendMessage("Wyrzucono").queue(),
                         (error) -> channel.sendMessageFormat("Nie udalo sie wyrzycuc %s", error.getMessage()).queue()
                 );



    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "Wyrzuc uzytkownika z kanalu.\n"+
                "Uzycie: `!!kick <@user>`";
    }
}
