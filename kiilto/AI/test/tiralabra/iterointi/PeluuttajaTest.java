package tiralabra.iterointi;

import logiikka.Pelaaja;
import logiikka.Pelinpystyttaja;
import logiikka.Poyta;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import tiralabra.AlmaIlmari;
import tiralabra.tietorakenteet.Peli;
import tiralabra.tietorakenteet.Strategia;
import tiralabra.tietorakenteet.Vuoro;
import tiralabra.tietorakenteet.VuoronToiminto;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

/**
 * Created by vili on 25.8.2017.
 */
public class PeluuttajaTest {

    private Peluuttaja p;
    private int kuinkaMontaAIta = 3;
    private AlmaIlmari mockAI;

    @Before
    public void setup() {
        //setup mockPP
        Pelinpystyttaja mockPP = mock(Pelinpystyttaja.class);
        doNothing().doThrow(new RuntimeException()).when(mockPP).nostaNallekarkkeja(any(int[].class));
        when(mockPP.osta(any(String.class))).thenReturn(true);
        when(mockPP.varaa(any(String.class))).thenReturn(true);
        mockPP.onkoPelaajaAI = new boolean[]{true,true,true};
        mockPP.pelaajat = new ArrayList<>();
        mockPP.pelaajat.add(new Pelaaja("1")); mockPP.pelaajat.add(new Pelaaja("2")); mockPP.pelaajat.add(new Pelaaja("3"));
        when(mockPP.kuinkaMontaAIta()).thenReturn(3);
        mockPP.peliJatkuu = true;

        //setup mokattava AI
        mockAI = mock(AlmaIlmari.class);
        Vuoro nostaKolme = new Vuoro(VuoronToiminto.NOSTA_KOLME);
        nostaKolme.mitaNallekarkkejaNostetaan = new int[]{0,0,1,1,1,0,0};
        Vuoro varaaJoku = new Vuoro(VuoronToiminto.VARAA_ARVOKAS);
        Vuoro ostaEka = new Vuoro(VuoronToiminto.OSTA_POYDASTA);

        when(mockAI.suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.OLETUS)))
                .thenReturn(varaaJoku);
        when(mockAI.suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_KARKKEJA)))
                .thenReturn(nostaKolme);
        when(mockAI.suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_OMISTUKSIA)))
                .thenReturn(ostaEka);

        p = new Peluuttaja(mockPP, mockAI);
    }

    @Test
    public void peluutaAInVuoroTest() {
        p.peluutaAInVuoro(Strategia.OLETUS);
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.OLETUS));
        p.peluutaAInVuoro(Strategia.HAMSTRAA_KARKKEJA);
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_KARKKEJA));
        p.peluutaAInVuoro(Strategia.HAMSTRAA_OMISTUKSIA);
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_OMISTUKSIA));
    }

    @Test
    public void peluutaKierroksenAIt() {
        p.peluutaSeuraavatTekoalyt();
        //olettaa, että strategioiden kolme ensimmäistä ovat OLETUS, KARKIT, OMISTUKSET
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_OMISTUKSIA));
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_KARKKEJA));
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.OLETUS));
    }
}
