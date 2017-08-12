package javaa.ui.gui;

import javaa.ui.gui.piirtaminen.Piirtoalusta;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javaa.main.Pelivelho;
import javaa.ui.gui.toiminnankuuntelijat.SelausnapinKuuntelija;
import javaa.ui.gui.toiminnankuuntelijat.ValintanapinKuuntelija;

/**
 * Luokka omistusten ja varausten selaamiseen. Sisältyy käyttöliittymään.
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

    /**
     * Luo valintanapit.
     *
     * @param pv pelivelho, jonka kanssa keskustellaan.
     * @param nappulat valikko, jonka näkyvyyttä säädellään.
     * @param pa piirtoalusta, jota pitää välillä pyytää päivittämään näkymä.
     * @param infokentta johon pitää pistää ohjetekstejä, kun käyttäjä töhöilee.
     */
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
    
    /**
     * Veikkaapa.
     * @param nakyvienNimet Veikkaapa.
     * @param osta Veikkaapa.
     */
    public void setNakyvienNimet(ArrayList<String> nakyvienNimet, boolean osta) {
        this.nakyvienNimet = nakyvienNimet;
        indeksi = 0;
        valitsin.setText(nakyvienNimet.get(indeksi));
        this.osta = osta;
    }

    /**
     * siirtää valitsinta "yhden oikealle".
     */
    public void kasvataIndeksia() {
        if (indeksi == nakyvienNimet.size() - 1) {
            indeksi = 0;
        } else {
            indeksi++;
        }
        valitsin.setText(nakyvienNimet.get(indeksi));
    }

    /**
     * Siirtää valitsinta "yhden vasemmalle".
     */
    public void pienennaIndeksia() {
        if (indeksi == 0) {
            indeksi = nakyvienNimet.size() - 1;
        } else {
            indeksi--;
        }
        valitsin.setText(nakyvienNimet.get(indeksi));
    }

    /**
     * Valitsee tarkastelussa olevan omistuksen / varauksen.
     */
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
