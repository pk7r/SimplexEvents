package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;

public class ChatEventPattern {

    public static void setBolaoDefault() {
        Main.getChatEventModel().setBolaoIniciado(false);
        Main.getChatEventModel().getBolaoParticipantes().clear();
        Main.getChatEventModel().setBolaoAcumulado(0);
        Main.getChatEventModel().setBolaoApostaIndividual(0);
    }

    public static void setLoteriaDefault() {
        Main.getChatEventModel().setLoteriaIniciado(false);
        Main.getChatEventModel().setLoteriaNumeroCorreto(0);
        Main.getChatEventModel().setLoteriaPremio(0);
    }

    public static void setSorteioDefault() {
        Main.getChatEventModel().setSorteioIniciado(false);
    }

    public static void setSpeedChatDefault() {
        Main.getChatEventModel().setSpeedChatChars("");
        Main.getChatEventModel().setSpeedChatPremio(0);
        Main.getChatEventModel().setSpeedChatIniciado(false);
    }

}
