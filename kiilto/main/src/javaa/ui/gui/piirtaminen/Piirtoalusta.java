package javaa.ui.gui.piirtaminen;

import javaa.main.Pelivelho;
import javaa.ui.gui.piirtaminen.piirtajat.PoydanPiirtaja;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Luokka vastaa pelin elementtien piirtämisestä.
 *
 * @author xvixvi
 */
public class Piirtoalusta extends JPanel {

    private final PoydanPiirtaja poydanpiirtaja;

    /**
     * Luo piirtoalustan, joka muistaa annetun piirtoavustajan.
     *
     * @param pelivelho jolta saadaan pöydän piirtämiseen pöytä.
     * @param pa piirtoavustaja.
     */
    public Piirtoalusta(Pelivelho pelivelho, Piirtoavustaja pa) {
        super.setBackground(Color.lightGray);
        this.poydanpiirtaja = new PoydanPiirtaja(pa, pelivelho.getPoyta());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        poydanpiirtaja.piirra(graphics);
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
