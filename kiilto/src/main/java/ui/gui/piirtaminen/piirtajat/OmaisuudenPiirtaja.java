
package ui.gui.piirtaminen.piirtajat;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import logiikka.omaisuusluokat.Omaisuus;
import logiikka.omaisuusluokat.Omistus;
import ui.gui.piirtaminen.Piirtoavustaja;

/**
 *
 * @author xvixvi
 */
public class OmaisuudenPiirtaja {
    
    private final Piirtoavustaja piirtoavustaja;

    OmaisuudenPiirtaja(Piirtoavustaja pa) {
        piirtoavustaja = pa;
    }


    public void piirraNakyvatOmistukset(Graphics graphics, Omaisuus omaisuus, int x, int y) {
        ArrayList<Omistus> paallimmaiset = omaisuus.getPaallimmaiset();

        for (Omistus omistus : paallimmaiset) {
            piirraOmistus(graphics, omistus, x, y, true);
            x += 110;
        }
    }
    
    /**
     * Piirtää omistuksen.
     * 
     * @param graphics joilla piirretään.
     * @param omistus joka piirretään.
     * @param x koordinaatti.
     * @param y koordinaatti.
     * @param iso piirretäänkö omistus isona vai pienenä.
     */
    public void piirraOmistus(Graphics graphics, Omistus omistus, int x, int y, boolean iso) {
        int leveys = 100;
        int korkeus = 125;
        if (!iso) {
            leveys = 80;
            korkeus = 100;
        }

        graphics.setColor(Color.gray);
        graphics.fillRect(x, y, leveys, korkeus);
        piirtoavustaja.asetaVari(graphics, omistus.getLisaKarkinVariNumerona());
        graphics.fillRect(x, y, leveys, korkeus / 5);
        graphics.setColor(Color.black);
        graphics.drawRect(x, y, leveys, korkeus);
        
        piirtoavustaja.asetaReunavari(graphics, omistus.getLisaKarkinVariNumerona());
        piirtoavustaja.piirraNimi(graphics, omistus.getNimi(), x + leveys / 3, y + korkeus / 7);
        piirtoavustaja.piirraArvovalta(graphics, omistus.getArvovalta(), x + korkeus / 17, y + korkeus / 26);

        piirraHinta(graphics, omistus, x + leveys / 10, y + korkeus / 5);
    }

    private void piirraHinta(Graphics graphics, Omistus omistus, int x, int y) {
        for (int i = 1; i < 6; i++) {
            piirtoavustaja.asetaVari(graphics, i);
            graphics.drawString("" + omistus.getKasanKoko(i), x, y + i * 15);
        }
    }
}
