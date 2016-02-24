package ui.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logiikka.Pelivelho;
import ui.gui.toiminnankuuntelijat.*;

/**
 *
 * @author xvixvi
 */
public class Valintanapit extends JPanel {

    private final Pelivelho pelivelho;
    private ArrayList<String> nakyvienNimet;
    private int indeksi;
    private JLabel valitsin;
    private boolean osta;
    private ValintanapinKuuntelija valintanapinkuuntelija;
    private final JLabel infokentta;

    public Valintanapit(Pelivelho pv, JComponent[] nappulat, Piirtoalusta pa, JLabel infokentta) {
        super(new GridLayout(2, 2));
        pelivelho = pv;
        nakyvienNimet = new ArrayList<>();
        indeksi = 0;
        osta = true;
        this.infokentta = infokentta;
        luoKomponentit(nappulat, pa, infokentta);
    }

    private void luoKomponentit(JComponent[] nappulat, Piirtoalusta pa, JLabel infokentta) {
        this.setBackground(Color.lightGray);

        valitsin = new JLabel("homo");
        valitsin.setBackground(Color.white);
        valitsin.setHorizontalAlignment(0);
        JButton valitse = new JButton("tämä!");
        valintanapinkuuntelija = new ValintanapinKuuntelija(this, pelivelho, nappulat, pa, infokentta);
        valitse.addActionListener(valintanapinkuuntelija);
        
        JButton vasen = new JButton("<--");
        vasen.addActionListener(new SelausnapinKuuntelija(true, this));
        JButton oikea = new JButton("-->");
        oikea.addActionListener(new SelausnapinKuuntelija(false, this));

        this.add(valitsin);
        this.add(valitse);
        this.add(vasen);
        this.add(oikea);

    }

    public void setNakyvienNimet(ArrayList<String> nakyvienNimet, boolean osta) {
        this.nakyvienNimet = nakyvienNimet;
        indeksi = 0;
        valitsin.setText(nakyvienNimet.get(indeksi));
        this.osta = osta;
    }

    public void kasvataIndeksia() {
        if (indeksi == nakyvienNimet.size() - 1) {
            indeksi = 0;
        } else {
            indeksi++;
        }
        valitsin.setText(nakyvienNimet.get(indeksi));
    }

    public void pienennaIndeksia() {
        if (indeksi == 0) {
            indeksi = nakyvienNimet.size() - 1;
        } else {
            indeksi--;
        }
        valitsin.setText(nakyvienNimet.get(indeksi));
    }
    
    public void valitse() {
        if (osta) {
            if (pelivelho.osta(nakyvienNimet.get(indeksi))) {
                valintanapinkuuntelija.lopetaVuoro();
            } else {
                infokentta.setText("Valitsemasi omaisuuden ostaminen ei onnistunut.");
            }
        } else {
            if (pelivelho.varaa(nakyvienNimet.get(indeksi))) {
                valintanapinkuuntelija.lopetaVuoro();
            } else {
                infokentta.setText("Valitsemasi omaisuuden varaaminen ei onnistunut (max kolme varausta).");
            }
        }
    }
}
