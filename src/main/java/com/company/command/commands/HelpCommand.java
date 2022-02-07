package com.company.command.commands;

import com.company.command.CommandContext;
import com.company.command.CommandManager;
import com.company.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import java.lang.StringBuilder;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()){
            StringBuilder builder = new StringBuilder();
            builder.append("Lista komend\n");
            manager.getCommands().stream().map(ICommand::getName).forEach(
                    (it) -> builder.append('`').append(System.getenv("prefix")).append(it).append("`\n")
            );
            channel.sendMessage(builder.toString()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if(command == null){
            channel.sendMessage("Nie znalazlem nic dla: "+ search).queue();
            return;
        }
        channel.sendMessage(command.getHelp()).queue();

    }

    @Override
    public String getHelp() {
        return "Pokazuje liste dostepnych komend \n"+
                "Uzycie: `!!help <command>`";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<String> getAliases() {
        return List.of("commands", "cmds", "commandslist", "komendy", "pomocy", "pomoc", "hilfe");
    }
}
