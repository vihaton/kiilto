package ui.gui;

import java.awt.*;
import javax.swing.*;
import logiikka.Pelivelho;
import ui.gui.toiminnankuuntelijat.ToimintonapinKuuntelija;
import ui.gui.toiminnankuuntelijat.MaaranappienKuuntelija;
import ui.gui.toiminnankuuntelijat.NallekarkkivalitsimenKuuntelija;

/**
 * Ylläpitää pelin graafista esitystä. Hoitaa kaikkien pelin aikana tarvittavien
 * valikkojen ym operoimisen. Delegoi peli-elementtien piirtämisen
 * piirtoalustalle. Keskustelee pelivelhon kanssa, joka hoitaa pelin logiikan
 * pyörittämisen.
 *
 * @see logiikka.Pelivelho
 *
 * @author xvixvi
 */
public class Kayttoliittyma implements Runnable {

    private final Pelivelho pelivelho;
    private final JFrame ruutu;
    private final JComponent[] nappulat;
    private final JLabel infokentta;
    private final Piirtoalusta piirtoalusta;
    private final Piirtoavustaja piirtoavustaja;
    private final int leveys;
    private final int korkeus;

    public Kayttoliittyma(Pelivelho pv) {
        this.pelivelho = pv;
        piirtoavustaja = new Piirtoavustaja();
        piirtoalusta = new Piirtoalusta(pv, piirtoavustaja);
        ruutu = new JFrame("Kiilto-the-Game");
        leveys = 1210;
        korkeus = 725;
        nappulat = new JComponent[4];
        infokentta = new JLabel("Tähän ilmestyvät pelin infotekstit", JLabel.CENTER);
    }

    @Override
    public void run() {
        ruutu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ruutu.setPreferredSize(new Dimension(leveys, korkeus));
        ruutu.setLocation(0, 0);

        luoKomponentit();

        for (int i = 1; i < 4; i++) {
            nappulat[i].setVisible(false);
        }

        infokentta.setText("Pelaajan " + pelivelho.getVuorossaOleva() + " vuoro kierroksella " + pelivelho.getKierros());

        ruutu.pack();
        ruutu.setVisible(true);
    }

    private void luoKomponentit() {
        ruutu.setLayout(new BorderLayout()); //turha, mutta muistuttaa asian tilasta
        JPanel valikkorivi = new JPanel();
        valikkorivi.setLayout(new GridLayout(1, 0));
        valikkorivi.setBackground(Color.lightGray);

        //valikkoriviin voidaan lisätä tarvittavat toiminnallisuusnapit
        valikkorivi.add(luoPelaajanToimintonapit());
        valikkorivi.add(luoValintavalineet());

        ruutu.add(piirtoalusta);
        ruutu.add(valikkorivi, BorderLayout.SOUTH);
        ruutu.add(infokentta, BorderLayout.NORTH);
    }

    private JPanel luoPelaajanToimintonapit() {
        JPanel toimiNapit = new JPanel(new GridLayout(1, 3));

        JButton nosta = new JButton("Nostan nallekarkkeja");
        nosta.addActionListener(new ToimintonapinKuuntelija(pelivelho, nosta, nappulat));
        JButton osta = new JButton("Ostan omaisuutta");
        osta.addActionListener(new ToimintonapinKuuntelija(pelivelho, osta, nappulat));
        JButton varaa = new JButton("Varaan omaisuutta");
        varaa.addActionListener(new ToimintonapinKuuntelija(pelivelho, varaa, nappulat));

        toimiNapit.add(nosta);
        toimiNapit.add(osta);
        toimiNapit.add(varaa);

        nappulat[0] = toimiNapit;

        return toimiNapit;
    }

    private JPanel luoValintavalineet() {
        JPanel valinnat = new JPanel(new GridLayout(1, 0));
        valinnat.setBackground(Color.lightGray);

        valinnat.add(luoKarkinvalitsemisnappulat());
        valinnat.add(luoValintanapit());

        return valinnat;
    }

    private JPanel luoKarkinvalitsemisnappulat() {
        JPanel karkinvalitsemisnappulat = new JPanel(new GridLayout(1, 6));
        JLabel[] nallekarkkikentat = new JLabel[5];

        for (int i = 1; i < 6; i++) {
            JPanel nappulat = new JPanel(new GridLayout(3, 1));

            JLabel kentta = new JLabel("0");
            kentta.setHorizontalAlignment(SwingConstants.CENTER);
            nallekarkkikentat[i - 1] = kentta;

            JButton plus = new JButton("+");
            piirtoavustaja.asetaNappulanVari(plus, i);
            plus.addActionListener(new MaaranappienKuuntelija(kentta, true));

            JButton miinus = new JButton("-");
            piirtoavustaja.asetaNappulanVari(miinus, i);
            miinus.addActionListener(new MaaranappienKuuntelija(kentta, false));

            nappulat.add(kentta);
            nappulat.add(plus);
            nappulat.add(miinus);
            karkinvalitsemisnappulat.add(nappulat);
        }

        JButton nosta = new JButton(">");
        nosta.addActionListener(new NallekarkkivalitsimenKuuntelija(pelivelho, infokentta, nallekarkkikentat, piirtoalusta, nappulat));

        karkinvalitsemisnappulat.add(nosta);

        nappulat[1] = karkinvalitsemisnappulat;

        return karkinvalitsemisnappulat;
    }

    private JPanel luoValintanapit() {
        JPanel kaikkiNapit = new JPanel(new GridLayout(1, 0));
        kaikkiNapit.setBackground(Color.lightGray);

        Valintanapit valintanapit = new Valintanapit(pelivelho, nappulat, piirtoalusta, infokentta);
        pelivelho.setValintanapit(valintanapit);

        JButton takaisin = new JButton("takaisin");
        takaisin.addActionListener(new ToimintonapinKuuntelija(pelivelho, takaisin, nappulat));

        kaikkiNapit.add(valintanapit);
        kaikkiNapit.add(takaisin);

        nappulat[2] = valintanapit;
        nappulat[3] = takaisin;

        return kaikkiNapit;
    }

    public void julistaVoittaja(String voittaja, String voittovalta, String kierros) {
        for (int i = 0; i < 4; i++) {
            nappulat[i].setVisible(false);
        }

        Loppuikkuna loppuikkuna = new Loppuikkuna(voittaja, voittovalta, kierros, this);
        loppuikkuna.run();
    }

    public void tuhoa() {
        ruutu.setVisible(false);
        ruutu.dispose();
    }
}
