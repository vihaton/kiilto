package ui.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.Pelivelho;
import ui.toiminnankuuntelijat.*;

/**
 * Pyörittää pelin graafista esitystä. Keskustelee pelivelhon kanssa, joka
 * hoitaa pelin pyörittämisen.
 *
 * @see logiikka.Pelivelho
 *
 * @author xvixvi
 */
public class Pelipoyta extends JPanel implements Runnable {

    private Pelivelho pelivelho;
    private final JFrame ruutu;
    private int leveys;
    private int korkeus;

    public Pelipoyta(Pelivelho pv) {
        this.pelivelho = pv;
        super.setBackground(Color.WHITE);
        ruutu = new JFrame("Kiilto-the-Game");
        leveys = 1000;
        korkeus = 666;
    }

    @Override
    public void run() {
        ruutu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ruutu.setPreferredSize(new Dimension(leveys, korkeus));

        luoKomponentit(ruutu);

        ruutu.pack();
        ruutu.setVisible(true);
    }

    private void luoKomponentit(final JFrame ruutu) {
        ruutu.setLayout(new BorderLayout()); //turha, mutta muistuttaa asian tilasta
        JPanel valikkorivi = new JPanel();
        valikkorivi.setLayout(new GridLayout(1, 0));

        //valikkoriviin voidaan lisätä tarvittavat toiminnallisuusnapit
        valikkorivi.add(luoTekstiIkkuna());
        
        JLabel info = new JLabel("Tähän tulevat infotekstit", JLabel.CENTER);

        ruutu.add(valikkorivi, BorderLayout.SOUTH);
        ruutu.add(info, BorderLayout.NORTH);

        valikkorivi.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        pelivelho.piirra(graphics, 0, 0);
    }

    private JPanel luoTekstiIkkuna() {
        JPanel tekstinsyotto = new JPanel();
        tekstinsyotto.setLayout(new GridLayout(2, 1));

        JTextField tekstiKentta = new JTextField();
        JButton nappi = new JButton("Paina minnuu!");

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
        
        return 1;
    }
}
