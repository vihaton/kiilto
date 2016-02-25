package ui.gui.toiminnankuuntelijat;

import java.awt.event.*;
import javax.swing.*;
import logiikka.Pelivelho;
import ui.gui.piirtaminen.Piirtoalusta;

/**
 *
 * @author xvixvi
 */
public class NallekarkkivalitsimenKuuntelija extends VuoronPaattavaKuuntelija implements ActionListener {

    private final Pelivelho pelivelho;
    private final JLabel[] nallekarkkikentat;
    private final JLabel infokentta;
    private int[] nostettavat;

    public NallekarkkivalitsimenKuuntelija(Pelivelho pelivelho, JLabel infokentta, JLabel[] nallekarkkikentat, Piirtoalusta pa, JComponent[] napit) {
        super(pelivelho, napit, pa, infokentta);
        this.pelivelho = pelivelho;
        this.nallekarkkikentat = nallekarkkikentat;
        this.infokentta = infokentta;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean kaksiSamanvarista = false;
        int summa = 0;
        int vari = 0;
        nostettavat = new int[5];

        for (int i = 0; i < nallekarkkikentat.length; i++) {
            JLabel nallekarkkikentta = nallekarkkikentat[i];

            int arvo = Integer.parseInt(nallekarkkikentta.getText());
            nostettavat[i] = arvo;

            if (arvo == 2) {
                kaksiSamanvarista = true;
                vari = i + 1;
            }

            summa += arvo;
        }
        tyhjennaKentat();

        if (tarkistasyote(kaksiSamanvarista, summa, vari)) {
            return;
        }

        pelivelho.nostaNallekarkkeja(nostettavat);
        super.lopetaVuoro();
    }

    private boolean tarkistasyote(boolean kaksiSamanvarista, int summa, int vari) {
        boolean lopeta = false;
        if (kaksiSamanvarista && summa > 2) {
            infokentta.setText("Jos haluat kaksi samanväristä, et voi valita muita karkkeja.");
            lopeta = true;
        } else if (summa > 3) {
            infokentta.setText("Et voi valita noin montaa nallekarkkia, senkin karvakorvainen humanisti!");
            lopeta = true;
        } else if (kaksiSamanvarista && !pelivelho.vahintaanNeljaKarkkia(vari)) {
            infokentta.setText("Kasassa on oltava vähintään neljä karkkia, että voit nostaa kaksi samasta kasasta.");
            lopeta = true;
        }
        return lopeta;
    }

    private void tyhjennaKentat() {
        for (JLabel kentta : nallekarkkikentat) {
            kentta.setText("0");
        }
    }

}
