package ep.ia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
/* Classe que le os arquivos de entrada.
 * Envia a entrada para a retirada de atributos e para a normalizacao, temporariamente*/ 

public class Leitor 
{
	int atributos;
	
	public Leitor(int tamanho){
		atributos = tamanho;
	}
	
	public LinkedList<Entrada> le() 
	{
			LinkedList<Entrada> listaEntrada = new LinkedList<Entrada>();
			System.out.println("Selecione o arquivo de treinamento!");
			listaEntrada = leArquivo();
			return listaEntrada;
	}	
	
	public LinkedList<Entrada> leArquivo()
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
            String linha = null;
            String[] linhasplit = null;
            while(scan.hasNext()){	//separa cada numero do arquivo de entrada em atributos de entrada, exceto a ultima, que se torna a classe
            	linha = scan.nextLine();
            	linhasplit = linha.split(",");
            	double[] linharray = new double[atributos];
            	for(int i = 0; i < atributos; i++)
            	{            		
            		linharray[i] = Integer.parseInt(linhasplit[i]);
            	}
            	Entrada entrada = new Entrada(linharray, Integer.parseInt(linhasplit[atributos]));
            	lista.add(entrada);
            }
            scan.close();
            return lista;
            
		}
		return null;
	}
	
}
