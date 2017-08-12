package javaa.ui.gui.piirtaminen.piirtajat;

import java.awt.Color;
import java.awt.Graphics;
import javaa.Pelaaja;
import javaa.omaisuusluokat.Omistus;
import javaa.omaisuusluokat.Varaus;
import javaa.ui.gui.piirtaminen.Piirtoavustaja;

/**
 * Piirtää pelin pelaajat.
 *
 * @author xvixvi
 */
public class PelaajanPiirtaja {
    
    private final Piirtoavustaja piirtoavustaja;
    private final KasakokoelmanPiirtaja kasakokoelmanpiirtaja;
    private final OmaisuudenPiirtaja omaisuudenpiirtaja;
    
    /**
     * Luo pelaajanpiirtäjän.
     * 
     * @param pa piirtoavustaja.
     * @param kkp kasakokoelmanpiirtäjä.
     * @param op omaisuudenpiirtäjä.
     */
    public PelaajanPiirtaja(Piirtoavustaja pa, KasakokoelmanPiirtaja kkp, OmaisuudenPiirtaja op) {
        this.piirtoavustaja = pa;
        kasakokoelmanpiirtaja = kkp;
        omaisuudenpiirtaja = op;
    }
    
    /**
     * Piirtää pelaajan nimen, arvovallan, omistukset, karkit ja varaukset.
     *
     * @param graphics grafiikat.
     * @param pelaaja piirrettävä pelaaja.
     * @param x koordinaatti.
     * @param y koordinaatti.
     */
    public void piirra(Graphics graphics, Pelaaja pelaaja, int x, int y) {
        graphics.setColor(Color.black);
        graphics.drawString(pelaaja.getNimi(), x, y);

        graphics.drawString("pelaajalla arvovaltapisteitä " + pelaaja.getArvovalta(), x, y + 15);

        y += 25;
        piirraOmaisuus(graphics, pelaaja, x, y);
        piirraVietellytMerkkihenkilot(graphics, pelaaja, x + 195, y - 25);
        x += 275;
        piirraVaraukset(graphics, pelaaja, x, y);
        y -= 28;
        kasakokoelmanpiirtaja.piirraPelaajanKarkit(graphics, pelaaja.getKarkit(), x, y);
    }

    private void piirraOmaisuus(Graphics graphics, Pelaaja pelaaja, int x, int y) {
        for (int i = 1; i < 6; i++) {
            if (piirraYhdenVarisetOmistukset(graphics, pelaaja, x, y, i)) {
                x += 50;
            }
        }
    }

    private boolean piirraYhdenVarisetOmistukset(Graphics graphics, Pelaaja pelaaja, int x, int y, int n) {
        boolean kyllaLoytyy = false;
        for (int i = 0; i < pelaaja.getOmaisuudenKoko(); i++) {
            Omistus omistus = pelaaja.getOmaisuus().getOmistusIndeksista(i);
            if (omistus.getLisaKarkinVariNumerona() == n) {
                kyllaLoytyy = true;
                piirraPelaajanOmistus(graphics, omistus, x, y, n);
                x += 3;
                y += 15;
            }
        }
        return kyllaLoytyy;
    }
    
    private void piirraPelaajanOmistus(Graphics graphics, Omistus omistus, int x, int y, int vari) {
        graphics.setColor(Color.gray);
        graphics.fillRect(x, y, 40, 50);
        piirtoavustaja.asetaVari(graphics, vari);
        graphics.fillRect(x, y, 40, 17);
        graphics.setColor(Color.black);
        graphics.drawRect(x, y, 40, 50);
        piirtoavustaja.asetaReunavari(graphics, vari);
        piirtoavustaja.piirraArvovaltaPieni(graphics, omistus.getArvovalta(), x + 4, y + 3);
    }

    private void piirraVaraukset(Graphics graphics, Pelaaja pelaaja, int x, int y) {
        for (Varaus varaus : pelaaja.getVaraukset()) {
            omaisuudenpiirtaja.piirraOmistus(graphics, varaus.getOmistus(), x, y, false);
            x += 90;
        }
    }

    private void piirraVietellytMerkkihenkilot(Graphics graphics, Pelaaja pelaaja, int x, int y) {
        for (int i = 0; i < pelaaja.getMerkkihenkiloidenMaara(); i++) {
            graphics.setColor(Color.darkGray);
            graphics.fill3DRect(x - 2, y - 2, 22, 21, true);
            graphics.setColor(Color.yellow);
            graphics.draw3DRect(x - 2, y - 2, 22, 21, true);
            piirtoavustaja.piirraArvovalta(graphics, 3, x, y);
            x += 26;
        }
    }
}
