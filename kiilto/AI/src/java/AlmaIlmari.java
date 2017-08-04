package java;

import logiikka.*;

/**
 * Created by vili on 4.8.2017.
 *
 * AI tekee päätökset sille annetun pelaajaolion tilanteen mukaan, eli ikään kuin tuo "älyn" ja "tavoitteen" tyhmälle oliolle.
 * On AI:n vastuulla tehdä laillisia siirtoja, tai vaihtoehtoisesti käsitellä pelivelhon huomautukset laittomista siirroista.
 */
public class AlmaIlmari {

    //todo AI ottaa huomioon pelissä olevat merkkihenkilöt
    //todo AI ottaa huomioon pöydällä näkyvillä olevat omistukset

    /**
     * AI pelaa vuoron pelaajan puolesta, kuten oikeakin pelaaja (eli kutsuu pelivelhon metodeita toiminnan mukaan).
     * @param keho jota AlmaIlmari ohjailee
     * @param poyta jolla näkyy tämänhetkinen pelitilanne
     */
    public void pelaaVuoro(Pelaaja keho, Poyta poyta) {
        System.out.println("tekoälyn pitäisi pelata vuoro kehon puolesta:\n" + keho);
        //todo AI tekee siirron
        //todo AI tekee aina laillisen siirron
    }
}
