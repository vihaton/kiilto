package main.ui.gui.piirtaminen.piirtajat;

import java.awt.Color;
import java.awt.Graphics;
import logiikka.Pelaaja;
import logiikka.Poyta;
import logiikka.valineluokat.Merkkihenkilo;
import main.ui.gui.piirtaminen.Piirtoavustaja;

/**
 * Piirtää pöydän ja kaiken mitä siinä on.
 *
 * @author xvixvi
 */
public class PoydanPiirtaja {

    private final Poyta poyta;
    private final KasakokoelmanPiirtaja kasakokoelmanpiirtaja;
    private final PelaajanPiirtaja pelaajanpiirtaja;
    private final MerkkihenkilonPiirtaja merkkihenkilonpiirtaja;
    private final OmaisuudenPiirtaja omaisuudenpiirtaja;

    /**
     * Luo pöydänpiirtäjän, ja sen sisään kasakakokoelmanpiirtäjän,
     * merkkihenkilönpiirtäjän, omaisuudenpiirtäjän sekä pelaajanpiirtäjän.
     *
     * @param pa piirtoavustaja.
     * @param poyta piirrettävä pöytä.
     */
    public PoydanPiirtaja(Piirtoavustaja pa, Poyta poyta) {
        this.poyta = poyta;
        kasakokoelmanpiirtaja = new KasakokoelmanPiirtaja(pa);
        merkkihenkilonpiirtaja = new MerkkihenkilonPiirtaja(pa);
        omaisuudenpiirtaja = new OmaisuudenPiirtaja(pa);
        pelaajanpiirtaja = new PelaajanPiirtaja(pa, kasakokoelmanpiirtaja, omaisuudenpiirtaja);
    }

    /**
     * Veikkaapa.
     *
     * @param graphics Veikkaapa.
     */
    public void piirra(Graphics graphics) {
        int x = 20;
        int y = 10;
        piirraMerkkihenkilot(graphics, x, y);
        y += 120;
        x -= 10;
        piirraOmistuspakat(graphics, x, y);
        x += 110;
        piirraNakyvatOmistukset(graphics, x, y);
        x += 450;
        kasakokoelmanpiirtaja.piirraIsosti(graphics, poyta.getMarkkinat(), x, y);
        y = 20;
        x += 80;
        piirraPelaajat(graphics, x, y);
    }

    private void piirraOmistuspakat(Graphics graphics, int x, int y) {
        for (int i = 0; i < 3; i++) {
            if (poyta.getOmistuspakat()[2 - i].getKoko() > 4) {
                graphics.setColor(Color.gray);
                graphics.fill3DRect(x, y, 100, 125, true);
                graphics.setColor(Color.black);
                graphics.drawString("KIILTO", x + 33, y + 66);
                graphics.drawOval(x + 22, y + 48, 55, 25);
                y += 135;
            }
        }
    }

    private void piirraMerkkihenkilot(Graphics graphics, int x, int y) {
        for (Merkkihenkilo mh : poyta.getMerkkihenkilot()) {
            merkkihenkilonpiirtaja.piirra(graphics, mh, x, y);
            x += 100;
        }
    }

    private void piirraNakyvatOmistukset(Graphics graphics, int x, int y) {
        for (int i = 2; i > -1; i--) {
            omaisuudenpiirtaja.piirraNakyvatOmistukset(graphics, poyta.getOmistuspakat()[i], x, y);
            y += 135;
        }
    }

    private void piirraPelaajat(Graphics graphics, int x, int y) {
        for (Pelaaja pelaaja : poyta.getPelaajat()) {
            pelaajanpiirtaja.piirra(graphics, pelaaja, x, y);
            y += 150;
        }
    }
}
