package ui.gui.toiminnankuuntelijat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.gui.*;

/**
 *
 * @author xvixvi
 */
public class UusintanapinKuuntelija implements ActionListener {

    private final Alkuikkuna alkuikkuna;
    private final Kayttoliittyma kayttoliittyma;
    private final Loppuikkuna loppuikkuna;

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
