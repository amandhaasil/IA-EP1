package ep.ia;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
/* Classe que le os arquivos de entrada.
 * Envia a entrada para a retirada de atributos e para a normalizacao, temporariamente será a main
 */
public class Leitor 
{
	public static void main(String[] args) 
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
            finally
            {
            	System.out.println("Finally");
            }
			scan = new Scanner(reader);
            LinkedList<Entrada> lista = new LinkedList<Entrada>(); 
            LinkedList<Entrada> listara = new LinkedList<Entrada>();
            int contador = 1;
            String linha = null;
            String[] linhasplit = null;
            while(scan.hasNext()){	
            	linha = scan.nextLine();
            	linhasplit = linha.split(",");
            	double[] linharray = new double[64];
            	for(int i = 0; i < 64; i++)
            	{            		
            		linharray[i] = Integer.parseInt(linhasplit[i]);
            	}
            	Entrada entrada = new Entrada(linharray, Integer.parseInt(linhasplit[64]));
            	lista.add(entrada);
            	Entrada backuplinha = lista.getFirst();
            	
            	contador++;
            }
            scan.close();  
            contador = lista.size();
            lista = RetiraAtributo.retirada(lista);
            listara = Normaliza.normalizacao(lista);
            //no caso da lvq/mlp - quando tiver
            //LVQ redeLVQ = new LVQ(1);
            //redeLVQ.treinamento(listara);
		}
	}
}
