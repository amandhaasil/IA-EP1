package ep.ia;

import java.util.LinkedList;

/* Retira os atributos indesejaveis
 * Consideramos uma taxa de 95%.
 * Se a quantidade de um mesmo valor de um atributo
 * dividida pela total de valores, no caso 1797, for maior do que a taxa, 
 * esse atributo deverá ser retirado. Isso significa que o atributo em questao
 * apresenta muitos valores de um tipo e um número insignificante dos outros.
 */

public class RetiraAtributo {

	public static LinkedList<Entrada> retirada(LinkedList<Entrada> lista ) {
		double taxa = 95;
		double porcent = 0.0;
		Entrada entrada;
		boolean remove = false;
		boolean checadnovo = false;
		int j = 0;
		int i = 0;
		int verdadeiraColuna = 0;
		while (i < lista.get(0).linha.length){
			if(checadnovo){
				i--;
				checadnovo = false;
			}	
		
			int[] quantidade = new int[17];
			for(int k = 0; k < lista.size(); k++){
				entrada = lista.get(k);				
				quantidade[(int) entrada.linha[i]]++;				
			}
			for(j = 0; j < 17; j++){
				//System.out.println("Coluna " + i + "  Elemento " + j + " Quantidade "+ quantidade[j]);
			}
			j = 0;
			while (j < 17 && remove == false){
				double a = quantidade[j];
				double b = lista.size();		
				porcent = (a/b)*100;
				if(porcent > taxa)
					remove = true;
				j++;
			}
			if (remove){
				System.out.println("Remover coluna" + verdadeiraColuna);
				lista = tiraColuna(lista,i);
				remove = false;
				checadnovo = true;
			}
			i++; 
			verdadeiraColuna++;
		}
		return lista;
	}
	
	public static LinkedList <Entrada> tiraColuna(LinkedList<Entrada> list, int p ){
		for(int m = 0; m < list.size(); m++){
			Entrada entrada;
			entrada = list.get(m);
			double[] linhanova = new double[entrada.linha.length-1];
			for(int n = 0;n<entrada.linha.length;n++){
				if(n != p && n < p)
					linhanova[n] = entrada.linha[n];
				if(n > p)  
					linhanova[n-1] = entrada.linha[n];
			}
			Entrada novaEntrada = new Entrada(linhanova, entrada.resultado);
			list.remove(m);
			list.add(m,novaEntrada);
		}
		return list;
	}
}
