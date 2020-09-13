#include <stdio.h>
#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

Player *ARRAY;
int SIZE = 100;
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
void SwapInt(const void *elementI, const void *elementJ);
void PrintArrayInt(void *array, int amountOfElement);
void PrintElement(Player *_player, int amount);
void SelectionSort(int *array, int n, void (*swap)(const void *, const void *));
bool IsAble(char *_word);
char *GetLineFromFile(int lineToRead, FILE *csvReader);
Player GetPlayerFromFile(char *word);
void PlayerSet(int checker, Player *player, char *valueToSet);
void PrintPlayer(Player *);
void GenerateLog(char *name, int comparisons, double time);
bool CompareStringBigger(char *first, char *second);
bool CompareStringSmaller(char *first, char *second);
bool CompareIntBigger(int first, int second);
bool CompareIntSmaller(int first, int second);
void RecursiveQuicksort(Player *array, int left, int right, bool (*Bcompare)(const void *, const void *), bool (*Scompare)(const void *, const void *), void (*swap)(const void *, const void *));
void Quicksort(Player *array, int n, bool (*smallCompare)(const void *, const void *), bool (*biggerCompare)(const void *, const void *), void (*swap)(const void *, const void *));
void Shelsort(int *array, int n, bool (*compare)(const void *, const void *));
void ExerciseNumber(int n, char *fileName);
void InsertByColor(Player *array, int n, int cor, int h, bool (*compare)(const void *, const void *));
void BubbleSort(Player *array, int n, bool (*compare)(const void *, const void *), void (*swap)(const void *, const void *));
void RecursiveSelection(int n, int menor, Player *array, bool (*compare)(const void *, const void *), int j);
void Selection(Player *array, int n, bool (*compare)(const void *, const void *), void (*swap)(const void *, const void *));
bool BinarySearch(Player *array, Player n, int begin, int end, bool (*compare)(const void *, const void *));

//region <------MAIN------>
int main()
{
	char word[1024];
	bool controller = false;
	scanf("%[^\n]%*c", word);
	int exerciseNumber = 1;
	do
	{
		controller = IsAble(word);
		if (!controller)
		{
			FILE *csvReader = fopen("tmp/players.csv", "r");
			char buffer[1024];
			int lineToRead = atoi(word);
			for (int i = 0; i < lineToRead + 1; i++)
			{
				fgets(buffer, 1024, csvReader);
			}
			fgets(buffer, 1024, csvReader);
			Player aux = GetPlayerFromFile(buffer);
			if (exerciseNumber == 1)
				PrintPlayer(&aux);
			else
			{
				// add to list
			}
			fclose(csvReader);
		}
		scanf("%[^\n]%*c", word);
	} while (!controller);
	if (exerciseNumber > 1)
	{
		ExerciseNumber(exerciseNumber, "tmp/players.csv");
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
		playerToReturn->birthCity = field;
	}
	else if (checker == 7)
	{
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
		if ((int)field[0] < 48)
		{
			PlayerSet(checker, &playerToReturn, "nao informado");
		}
		else
		{
			PlayerSet(checker, &playerToReturn, field);
		}
		checker++;
		field = strtok(NULL, ",");
	}
	if ((int)playerToReturn.birthState[0] < 48)
	{
		playerToReturn.birthState = "nao informado";
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
void SwapPlayer(const void *elementI, const void *elementJ)
{
	Player temp = (*(Player *)elementI);
	(*(Player *)elementI) = (*(Player *)elementJ);
	(*(Player *)elementJ) = temp;
}
void PrintArrayInt(void *array, int amountOfElement)
{
	// for (int i = 0; i < amountOfElement; i++)
	// {
	// 	PrintElement((Player *)array[i]);
	// }
}
void PrintElement(Player *_player, int amount)
{
	for (int i = 0; i < amount; i++)
	{
		PrintPlayer(&_player[i]); //! <<- Passivo de erro
	}
}
void SelectionSort(int *array, int n, void (*swap)(const void *, const void *))
{
	for (int i = 0; i < (n - 1); i++)
	{
		int menor = i;
		for (int j = (i + 1); j < n; j++)
		{
			if (array[menor] > array[j])
			{
				menor = j;
			}
		}
		swap(&array[menor], &array[i]);
	}
}
void GenerateLog(char *name, int comparisons, double time)
{
	FILE *toWrite = fopen(name, "w");
	fprintf(toWrite, "634878\t%f\t%d\n", time, comparisons);
	fclose(toWrite);
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

//endregion
bool BinarySearch(Player *array, Player n, int begin, int end, bool (*compare)(const void *, const void *))
{ //ex 4
	bool found = false;
	int middle = (begin + end) / 2;
	if (begin <= end)
	{
		if (strcmp(n.name, array[middle].name) == 0)
		{
			found = true;
		}
		else if (compare(n.name, array[middle].name))
		{
			BinarySearch(array, n, begin, middle - 1, compare);
		}
		else
		{
			BinarySearch(array, n, middle + 1, end, compare);
		}

		return found;
	}
}
void RecursiveSelection(int n, int menor, Player *array, bool (*compare)(const void *, const void *), int j)
{
	if (j < n)
	{
		if (compare(array[menor].name, array[j].name))
		{
			menor = j;
		}
		RecursiveSelection(n, menor, array, compare, j + 1);
	}
}
void Selection(Player *array, int n, bool (*compare)(const void *, const void *), void (*swap)(const void *, const void *))
{
	int i = 0;
	int menor = i;
	if (i < (n - 1))
	{
		RecursiveSelection(n, menor, array, compare, (i + 1));
	}
	swap(&array[menor], &array[i]);
}
void BubbleSort(Player *array, int n, bool (*compare)(const void *, const void *), void (*swap)(const void *, const void *))
{
	for (int i = (n - 1); i > 0; i--)
	{
		for (int j = 0; j < i; j++)
		{
			if (compare(&array[j].birthYear, &array[j + 1].birthYear))
			{
				swap(&array[j], &array[j + 1]);
			}
		}
	}
}
void RecursiveQuicksort(Player *array, int left, int right, bool (*Bcompare)(const void *, const void *), bool (*Scompare)(const void *, const void *), void (*swap)(const void *, const void *))
{
	int i = left, j = right;
	Player pivo = array[(left + right) / 2];
	while (i <= j)
	{
		while (Scompare(array[i].birthState, pivo.birthState))
			i++;
		while (Bcompare(array[i].birthState, pivo.birthState))
			j++;
		if (i <= j)
		{
			swap(&array[i], &array[j]);
			i++;
			j--;
		}
	}
	if (left < j)
		RecursiveQuicksort(array, left, j, Bcompare, Scompare, swap);
	if (i < right)
		RecursiveQuicksort(array, i, right, Bcompare, Scompare, swap);
}
void Quicksort(Player *array, int n, bool (*smallCompare)(const void *, const void *), bool (*biggerCompare)(const void *, const void *), void (*swap)(const void *, const void *))
{
	RecursiveQuicksort(array, 0, n - 1, biggerCompare, smallCompare, swap);
}

void InsertByColor(Player *array, int n, int cor, int h, bool (*compare)(const void *, const void *))
{
	for (int i = (h + cor); i < n; i += h)
	{
		Player tmp = array[i];
		int j = i - h;
		while ((j >= 0) && (compare(array[j].weigth, tmp.weigth)))
		{
			array[j + h] = array[j];
			j -= h;
		}
		array[j + h] = tmp;
	}
}
void Shelsort(int *array, int n, bool (*compare)(const void *, const void *))
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
			InsertByColor(array, n, cor, h, compare);
		}
	} while (h != 1);
}
void ExerciseNumber(int i, char *fileName)
{
	if (i == 4)
	{
		FILE *reader = fopen(fileName, "r");
		char buffer[1024];
		fgets(buffer, 1024, reader);
		while (buffer[0] != 'F' && buffer[1] != 'I' && buffer[2] != 'M')
		{
			if (BinarySearch(ARRAY, GetPlayerFromFile(buffer), 0, SIZE - 1, CompareStringSmaller))
			{
				printf("SIM ");
			}
			else
				printf("NAO");
			printf("\n");
			fgets(buffer, 1024, reader);
		}

		fclose(reader);
	}
	else
	{
		if (i == 6)
		{
			Selection(ARRAY, SIZE, CompareStringBigger, SwapPlayer);
		}else if(i == 8){
			Shelsort(ARRAY,SIZE,CompareIntBigger);
		}else if(i == 10){
			Quicksort(ARRAY,SIZE,CompareStringSmaller,CompareStringBigger,SwapPlayer);
		}else if(i == 12){
			BubbleSort(ARRAY,SIZE,CompareStringBigger,SwapPlayer);
		}

		for (int i = 0; i < SIZE; i++)
		{
			PrintPlayer(&ARRAY[i]);
		}
	}
}

/*
RADIXSORT
INSERCAO PARCIAL
HEAPSORT PARCIAL
*/