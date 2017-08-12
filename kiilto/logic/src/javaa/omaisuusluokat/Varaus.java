package javaa.omaisuusluokat;

import javaa.valineluokat.Kasakokoelma;

/**
 * Kuvaa pelaajan varaamaa omistusta.
 * 
 * @author xvixvi
 */
public class Varaus {

    private final Omistus omistus;

    /**
     * Luo varauksen omistuksesta.
     * 
     * @param o omistus.
     */
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
