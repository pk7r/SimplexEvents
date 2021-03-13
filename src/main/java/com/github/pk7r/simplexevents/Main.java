package com.github.pk7r.simplexevents;

import co.aikar.commands.PaperCommandManager;
import com.github.pk7r.simplexevents.commands.EnqEventCommand;
import com.github.pk7r.simplexevents.commands.GenericEventCommand;
import com.github.pk7r.simplexevents.commands.QuizEventCommand;
import com.github.pk7r.simplexevents.commands.SECommand;
import com.github.pk7r.simplexevents.commands.chat.BolaoCommand;
import com.github.pk7r.simplexevents.commands.chat.ChatEventCommand;
import com.github.pk7r.simplexevents.commands.chat.LoteriaCommand;
import com.github.pk7r.simplexevents.events.ChatEvent;
import com.github.pk7r.simplexevents.events.EnqEvent;
import com.github.pk7r.simplexevents.events.GenericEvent;
import com.github.pk7r.simplexevents.events.QuizEvent;
import com.github.pk7r.simplexevents.listeners.ListenerRegister;
import com.github.pk7r.simplexevents.model.ChatEventModel;
import com.github.pk7r.simplexevents.model.EnqEventModel;
import com.github.pk7r.simplexevents.model.GenericEventModel;
import com.github.pk7r.simplexevents.model.QuizEventModel;
import com.github.pk7r.simplexevents.utils.ChatEventPattern;
import com.github.pk7r.simplexevents.utils.EnqEventPattern;
import com.github.pk7r.simplexevents.utils.GenericEventPattern;
import com.github.pk7r.simplexevents.utils.QuizEventPattern;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter @Setter
    private static Main main;
    @Getter @Setter
    private static GenericEventModel genericEventModel;
    @Getter @Setter
    private static GenericEvent genericEvent;
    @Getter @Setter
    private static QuizEventModel quizEventModel;
    @Getter @Setter
    private static QuizEvent quizEvent;
    @Getter @Setter
    private static ChatEventModel chatEventModel;
    @Getter @Setter
    private static ChatEvent chatEvent;
    @Getter @Setter
    private static EnqEventModel enqEventModel;
    @Getter @Setter
    private static EnqEvent enqEvent;
    @Getter @Setter
    private static Economy econ;
    @Getter @Setter
    private CommandSender sender;

    @Override
    public void onEnable() {
        setSender(Bukkit.getConsoleSender());
        if (!setupEconomy() ) {
            getSender().sendMessage("[SimplexEvents] Vault não encontrado, desabilitando plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setMain(this);
        setGenericEventModel(new GenericEventModel());
        setQuizEventModel(new QuizEventModel());
        setChatEventModel(new ChatEventModel());
        setEnqEventModel(new EnqEventModel());
        setGenericEvent(new GenericEvent());
        setChatEvent(new ChatEvent());
        setQuizEvent(new QuizEvent());
        setEnqEvent(new EnqEvent());

        GenericEventPattern.setDefault();
        QuizEventPattern.setDefault();
        EnqEventPattern.setDefault();
        ChatEventPattern.setSpeedChatDefault();
        ChatEventPattern.setSorteioDefault();
        ChatEventPattern.setLoteriaDefault();
        ChatEventPattern.setBolaoDefault();

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new GenericEventCommand());
        manager.registerCommand(new QuizEventCommand());
        manager.registerCommand(new ChatEventCommand());
        manager.registerCommand(new EnqEventCommand());
        manager.registerCommand(new SECommand());
        manager.registerCommand(new BolaoCommand());
        manager.registerCommand(new LoteriaCommand());

        ListenerRegister listenerRegister = new ListenerRegister(this);

        getSender().sendMessage("[SimplexEvents] Plugin carregado com sucesso!");
    }

    @Override
    public void onDisable() {
        getSender().sendMessage("[SimplexEvents] Limpando eventos...");
        GenericEventPattern.setDefault();
        QuizEventPattern.setDefault();
        EnqEventPattern.setDefault();
        ChatEventPattern.setSpeedChatDefault();
        ChatEventPattern.setSorteioDefault();
        ChatEventPattern.setLoteriaDefault();
        ChatEventPattern.setBolaoDefault();
        Bukkit.getScheduler().cancelTasks(this);
        getSender().sendMessage("[SimplexEvents] Plugin desabilitado.");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        setEcon(rsp.getProvider());
        getSender().sendMessage("[SimplexEvents] Vinculado ao Vault com sucesso!");
        return getEcon() != null;
    }
}