
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
        //wip
        return 0;
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
}
