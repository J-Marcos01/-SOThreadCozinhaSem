package controller;
/*Existem diversos jogos de simulação. Um deles simula a participação de cozinheiros em
uma cozinha profissional realizando pratos. Numa das fases, o cozinheiro precisa
realizar o cozimento de 5 pratos simultâneos, onde cada cozimento não depende da
interação do jogador. Pratos de ID ímpar, são chamados de Sopa de Cebola e levam de
0,5 a 0,8 segundos para ficar prontos. Pratos de ID par, são chamados de Lasanha a
Bolonhesa e levam de 0,6 a 1,2 segundos para ficar prontos. Quando um prato inicia, é
necessário comunicar, em console, que se iniciou e, a cada 0,1 segundos, deve-se exibir
o percentual de cozimento (O percentual é definido pelo tempo total dividido por 0,1
segundos). Quando um prato fica pronto, é necessário comunicar em console o final e
fazer a entrega, que leva 0,5 segundos. O jogador só pode entregar um prato por vez e
deve comunicar a entrega. Simular a situação em Java.
*/

import java.util.concurrent.Semaphore;

public class ThreadCozinha extends Thread{

	private int idPrato;
	private static Semaphore semaforo;
	
	public ThreadCozinha(int idPrato,Semaphore semaforo)
	{
		this.idPrato=idPrato;
		this.semaforo=semaforo;
	}
	
	public void run()
	{
		cozinhar();
		try {
		entregar();
		semaforo.acquire();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			semaforo.release();
		}
	}

	private void cozinhar() {
		
		String prato;
		int tempo;
			
			if(idPrato%2==1)
			{
				prato="Sopa de Cebola";
				tempo=(int)((Math.random()*301)+500);
				
			}else {
				prato="Lasanha a Bolanhesa";
				tempo=(int)((Math.random()*601)+600);
			}
		System.out.println("Prato :" +idPrato +" "+prato +"  iniciou preparo");
		
		int tempoAgora=0;
		int pausa=100;
		
		while(tempoAgora<tempo)
		{
			int tempoRestante=tempo-tempoAgora;
			int tempoI=pausa;

			if(tempoRestante<pausa) {
				tempoI=tempoRestante;
			}
			try {
				sleep(tempoI);
				}catch (Exception e) {
					System.err.println(e.getMessage());
				}
			tempoAgora+=tempoI;
			
			int percentual=(tempoAgora*100)/tempo;
			System.out.println("Prato :" +idPrato +" " +percentual +" % cozido");
		}
				
	}

	private void entregar() {
		
		int tempoEntrega=500;
		System.out.println("O prato :" + idPrato + " foi entregue.");
		try {
			sleep(tempoEntrega);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	
}
