package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Produto;

public class NovoArquivo {

	public static void main(String[] args) throws IOException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner (System.in);
		
		List<Produto> list = new ArrayList<>();
		
		// Lendo o caminho do arquivo e pegando somente o caminho
		System.out.println("Enter file path: ");
		String sourceFileStr = sc.nextLine();
		
		File sourceFile = new File (sourceFileStr);
		String sourceFolderStr = sourceFile.getParent();
		
		// Criando a sub pasta Out no caminho informado
		boolean success = new File (sourceFolderStr + "\\out").mkdir();
		
		// Criando o arquivo summary.csv
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";
		
		// Vamos ler os arquivos
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
			
			String itemCsv = br.readLine();
			while (itemCsv != null) {
				
				// Inserindo as informações no vetor separado por virgula
				String[] fields = itemCsv.split(",");
				String name = fields[0];
				double price = Double.parseDouble(fields[1]);
				int quantity = Integer.parseInt(fields[2]);
				
				// Instanciando a lista
				list.add(new Produto(name, price, quantity));
				
				itemCsv = br.readLine();
			}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
			
			for (Produto item : list) {
				bw.write(item.getName() + "," + String.format("%.2f", item.total()));
				bw.newLine();
			}
			
			System.out.println(targetFileStr + " CREATED!");
			
			} catch (IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			} 
		
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		

		sc.close();
	}

}
