package java;

public class Matrix {

	public static void main(String args[]) {

		try {
			Matriz matriz = new Matriz(3, 3);
			matriz.mostrar();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void mostrar() {
		CelulaMatriz inicio = primeiro;
		for (CelulaMatriz atual = inicio; atual != null; inicio = inicio.inf, atual = inicio) {
			for (; atual != null; atual = atual.dir)
				System.out.print(atual.elemento + " ");
			System.out.println("");
		}
	}

}
