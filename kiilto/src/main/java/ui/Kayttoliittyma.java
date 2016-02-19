
package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.*;

/**
 * Käyttöliittymä pelin pelaamiseksi.
 * 
 * @author xvixvi
 */
public class Kayttoliittyma implements Runnable {

    private JFrame paavalikko;
    private final Pelivelho pelivelho;
    
    public Kayttoliittyma() {
        pelivelho = new Pelivelho();
    }

    @Override
    public void run() {
        paavalikko = new JFrame();
        paavalikko.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        paavalikko.setPreferredSize(new Dimension(200, 400));

        luoPaavalikko(paavalikko);

        paavalikko.pack();
        paavalikko.setVisible(true);
    }

    private void luoPaavalikko(Container container) {
        GridLayout layout = new GridLayout(2, 0);
        container.setLayout(layout);
        JButton pelaa = new JButton("Pelaa");
        JButton lopeta = new JButton("Lopeta");

        pelaa.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                        paavalikko.setVisible(false);
                        paavalikko.dispose();
                        pelivelho.pelaa();
                }
        });

        lopeta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                        System.exit(0);
                }
        });

        container.add(pelaa);
        container.add(lopeta);


    }

}
