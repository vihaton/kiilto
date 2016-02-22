package ui.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.Pelivelho;
import ui.toiminnankuuntelijat.*;

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
    private final JLabel infoTekstit;
    private final JPanel valikkorivi;
    private final Piirtoalusta piirtoalusta;
    private final PiirtoAvustaja piirtoAvustaja;
    private final int leveys;
    private final int korkeus;

    public Kayttoliittyma(Pelivelho pv) {
        this.pelivelho = pv;
        piirtoAvustaja = new PiirtoAvustaja();
        piirtoalusta = new Piirtoalusta(pv, piirtoAvustaja);
        ruutu = new JFrame("Kiilto-the-Game");
        leveys = 1210;
        korkeus = 725;
        infoTekstit = new JLabel("Tähän ilmestyvät pelin infotekstit", JLabel.CENTER);
        valikkorivi = new JPanel();
    }

    @Override
    public void run() {
        ruutu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ruutu.setPreferredSize(new Dimension(leveys, korkeus));
        ruutu.setLocation(0, 0);

        luoKomponentit();

        ruutu.pack();
        ruutu.setVisible(true);
    }

    private void luoKomponentit() {
        ruutu.setLayout(new BorderLayout()); //turha, mutta muistuttaa asian tilasta
        valikkorivi.setLayout(new GridLayout(1, 0));

        //valikkoriviin voidaan lisätä tarvittavat toiminnallisuusnapit
        valikkorivi.add(luoPelaajanToimintanapit());
        valikkorivi.add(luoValintavalineet());

        ruutu.add(piirtoalusta);
        ruutu.add(valikkorivi, BorderLayout.SOUTH);
        ruutu.add(infoTekstit, BorderLayout.NORTH);
    }
    
    private JPanel luoPelaajanToimintanapit() {
        JPanel toimiNapit = new JPanel(new GridLayout(1, 3));

        JButton nosta = new JButton("Nostan nallekarkkeja");
        JButton osta = new JButton("Ostan omaisuutta");
        JButton varaa = new JButton("Varaan omaisuutta");

        toimiNapit.add(nosta);
        toimiNapit.add(osta);
        toimiNapit.add(varaa);

        return toimiNapit;
    }

    private JPanel luoValintavalineet() {
        JPanel valinnat = new JPanel(new GridLayout(1, 0));
        
        valinnat.add(luoKarkinvalitsemisnappulat());
        valinnat.add(luoValintanapit());
        
        return valinnat;
    }
    
    private JPanel luoKarkinvalitsemisnappulat() {
        JPanel kaikkiNappulat = new JPanel(new GridLayout(1, 5));
        
        for (int i = 1; i < 6; i++) {
            JPanel nappulat = new JPanel(new GridLayout(3, 1));
            
            JLabel kentta = new JLabel("0");
            kentta.setHorizontalAlignment(SwingConstants.CENTER);
            piirtoAvustaja.asetaNappulanVari(kentta, i);
            
            JButton plus = new JButton("+");
            piirtoAvustaja.asetaNappulanVari(plus, i);
            
            JButton miinus = new JButton("-");
            piirtoAvustaja.asetaNappulanVari(miinus, i);
            
            //kuuntelijat
            
            nappulat.add(kentta);
            nappulat.add(plus);
            nappulat.add(miinus);
            kaikkiNappulat.add(nappulat);
        }
        
        return kaikkiNappulat;
    }
    
    private JPanel luoValintanapit() {
        JPanel kaikkiNapit = new JPanel(new GridLayout(1, 0));        
        JPanel valintanapit = new JPanel(new GridLayout(2, 3));
        
        JLabel valitsin = new JLabel("");
        JButton valitse = new JButton("tämä!");
        JButton vasen = new JButton("<--");
        JButton oikea = new JButton("-->");
        
        //kuuntelijat
                
        valintanapit.add(valitsin);
        valintanapit.add(valitse);
        valintanapit.add(vasen);
        valintanapit.add(oikea);
        
        kaikkiNapit.add(valintanapit);
        
        JButton takaisin = new JButton("takaisin");
        //kuuntelija
        
        kaikkiNapit.add(takaisin);
        
        return kaikkiNapit;
    }
    
    private JPanel luoTekstinsyotto() {
        JPanel tekstinsyotto = new JPanel();
        tekstinsyotto.setLayout(new GridLayout(2, 1));

        JTextField tekstiKentta = new JTextField();
        tekstiKentta.setHorizontalAlignment(JTextField.CENTER);

        JButton lahetysNappi = new JButton("Lähetä käsky! Paina minnuu!");

        lahetysNappi.addActionListener(new Tekstinsyotonkuuntelija(pelivelho, tekstiKentta));

        tekstinsyotto.add(tekstiKentta);
        tekstinsyotto.add(lahetysNappi);

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
        infoTekstit.setText("Mitä haluat tehdä " + nimi + "? Valitse vuoron toiminto!");

        valikkorivi.add(luoPelaajanToimintanapit());

        return 1;
    }
}
