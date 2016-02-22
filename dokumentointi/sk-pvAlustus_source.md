participant Alkuikkuna as A
participant Pelivelho as P
participant Kayttoliittyma as K
participant Pelaaja as Pe
participant Poyta as Pö

A->*+P: luo
P->*+K: Kayttoliittyma(this.pelivelho)
K->-P: 
P->-A: 
A->+P: luoPelaajat(int x)\nluoPelaajat(ArrayList<String> nimet)
loop for x tai for nimet.size
    P->*Pe: luo
end
P->-A: 

A->+P: pelaa()
P->+P: alustaGUIPeli()
P->*+Pö: Poyta(ArrayList<Pelaaja>)
Pö->-P: 
P->-P: 

P->+K: run()
