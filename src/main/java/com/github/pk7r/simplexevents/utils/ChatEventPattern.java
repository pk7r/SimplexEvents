package com.github.pk7r.simplexevents.utils;

import com.github.pk7r.simplexevents.Main;
import com.github.pk7r.simplexevents.model.ChatEventModel;

public class ChatEventPattern {

    private static final ChatEventModel ev = Main.getChatEventModel();

    public static void setBolaoDefault() {
        ev.setBolaoIniciado(false);
        ev.getBolaoParticipantes().clear();
        ev.setBolaoAcumulado(0);
        ev.setBolaoApostaIndividual(0);
    }

    public static void setLoteriaDefault() {
        ev.setLoteriaIniciado(false);
        ev.setLoteriaNumeroCorreto(0);
        ev.setLoteriaPremio(0);
    }

    public static void setSorteioDefault() {
        ev.setSorteioIniciado(false);
    }

    public static void setSpeedChatDefault() {
        ev.setSpeedChatChars("");
        ev.setSpeedChatPremio(0);
        ev.setSpeedChatIniciado(false);
    }

}
