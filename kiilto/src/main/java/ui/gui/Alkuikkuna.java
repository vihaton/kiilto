package ui.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.*;

/**
 * Käyttöliittymä pelin alustamiseksi. Kerää käyttäjältä tarvittavan
 * informaation annettavaksi pelivelholle.
 *
 * @author xvixvi
 */
public class Alkuikkuna implements Runnable {

    private JFrame alkuvalikko;
    private final Pelivelho pelivelho;

    /**
     * Luo alkuikkunan, joka luo itselleen pelin pelivelhon.
     */
    public Alkuikkuna() {
        pelivelho = new Pelivelho();
    }

    @Override
    public void run() {
        alkuvalikko = new JFrame();
        alkuvalikko.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        alkuvalikko.setPreferredSize(new Dimension(350, 400));
        alkuvalikko.setLocation(500, 200);

        luoSisalto(alkuvalikko);

        alkuvalikko.pack();
        alkuvalikko.setVisible(true);

        //käyttöliittymän manuaalisen testaamisen nopeuttamiseksi
//        tuhoa();
//        pelivelho.luoPelaajat(3);
//        pelivelho.alustaPeli();
    }

    private void luoSisalto(Container container) {
        GridLayout layout = new GridLayout(5, 0);
        container.setLayout(layout);

        JLabel pelaajia = new JLabel("Kuinka monen pelaajan pelin haluaisit pelata?");
        pelaajia.setHorizontalAlignment(JLabel.CENTER);

        final JButton kaksiPelaa = new JButton("Kaksin aina kaunihimpi");
        final JButton kolmePelaa = new JButton("Kolmin selvästi kovempi");
        final JButton neljaPelaa = new JButton("Nelistään, saa eniten nautintoa, pelistään");
        final JButton lopeta = new JButton("Lopeta sekoilu, kiitos!");

        /*
         WIP

         -mahdollisuus syöttää pelaajien nimet tekstikenttiin
         */
        lisaaKuuntelijaPelinaloitusnappulalle(kaksiPelaa, 2, kuinkaMontaIhmistaJaAlya(1,1));
        lisaaKuuntelijaPelinaloitusnappulalle(kolmePelaa, 3, kuinkaMontaIhmistaJaAlya(1,2));
        lisaaKuuntelijaPelinaloitusnappulalle(neljaPelaa, 4, kuinkaMontaIhmistaJaAlya(1,3));
        lisaaKuuntelijaLopetaNapille(lopeta);

        container.add(pelaajia);
        container.add(kaksiPelaa);
        container.add(kolmePelaa);
        container.add(neljaPelaa);
        container.add(lopeta);
    }

    protected boolean[] kuinkaMontaIhmistaJaAlya(int ihmisia, int alyja) {
        boolean[] onkoAI = new boolean[ihmisia+alyja];
        for (int i = ihmisia; i < onkoAI.length; i++) {
            onkoAI[i] = true;
        }
        return onkoAI;
    }

    private void lisaaKuuntelijaPelinaloitusnappulalle(JButton nappi, int kuinkaMontaPelaajaa, boolean[] onkoPelaajaAI) {
        nappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alkuvalikko.setVisible(false);
                alkuvalikko.dispose();

                //pelivelho.luoPelaajat(kuinkaMontaPelaajaa);
                pelivelho.luoPelaajatJaAIt(onkoPelaajaAI);
                pelivelho.pelaa();
            }
        });
    }

    private void lisaaKuuntelijaLopetaNapille(JButton lopeta) {
        lopeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }

    /**
     * Katso ->.
     *
     * @see Kayttoliittyma#tuhoa()
     */
    public void tuhoa() {
        alkuvalikko.setVisible(false);
        alkuvalikko.dispose();
    }
}
