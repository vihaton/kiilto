package ui.gui.piirtaminen.piirtajat;

import java.awt.Color;
import java.awt.Graphics;
import logiikka.valineluokat.Merkkihenkilo;
import ui.gui.piirtaminen.Piirtoavustaja;

/**
 * Piirtää pelin kaikki merkkihenkilöt.
 *
 * @author xvixvi
 */
public class MerkkihenkilonPiirtaja {

    private final Piirtoavustaja piirtoavustaja;

    /**
     * Luo piirtäjän.
     *
     * @param pa piirtoavustaja.
     */
    public MerkkihenkilonPiirtaja(Piirtoavustaja pa) {
        piirtoavustaja = pa;
    }

    /**
     * Veikkaapa.
     * @param graphics Veikkaapa.
     * @param mh Veikkaapa.
     * @param x Veikkaapa.
     * @param y Veikkaapa.
     */
    public void piirra(Graphics graphics, Merkkihenkilo mh, int x, int y) {
        graphics.setColor(Color.darkGray);
        graphics.fill3DRect(x, y, 90, 100, true);
        graphics.setColor(Color.yellow);
        graphics.draw3DRect(x, y, 90, 100, true);

        piirtoavustaja.piirraNimi(graphics, mh.getNimi(), x + 40, y + 14);
        piirtoavustaja.piirraArvovalta(graphics, mh.getArvovaltalisa(), x + 5, y + 5);
        piirraOmaisuusvaatimus(graphics, mh, x + 10, y + 25);
    }

    private void piirraOmaisuusvaatimus(Graphics graphics, Merkkihenkilo mh, int x, int y) {
        for (int i = 1; i < 6; i++) {
            piirtoavustaja.asetaVari(graphics, i);
            graphics.drawString("" + mh.getOmaisuusvaatimusVarilla(i), x, y + i * 14);
        }
    }
}
