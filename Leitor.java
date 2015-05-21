package ep.ia;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
/* Classe que le os arquivos de entrada.
 * Envia a entrada para a retirada de atributos e para a normalizacao, temporariamente*/ 

public class Leitor 
{
	public static int atributos = 64;
	public static void main(String[] args) 
	{
			LinkedList<Entrada> listaEntrada = new LinkedList<Entrada>();
			System.out.println("Selecione o arquivo de treinamento!");
			listaEntrada = leArquivo();
            //contador = lista.size();
            listaEntrada = RetiraAtributo.retirada(listaEntrada);
            listaEntrada = Normaliza.normalizacao(listaEntrada);
            //Com holdout
            Holdout holdout = new Holdout();
            holdout.separa(listaEntrada);
            //Usando arquivo .tes para teste
            concatena(holdout.validacao, holdout.teste);
            System.out.println("");
            System.out.println("Selecione o arquivo de teste!");
            holdout.teste = leArquivo();
            holdout.teste = RetiraAtributo.retirada(holdout.teste);
            holdout.teste = Normaliza.normalizacao(holdout.teste);
            LVQ redeLVQ = new LVQ(0.9);
            redeLVQ.treinamento(holdout.treinamento, holdout.validacao, holdout.teste);
	}
	
	public static LinkedList<Entrada> leArquivo()
	{
		JFileChooser choice = new JFileChooser();
		int option = choice.showOpenDialog(choice);
		if (option == JFileChooser.APPROVE_OPTION) 
		{
			FileReader reader = null;
			Scanner scan = null;
			File file;
			try
			{            		
            		file = choice.getSelectedFile();
            		reader = new FileReader(file);
            		
			} 
            catch (FileNotFoundException e) 
            {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			scan = new Scanner(reader);
            LinkedList<Entrada> lista = new LinkedList<Entrada>(); 
            //LinkedList<Entrada> listara = new LinkedList<Entrada>();
            //int contador;
            String linha = null;
            String[] linhasplit = null;
            while(scan.hasNext()){	
            	linha = scan.nextLine();
            	linhasplit = linha.split(",");
            	double[] linharray = new double[atributos];
            	for(int i = 0; i < atributos; i++)
            	{            		
            		linharray[i] = Integer.parseInt(linhasplit[i]);
            	}
            	Entrada entrada = new Entrada(linharray, Integer.parseInt(linhasplit[atributos]));
            	lista.add(entrada);
            	//Entrada backuplinha = lista.getFirst();
            	//contador++;
            }
            scan.close();
            return lista;
            
		}
		return null;
	}
	public static LinkedList<Entrada> concatena(LinkedList<Entrada> lista1, LinkedList<Entrada> lista2){
		lista1.addAll(0, lista2);
		Collections.shuffle(lista1);
		return lista1;
			
	}
}
