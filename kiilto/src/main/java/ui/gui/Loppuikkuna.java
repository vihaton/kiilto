package ui.gui;

import java.awt.*;
import javax.swing.*;
import ui.gui.toiminnankuuntelijat.UusintanapinKuuntelija;

/**
 *
 * @author xvixvi
 */
public class Loppuikkuna implements Runnable {

    private final String voittaja;
    private final String voittovalta;
    private final String kierros;
    private Kayttoliittyma kayttoliittyma;
    private final JFrame ruutu;

    public Loppuikkuna(String voittaja, String voittovalta, String kierros, Kayttoliittyma kayttis) {
        this.voittaja = voittaja;
        this.voittovalta = voittovalta;
        this.kierros = kierros;
        kayttoliittyma = kayttis;
        ruutu = new JFrame();
    }

    @Override
    public void run() {
        ruutu.setLayout(new GridLayout(1, 4));
        ruutu.setPreferredSize(new Dimension(200, 300));

        luoKomponentit();

        ruutu.pack();
        ruutu.setVisible(true);
    }

    private void luoKomponentit() {
        JTextArea tekstikentta = new JTextArea("Pelin voitti pelaaja " + voittaja + " " + voittovalta
                + "arvovaltapisteellä\n kierroksella " + kierros + "! Onneksi olkoon!\n"
                + "Tuomari lähettää erityiskiitoksensa voittajalle lahjuksista, ensi perjantaina juhlitaan!");
        JButton uudestaan = new JButton("Haluan revanssin!");
        uudestaan.addActionListener(new UusintanapinKuuntelija(kayttoliittyma, this));
        JButton lopeta = new JButton("Ei ikinä enää, lopeta, LOPETA!");
        lopeta.addActionListener(new UusintanapinKuuntelija(kayttoliittyma, this));

        ruutu.add(tekstikentta);
        ruutu.add(uudestaan);
        ruutu.add(lopeta);
    }

    public void tuhoa() {
        ruutu.setVisible(false);
        ruutu.dispose();
    }
}
