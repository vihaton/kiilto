
package logiikka.omaisuusluokat;
import java.util.*;
import logiikka.valineluokat.*;
/**
 *
 * @author xvixvi
 */
public class Omaisuus {
    private ArrayList<Omistus> omistukset;
    
    public Omaisuus() {
        omistukset = new ArrayList<>();
    }
    
    public void lisaaOmistus(Omistus o) {
        omistukset.add(o);
    }
    
    public int getArvovalta() { 
        int arvovalta = 0;
        for (Omistus o : omistukset) {
            arvovalta += o.getArvovalta();
        }
        return arvovalta;
    }
    
    public Omistus getEkaOmistus() {
        return omistukset.get(0);
    }
    
    public Omistus getVikaOmistus() {
        return omistukset.get(omistukset.size()-1);
    }
    
    public int getOmaisuudenKoko() {
        return omistukset.size();
    }
    
    public boolean onkoPA() {
        return omistukset.isEmpty();
    }
    
    @Override
    public String toString() {
        if (this.onkoPA()) {
            return "olen köyhä, minulla ei ole omaisuutta :´(" + "\n";
        }
        
        String s = "";
        for (Omistus o : omistukset) {
            s = s.concat(o.toString()+"\n");
        }
        return s;
    }

    public void sekoita(Random arpoja) {
        for (int i = 0; i < omistukset.size(); i++) {
            for (int j = omistukset.size(); j > 0; j--) {
                //siirretään omistus o indeksistä i satunnaiseen indeksiin r.
                int r = arpoja.nextInt(omistukset.size());
                Omistus o = omistukset.remove(i);
                omistukset.add(r, o);
            }
        }
    }

    public String paallimmaisetToString() {
        String s = "";
        int raja = omistukset.size() < 4 ? omistukset.size() : 4;
        for (int i = 0; i < raja; i++) {
            s = s.concat(omistukset.get(i).toString() + "\n");
        }
        return s;
    }
}
