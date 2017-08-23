package main.ui.gui;

import main.Pelivelho;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
//        pelivelho.alustaPeli(3);
//        pelivelho.alustaPeli();
    }

    private void luoSisalto(Container container) {
        GridLayout layout = new GridLayout(6, 0);
        container.setLayout(layout);

        JLabel pelaajia = new JLabel("Kuinka monen pelaajan pelin haluaisit pelata?\n");
        pelaajia.setHorizontalAlignment(JLabel.CENTER);

        JPanel conffaukset = new JPanel();
        conffaukset.setLayout(new GridLayout(0,2));
        JCheckBox testipeli = new JCheckBox("pelaa testipeli");
        conffaukset.add(testipeli);
        JTextField pelaajanNimi = new JTextField("pelaajan nimi?");
        conffaukset.add(pelaajanNimi);

        //final JButton kaksiPelaa = new JButton("Kaksin aina kaunihimpi");
        final JButton kaksiPelaa = new JButton("AI vs AI");

        //final JButton kolmePelaa = new JButton("Kolmin selvästi kovempi");
        //final JButton kolmePelaa = new JButton("AI vs AI vs AI");
        final JButton kolmePelaa = new JButton("Player vs AI vs AI");

        //final JButton neljaPelaa = new JButton("Nelistään, saa eniten nautintoa, pelistään");
        final JButton neljaPelaa = new JButton("Player and 3 AIs");
        final JButton lopeta = new JButton("Lopeta sekoilu, kiitos!");

        /*
         WIP

         -mahdollisuus syöttää pelaajien nimet tekstikenttiin
         */
        lisaaKuuntelijaPelinaloitusnappulalle(kaksiPelaa, kuinkaMontaIhmistaJaAlya(0,2), conffaukset);
        lisaaKuuntelijaPelinaloitusnappulalle(kolmePelaa, kuinkaMontaIhmistaJaAlya(1,2), conffaukset);
        lisaaKuuntelijaPelinaloitusnappulalle(neljaPelaa, kuinkaMontaIhmistaJaAlya(1,3), conffaukset);
        lisaaKuuntelijaLopetaNapille(lopeta);

        container.add(pelaajia);
        container.add(conffaukset);
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

    private void lisaaKuuntelijaPelinaloitusnappulalle(JButton nappi, final boolean[] onkoPelaajaAI, final JPanel conffaukset) {
        nappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alkuvalikko.setVisible(false);
                alkuvalikko.dispose();

                JCheckBox testipeli = (JCheckBox) conffaukset.getComponent(0);
                JTextField pelaajanNimi = (JTextField) conffaukset.getComponent(1);

                pelivelho.alustaPeli(onkoPelaajaAI, pelaajanNimi.getText());
                pelivelho.pelaa(testipeli.isSelected());
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
