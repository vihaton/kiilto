package ui.gui.toiminnankuuntelijat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author xvixvi
 */
public class MaaranappienKuuntelija implements ActionListener {

    private final JLabel kentta;
    private final boolean plus;

    public MaaranappienKuuntelija(JLabel kentta, boolean onkoPlussa) {
        this.kentta = kentta;
        this.plus = onkoPlussa;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int arvo = Integer.parseInt(kentta.getText());

        if (plus && arvo < 2) {
            arvo++;
        } else if (!plus && arvo > 0) {
            arvo--;
        }

        kentta.setText("" + arvo);
    }

}
