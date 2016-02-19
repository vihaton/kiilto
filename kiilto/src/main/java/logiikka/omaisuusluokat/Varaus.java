package logiikka.omaisuusluokat;

import logiikka.valineluokat.Kasakokoelma;

/**
 *
 * @author xvixvi
 *
 * Luokka kuvaa omistusta, jonka pelaaja on varannut ostamista varten
 */
public class Varaus {

    private Omistus omistus;

    public Varaus(Omistus o) {
        omistus = o;
    }

    public Kasakokoelma getHintaKasat() {
        return omistus.getHintaKasat();
    }

    public Omistus getOmistus() {
        return omistus;
    }

    @Override
    public String toString() {
        return "**varaus**\n" + omistus.toString() + "\n**********";
    }
}
