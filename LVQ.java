package ep.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LVQ {
	private double taxa;
	private int contadorErros;
	private List<NeuronioLVQ> neuronios;
	private int epocas;
	private double critParada;
	private int numNeuronios;
	private int classes;
	
	public LVQ(double taxa, int epocas, double critParada, int numNeuronios, int classes){
		 contadorErros = 0;
		 this.taxa = taxa;
		 this.epocas = epocas;
		 this.critParada = critParada;
		 this.numNeuronios = numNeuronios;
		 this.classes = classes;
	 }
	
	public void inicializa(LinkedList<Entrada> entradas)
	{
		neuronios = new ArrayList<NeuronioLVQ>();
		int tamanho = entradas.get(0).linha.length;	
		int indice = 0;
		for(int j = 0; j < numNeuronios; j++){
			NeuronioLVQ neuronioNovo = new NeuronioLVQ(tamanho);
			neuronioNovo.setClasse(j%classes);
			indice = neuronioNovo.inicAleatorio(entradas, j%classes, indice); // ou 0
			neuronios.add(neuronioNovo);
		}
	}
	
	public void incrementaContador(){
		contadorErros++;
	}
	public int retornaErros(){
		return contadorErros;
	}
	public void resetaErros(){
		contadorErros = 0;
	}
	public void reduzTaxa(int epoca, int epocas){
		taxa = taxa * 0.9;		
	}
	public int calculaDistancia(Entrada entrada,List<NeuronioLVQ> neuronios){
		double minimod = Double.MAX_VALUE;
		double distancia = 0;
		int vencedor = -1;
		for (int k = 0; k < neuronios.size(); k++){
			distancia = euclidiana(entrada.linha, neuronios.get(k).getPeso());				
			if(distancia < minimod){
				minimod = distancia;
				vencedor = k;	
			}
		}
		return vencedor;
	}
	
	public double euclidiana(double[] linha, double[] peso){
		double soma = 0;
		for(int p = 0; p < linha.length; p++)
			soma += Math.pow(linha[p] - peso[p],2);	
		return soma;
	}
	
	public double[] somaDoubles(double[] d1, double[] d2){
		double[] soma = new double[d1.length];
		for(int i = 0; i < soma.length; i++){
			soma[i] = d1[i] + d2[i];
		}
		return soma;
	}
	
	public double[] subtraiDoubles(double[] d1, double[] d2){
		double[] subtrai = new double[d1.length];
		for(int j = 0; j < subtrai.length; j++){
			subtrai[j] = d1[j] - d2[j];
		}
		return subtrai;
	}
	
	public double[] multiplicaDoubles(double[] d, double m){
		double[] multiplica = new double[d.length];
		for(int k = 0; k < multiplica.length; k++){
			multiplica[k] = d[k] * m;
		}
		return multiplica;
	}
	
	public double[] atualizaPeso(double[] pesos, double[] entradas, boolean resultado){
		if(resultado)
			pesos = somaDoubles(pesos, multiplicaDoubles(subtraiDoubles(entradas, pesos),taxa));
		else
			pesos = subtraiDoubles(pesos, multiplicaDoubles(subtraiDoubles(entradas, pesos),taxa));
		return pesos;
	}
	 
	public void treinamento(LinkedList<Entrada> treinamento, LinkedList<Entrada> validacao, LinkedList<Entrada> teste)
	{
		//double taxaTreino = this.taxa; 
		inicializa(treinamento);
		for(int epoca = 0; epoca < epocas; epoca++){
			if(taxa < critParada)
				break;
			Collections.shuffle(treinamento);
			resetaErros();
			for(int i = 0; i < treinamento.size(); i++)
			{
				int vencedor = calculaDistancia(treinamento.get(i), neuronios);
				neuronios.get(vencedor).alterado = true;
				boolean resultado;
				if(neuronios.get(vencedor).getClasse() == treinamento.get(i).resultado)
					resultado = true;
				else{
					resultado = false;
					incrementaContador();
				}
					
				neuronios.get(vencedor).setPeso(atualizaPeso(neuronios.get(vencedor).getPeso(), treinamento.get(i).linha, resultado));
			}
			System.out.println("Erro da epoca de treinamento  "+ epoca+": "+retornaErros());
			reduzTaxa(epoca, epocas);
			System.out.println("taxa: "+ taxa);
			resetaErros();
			for(int y = 0; y < validacao.size(); y++){
				int vencedor = calculaDistancia(validacao.get(y), neuronios);
				//System.out.println("talvez eu entre no if");
				if(neuronios.get(vencedor).getClasse() != validacao.get(y).resultado){
					incrementaContador();
					//System.out.println("to no if");
				}
					
				
			}
			System.out.println("Erro da epoca de validacao  "+ epoca +": "+retornaErros());
			if((epoca+1)%3 == 0)
				neuronios = retiraNeuronios(neuronios);
			System.out.println("Num de neuronios: "+ neuronios.size());
			
		}
		resetaErros();
		MatrizConfusao matriz = new MatrizConfusao(classes);
		for(int z = 0; z < teste.size(); z++){
			int vencedor = calculaDistancia(teste.get(z), neuronios);
			matriz.populaMatriz(teste.get(z).resultado, neuronios.get(vencedor).getClasse());
			if(neuronios.get(vencedor).getClasse() != teste.get(z).resultado){
				incrementaContador();
			}
				
			
		}
		System.out.println("Tamanho do teste:" + teste.size());
		System.out.println("Erro do teste: "+retornaErros());
		matriz.calculaValores();
		matriz.imprimeMatriz();
		System.out.println("F-Score: "+matriz.FScore);
	}
	public List<NeuronioLVQ> retiraNeuronios(List<NeuronioLVQ> neuronios){
		for(int r = 0; r < neuronios.size(); r++){
			if(neuronios.get(r).alterado == false)
				neuronios.remove(r);
			else
				neuronios.get(r).alterado = false;
			
		}
		return neuronios;
	}
}
