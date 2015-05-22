package ep.ia;

import java.util.LinkedList;

public class Holdout {
	
	public LinkedList<Entrada> treinamento;
	public LinkedList<Entrada> validacao;
	public LinkedList<Entrada> teste;
	
	public void separa(LinkedList<Entrada> entradas) {
		
		treinamento = new LinkedList<Entrada>();
		validacao = new LinkedList<Entrada>();
		teste = new LinkedList<Entrada>();
		
		int[] quantidade = new int[10];
		for(int a = 0; a < entradas.size(); a++){
			quantidade[entradas.get(a).resultado]++;
		}
		int[] quantidadeTreino = new int[10];
		for(int b = 0; b < quantidade.length; b++){
			quantidadeTreino[b] = (int) Math.ceil(quantidade[b]*0.6);
		}
		
		for(int c = 0; c < quantidadeTreino.length; c++){
			for(int e = 0; e < entradas.size(); e ++){	
			   if(quantidadeTreino[c] > 0){
				   if(entradas.get(e).resultado == c){
					   quantidadeTreino[c]--;
					   treinamento.add(entradas.get(e));
					   entradas.remove(e);
				   }
			   }
			}	
		}
		
		int[] quantidadeValidacao = new int[10];
		for(int b = 0; b < quantidade.length; b++){
			quantidadeValidacao[b] = (int) Math.ceil(quantidade[b]*0.2);
		}
		
		for(int c = 0; c < quantidadeValidacao.length; c++){
			for(int e = 0; e < entradas.size(); e ++){	
			   if(quantidadeValidacao[c] > 0){
				   if(entradas.get(e).resultado == c){
					   quantidadeValidacao[c]--;
					   validacao.add(entradas.get(e));
					   entradas.remove(e);
				   }
			   }
			}	
		}
		teste = entradas;
	}
}
