//package ep.ia;
/*Objeto de Entrada que possui um array de linhas que contém os 64 valores das respectivas colunas e
 * o resultado de cada linha que representa o valor escrito a mao pela pessoa.
 */
public class Entrada {
	public double[] linha;
	public int resultado;
	
	public Entrada(double[] linha, int resultado){
		this.linha = linha;
		this.resultado = resultado;
	}
}
