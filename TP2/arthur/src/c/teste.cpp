#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// <------------TYPEDEFS -------------------->
typedef struct Players
{
	int id;
	char *name;
	int heigth;
	int weigth;
	char *university;
	char *birthYear;
	char *birthCity;
	char *birthState;
} Player;

Player *array;
int pointerToLastValidPosition = 0;
int arraySize = 1000;
int MOVEMENTS = 0;
int COMPARISONS = 0;

// <------------ METHODS DECLARATIONS -------------------->
void BuildHeap(int i);
void RebuildHeap(int n, int i);
void PartialHeapSort(Player *array, int pointerToLastValidPosition, int n);
void PrintElement(Player *_player, int amount);
void SelectionSort(int *array, int n, void (*swap)(const void *, const void *));
bool IsAble(char *_word);
char *GetLineFromFile(int lineToRead, FILE *csvReader);
void SwapPlayer(int elementI, int elementJ);
Player GetPlayerFromFile(char *word);
void PlayerSet(int checker, Player *player, char *valueToSet);
void PrintPlayer(Player *);
char *Replace(char const *const original, char const *const pattern, char const *const replacement);
void GenerateLog(char *name, double time);
bool CompareStringBigger(char *first, char *second);
bool CompareStringSmaller(char *first, char *second);
bool CompareIntBigger(int first, int second);
bool CompareIntSmaller(int first, int second);
void RecursiveQuicksort(Player *array, int left, int right);
void Quicksort(Player *array, int n);
void Shelsort(Player *array, int n);
void ExerciseNumber(int n);
void InsertIntoArray(Player player);
void InsertByColor(Player *array, int n, int cor, int h);
void BubbleSort(Player *array, int n);
void RecursiveSelection(int n, int menor, Player *array, int j);
void SelectionSort(Player *array, int n);
bool BinarySearch(Player *array, char *n, int begin, int end);
Player Clone(Player aux);
void RecursiveSelection(Player *array, int n, int index);
int minIndex(Player a[], int i, int j);
void CommonSort(Player *array, int n);
void RadixSort(Player *array, int n);
void PartialInsertion(Player *array, int n, int k);
void PartialSortArrayBirthState(Player *array, int size);
void PartialSortArrayBirthYear(Player *array, int size);
void PartialSortArrayWeigth(Player *array, int size);

//region <------MAIN------>
int main()
{
	char word[1024];
	bool controller = false;
	scanf("%[^\n]%*c", word);
	int exerciseNumber = 17;
	if (exerciseNumber > 1)
	{
		array = (Player *)malloc(sizeof(Player) * arraySize);
	}
	do
	{
		controller = IsAble(word);
		if (!controller)
		{
			FILE *csvReader = fopen("/tmp/players.csv", "r");
			char buffer[1024];
			int lineToRead = atoi(word);
			for (int i = 0; i < lineToRead + 1; i++)
			{
				fgets(buffer, 1024, csvReader);
			}
			fgets(buffer, 1024, csvReader);
			char *test = Replace(buffer, ",,", ",nao informado,");
			char *final = Replace(test, ",\n", ",nao informado\n");
			free(test);
			Player aux = GetPlayerFromFile(final);
			if (exerciseNumber > 1)
			{
				InsertIntoArray(Clone(aux));
			}
			else
			{
				PrintPlayer(&aux);
			}
			fclose(csvReader);
		}
		scanf("%[^\n]%*c", word);
	} while (!controller); //END OF FIRST ITERATION
	if (exerciseNumber > 1)
	{
		ExerciseNumber(exerciseNumber);
	}
	return 0;
}
//endregion

//region <-----------IMPLEMENTATION----------->
void PrintPlayer(Player *player)
{
	printf("[%d ## %s ## %d ## %d ## %s ## %s ## %s ## %s]\n", player->id, player->name, player->heigth, player->weigth, player->birthYear, player->university, player->birthCity, player->birthState);
}

void PlayerSet(int checker, Player *playerToReturn, char *field)
{

	if (checker == 0)
	{
		playerToReturn->id = atoi(field);
	}
	else if (checker == 1)
	{
		playerToReturn->name = field;
	}
	else if (checker == 2)
	{
		playerToReturn->heigth = atoi(field);
	}
	else if (checker == 3)
	{
		playerToReturn->weigth = atoi(field);
	}
	else if (checker == 4)
	{
		playerToReturn->university = field;
	}
	else if (checker == 5)
	{
		playerToReturn->birthYear = field;
	}
	else if (checker == 6)
	{
		//FIX \n at the end
		int place = strlen(field);
		if (field[place - 1] == '\n')
		{
			field[place - 1] = field[place];
			// printf("%s\n", field);
		}
		playerToReturn->birthCity = field;
	}
	else if (checker == 7)
	{

		int place = strlen(field);
		if (field[place - 1] == '\n')
		{
			field[place - 1] = field[place];
		}
		playerToReturn->birthState = field;
	}
}

Player GetPlayerFromFile(char *word)
{
	// printf("%s\n", word);
	Player playerToReturn;
	char *field = strtok(word, ",");
	int checker = 0;
	while (field)
	{
		PlayerSet(checker, &playerToReturn, field);
		checker++;
		field = strtok(NULL, ",");
	}
	return playerToReturn;
}

char *GetLineFromFile(int lineToRead, FILE *csvReader)
{
	static char buffer[1024];
	for (size_t i = 0; i < lineToRead + 1; i++)
	{
		fgets(buffer, 1024, csvReader);
	}
	fgets(buffer, 1024, csvReader);
	fclose(csvReader);
	return buffer;
}

bool IsAble(char *_word)
{
	bool valueToReturn = false;
	if (_word[0] == ('F') && _word[1] == ('I') && _word[2] == ('M'))
	{
		valueToReturn = true;
	}
	return valueToReturn;
}

char *Replace(char const *const original, char const *const pattern, char const *const replacement)
{
	size_t const replen = strlen(replacement);
	size_t const patlen = strlen(pattern);
	size_t const orilen = strlen(original);
	size_t patcnt = 0;
	const char *oriptr;
	const char *patloc;
	for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen)
	{
		patcnt++;
	}
	{
		size_t const retlen = orilen + patcnt * (replen - patlen);
		char *const returned = (char *)malloc(sizeof(char) * (retlen + 1));
		if (returned != NULL)
		{
			char *retptr = returned;
			for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen)
			{
				size_t const skplen = patloc - oriptr;
				strncpy(retptr, oriptr, skplen);
				retptr += skplen;
				strncpy(retptr, replacement, replen);
				retptr += replen;
			}
			strcpy(retptr, oriptr);
		}
		return returned;
	}
}

void ExerciseNumber(int n)
{
	char name[45];
	double begin = 0;
	if (n == 4)
	{ //BINARY SEARCH
		CommonSort(array, pointerToLastValidPosition);
		char word[1024];
		bool controller = false;
		begin = clock();
		scanf("%[^\n]%*c", word);
		printf("NAO\n");
		do
		{
			controller = IsAble(word);
			if (!controller)
			{
				if (BinarySearch(array, word, 0, pointerToLastValidPosition - 1))
				{
					printf("SIM\n");
				}
				else
				{
					printf("NAO\n");
				}
			}
			scanf("%[^\n]%*c", word);
		} while (!controller);
		begin = clock() - begin;
		strcpy(name, "634878_binaria");
	}
	else
	{
		if (n == 6)
		{
			//NOME ONLY
			SelectionSort(array, pointerToLastValidPosition);
			strcpy(name, "634878_selecao_recursiva");
		}
		else if (n == 8)
		{
			//WEIGHT THEN NAME
			Shelsort(array, pointerToLastValidPosition);
			//PartialSortArrayWeigth(array, pointerToLastValidPosition);
			strcpy(name, "634878_shellsort");
		}
		else if (n == 10)
		{
			//BirthState THEN NAME
			Quicksort(array, pointerToLastValidPosition);
			//PartialSortArrayBirthState(array, pointerToLastValidPosition);
			strcpy(name, "634878_quicksort");
			//int notInformedPos = 0;
			//int n = pointerToLastValidPosition;
			//for (int i = 0; i < n; i++)
			//{
			//if (strcmp(array[i].birthState, "nao informado") == 0)
			//{
			//notInformedPos = i;
			//i += n;
			//}
			//}
			//printf("NOT INFORMED %d\n\n", notInformedPos);
			//			for (int a = n - 1; a > notInformedPos; a--)
			//	{
			//for (int z = notInformedPos; z < a; z++)
			//{
			//if (strcmp(array[z].name, array[z + 1].name) > 0)
			//{
			//SwapPlayer(z, z + 1);
			//}
			//}
			//}
		}
		else if (n == 12)
		{
			//BirthYear THEN NAME
			BubbleSort(array, pointerToLastValidPosition);
			//PartialSortArrayBirthYear(array, pointerToLastValidPosition);
			strcpy(name, "634878_bolha");
		}
		else if (n == 14)
		{
			//ID
			RadixSort(array, pointerToLastValidPosition);
			strcpy(name, "634878_radix");
		}
		else if (n == 16)
		{
			//BirthYear
			PartialInsertion(array, pointerToLastValidPosition, 10);
			//PartialSortArrayBirthYear(array, pointerToLastValidPosition);
			strcpy(name, "634878_insercao_parcial");

			int begin = 0;
			for (int a = 100; a > begin; a--)
			{
				for (int z = begin; z < a; z++)
				{
					if (strcmp(array[z].name, array[z + 1].name) > 0 && (strcmp(array[z].birthYear, array[z + 1].birthYear) == 0))
					{
						SwapPlayer(z, z + 1);
					}
				}
			}
			pointerToLastValidPosition = 10;
			//PartialSortArrayBirthYear(array, pointerToLastValidPosition);
		}

		else if (n == 17)
		{
			//BirthYear
			PartialHeapSort(array, pointerToLastValidPosition, 10);
			int begin = 0;
			for (int a = 50; a > begin; a--)
			{
				for (int z = begin; z < a; z++)
				{
					if (strcmp(array[z].name, array[z + 1].name) > 0 && array[z].heigth == array[z + 1].heigth)
					{
						SwapPlayer(z, z + 1);
					}
				}
			}
			pointerToLastValidPosition = 10;
			strcpy(name, "634878_heapsort_parcial");
		}
		for (int i = 0; i < pointerToLastValidPosition; i++)
		{
			PrintPlayer(&array[i]);
		}
		GenerateLog(name, (begin));
	}
}

void SwapPlayer(int elementI, int elementJ)
{
	Player temp = array[elementI];
	array[elementI] = array[elementJ];
	array[elementJ] = temp;
}

void GenerateLog(char *name, double time)
{
	MOVEMENTS *= 3;
	FILE *toWrite = fopen(name, "w");
	fprintf(toWrite, "634878\t%f\t%d\t%d\n", time, COMPARISONS, MOVEMENTS);
	fclose(toWrite);
}

Player Clone(Player aux)
{
	Player valueToReturn;
	valueToReturn.name = aux.name;
	valueToReturn.birthCity = aux.birthCity;
	valueToReturn.birthState = aux.birthState;
	valueToReturn.birthYear = aux.birthYear;
	valueToReturn.heigth = aux.heigth;
	valueToReturn.id = aux.id;
	valueToReturn.university = aux.university;
	valueToReturn.weigth = aux.weigth;
	return valueToReturn;
}

void InsertIntoArray(Player player)
{
	if (pointerToLastValidPosition < arraySize)
	{
		array[pointerToLastValidPosition] = player;
		pointerToLastValidPosition++;
	}
}

bool CompareStringBigger(char *first, char *second)
{
	return strcmp(first, second) > 1;
}

bool CompareStringSmaller(char *first, char *second)
{
	return strcmp(first, second) < 1;
}

bool CompareIntBigger(int first, int second)
{
	return first > second;
}

bool CompareIntSmaller(int first, int second)
{
	return first < second;
}

// < ----------------  SORTING METHODS ----------------   >
bool BinarySearch(Player *array, char *n, int begin, int end)
{
	bool found = false;
	int middle;
	while (begin <= end)
	{
		middle = (begin + end) / 2;
		int cache = strcmp(n, array[middle].name);
		COMPARISONS++;
		if (cache == 0)
		{
			found = true;
			begin += end;
		}
		else if (cache > 0)
		{
			begin = middle + 1;
		}
		else
		{
			end = middle - 1;
		}
	}
	return found;
}

void CommonSort(Player *array, int n)
{
	for (int i = 0; i < (n - 1); i++)
	{
		int menor = i;
		for (int j = (i + 1); j < n; j++)
		{
			if (strcmp(array[menor].name, array[j].name) > 0)
			{
				menor = j;
			}
		}
		SwapPlayer(menor, i);
	}
}

void SelectionSort(Player *array, int n)
{
	RecursiveSelection(array, n, 0);
}

void RecursiveSelection(Player *array, int n, int index)
{
	if (index == n)
		return;

	int k = minIndex(array, index, n - 1);

	if (k != index)
	{
		SwapPlayer(k, index);
	}
	RecursiveSelection(array, n, index + 1);
}

int minIndex(Player a[], int i, int j)
{
	if (i == j)
		return i;
	int smaller = minIndex(a, i + 1, j);
	return (strcmp(a[i].name, a[smaller].name) < 0) ? i : smaller;
}

void BubbleSort(Player *array, int n)
{
	for (int i = (n - 1); i > 0; i--)
	{
		for (int j = 0; j < i; j++)
		{
			COMPARISONS += 2;
			if (strcmp(array[j].birthYear, array[j + 1].birthYear) > 0 && strcmp(array[j].name, array[j + 1].name) > 0)
			{
				SwapPlayer(j, (j + 1));
				MOVEMENTS++;
			}
			if ((strcmp(array[j].birthYear, array[j + 1].birthYear) == 0) && strcmp(array[j].name, array[j + 1].name) > 0)
			{
				MOVEMENTS++;
				SwapPlayer(j, j + 1);
			}
		}
	}
}

void RecursiveQuicksort(Player *array, int left, int right)
{
	int i = left, j = right;
	Player pivo = array[(left + right) / 2];
	while (i <= j)
	{
		while (strcmp(array[i].birthState, pivo.birthState) < 0 || strcmp(array[i].birthState, pivo.birthState) == 0 && strcmp(array[i].name, pivo.name) < 0)
		{
			COMPARISONS++;
			i++;
		}
		while (strcmp(array[j].birthState, pivo.birthState) > 0 || strcmp(array[j].birthState, pivo.birthState) == 0 && strcmp(array[j].name, pivo.name) > 0)
		{
			COMPARISONS++;
			j--;
		}
		if (i <= j)
		{
			MOVEMENTS++;
			SwapPlayer(i, j);
			i++;
			j--;
		}
	}
	if (left < j)
		RecursiveQuicksort(array, left, j);
	if (i < right)
		RecursiveQuicksort(array, i, right);
}

void Quicksort(Player *array, int n)
{
	RecursiveQuicksort(array, 0, n - 1);
}

void InsertByColor(Player *array, int n, int cor, int h)
{
	for (int i = (h + cor); i < n; i += h)
	{
		Player tmp = array[i];
		int j = i - h;
		int tmpCache = tmp.weigth;
		while ((j >= 0) && (array[j].weigth > tmpCache || array[j].weigth == tmpCache && strcmp(array[j].name, tmp.name) > 0))

		{
			COMPARISONS++;
			array[j + h] = array[j];
			j -= h;
		}
		MOVEMENTS++;
		array[j + h] = tmp;
	}
}

void Shelsort(Player *array, int n)
{
	int h = 1;
	do
	{
		h = (h * 3) + 1;
	} while (h < n);
	do
	{
		h /= 3;
		for (int cor = 0; cor < h; cor++)
		{
			InsertByColor(array, n, cor, h);
		}
	} while (h != 1);
}

int GetHighestValue(Player *array, int size)
{
	int i;
	int largestNum = -1;
	for (i = 0; i < size; i++)
	{
		if (array[i].id > largestNum)
			largestNum = array[i].id;
	}
	return largestNum;
}

// Radix Sort
void RadixSort(Player *array, int size)
{
	// Base 10 is used
	int i;
	Player semiSorted[size];
	int significantDigit = 1;
	int largestNum = GetHighestValue(array, size);
	while (largestNum / significantDigit > 0)
	{
		int bucket[10] = {0};
		for (i = 0; i < size; i++)
			bucket[(array[i].id / significantDigit) % 10]++;
		for (i = 1; i < 10; i++)
			bucket[i] += bucket[i - 1];
		for (i = size - 1; i >= 0; i--)
			semiSorted[--bucket[(array[i].id / significantDigit) % 10]] = array[i];
		for (i = 0; i < size; i++)
			array[i] = semiSorted[i];
		MOVEMENTS++;
		// Move to next significant digit
		significantDigit *= 10;
	}
}

void PartialInsertion(Player *array, int n, int k)
{
	int i, j;
	for (i = 1; i < n; i++)
	{
		Player x = array[i];
		//	if (i > k)
		//j = k - 1;
		//else
		j = i - 1;
		//array[0] = x; /* sentinela */
		while ((j >= 0) && strcmp(array[j].birthYear, x.birthYear) >= 0)
		{
			COMPARISONS++;
			MOVEMENTS;
			array[j + 1] = array[j];
			j--;
		}
		array[j + 1] = x;
	}
}

void PartialSortArrayBirthState(Players *array, int size)
{
	int beginOfInterval = 0, endOfInterval = -1;
	for (int i = 1; i < size; i++)
	{
		//POSSIVEL INICIO DE INTERVALO
		if (strcmp(array[i].birthState, array[beginOfInterval].birthState) == 0)
		{
			endOfInterval = i;
		}
		else if (strcmp(array[beginOfInterval].birthState, array[endOfInterval].birthState) == 0)
		{
			for (int i = endOfInterval; i > beginOfInterval; i--)
			{
				for (int j = beginOfInterval; j < i; j++)
				{
					if (strcmp(array[j].name, array[j + 1].name) > 0)
					{
						SwapPlayer(j, j + 1);
					}
				}
			}
			beginOfInterval = i + 1;
		}
		else
		{
			beginOfInterval++;
		}
	}
}

void PartialSortArrayBirthYear(Players *array, int size)
{
	int beginOfInterval = 0, endOfInterval = 0;
	for (int i = 1; i < size; i++)
	{
		//POSSIVEL INICIO DE INTERVALO
		if ((array[i].birthYear, array[beginOfInterval].birthYear) == 0)
		{
			endOfInterval = i;
		}
		else if (strcmp(array[beginOfInterval].birthYear, array[endOfInterval].birthYear) == 0)
		{
			if (strcmp(array[beginOfInterval].birthYear, array[endOfInterval].birthYear) == 0)
			{
				for (int i = endOfInterval; i > beginOfInterval; i--)
				{
					for (int j = beginOfInterval; j < i; j++)
					{
						if (strcmp(array[j].name, array[j + 1].name) > 0)
						{
							SwapPlayer(j, j + 1);
						}
					}
				}
			}
			beginOfInterval = i + 1;
		}
		else
		{
			beginOfInterval++;
		}
	}
}

void PartialSortArrayWeigth(Players *array, int size)
{
	int beginOfInterval = 0, endOfInterval = -1;
	for (int i = 1; i < size; i++)
	{
		//POSSIVEL INICIO DE INTERVALO
		if (array[i].weigth == array[beginOfInterval].weigth)
		{
			endOfInterval = i;
		}
		else if (array[beginOfInterval].weigth == array[endOfInterval].weigth)
		{
			for (int i = endOfInterval; i > beginOfInterval; i--)
			{
				for (int j = beginOfInterval; j < i; j++)
				{
					if (strcmp(array[j].name, array[j + 1].name) > 0)
					{
						SwapPlayer(j, j + 1);
					}
				}
			}
			beginOfInterval = i + 1;
		}
		//	else
		//{
		//beginOfInterval++;
		// }
	}
}

void BuildHeap(int tam)
{
	for (int i = tam; i > 1 && array[i].heigth > array[i / 2].heigth; i /= 2)
	{
		SwapPlayer(i, i / 2);
	}
}

void RebuildHeap(int n, int i)
{
	int largest = i;
	int l = 2 * i + 1;
	int r = 2 * i + 2;
	if ((l < n) && array[l].heigth > array[largest].heigth)
	{
		COMPARISONS++;
		largest = l;
	}
	if ((r < n) && array[r].heigth > array[largest].heigth)
	{
		largest = r;
		COMPARISONS++;
	}
	if (largest != i)
	{
		SwapPlayer(i, largest);
		RebuildHeap(n, largest);
	}
}

void PartialHeapSort(Player *array, int pointerToLastValidPosition, int k)
{
	int n = pointerToLastValidPosition;
	for (int i = n / 2 - 1; i >= 0; i--)
		RebuildHeap(n, i);
	for (int i = n - 1; i > 0; i--)
	{
		SwapPlayer(0, i);
		MOVEMENTS++;
		RebuildHeap(i, 0);
	}
}