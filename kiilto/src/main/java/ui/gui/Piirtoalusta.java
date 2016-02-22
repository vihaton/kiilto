package ui.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import logiikka.Pelivelho;

/**
 * Luokka vastaa pelin elementtien piirtämisestä.
 *
 * @author xvixvi
 */
public class Piirtoalusta extends JPanel {

    Pelivelho pelivelho;
    PiirtoAvustaja varinAsettaja;

    public Piirtoalusta(Pelivelho pv) {
        super.setBackground(Color.lightGray);
        pelivelho = pv;
        varinAsettaja = new PiirtoAvustaja();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        pelivelho.piirra(graphics, varinAsettaja);
    }
}
