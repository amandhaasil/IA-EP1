package ep.ia;

import java.util.LinkedList;

/* Essa classe e responsavel por normalizar as 64 entradas do arquivo.
 * Utiliza-se o metodo da media com desvio padrao para isso. 
 */

public class Normaliza {
	public static LinkedList<Entrada> normalizacao(LinkedList<Entrada> lista ) {
		double media = 0.0;
		double dpadrao = 0.0;
		double valor = 0.0;
			
		for(int i = 0; i < lista.get(0).linha.length; i++){
			for(int k = 0; k < lista.size(); k++){
				Entrada entrada;
				entrada = lista.get(k);
				media = media + entrada.linha[i];
			}
			media = media / lista.size(); 
			for(int f = 0; f < lista.size(); f++){
				Entrada entrada;
				entrada = lista.get(f);
				dpadrao = dpadrao + Math.pow((entrada.linha[i] - media), 2);
			}
			dpadrao = Math.sqrt(dpadrao/lista.size());
			for(int c = 0; c < lista.size(); c++){
				Entrada entrada = lista.get(c);
				double[] linhaNova = entrada.linha;
				valor = (entrada.linha[i] - media)/dpadrao;
				linhaNova[i] = valor;
				Entrada entradaNova = new Entrada(linhaNova, entrada.resultado);
				lista.remove(c);
				lista.add(c, entradaNova);				
			}
		}
		return lista;

	}

}
