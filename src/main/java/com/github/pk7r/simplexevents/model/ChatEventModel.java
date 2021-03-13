package com.github.pk7r.simplexevents.model;

import lombok.Data;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatEventModel {

    private boolean bolaoIniciado = false;
    private int bolaoAcumulado = 0;
    private int bolaoApostaIndividual = 0;
    private List<Player> bolaoParticipantes = new ArrayList<>();

    private boolean sorteioIniciado = false;

    private boolean loteriaIniciado = false;
    private int loteriaNumeroCorreto = 0;
    private int loteriaPremio = 0;

    private boolean speedChatIniciado = false;
    private int speedChatPremio = 0;
    private String speedChatChars = "";

}