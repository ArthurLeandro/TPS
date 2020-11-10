import java.util.Scanner;

public class Matrix {

	public static void main(String args[]) {
		Scanner reader = new Scanner(System.in);
		int amount = Integer.parseInt(reader.nextLine());
		int controller = 0;
		do {
			Matriz one = GenerateMatriz(reader, Integer.parseInt(reader.nextLine()), Integer.parseInt(reader.nextLine()));
			Matriz two = GenerateMatriz(reader, Integer.parseInt(reader.nextLine()), Integer.parseInt(reader.nextLine()));
			one.ShowMainDiagonnal();
			one.ShowSecondaryDiagonnal();
			one.Sum(two);
			one.Multiply(two);
			one.mostrar();
			controller++;
		} while (controller < amount);
		reader.close();
	}

	public static Matriz GenerateMatriz(Scanner reader, int line, int column) {
		Matriz one = new Matriz(line, column);
		int[] array = new int[line * column];
		String word = "";
		for (int i = 0; i < line; i++) {
			word += reader.nextLine() + " ";
		}
		String[] splitted = word.split(" ");
		for (int j = 0; j < splitted.length; j++) {
			array[j] = Integer.parseInt(splitted[j]);
		}
		one.populate(array);
		return one;
	}
}

class CelulaMatriz {
	public CelulaMatriz esq;
	public CelulaMatriz dir;
	public CelulaMatriz sup;
	public CelulaMatriz inf;
	public int elemento;

	public CelulaMatriz() {
		this(0);
	}

	public CelulaMatriz(int elemento) {
		this.esq = this.dir = this.sup = this.inf = null;
		this.elemento = elemento;
	}

}

class Matriz {
	private int coluna;
	private int linha;
	private CelulaMatriz primeiro;

	public Matriz(int l, int c) {
		linha = l;
		coluna = c;
		int x = 1;
		primeiro = new CelulaMatriz();
		primeiro.elemento = x;
		CelulaMatriz inicio = primeiro;
		CelulaMatriz atual = inicio;
		CelulaMatriz cima = inicio;
		for (int i = 0; i < l; i++) {
			if (i > 1)
				inicio = inicio.inf;
			cima = inicio;
			if (i > 0) {
				CelulaMatriz temp = new CelulaMatriz();
				x++;
				temp.elemento = x;
				cima.inf = temp;
				temp.sup = cima;
				atual = temp;
			}
			for (int j = 1; j < c; j++, atual = atual.dir) {
				CelulaMatriz temp = new CelulaMatriz();
				x++;
				temp.elemento = x;
				temp.esq = atual;
				atual.dir = temp;
				cima = cima.dir;
				if (i > 0) {
					temp.sup = cima;
					cima.inf = temp;
				}
			}
		}
	}

	public void populate(int[] array) {
		CelulaMatriz inicio = primeiro;
		int aux = 0;
		for (CelulaMatriz atual = inicio; atual != null; inicio = inicio.inf, atual = inicio) {
			for (; atual != null; atual = atual.dir, aux++)
				atual.elemento = array[aux];
		}
	}

	public void mostrar() {
		CelulaMatriz inicio = primeiro;
		for (CelulaMatriz atual = inicio; atual != null; inicio = inicio.inf, atual = inicio) {
			for (; atual != null; atual = atual.dir)
				System.out.print(atual.elemento + " ");
			System.out.println("");
		}
	}

	public void Sum(Matriz other) {
		if (this.linha == other.linha && this.coluna == other.coluna) {
			CelulaMatriz iLINHA, jLINHA;
			CelulaMatriz auxL, auxLj;
			for (auxL = this.primeiro, auxLj = other.primeiro; auxL != null; auxL = auxL.inf, auxLj = auxLj.inf) {
				for (iLINHA = auxL, jLINHA = auxLj; iLINHA != null; iLINHA = iLINHA.dir, jLINHA = jLINHA.dir) {
					System.out.print(iLINHA.elemento + jLINHA.elemento + " ");
				}
				System.out.println();
			}
		}
	}

	public void Multiply(Matriz other) {
		if (this.coluna == other.linha) {
			Matriz result = new Matriz(this.linha, other.coluna);
			CelulaMatriz iCOLUNA, jLINHA;
			CelulaMatriz auxL = primeiro, auxLj;
			int aux = 0;
			CelulaMatriz foot = result.primeiro;
			for (CelulaMatriz resultIs = result.primeiro; resultIs != null; auxL = auxL.inf) {
				for (auxLj = other.primeiro; auxLj != null; auxLj = auxLj.dir) {
					for (iCOLUNA = auxL, jLINHA = auxLj; iCOLUNA != null; iCOLUNA = iCOLUNA.dir, jLINHA = jLINHA.inf) {
						aux += iCOLUNA.elemento * jLINHA.elemento;
					}
					resultIs.elemento = aux;
					aux = 0;
					resultIs = resultIs.dir;
				}
				if (resultIs == null) {
					resultIs = foot.inf;
					foot = foot.inf;
				}
			}
			this.primeiro = result.primeiro;
		}
	}

	public void ShowMainDiagonnal() {
		ShowMainDiagonnal(this.primeiro);
		System.out.println();
	}

	public void ShowMainDiagonnal(CelulaMatriz cell) {
		if (cell != null) {
			System.out.print(cell.elemento + " ");
			try {
				ShowMainDiagonnal(cell.dir.inf);
			} catch (NullPointerException e) {
				return;
			}
		}
	}

	public void ShowSecondaryDiagonnal() {
		CelulaMatriz i = primeiro;
		for (i = primeiro; i.dir != null; i = i.dir)
			;
		ShowSecondaryDiagonnal(i);
		System.out.println();
	}

	public void ShowSecondaryDiagonnal(CelulaMatriz cell) {
		if (cell != null) {
			System.out.print(cell.elemento + " ");
			try {
				ShowSecondaryDiagonnal(cell.esq.inf);
			} catch (NullPointerException e) {
				return;
			}
		}
	}
}