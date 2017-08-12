package javaa.ui.gui.toiminnankuuntelijat;

import javaa.ui.gui.Alkuikkuna;
import javaa.ui.gui.Kayttoliittyma;
import javaa.ui.gui.Loppuikkuna;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Aloittaa uuden pelin.
 *
 * @author xvixvi
 */
public class UusintanapinKuuntelija implements ActionListener {

    private final Alkuikkuna alkuikkuna;
    private final Kayttoliittyma kayttoliittyma;
    private final Loppuikkuna loppuikkuna;

    /**
     * Luo kuuntelijan.
     * 
     * @param kayttis joka tuhotaan.
     * @param loppis joka tuhotaan.
     */
    public UusintanapinKuuntelija(Kayttoliittyma kayttis, Loppuikkuna loppis) {
        alkuikkuna = new Alkuikkuna();
        kayttoliittyma = kayttis;
        loppuikkuna = loppis;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        kayttoliittyma.tuhoa();
        loppuikkuna.tuhoa();

        alkuikkuna.run();
    }

}
