package javaa.ui.gui.toiminnankuuntelijat;

import javaa.main.Pelivelho;
import javaa.ui.gui.Valintanapit;
import javaa.ui.gui.piirtaminen.Piirtoalusta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Kuuntelee osto- tai varaustoiminnon laukaisevaa nappia.
 *
 * @author xvixvi
 */
public class ValintanapinKuuntelija extends VuoronPaattavaKuuntelija implements ActionListener {

    private final Valintanapit valintanapit;

    /**
     * Luo kuuntelijan.
     * 
     * @param valintanapit joita kuunnellaan.
     * @param pelivelho Veikkaapa.
     * @param napit joiden näkyvyyttä säädetään.
     * @param pa piirtoalusta
     * @param infokentta Veikkaapa.
     */
    public ValintanapinKuuntelija(Valintanapit valintanapit, Pelivelho pelivelho, JComponent[] napit, Piirtoalusta pa, JLabel infokentta) {
        super(pelivelho, napit, pa, infokentta);
        this.valintanapit = valintanapit;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        valintanapit.valitse();
    }
}
