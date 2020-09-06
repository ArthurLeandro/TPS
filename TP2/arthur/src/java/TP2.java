import java.io.*;
import java.util.*;
import java.time.*;

//#region <---------------- ALL FUNCTIONAL INTERFACES---------------------->
@FunctionalInterface
interface OperateOnArray<T> {
	void Operate(T[] array, T start);
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
interface OperateOnArrayWithArray<T> {
	void Operate(T[] array, int v1, int v2);
}

@FunctionalInterface
interface Sort<T> {
	void Sort(T[] array, OperateInArray swap);
}

@FunctionalInterface
interface SortWithComparison<T> {
	void Sort(T[] array);
}

// #endregion

class SortingRelatedItems {
	int amountOfComparisons = 0;
	int amountOfSwaps = 0;

	OperateOnArrayWithArray<Integer> SwapInt = (array, elementI, elementJ) -> {
		Integer temp = array[elementI];
		array[elementI] = array[elementJ];
		array[elementJ] = temp;
		amountOfSwaps++;
	};
	OperateInArray<Integer, Boolean> CompareInt = (first, second) -> {
		return (int) first > (int) second;
	};
	OperateInArray<Integer, Boolean> CompareLesserInt = (first, second) -> {
		return (int) first < (int) second;
	};

	OperateOnArray<Integer> PrintArrayInteger = (array, start) -> {
		String auxiliar = "[";
		for (int i = 0; i < array.length; i++) {
			auxiliar = auxiliar.concat(Integer.toString(array[i]));
			auxiliar = auxiliar.concat("] ");
			System.out.print(auxiliar);
			auxiliar = "[";
		}
	};

	SortWithComparison<Integer> SelectionSortInt = (array) -> {
		amountOfComparisons = 0;
		amountOfSwaps = 0;
		for (int i = 0; i < (array.length - 1); i++) {
			int menor = i;
			for (int j = (i + 1); j < array.length; j++) {
				amountOfComparisons++;
				if (CompareInt.Operate(array[menor], array[j])) {
					menor = j;
				}
			}
			SwapInt.Operate(array, menor, i);
		}
	};

}