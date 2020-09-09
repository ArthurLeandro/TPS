import java.io.*;
import java.util.*;

import jdk.jfr.events.FileWriteEvent;

import java.time.*;

public class TP2 {
	public static void main(String[] args) throws Exception {
		Scanner reader = new Scanner(System.in);
		// csvReader.useDelimiter(",");
		String word = "";
		boolean controller = false;
		do {
			word = reader.nextLine();
			controller = IsAble(word);
			if (!controller) {
				Scanner csvReader = new Scanner(new File("./players.csv"));
				int lineToread = Integer.parseInt(word);
				String auxWord = "";
				for (int i = 0; i < lineToread + 2; i++) {
					auxWord = csvReader.nextLine();
				}
				csvReader.close();
				String[] splitted = auxWord.split(",");
				splitted = FixEntry(splitted);
				Player aux = new Player(splitted);
				aux.PrintPlayer();
			}
		} while (!controller);
		reader.close();

	}

	public static boolean IsAble(String _word) {
		return _word.charAt(0) == ('F') && _word.charAt(1) == ('I') && _word.charAt(2) == ('M');
	}

	public static String[] FixEntry(String[] word) {
		if (word.length != 8) {
			String[] valueToReturn = new String[8];
			String NULO = "nao informado";
			for (int i = 0; i < word.length; i++) {
				if (!word[i].equals(""))
					valueToReturn[i] = word[i];
				else
					valueToReturn[i] = NULO;
			}
			if (word.length != 8) {
				for (int i = word.length; i < valueToReturn.length; i++) {
					valueToReturn[i] = NULO;
				}
			}
			return valueToReturn;
		} else {
			return word;
		}
	}
}

// #region <---------------- ALL FUNCTIONAL INTERFACES---------------------->
@FunctionalInterface
interface OperateOnArray<T> {
	void Operate(T[] array, T start);
}

@FunctionalInterface
interface OperateOnArrayWithAuxiliar<T, Y> {
	void Operate(T[] array, Y start);
}

@FunctionalInterface
interface OperateOnArrayRand<T> {
	void Operate(T[] array, Random start);
}

@FunctionalInterface
interface OperateInArray<T, Y> {
	Y Operate(T first, T second);
}

@FunctionalInterface
interface OperateOnArrayComplete<T, Y, Z> {
	void Operate(T name, Y comparacao, Z tempo);
}

@FunctionalInterface
interface OperateOnArrayWithArray<T> {
	void Operate(T[] array, int v1, int v2);
}

@FunctionalInterface
interface Sort<T> {
	void Sort(T[] array, OperateInArray swap, OperateInArray compare, OperateInArray onComplete);
}

@FunctionalInterface
interface SortWithComparison<T> {
	void Sort(T[] array);
}

// #endregion

class Player {
	private int id, heigth, weigth;
	private String name, university, birthYear, birthCity, birthState;
	private final String NULO = "nao informado";

	// #region <----------- CTOR & GETTERS & SETTERS ------------------>
	public Player(int id, int heigth, int weigth, String name, String university, String birthYear, String birthCity,
			String birthState) {
		this.id = id;
		this.heigth = heigth;
		this.weigth = weigth;
		this.name = name;
		this.university = university;
		this.birthYear = birthYear;
		this.birthCity = birthCity;
		this.birthState = birthState;
	}

	public Player(String[] splitted) {
		setId(Integer.parseInt(splitted[0]));
		setName(splitted[1]);
		setHeigth(Integer.parseInt(splitted[2]));
		setWeigth(Integer.parseInt(splitted[3]));
		setUniversity(splitted[4]);
		setBirthYear(splitted[5]);
		setBirthCity(splitted[6]);
		setBirthState(splitted[7]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getWeigth() {
		return weigth;
	}

	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = (!name.equals("")) ? name : NULO;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = (!university.equals("")) ? university : NULO;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = (!birthYear.equals("")) ? birthYear : NULO;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = (!birthCity.equals("")) ? birthCity : NULO;
	}

	public String getBirthState() {
		return birthState;
	}

	public void setBirthState(String birthState) {
		this.birthState = (!birthState.equals("")) ? birthState : NULO;
	}
	// #endregion

	public void PrintPlayer() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(getId());
		stringBuilder.append(" ## ");
		stringBuilder.append(getName());
		stringBuilder.append(" ## ");
		stringBuilder.append(getHeigth());
		stringBuilder.append(" ## ");
		stringBuilder.append(getWeigth());
		stringBuilder.append(" ## ");
		stringBuilder.append(getBirthYear());
		stringBuilder.append(" ## ");
		stringBuilder.append(getUniversity());
		stringBuilder.append(" ## ");
		stringBuilder.append(getBirthCity());
		stringBuilder.append(" ## ");
		stringBuilder.append(getBirthState());
		stringBuilder.append(" ## ");
		stringBuilder.append("]");
		System.out.println(stringBuilder.toString());
	}

}

class SortingRelatedItems {
	int amountOfComparisons = 0;
	int amountOfSwaps = 0;
	/**
	 * Procedimento para efetuar o swap entre dois Player
	 * 
	 * @param array    O array contendo os elementos que devem ser trocados
	 * @param elementI um dos elementos que serao envolvidos na troca
	 * @param elementJ um dos elementos que serao envolvidos na troca
	 */
	OperateOnArrayWithArray<Player> SwapPlayer = (array, elementI, elementJ) -> {
		Player temp = array[elementI];
		array[elementI] = array[elementJ];
		array[elementJ] = temp;
		amountOfSwaps++;
	};
	/**
	 * @param first  primeira String
	 * @param second segunda String
	 * @return boolean second > first
	 * @apiNote first > second
	 */
	OperateInArray<String, Boolean> CompareStringBigger = (first, second) -> {
		return first.compareTo(second) > 1;
	};
	/**
	 * @param first  primeira String
	 * @param second segunda String
	 * @return boolean second < first
	 * @apiNote first < second
	 */
	OperateInArray<String, Boolean> CompareStringSmaller = (first, second) -> {
		return first.compareTo(second) < 1;
	};
	/**
	 * @param first  primeira int
	 * @param second segunda int
	 * @return boolean second < first
	 * @apiNote first < second
	 */
	OperateInArray<Integer, Boolean> CompareInt = (first, second) -> {
		return (int) first > (int) second;
	};
	/**
	 * @param first  primeira int
	 * @param second segunda int
	 * @return boolean second > first
	 * @apiNote first > second
	 */
	OperateInArray<Integer, Boolean> CompareLesserInt = (first, second) -> {
		return (int) first < (int) second;
	};
	/**
	 * Printa os elementos do array.
	 * 
	 * @param array Player array a ser impresso
	 * @param start Integer posição inicial
	 */
	OperateOnArrayWithAuxiliar<Player, Integer> PrintArrayInteger = (array, start) -> {
		for (int i = 0; i < array.length; i++) {
			array[i].PrintPlayer();
		}
	};
	/**
	 * Procedimento para gerar um log, que é chamado ao fim de todas as ordenações.
	 * @param name Nome do exercicio
	 * @param comparacao número de comparações executada pelo algoritmo
	 * @param tempo tempo levado para executar o algoritmo
	 */
	OperateOnArrayComplete<String, Integer, Double> GenerateLog = (name, comparacao, tempo) -> {
		try {
			FileWriter toWrite = new FileWriter(new File("634878_"+name+".txt")
	StringBuilder stringBuilder = new StringBuilder();stringBuilder.append("634878\t");stringBuilder.append(tempo);stringBuilder.append("\t");stringBuilder.append(comparacao);toWrite.write(stringBuilder.toString());}catch(
	Exception e)
	{
		System.out.println("Ocorreu um erro ao finalizar o método de ordenação");
	}};


	// PESQUISA SEQUENCIAL
	// ORDENAÇÂO SELEçÂO
	// INSERçÂO
	// HEapSort
	// Counting Sort
	// Merge Sort
	// Ordenação parcial selecao
	// quicksort parcial

}