package ui.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.Pelivelho;
import ui.toiminnankuuntelijat.*;

/**
 * Pyörittää pelin graafista esitystä. Hoitaa kaikkien pelin aikana tarvittavien
 * valikkojen ym operoimisen. Delegoi peli-elementtien piirtämisen
 * piirtoalustalle. Keskustelee pelivelhon kanssa, joka hoitaa pelin
 * pyörittämisen.
 *
 * @see logiikka.Pelivelho
 *
 * @author xvixvi
 */
public class Pelipoyta implements Runnable {

    private Pelivelho pelivelho;
    private final JFrame ruutu;
    private final JLabel infoTekstit;
    private final JPanel valikkorivi;
    private final Piirtoalusta piirtoalusta;
    private int leveys;
    private int korkeus;

    public Pelipoyta(Pelivelho pv) {
        this.pelivelho = pv;
        piirtoalusta = new Piirtoalusta(pv);
        ruutu = new JFrame("Kiilto-the-Game");
        leveys = 1000;
        korkeus = 666;
        infoTekstit = new JLabel("Tähän ilmestyvät pelin infotekstit", JLabel.CENTER);
        valikkorivi = new JPanel();
    }

    @Override
    public void run() {
        ruutu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ruutu.setPreferredSize(new Dimension(leveys, korkeus));

        luoKomponentit();

        ruutu.pack();
        ruutu.setVisible(true);
    }

    private void luoKomponentit() {
        ruutu.setLayout(new BorderLayout()); //turha, mutta muistuttaa asian tilasta
        valikkorivi.setLayout(new GridLayout(1, 0));

        //valikkoriviin voidaan lisätä tarvittavat toiminnallisuusnapit
        valikkorivi.add(luoTekstiIkkuna());

        ruutu.add(piirtoalusta);
        ruutu.add(valikkorivi, BorderLayout.SOUTH);
        ruutu.add(infoTekstit, BorderLayout.NORTH);
    }

    private JPanel luoTekstiIkkuna() {
        JPanel tekstinsyotto = new JPanel();
        tekstinsyotto.setLayout(new GridLayout(2, 1));

        JTextField tekstiKentta = new JTextField();
        tekstiKentta.setHorizontalAlignment(JTextField.CENTER);

        JButton nappi = new JButton("Lähetä käsky! Paina minnuu!");

        nappi.addActionListener(new Tekstinsyotonkuuntelija(pelivelho, tekstiKentta));

        tekstinsyotto.add(tekstiKentta);
        tekstinsyotto.add(nappi);

        return tekstinsyotto;
    }

    /**
     * Mitä pelaaja päättää vuorollaan tehdä?
     *
     * @param nimi vuorossa olevan pelaajan nimi.
     * @return int toimi, 1 == nostetaan nallekarkkeja, 2 == ostetaan
     * omaisuutta, 3 == tehdään varaus.
     */
    public int pelaajanToimi(String nimi) {
        infoTekstit.setText("Mitä haluat tehdä " + nimi + "? Valitse vuoron toiminto!");

        valikkorivi.add(luoPelaajanToimiNapit());

        return 1;
    }

    private JPanel luoPelaajanToimiNapit() {
        JPanel toimiNapit = new JPanel(new GridLayout(3, 1));

        JButton nosta = new JButton("Nostan nallekarkkeja markkinoilta");
        JButton osta = new JButton("Ostetan omaisuutta");
        JButton varaa = new JButton("Varaan omaisuutta");

        toimiNapit.add(nosta);
        toimiNapit.add(osta);
        toimiNapit.add(varaa);

        return toimiNapit;
    }
}
