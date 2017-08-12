package javaa.ui.gui.piirtaminen.piirtajat;

import java.awt.Graphics;

import javaa.ui.gui.piirtaminen.Piirtoavustaja;
import javaa.valineluokat.Kasakokoelma;
import javaa.valineluokat.Nallekarkkikasa;

/**
 *
 * @author xvixvi
 */
public class KasakokoelmanPiirtaja {

    private final Piirtoavustaja piirtoavustaja;

    KasakokoelmanPiirtaja(Piirtoavustaja pa) {
        piirtoavustaja = pa;
    }

    /**
     * Piirtää karkkimarkkinat.
     *
     * @param graphics joilla piirretään.
     * @param pa piirtoavustaja.
     * @param x koordinaatti.
     * @param y koordinaatti.
     */
    public void piirraIsosti(Graphics graphics, Kasakokoelma kasat, int x, int y) {
        for (int i = 0; i < 6; i++) {
            Nallekarkkikasa kasa = kasat.getKasa(i);
            if (!kasa.onTyhja()) {
                piirraKasa(graphics, kasa, x, y, true);
            }
            y += 65;
        }
    }

    /**
     * Piirtää pelaajalla olevat karkit.
     *
     * @param graphics joilla piirretään.
     * @param pa piirtoavustaja.
     * @param x koordinaatti.
     * @param y koordinaatti.
     */
    public void piirraPelaajanKarkit(Graphics graphics, Kasakokoelma kasat, int x, int y) {
        for (int i = 0; i < 6; i++) {
            Nallekarkkikasa kasa = kasat.getKasa(i);
            if (!kasa.onTyhja()) {
                piirraKasa(graphics, kasa, x, y, false);
                x += 32;
            }
        }
    }

    private void piirraKasa(Graphics graphics, Nallekarkkikasa kasa, int x, int y, boolean iso) {
        int koko = kasa.getKoko();
        int d = 22;
        if (iso) {
            d = 42;
        }
        int kerrosSiirtyma = 2;
        if (iso) {
            kerrosSiirtyma++;
        }
        int v = kasa.getVari().ordinal();
        int reunaVari = v != 5 ? 5 : 1;

        for (int i = 0; i <= koko; i++) {
            piirtoavustaja.asetaVari(graphics, v);
            graphics.fillOval(x, y, d, d);

            piirtoavustaja.asetaVari(graphics, reunaVari);
            graphics.drawOval(x, y, d, d);

            x += kerrosSiirtyma;
            y -= kerrosSiirtyma;
        }
        if (koko == 0) {
            return;
        }

        if (iso) {
            graphics.drawString("" + koko + "kpl", x + d / 8, y + 2 * d / 3);
        } else {
            graphics.drawString("" + koko, x + d / 4, y + 4 * d / 5);
        }
    }

}
