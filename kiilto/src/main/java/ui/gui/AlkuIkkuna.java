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
public class AlkuIkkuna implements Runnable {

    private JFrame alkuvalikko;
    private final Pelivelho pelivelho;

    public AlkuIkkuna() {
        pelivelho = new Pelivelho();
    }

    @Override
    public void run() {
        alkuvalikko = new JFrame();
        alkuvalikko.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        alkuvalikko.setPreferredSize(new Dimension(350, 400));
        alkuvalikko.setLocation(500,200);

        luoSisalto(alkuvalikko);

        alkuvalikko.pack();
        alkuvalikko.setVisible(true);
        
        //käyttöliittymän manuaalisen testaamisen nopeuttamiseksi
        alkuvalikko.setVisible(false);
        alkuvalikko.dispose();
        pelivelho.luoPelaajat(4);
        pelivelho.pelaa();
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
        
        
        lisaaKuuntelijat(kaksiPelaa, kolmePelaa, neljaPelaa, lopeta);

        container.add(pelaajia);
        container.add(kaksiPelaa);
        container.add(kolmePelaa);
        container.add(neljaPelaa);
        container.add(lopeta);
    }

    private void lisaaKuuntelijat(JButton kaksiPelaa, JButton kolmePelaa, JButton neljaPelaa, JButton lopeta) {
        kaksiPelaa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                alkuvalikko.setVisible(false);
                alkuvalikko.dispose();

                pelivelho.luoPelaajat(2);
                pelivelho.pelaa();
            }
        });

        kolmePelaa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                alkuvalikko.setVisible(false);
                alkuvalikko.dispose();

                pelivelho.luoPelaajat(3);
                pelivelho.pelaa();
            }
        });

        neljaPelaa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                alkuvalikko.setVisible(false);
                alkuvalikko.dispose();

                pelivelho.luoPelaajat(4);
                pelivelho.pelaa();
            }
        });

        lopeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }

}
