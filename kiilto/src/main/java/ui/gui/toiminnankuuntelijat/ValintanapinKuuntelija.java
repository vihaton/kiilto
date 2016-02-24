
package ui.gui.toiminnankuuntelijat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import logiikka.Pelivelho;
import ui.gui.Piirtoalusta;
import ui.gui.Valintanapit;

/**
 *
 * @author xvixvi
 */
public class ValintanapinKuuntelija extends VuoronPaattavaKuuntelija implements ActionListener {

    private final Valintanapit valintanapit;
    
    public ValintanapinKuuntelija(Valintanapit valintanapit, Pelivelho pelivelho, JComponent[] napit, Piirtoalusta pa, JLabel infokentta) {
        super(pelivelho, napit, pa, infokentta);
        this.valintanapit = valintanapit;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        valintanapit.valitse();
    }
}
