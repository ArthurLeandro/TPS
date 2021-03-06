import java.io.*;
import java.util.*;
import java.time.*;

public class TP2 {
	public static void main(String[] args) throws Exception {
		Scanner reader = new Scanner(System.in);
		Lista list = new Lista(600);
		int exerciseNumber = 18;
		String word = "";
		boolean controller = false;
		do {
			word = reader.nextLine();
			controller = IsAble(word);
			if (!controller) {
				Scanner csvReader = new Scanner(new File("/tmp/players.csv"));
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
		String name = "";
		double end = 0, timeElapsed = 0;
		boolean partial = false;
		if (i == 3) {// PESQUISA SEQUENCIAL
			String word = reader.nextLine();
			timeElapsed = System.currentTimeMillis();
			while (!(word.charAt(0) == 'F' && word.charAt(1) == 'I' && word.charAt(2) == 'M')) {
				if (sort.PesquisaSequencial.Operate(aux, word)) {
					System.out.println("SIM");
				} else {
					System.out.println("NAO");
				}
				word = reader.nextLine();
			}
			end = System.currentTimeMillis();
			name = "binaria";
		} else {
			if (i == 5) {// ORDENAÇÂO SELEçÂO NAME ONLY
				timeElapsed = System.currentTimeMillis();
				sort.Selecao.Sort(aux);
				name = "selecao";
				end = System.currentTimeMillis();
			} else if (i == 7) {
				// BIRTH YEAR THEN NAME
				timeElapsed = System.currentTimeMillis();
				sort.Insercao.Sort(aux);
				name = "insercao";
				end = System.currentTimeMillis();
			} else if (i == 9) {
				// HEIGHT THEN NAME
				timeElapsed = System.currentTimeMillis();
				sort.Heapsort.Sort(aux);
				name = "heapsort";
				end = System.currentTimeMillis();
			} else if (i == 11) {
				// HEIGHT THEN NAME
				timeElapsed = System.currentTimeMillis();
				sort.Counting.Sort(aux);
				name = "counting";
				end = System.currentTimeMillis();
			} else if (i == 13) {
				// UNIVERSITY ONLY
				timeElapsed = System.currentTimeMillis();
				sort.MergeSort(aux, list.getN() - 1);
				name = "merge";
				end = System.currentTimeMillis();
			} else if (i == 15) {
				name = "selecao_parcial";
				partial = true;
				timeElapsed = System.currentTimeMillis();
				sort.Selecao.Sort(aux);
				name = "selecao";
				end = System.currentTimeMillis();

			} else if (i == 18) {
				partial = true;
				timeElapsed = System.currentTimeMillis();
				sort.QuicksortCaller(aux);
				end = System.currentTimeMillis();
				name = "quicksort_parcial";
				int begin = 0;
				for (int a = 100; a > begin; a--) {
					for (int z = begin; z < a; z++) {
						if (aux[z].getName().compareTo(aux[z + 1].getName()) > 0
								&& (aux[z].getBirthState().compareTo(aux[z + 1].getBirthState()) == 0)) {
							sort.SwapPlayer.Operate(aux, z, z + 1);
						}
					}
				}
			}
			if (!partial)
				sort.PrintArrayPlayer.Operate(aux, 0);
			else {
				for (int a = 0; a < 10; a++) {
					aux[a].PrintPlayer();
				}
			}

			sort.GenerateLog.Operate(name, 0, (end - timeElapsed) / 1000);
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
			amountOfComparisons++;
		}
		return valueToReturn;
	};

	SortWithComparison<Player> Insercao = (array) -> {
		int n = getLookForTillWhere();
		for (int i = 1; i < n; i++) {
			Player tmp = array[i];
			int j = i - 1;
			while ((j >= 0) && (CompareStringBigger.Operate(array[j].getBirthYear(), tmp.getBirthYear()))) {
				amountOfComparisons++;
				array[j + 1] = array[j];
				j--;
			}
			amountOfSwaps++;
			array[j + 1] = tmp;
		}
		SortArraySecondaryBirthYear(array, n);
	};

	SortWithComparison<Player> Selecao = (array) -> {
		for (int i = 0; i < getLookForTillWhere() - 1; i++) {
			int menor = i;
			for (int j = (i + 1); j < getLookForTillWhere(); j++) {
				if (CompareStringBigger.Operate(array[menor].getName(), array[j].getName())) {
					amountOfComparisons++;
					menor = j;
				}
			}
			amountOfSwaps++;
			SwapPlayer.Operate(array, menor, i);
		}
	};

	void downheapify(Player arr[], int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;

		// finding the maximum of left and right
		if (l < n && CompareInt.Operate(arr[l].getHeigth(), arr[largest].getHeigth())) {
			amountOfComparisons++;
			largest = l;
		}

		if (r < n && CompareInt.Operate(arr[r].getHeigth(), arr[largest].getHeigth())) {
			largest = r;
			amountOfComparisons++;
		}

		// swapping if child >parent
		if (largest != i) {
			SwapPlayer.Operate(arr, i, largest);
			downheapify(arr, n, largest);
		}
	}

	// HEapSort
	SortWithComparison<Player> Heapsort = (array) -> {
		int n = getLookForTillWhere();
		for (int i = n / 2 - 1; i >= 0; i--)
			downheapify(array, n, i);
		for (int i = n - 1; i > 0; i--) {
			SwapPlayer.Operate(array, 0, i);
			amountOfSwaps++;
			downheapify(array, i, 0);
		}
		SortArraySecondary(array, n);
	};

	int getMaior(Player[] array) {
		Player maior = array[0];
		int valueToReturn = 0;
		for (int i = 0; i < getLookForTillWhere(); i++) {
			if (CompareLesserInt.Operate(maior.getHeigth(), array[i].getHeigth())) {
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
		int[] count = new int[250];
		Player[] ordenado = new Player[n];

		// Inicializar cada posicao do array de contagem
		for (int i = 0; i < count.length; count[i] = 0, i++)
			;

		// Agora, o count[i] contem o numero de elemento iguais a i
		for (int i = 0; i < n; i++) {
			count[array[i].getHeigth()]++;
			amountOfComparisons++;
		}

		// Agora, o count[i] contem o numero de elemento menores ou iguais a i
		for (int i = 1; i < count.length; count[i] += count[i - 1], i++)
			;

		// Ordenando
		for (int i = n - 1; i >= 0; ordenado[count[array[i].getHeigth()] - 1] = array[i], count[array[i]
				.getHeigth()]--, i--)
			;

		// Copiando para o array original
		for (int i = 0; i < n; array[i] = ordenado[i], i++) {
			amountOfSwaps++;
		}
		SortArraySecondary(array, n);
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
			if (LeftArray[i].getUniversity().compareTo(RightArray[j].getUniversity()) <= 0
					// || LeftArray[i].getUniversity().compareTo(RightArray[j].getUniversity()) == 0
					&& LeftArray[i].getName().compareTo(RightArray[j].getName()) > 0) {
				amountOfComparisons++;
				arr[k] = LeftArray[i];
				i++;
				amountOfSwaps++;
			} else {
				amountOfSwaps++;
				arr[k] = RightArray[j];
				j++;
			}
			k++;
		}
		while (i < l) {
			amountOfSwaps++;
			arr[k] = LeftArray[i];
			i++;
			k++;
		}

		while (j < r) {
			amountOfSwaps++;
			arr[k] = RightArray[j];
			j++;
			k++;
		}
	}

	void MergeSort(Player[] array, int size) {
		MergeSortRecursive(array, 0, size);
		// SortArraySecondaryUniversity(array, size);
	}

	void MergeSortRecursive(Player[] array, Integer beg, Integer end) {
		amountOfComparisons = amountOfSwaps = 0;
		if (beg < end) {
			Integer mid = (beg + end) / 2;
			MergeSortRecursive(array, beg, mid);
			MergeSortRecursive(array, mid + 1, end);
			merge(array, beg, mid, end);
		}
	}

	// Ordenação parcial selecao nome

	// quicksort parcial estado de nascimento -> name
	public void QuicksortCaller(Player[] array) {
		QuickSort(0, getLookForTillWhere() - 1, array);
	}

	public void QuickSort(int esq, int dir, Player[] array) {
		int i = esq, j = dir;
		Player pivo = array[(dir + esq) / 2];
		while (i <= j) {
			while (CompareStringSmaller.Operate(array[i].getBirthState(), pivo.getBirthState()))
				i++;
			while (CompareStringBigger.Operate(array[j].getBirthState(), pivo.getBirthState()))
				j--;
			if (i <= j) {
				SwapPlayer.Operate(array, i, j);
				i++;
				j--;
			}
		}
		if (esq < j)
			QuickSort(esq, j, array);
		if (i < dir)
			QuickSort(i, dir, array);
	}

	/**
	 * Procedimento para gerar um log, que é chamado ao fim de todas as ordenações.
	 * 
	 * @param name       Nome do exercicio
	 * @param comparacao número de comparações executada pelo algoritmo
	 * @param tempo      tempo levado para executar o algoritmo
	 */
	OperateOnArrayComplete<String, Integer, Double> GenerateLog = (name, something, tempo) -> {
		try {
			FileWriter toWrite = new FileWriter(new File("634878_" + name + ".txt"));
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("634878\t");
			stringBuilder.append(tempo);
			stringBuilder.append("\t");
			stringBuilder.append((amountOfComparisons * 3));
			stringBuilder.append("\t");
			stringBuilder.append(amountOfSwaps);
			toWrite.write(stringBuilder.toString());
			toWrite.close();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao finalizar o método de ordenação");
		}
	};

	public void SortArraySecondary(Player[] array, int size) {
		int beginOfInterval = 0, endOfInterval = 0;
		for (int i = 1; i < size; i++) {
			// POSSIVEL INICIO DE INTERVALO
			if (array[i].getHeigth() == array[beginOfInterval].getHeigth()) {
				endOfInterval = i;
			} else if (array[beginOfInterval].getHeigth() == array[endOfInterval].getHeigth()) {
				// System.out.println("\nBEGIN OF INTERVALL");
				beginOfInterval = (beginOfInterval != 0) ? beginOfInterval - 1 : 0;
				// endOfInterval++;
				if (array[beginOfInterval].getHeigth() == array[endOfInterval].getHeigth()) {
					for (int k = endOfInterval; k > beginOfInterval; k--) {
						for (int j = beginOfInterval; j < k; j++) {
							if (array[j].getName().compareTo(array[j + 1].getName()) > 0) {
								SwapPlayer.Operate(array, j, j + 1);
							}
						}
					}
				}
				beginOfInterval = i + 1;
			}
		}
	}

	public void SortArraySecondaryUniversity(Player[] array, int size) {
		int beginOfInterval = 0, endOfInterval = 0;
		int notInformedPos = 0;
		for (int i = 1; i < size; i++) {
			if (array[i].getUniversity().equals("nao informado") && notInformedPos == 0)
				notInformedPos = i;
			// POSSIVEL INICIO DE INTERVALO
			if (array[i].getUniversity().equals(array[beginOfInterval].getUniversity())) {
				endOfInterval = i;
			} else if (array[beginOfInterval].getUniversity().equals(array[endOfInterval].getUniversity())) {
				// System.out.println("\nBEGIN OF INTERVALL");
				beginOfInterval = (beginOfInterval != 0) ? beginOfInterval - 1 : 0;
				endOfInterval++;
				if ((array[beginOfInterval].getUniversity().equals(array[endOfInterval].getUniversity()))) {

					for (int k = endOfInterval; k > beginOfInterval; k--) {
						for (int j = beginOfInterval; j < k; j++) {
							if (array[j].getName().compareTo(array[j + 1].getName()) > 0) {
								SwapPlayer.Operate(array, j, j + 1);
							}
						}
					}
				}
				beginOfInterval = i + 1;
			}
		}
		for (int l = size; l > notInformedPos; l--) {
			for (int w = notInformedPos; w < l; w++) {
				if (array[w].getName().compareTo(array[w + 1].getName()) > 0) {
					SwapPlayer.Operate(array, w, w + 1);
				}
			}
		}
	}

	public void SortArraySecondaryBirthYear(Player[] array, int size) {
		int beginOfInterval = 0, endOfInterval = 0;
		for (int i = 1; i < size; i++) {
			// POSSIVEL INICIO DE INTERVALO
			if (Integer.parseInt(array[i].getBirthYear()) == Integer.parseInt(array[beginOfInterval].getBirthYear())) {
				endOfInterval = i;
			} else if (Integer.parseInt(array[beginOfInterval].getBirthYear()) == Integer
					.parseInt(array[endOfInterval].getBirthYear())) {
				// System.out.println("\nBEGIN OF INTERVALL");
				beginOfInterval = (beginOfInterval != 0) ? beginOfInterval - 1 : 0;
				// endOfInterval++;
				if (Integer.parseInt(array[beginOfInterval].getBirthYear()) == Integer
						.parseInt(array[endOfInterval].getBirthYear())) {
					for (int k = endOfInterval; k > beginOfInterval; k--) {
						for (int j = beginOfInterval; j < k; j++) {
							if (array[j].getName().compareTo(array[j + 1].getName()) > 0) {
								SwapPlayer.Operate(array, j, j + 1);
							}
						}
					}
				}
				beginOfInterval = i + 1;
			}
		}
	}

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
