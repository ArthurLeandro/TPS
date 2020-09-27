import java.io.*;
import java.util.*;
import java.time.*;

public class TP2 {
	public static void main(String[] args) throws Exception {
		Scanner reader = new Scanner(System.in);
		Lista list = new Lista(600);
		int exerciseNumber = 7;
		String word = "";
		boolean controller = false;
		do {
			word = reader.nextLine();
			controller = IsAble(word);
			if (!controller) {
				Scanner csvReader = new Scanner(new File("players.csv"));
				int lineToread = Integer.parseInt(word);
				String auxWord = "";
				for (int i = 0; i < lineToread + 2; i++) {
					auxWord = csvReader.nextLine();
				}
				csvReader.close();
				String[] splitted = auxWord.split(",");
				splitted = FixEntry(splitted);
				Player aux = new Player(splitted);
				if (exerciseNumber > 1) {
					list.inserirFim(aux);
				} else {
					aux.PrintPlayer();
				}
			}
		} while (!controller);
		if (exerciseNumber > 1)
			Exercise(exerciseNumber, list, reader);
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

	public static void Exercise(int i, Lista list, Scanner reader) {
		SortingRelatedItems sort = new SortingRelatedItems(list.getN());
		Player[] aux = list.getArray();
		if (i == 3) {// PESQUISA SEQUENCIAL
			String word = reader.nextLine();
			while (word.charAt(0) != 'F' && word.charAt(1) != 'I' && word.charAt(2) != 'M') {
				if (sort.PesquisaSequencial.Operate(aux, word)) {
					System.out.println("SIM");
				} else {
					System.out.println("NAO");
				}
				word = reader.nextLine();
			}
		} else {
			if (i == 5) {// ORDENAÇÂO SELEçÂO
				sort.Selecao.Sort(aux);
			} else if (i == 7) {
				sort.Insercao.Sort(aux);
			} else if (i == 9) {
				sort.Heapsort.Sort(aux);
			} else if (i == 11) {
				sort.Counting.Sort(aux);
			} else if (i == 13) {
				sort.MergeSort(aux, 0, aux.length - 1);
			}
			sort.PrintArrayPlayer.Operate(aux, 0);
		}

		// INSERçÂO
		// HEapSort
		// Counting Sort
		// Merge Sort
		// Ordenação parcial selecao
		// quicksort parcial
	}
}

class Lista {
	private Player[] array;
	private int n;

	/**
	 * Construtor da classe.
	 */
	public Lista() {
		this(50);
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param tamanho Tamanho da lista.
	 */
	public Lista(int tamanho) {
		array = new Player[tamanho];
		n = 0;
	}

	/**
	 * Insere um elemento na primeira posicao da lista e move os demais elementos
	 * para o fim da lista.
	 * 
	 * @param x int elemento a ser inserido.
	 * @throws Exception Se a lista estiver cheia.
	 */
	public void inserirInicio(Player x) throws Exception {

		// validar insercao
		if (n >= array.length) {
			throw new Exception("Lista cheia!");
		}

		// levar elementos para o fim do array
		for (int i = n; i > 0; i--) {
			array[i] = array[i - 1];
		}

		array[0] = x;
		n++;
	}

	/**
	 * Insere um elemento na ultima posicao da lista.
	 * 
	 * @param x int elemento a ser inserido.
	 * @throws Exception Se a lista estiver cheia.
	 */
	public void inserirFim(Player x) throws Exception {

		// validar insercao
		if (n >= array.length) {
			throw new Exception("Lista lotada!");
		}

		array[n] = x;
		n++;
	}

	/**
	 * Insere um elemento em uma posicao especifica e move os demais elementos para
	 * o fim da lista.
	 * 
	 * @param x   int elemento a ser inserido.
	 * @param pos Posicao de insercao.
	 * @throws Exception Se a lista estiver cheia ou a posicao invalida.
	 */
	public void inserir(Player x, int pos) throws Exception {

		// validar insercao
		if (n >= array.length || pos < 0 || pos > n) {
			throw new Exception("Parametros ruins ou lista cheia!");
		}

		// levar elementos para o fim do array
		for (int i = n; i > pos; i--) {
			array[i] = array[i - 1];
		}

		array[pos] = x;
		n++;
	}

	/**
	 * Remove um elemento da primeira posicao da lista e movimenta os demais
	 * elementos para o inicio da mesma.
	 * 
	 * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista estiver vazia.
	 */
	public Player removerInicio() throws Exception {

		// validar remocao
		if (n == 0) {
			throw new Exception("Lista nula!");
		}

		Player resp = array[0];
		n--;

		for (int i = 0; i < n; i++) {
			array[i] = array[i + 1];
		}

		return resp;
	}

	/**
	 * Remove um elemento da ultima posicao da lista.
	 * 
	 * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista estiver vazia.
	 */
	public Player removerFim() throws Exception {

		// validar remocao
		if (n == 0) {
			throw new Exception("Erro ao remover!");
		}

		return array[--n];
	}

	/**
	 * Remove um elemento de uma posicao especifica da lista e movimenta os demais
	 * elementos para o inicio da mesma.
	 * 
	 * @param pos Posicao de remocao.
	 * @return resp int elemento a ser removido.
	 * @throws Exception Se a lista estiver vazia ou a posicao for invalida.
	 */
	public Player remover(int pos) throws Exception {

		// validar remocao
		if (n == 0 || pos < 0 || pos >= n) {
			throw new Exception("Erro ao remover!");
		}

		Player resp = array[pos];
		n--;

		for (int i = pos; i < n; i++) {
			array[i] = array[i + 1];
		}

		return resp;
	}

	/**
	 * Mostra os elementos da lista separados por espacos.
	 */
	public void mostrar() {
		for (int i = 0; i < n; i++) {
			array[i].PrintPlayer();
		}
	}

	/**
	 * Procura um elemento e retorna se ele existe.
	 * 
	 * @param x int elemento a ser pesquisado.
	 * @return <code>true</code> se o array existir, <code>false</code> em caso
	 *         contrario.
	 */
	public boolean pesquisar(Player x) {
		boolean retorno = false;
		for (int i = 0; i < n && retorno == false; i++) {
			retorno = (array[i].getName().equals(x.getName()));
		}
		return retorno;
	}

	public Player[] getArray() {
		return array;
	}

	public void setArray(Player[] array) {
		this.array = array;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
}

// #region <---------------- ALL FUNCTIONAL INTERFACES---------------------->
@FunctionalInterface
interface OperateOnArray<T> {
	void Operate(T[] array, T start);
}

@FunctionalInterface
interface OperateOnArrayReturning<T, Y, Z> {
	Y Operate(T[] array, Z start);
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
	void Sort(T[] array);
}

@FunctionalInterface
interface SortWithComparison<T> {
	void Sort(T[] array);
}

@FunctionalInterface
interface SortWithComparisonExtra<T, Y> {
	void Sort(T[] array, Y a, Y b);
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
		stringBuilder.append("]");
		System.out.println(stringBuilder.toString());
	}

}

class SortingRelatedItems {
	int amountOfComparisons = 0;
	int amountOfSwaps = 0;
	int lookForTillWhere = 0;

	public SortingRelatedItems(int n) {
		setLookForTillWhere(n);
		setAmountOfComparisons(0);
		setAmountOfSwaps(0);
	}

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
		return first.compareTo(second) > 0;
	};
	/**
	 * @param first  primeira String
	 * @param second segunda String
	 * @return boolean second < first
	 * @apiNote first < second
	 */
	OperateInArray<String, Boolean> CompareStringSmaller = (first, second) -> {
		return first.compareTo(second) < 0;
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
	OperateOnArrayWithAuxiliar<Player, Integer> PrintArrayPlayer = (array, start) -> {
		for (int i = 0; i < getLookForTillWhere(); i++) {
			array[i].PrintPlayer();
		}
	};
	OperateOnArrayReturning<Player, Boolean, String> PesquisaSequencial = (array, nome) -> {
		Boolean valueToReturn = false;
		for (int i = 0; i < getLookForTillWhere(); i++) {
			if (array[i].getName().equals(nome)) {
				valueToReturn = true;
				i += array.length;
			}
		}
		return valueToReturn;
	};
	SortWithComparison<Player> Insercao = (array) -> {
		int n = getLookForTillWhere();
		for (int i = 1; i < n; i++) {
			Player tmp = array[i];
			int j = i - 1;
			while ((j >= 0) && (CompareStringBigger.Operate(array[j].getBirthYear(), tmp.getBirthYear()))) {
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = tmp;
		}
	};
	SortWithComparison<Player> Selecao = (array) -> {
		for (int i = 0; i < getLookForTillWhere() - 1; i++) {
			int menor = i;
			for (int j = (i + 1); j < getLookForTillWhere(); j++) {
				if (CompareStringBigger.Operate(array[menor].getName(), array[j].getName())) {
					menor = j;
				}
			}
			SwapPlayer.Operate(array, menor, i);
		}
	};

	void downheapify(Player arr[], int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;

		// finding the maximum of left and right
		if (l < n && CompareStringBigger.Operate(arr[l].getName(), arr[largest].getName()))
			largest = l;

		if (r < n && CompareStringBigger.Operate(arr[r].getName(), arr[largest].getName()))
			largest = r;

		// swapping if child >parent
		if (largest != i) {
			SwapPlayer.Operate(arr, i, largest);
			downheapify(arr, n, largest);
		}
	}

	// HEapSort
	SortWithComparison<Player> Heapsort = (array) -> {
		int n = getLookForTillWhere();
		// build heap by calling heapify on non leaf elements
		for (int i = n / 2 - 1; i >= 0; i--)
			downheapify(array, n, i);
		// extract elements from the root and call healpify
		for (int i = n - 1; i > 0; i--) {
			// swap the last element with root
			SwapPlayer.Operate(array, 0, i);
			// i is the size of the reduced heap
			downheapify(array, i, 0);
		}
	};

	int getMaior(Player[] array) {
		Player maior = array[0];
		int valueToReturn = 0;
		for (int i = 0; i < getLookForTillWhere(); i++) {
			if (CompareInt.Operate(maior.getHeigth(), array[i].getHeigth())) {
				valueToReturn = i;
				maior = array[i];
			}
		}
		return valueToReturn;
	}

	// Counting Sort
	SortWithComparison<Player> Counting = (array) -> {
		int n = getLookForTillWhere();
		// Array para contar o numero de ocorrencias de cada elemento
		int[] count = new int[getMaior(array) + 1];
		Player[] ordenado = new Player[n];

		// Inicializar cada posicao do array de contagem
		for (int i = 0; i < count.length; count[i] = 0, i++)
			;

		// Agora, o count[i] contem o numero de elemento iguais a i
		for (int i = 0; i < n; i++) {
			count[array[i].getHeigth()]++;
		}

		// Agora, o count[i] contem o numero de elemento menores ou iguais a i
		for (int i = 1; i < count.length; count[i] += count[i - 1], i++)
			;

		// Ordenando
		for (int i = n - 1; i >= 0; ordenado[count[array[i].getHeigth()] - 1] = array[i], count[array[i]
				.getHeigth()]--, i--)
			;

		// Copiando para o array original
		for (int i = 0; i < n; array[i] = ordenado[i], i++)
			;
	};

	// Merge Sort
	void merge(Player arr[], Integer beg, Integer mid, Integer end) {
		int l = mid - beg + 1;
		int r = end - mid;
		Player LeftArray[] = new Player[l];
		Player RightArray[] = new Player[r];
		for (int i = 0; i < l; ++i)
			LeftArray[i] = arr[beg + i];
		for (int j = 0; j < r; ++j)
			RightArray[j] = arr[mid + 1 + j];
		int i = 0, j = 0;
		int k = beg;
		while (i < l && j < r) {
			if (CompareStringSmaller.Operate(LeftArray[i].getUniversity(), RightArray[j].getUniversity())) {
				arr[k] = LeftArray[i];
				i++;
			} else {
				arr[k] = RightArray[j];
				j++;
			}
			k++;
		}
		while (i < l) {
			arr[k] = LeftArray[i];
			i++;
			k++;
		}

		while (j < r) {
			arr[k] = RightArray[j];
			j++;
			k++;
		}
	}

	void MergeSort(Player[] array, Integer beg, Integer end) {
		if (beg < end) {
			Integer mid = (beg + end) / 2;
			MergeSort(array, beg, mid);
			MergeSort(array, mid + 1, end);
			merge(array, beg, mid, end);
		}
	}

	// Ordenação parcial selecao

	// quicksort parcial

	/**
	 * Procedimento para gerar um log, que é chamado ao fim de todas as ordenações.
	 * 
	 * @param name       Nome do exercicio
	 * @param comparacao número de comparações executada pelo algoritmo
	 * @param tempo      tempo levado para executar o algoritmo
	 */
	OperateOnArrayComplete<String, Integer, Double> GenerateLog = (name, comparacao, tempo) -> {
		try {
			FileWriter toWrite = new FileWriter(new File("634878_" + name + ".txt"));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("634878\t");
			stringBuilder.append(tempo);
			stringBuilder.append("\t");
			stringBuilder.append(comparacao);
			toWrite.write(stringBuilder.toString());
			toWrite.close();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao finalizar o método de ordenação");
		}
	};

	public int getAmountOfComparisons() {
		return amountOfComparisons;
	}

	public void setAmountOfComparisons(int amountOfComparisons) {
		this.amountOfComparisons = amountOfComparisons;
	}

	public int getAmountOfSwaps() {
		return amountOfSwaps;
	}

	public void setAmountOfSwaps(int amountOfSwaps) {
		this.amountOfSwaps = amountOfSwaps;
	}

	public int getLookForTillWhere() {
		return lookForTillWhere;
	}

	public void setLookForTillWhere(int lookForTillWhere) {
		this.lookForTillWhere = lookForTillWhere;
	}

}
