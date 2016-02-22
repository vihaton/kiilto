package logiikka.omaisuusluokat;

import java.awt.Graphics;
import logiikka.valineluokat.Kasakokoelma;
import ui.gui.Piirtoavustaja;

/**
 *
 * @author xvixvi
 *
 * Luokka kuvaa omistusta, jonka pelaaja on varannut ostamista varten
 */
public class Varaus {

    private final Omistus omistus;

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

    /**
     * Piirtää pelaajan varauksen.
     *
     * @param graphics
     * @param pa piirtoavustaja.
     * @param x
     * @param y
     */
    public void piirra(Graphics graphics, Piirtoavustaja pa, int x, int y) {
        omistus.piirra(graphics, pa, x, y, false);
    }
}
