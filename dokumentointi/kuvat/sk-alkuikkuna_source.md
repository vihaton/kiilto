participant Main
participant AlkuIkkuna as A
participant Pelivelho as P

Main->*+A: luo
A->*P: luo
A->-Main:

Main->+A: run()
A->+A: luoSisalto(JFrame)
A->A: lisaaKuuntelijat(JButtons)
