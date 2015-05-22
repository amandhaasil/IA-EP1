package ep.ia;

import java.util.Collections;
import java.util.LinkedList;

public class Redes {
	public static void main(String[] args) {
		//Configuracoes da rede, alterar esses atributos para testes
		int rede = 0; //0 para LVQ, 1 para MLP
		int hold = 1; //0 para usar o holdout requisito minimo, 1 para utilizar 60% do .tra como treinamento, 40% como validacao e .tes como teste
		//int matriz = 0; //0 para oneXone e 1 para uma matriz de confusao para todos os classificadores TODO
		
		//atributos do arquivo de teste
		int atributos = 64;
		int classes = 10;
		
		//atributos do preprocessamento
		int normaliza = 1;//0 para nao, 1 para sim
		int retiraAtributos = 1;//0 para nao, 1 para sim
		
		Leitor leitor = new Leitor(atributos);
		LinkedList<Entrada> listaEntrada = leitor.le();
		if(retiraAtributos == 1)
			listaEntrada = RetiraAtributo.retirada(listaEntrada);
		if(normaliza == 1)
			listaEntrada = Normaliza.normalizacao(listaEntrada);
		
		Holdout holdout = new Holdout();//aplicacao do holdout.
        holdout.separa(listaEntrada);//separacao 60%, 20%, 20%. Treinamento, validacao e teste respectivamente.
		
		if(hold == 1)
		{
			concatena(holdout.validacao, holdout.teste);//torna a separacao em 60% 40%. Treinamento e validacao, respectivamente.
			System.out.println("");
            System.out.println("Selecione o arquivo de teste!");
            holdout.teste = leitor.leArquivo();//teste agora se torna o novo arquivo selecionado.
            if(retiraAtributos == 1)
            	holdout.teste = RetiraAtributo.retirada(holdout.teste);
            if(normaliza == 1)
            	holdout.teste = Normaliza.normalizacao(holdout.teste);
		}		
		
		if(rede == 0)//Configs da LVQ, alterar para testes, valores ja atribuidos representam condicoes relatadas no relatorio
		{
			double taxa = 0.9;//taxa inicial do treinamento. Variar entre 0 e 1;
			int epocas = 100;//numero total de epocas, caso nao chegue ao criterio de parada;
			double critParada = 0.01;//numero que a taxa devera atingir para interromper treinamento
			int numNeuronios = 500;//define numero de neuronios total;	
			
			LVQ redeLVQ = new LVQ(taxa, epocas, critParada, numNeuronios, classes);//Cria e isntancia a rede.
            redeLVQ.treinamento(holdout.treinamento, holdout.validacao, holdout.teste);//Realiza o treinamento.
		}
		

	}
	
	public static LinkedList<Entrada> concatena(LinkedList<Entrada> lista1, LinkedList<Entrada> lista2){
		lista1.addAll(0, lista2);
		Collections.shuffle(lista1);
		return lista1;
			
	}

}
