
package ui.gui;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author xvixvi
 */
public class VarinAsettaja {

    public void asetaVari(Graphics g, int i) {
        if (i == 0) {
            g.setColor(Color.yellow);
        }
        if (i == 1) {
            g.setColor(Color.white);
        }
        if (i == 2) {
            g.setColor(Color.blue);
        }
        if (i == 3) {
            g.setColor(Color.green);
        }
        if (i == 4) {
            g.setColor(Color.red);
        }
        if (i == 5) {
            g.setColor(Color.black);
        }
    }
}
