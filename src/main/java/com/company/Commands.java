package com.company;


import com.company.lavaplayer.GuildMusicManager;
import com.company.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;


public class Commands extends ListenerAdapter {

    private final String[] commands = {"!!join", "!!quit", "!!help", "!!pomocy", "!!legends", "!!aurora", "!!klasa", "!!mkit", "!!test", "!!louda", "!!play", "!!yyy",
    "!!sprawdzam", "!!olivka", "!!damage", "!!stop", "!!skip", "!!queue", "!!ja", "!!spierdalaj", "!!yt", "!!leave"};
    private final String BINDY = "C:\\Users\\Maks\\home\\_java\\discord\\sztorm\\src\\main\\resources\\bindy\\";
    public String prefix = "!!";

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "!!")
                .split("\\s+");


        final TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();


        if (event.getAuthor().isBot()) {
            return;
        }
        if (args[0].equalsIgnoreCase(prefix) && args[1].isEmpty()) {
            channel.sendMessage("Zapomniales czegos").queue();
            return;
        }

        //Testuje przyjaciela
        if ((args[0]).equalsIgnoreCase(prefix + "test")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Co jest tytuł");
            embed.setDescription("Co jest opis");
            event.getChannel().sendMessage("siema tu kiki czego ci trzeba").queue();
            event.getChannel().sendMessage(embed.build()).queue();
            event.getChannel().canTalk();
            embed.clear();
        }
        //Przywoluje przyjaciela
        if ((args[0]).equalsIgnoreCase(prefix + "join")) {
            join(event, audioManager, channel);
        }
        //Legendsy
        if (args[0].equalsIgnoreCase(prefix + "legends")) {
            legends(event, channel);
        }
        //Odwoluje przyjaciela
        if (args[0].equalsIgnoreCase(prefix + "quit")||args[0].equalsIgnoreCase(prefix + "leave")||args[0].equalsIgnoreCase(prefix + "spierdalaj")) {
            quit(event, audioManager, channel);
        }
        //Aurora
        if (args[0].equalsIgnoreCase(prefix + "aurora")) {
            aurora(event, channel);
        }
        //MKIT
        if (args[0].equalsIgnoreCase(prefix + "mkit")) {
            mkit(event, channel);
        }
        //Klasa
        if (args[0].equalsIgnoreCase(prefix + "klasa")) {
            klasa(event, channel);
        }
        //Pomoc
        if (args[0].equalsIgnoreCase(prefix + "help") || (args[0].equalsIgnoreCase(prefix + "pomocy"))) {
            help(commands, channel);
        }
        //Louda
        if (args[0].equalsIgnoreCase(prefix + "louda")) {
            louda(event, channel);
        }
        //yyyyyyyy
        if (args[0].equalsIgnoreCase((prefix + "yyy"))) {
            yyy(event, channel);
        }
        //sprawdzam
        if(args[0].equalsIgnoreCase(prefix+"sprawdzam")){
            sprawdzam(event, channel);
        }
        //Olivka
        if(args[0].equalsIgnoreCase(prefix+"olivka")){
            olivka(event, channel);
        }
        //EMOTIONAL DAAAMAGE
        if (args[0].equalsIgnoreCase(prefix+"damage")) {
            damage(event, channel);
        }
        //Yt
        if (args[0].equalsIgnoreCase((prefix + "play"))) {
            join(event, audioManager, channel);
            String link = "";
            if(args.length>2){
                String[] songName = Arrays.copyOfRange(args, 1, args.length);
                link = Arrays.toString(songName);
            }
            else{
                link = args[1];
            }
            yt(event, args, channel, link);
        }
        //Stop
        if(args[0].equalsIgnoreCase(prefix+ "stop")){
            stop(event, channel);
        }
        //Skip
        if(args[0].equalsIgnoreCase(prefix+ "skip")){
            skip(event, channel);
        }
        //Teraz jest grane
        if(args[0].equalsIgnoreCase((prefix+ "queue"))){
            q(event, channel);
        }
        //Ja
        if (args[0].equalsIgnoreCase(prefix+"ja")) {
            final String messageId = event.getMessageId();
            channel.deleteMessageById(messageId);
            channel.sendMessage("Ja pytalem").queue();
        }
        // Brak
        if (args[0].toCharArray()[0] == prefix.toCharArray()[0] && !(Arrays.toString(commands).contains(args[0]))) {
            channel.sendMessage("nie znam").queue();
            help(commands, channel);
        }


        }

    @SuppressWarnings("ConstantConditions")
    private void louda (@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze być z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "loudac.mp3", "louda");
    }

    @SuppressWarnings("ConstantConditions")
    private void join (@NotNull GuildMessageReceivedEvent event, AudioManager audioManager, TextChannel channel){
        if (!event.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)) {
            channel.sendMessage("I CAN'T JOOOOOIN").queue();
            return;
        }
        VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
        if (voiceChannel == null) {
            channel.sendMessage("Mordo gdzie ty").queue();
            return;
        }
        audioManager.openAudioConnection(voiceChannel);

        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();

        Member member = event.getMember();

        if (selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Juz tu jestem").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "czesc.mp3");

    }

    @SuppressWarnings("ConstantConditions")
    private void legends (@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze być z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "legends.mp3", "mobajl legends");
    }

    @SuppressWarnings("ConstantConditions")
    private void aurora (@NotNull GuildMessageReceivedEvent event, TextChannel channel){


        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze być z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "aurora.mp3", "aurora");
    }
    @SuppressWarnings("ConstantConditions")
    private void mkit (@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze być z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "MKIT.mp3", "MKIT-900 PRO");
    }

    @SuppressWarnings("ConstantConditions")
    private void klasa (@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "pierwszaklasa.mp3", "Lekcja z Patrysiem");
    }

    @SuppressWarnings("ConstantConditions")
    private void quit (@NotNull GuildMessageReceivedEvent event, AudioManager audioManager, TextChannel channel){
        VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();

        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState selfStateMember = selfMember.getVoiceState();

        Member member = event.getMember();
        GuildVoiceState stateMember = member.getVoiceState();

        if (voiceChannel == null) {
            channel.sendMessage("I CAN'T LEEAAAAVEEE").queue();
            return;
        }

        if (!selfStateMember.inVoiceChannel()) {
            channel.sendMessage("Głupi czy głupi?").queue();
            return;
        }

        if (!stateMember.getChannel().equals(selfStateMember.getChannel())) {
            channel.sendMessage("I CAN'T LEEAAAAVEEE").queue();
            return;
        }
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

        musicManager.audioPlayer.stopTrack();

        channel.sendMessage("To spierdalam").queue();
        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "trzymajciesze.mp3");
        try {
            Thread.sleep(2500);
            audioManager.closeAudioConnection();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        stop(event, channel);
    }

    @SuppressWarnings("ConstantConditions")
    private void help (String[]commands, TextChannel channel){

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.green);
        embedBuilder.setTitle("Cos ci nie poszlo");
        embedBuilder.setDescription("Dostepne komendy to: " + Arrays.toString(commands));
        channel.sendMessage(embedBuilder.build()).queue();

    }

    @SuppressWarnings("ConstantConditions")
    private void yyy(@NotNull GuildMessageReceivedEvent event, TextChannel channel) {
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "yy.mp3", "yyyyyyyy");
}

    @SuppressWarnings("ConstantConditions")
    private void sprawdzam(@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "sprawdzam.mp3", "sprawdzam!");
    }

    @SuppressWarnings("ConstantConditions")
    private void olivka(@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "olivka.mp3", "OLIVKA BRASIL!");
    }
    @SuppressWarnings("ConstantConditions")
    private void damage(@NotNull GuildMessageReceivedEvent event, TextChannel channel) {
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, BINDY + "damage.mp3", "fajny chinol");
    }

    private void yt (@NotNull GuildMessageReceivedEvent event, String[]args, TextChannel channel, String link){
        if (!isUrl(link)) {
            System.out.println("przeszlo ifa");
            link = "ytsearch: " + link;
        }

        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze być z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musisz byc gdzies, co ja jestem wrozka?").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("Mordo ale musisz byc tam gdzie ja").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link, "");

    }

    private void stop(@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
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

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();

        channel.sendMessage("Zatrzymano i wyczyszczono").queue();
    }

    public void skip(@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
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

        if(audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("Nic nie jest grane").queue();
            return;
        }

        musicManager.scheduler.nextTrack();


    }

    public void q(@NotNull GuildMessageReceivedEvent event, TextChannel channel){
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Mordo ale musze byc z toba, tak to nie dziala").queue();
            return;
        }

        final Member member = event.getMember();
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
            channel.sendMessage("Pusto").queue();
            return;
        }
        final int trackCount = Math.min(queue.size(), 20);
        final List<AudioTrack> trackList = new ArrayList(queue);
        MessageAction messageAction = channel.sendMessage("**Teraz w kolejce:**\n");
        for(int i = 0; i<trackCount; i++){
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            messageAction.append("`#")
                    .append(String.valueOf(i+1))
                    .append(" ")
                    .append(info.title)
                    .append(" by ")
                    .append(info.author)
                    .append("`\n");
        }

        if(trackList.size() > trackCount){
            messageAction.append("Oraz '")
                    .append(String.valueOf(trackList.size() - trackCount))
                    .append("' więcej...");
        }
        messageAction.queue();
    }

    private boolean isUrl (String link){
            try {
                new URL(link);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    }



