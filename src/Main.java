/**
 * 
 * Universidad del Valle de Guatemala
 * Algoritmos y estructuras de datos
 * Hoja de trabajo 9
 * 26/04/2020
 * 
 * Diccionario Ingles-Español.
 * Traducción de texto en archivo usando un archivo de diccionario.
 * Implementación de árboles binarios de búsqueda. Splay Tree y HashMap.
 * Uso de patrón Factory.
 * 
 * @author Julio Herrera 19402
 * @author Daniela Batz 19214
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	/**
	 * 
	 * Este método es utilizado para leer el archivo datos.txt. La lectura se
	 * realiza para todas las líneas del archivo y separa cada caracter que tenga el
	 * archivo para agregarlo a la lista de Strings que devolverá.
	 * 
	 * @return una lista de todos los elementos del archivo separados
	 * @throws Exception excepción general para la lectura del archivo
	 */
	public static ArrayList<String> textReader(String fileName) throws Exception {
		final String bar = File.separator;
		final String dir = System.getProperty("user.dir");
		/**
		 * AQUI SE LEE EL ARCHIVO TXT si no corre se debe de reemplazar en el parentesis
		 * (dir + barra +"NOMBRE DEL FOLDER EN DONDE ESTA EL PROYECTO" +barra+
		 * "datos.txt") El error del archivo de texto puede pasar si se corre el
		 * programa en eclipse y no en consola o tambien sucede al trabajar con paquetes
		 */
		final File file = new File(dir + bar + fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("No se encontro el archivo, ver lineas comentadas");
		}
		FileReader fr;
		fr = new FileReader(file);
		final BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lineList = new ArrayList<>(); //ya que solo se agrega al final
		String line = "";
		while ((line = br.readLine()) != null) {
			lineList.add(line);
		}
		br.close();
		return lineList;
	}

	public static void main(final String[] args) throws Exception {
        /**
		 * Menu de elección de implementación a usar
		 */
		Scanner scan = new Scanner(System.in);
		int mapType = -1;
		boolean isCorrect = false;
		while (!isCorrect) {
			System.out.println("Ingrese la implementacion de Map que desea usar:");
			System.out.println("1. SplayTree");
			System.out.println("2. HashMap de JCF.");
			System.out.println("3. TreeMap de JCF");
			try {
				mapType = Integer.valueOf(scan.nextLine());
				if (mapType > 0 && mapType < 4) {
					isCorrect = true;
				} else {
					System.out.println("Ingrese un numero valido");
				}
			} catch (Exception e) {
				System.out.println("Ingrese un numero valido");
			}
		}
		isCorrect = false;
		/**
		 * Menu de eleccion de archivo a leer
		 */
		String fileName = "";
		while (!isCorrect) {
            System.out.println("Escriba el nombre del archivo que va a leer");
            System.out.println("Si presiona solo enter se escoge por default ('text.txt'): ");
			fileName = scan.nextLine();
			if (fileName.split(".").length < 1) {
                System.out.println("Indique la extension del archivo .txt");
            } else {
                isCorrect = true;
            }
		}
        isCorrect = false;
		if (fileName.equals("")) {
			fileName = "text.txt";
		}
		/**
		 * Se leen los archivos de texto
		 */
        ArrayList<String> listDictionary = textReader("Spanish.txt");
        ArrayList<String> sentences = textReader(fileName);
        /**
         * Se implementa el patron Factory para decidir que mapeo usar
         */
        MapFactory<String, String> mapFactory = new MapFactory<>();
        /**
		 * Estructuras de datos
         * Se obtiene la implementacion de mapa del factory segun lo elegido por el usuario
		 * Se crea una lista de oraciones con la lista de palabras.
         */
        java.util.Map<String, String> map = mapFactory.getMap(mapType);
		ArrayList<ArrayList<Association<String, String>>> sentencesAssociations = new ArrayList<>();
		/**
		 * Comienza la lectura de los elementos del archivo en la lista para ingresarlos
		 * al map.
		 */
        String[] traductions = new String[2];
		for (String enAndSpa : listDictionary) {
			if (enAndSpa.indexOf("#") == -1) {
				traductions[0] = enAndSpa.split("\t")[0];
				traductions[1] = enAndSpa.split("\t")[1].split(",")[0];
				//TODO: quitar todo lo demas
				map.put(traductions[0].toLowerCase(), traductions[1]);
			}
		}
		/**
		 * Se agregan las palabras a la lista de oraciones tambien como asociaciones
		 */
        Association<String, String> node;
		int contSentence = 0;
		for (String sentence : sentences) {
			sentencesAssociations.add(new ArrayList<Association<String, String>>());
			String[] words = sentence.replaceAll("\\.","").split(" ");
			for (String word : words) {
				node = new Association<>(word.toLowerCase(), null);
				sentencesAssociations.get(contSentence).add(node);
			}
			contSentence++;
		}
		/**
		 * se recorren las lineas del texto *(oraciones) para traducir
		 */
		String result = "";
		for (ArrayList<Association<String, String>> sentence : sentencesAssociations) {
			for (Association<String, String> wordToTranslate : sentence) {
				if (map.containsKey(wordToTranslate.getKey())) {
					result += String.valueOf(map.get(wordToTranslate.getKey()));
				} else {
					result += "*" + wordToTranslate.getKey() + "*";
				}
				result += " ";
			}
			result = result.substring(0, result.length() - 1);
			result += ".";
			result += "\n";
		}
		/**
		 * Se imprime el resultado
		 */
		System.out.println(result);
		scan.close();
	}
}
