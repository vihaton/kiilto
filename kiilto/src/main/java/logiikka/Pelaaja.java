
package logiikka;

import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class Pelaaja {
    private Kasakokoelma karkit;
    private Omaisuus omaisuus;
    private String nimi;

    Pelaaja(String nimi) {
        this.nimi = nimi;
        omaisuus = new Omaisuus();
        karkit = new Kasakokoelma(0);
    }
    
    public Kasakokoelma getKarkit() {
        return karkit;
    }
    
    String getNimi() {
        return nimi;
    }
    
    @Override
    public String toString() { //ilman omistuksia
        return nimi + "\n" + karkit +"\n" + omaisuus;
    }

    public boolean voittaja(int voittoraja) {
        if (omaisuus.getArvovalta() >= voittoraja) {
            return true;
        }
        return false;
    }
    
    private int[] getHintaOmaisuustulotHuomioituna(Omistus omistus) {
        int[] bonukset = omaisuus.getOmaisuudestaTulevatBonusKarkit();
        int[] hintaOmaisuustulotHuomioituna = new int[5];
        
        Kasakokoelma hinta = omistus.getHintaKasat();
        for (int i = 1; i < 6; i++) {
            int kasanKoko = hinta.getKasanKoko(i);
            hintaOmaisuustulotHuomioituna[i-1] = kasanKoko-bonukset[i-1];
        }
        return hintaOmaisuustulotHuomioituna;
    }

    boolean onkoVaraa(Omistus omistus) {
        int[] hintaOmaisuustulotHuomioituna = getHintaOmaisuustulotHuomioituna(omistus);
        
        int kaytettyKulta = 0;
        int kkk = karkit.getKasanKoko(0); //kulta kasan koko
        
        //käydään läpi pelaajan karkkikasat, ja vertaillaan niitä oston hintaan pelaajan omaisuus huomioituna
        for (int i = 1; i < 6; i++) {
            int kki = karkit.getKasanKoko(i); //kasan koko i
            int hohi = hintaOmaisuustulotHuomioituna[i-1];
            
            //jos pelaajalla ei ole varaa, tarkistetaan kultavarannot
            if (kki < hohi) {
                //kultaa pitää olla tarpeeksi puutteen täyttämiseksi
                //vasemmalla puute, oikealla käytettävissä oleva kulta
                if (hohi - kki <= kkk - kaytettyKulta) {
                    kaytettyKulta += hohi - kki;
                } else {
                    return false;
                }
            }
        }
        
        return true;
    }

    //luotetaan, että pelaajalla on varaa suorittaa ostos
    void osta(Omaisuus lahtoOmaisuus, Omistus o, Kasakokoelma markkinat) {
        int[] todellinenHinta = getHintaOmaisuustulotHuomioituna(o);
        
        for (int i = 0; i < todellinenHinta.length; i++) {
            int karkkia = todellinenHinta[i];
            if (karkkia > 0) {
                int pelaajanKarkkikasanKoko = karkit.getKasanKoko(i+1);
                if (karkkia > pelaajanKarkkikasanKoko) {
                    //korvataan puuttuvat karkit kultaisilla
                    karkit.siirraToiseenKasaan(markkinat, 0, karkkia-pelaajanKarkkikasanKoko);
                    karkit.siirraToiseenKasaan(markkinat, i, pelaajanKarkkikasanKoko);
                } else {
                    //pelaajalla on vaara maksaa karkit samanvärisinä
                    karkit.siirraToiseenKasaan(markkinat, i, karkkia);
                }
            }
        }
        
        lahtoOmaisuus.siirraOmistus(omaisuus, o);
    }

}
