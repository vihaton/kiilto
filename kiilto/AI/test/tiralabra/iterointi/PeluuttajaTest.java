package tiralabra.iterointi;

import logiikka.Pelaaja;
import logiikka.Pelinpystyttaja;
import logiikka.Poyta;
import org.junit.Before;
import org.junit.Test;
import tiralabra.AlmaIlmari;
import tiralabra.tietorakenteet.Strategia;
import tiralabra.tietorakenteet.Vuoro;
import tiralabra.tietorakenteet.VuoronToiminto;

import java.util.ArrayList;

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
        when(mockAI.suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.TEHOKAS)))
                .thenReturn(nostaKolme);
        when(mockAI.suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_OMISTUKSIA)))
                .thenReturn(ostaEka);

        p = new Peluuttaja(mockPP, mockAI, false);
    }

    @Test
    public void peluutaAInVuoroTest() {
        p.peluutaAInVuoro(Strategia.OLETUS);
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.OLETUS));
        p.peluutaAInVuoro(Strategia.TEHOKAS);
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.TEHOKAS));
        p.peluutaAInVuoro(Strategia.HAMSTRAA_OMISTUKSIA);
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_OMISTUKSIA));
    }

    @Test
    public void peluutaKierroksenAIt() {
        p.peluutaSeuraavatTekoalyt();
        //olettaa, että strategioiden kolme ensimmäistä ovat OLETUS, TEHOKAS, OMISTUKSET
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.HAMSTRAA_OMISTUKSIA));
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.TEHOKAS));
        verify(mockAI, times(1)).suunnitteleVuoro(nullable(Pelaaja.class), nullable(Poyta.class), eq(Strategia.OLETUS));
    }

    @Test
    public void peluutaTest() {
        p = new Peluuttaja(new Pelinpystyttaja(kuinkaMontaAIta));
        p.peluutaPeli(new Strategia[]{Strategia.OLETUS, Strategia.HAMSTRAA_KARKKEJA, Strategia.HAMSTRAA_OMISTUKSIA});
    }
}
