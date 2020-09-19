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
int arraySize = 600;

// <------------ METHODS DECLARATIONS -------------------->
void PrintElement(Player *_player, int amount);
void SelectionSort(int *array, int n, void (*swap)(const void *, const void *));
bool IsAble(char *_word);
char *GetLineFromFile(int lineToRead, FILE *csvReader);
Player GetPlayerFromFile(char *word);
void PlayerSet(int checker, Player *player, char *valueToSet);
void PrintPlayer(Player *);
char *Replace(char const *const original, char const *const pattern, char const *const replacement);
void GenerateLog(char *name, int comparisons, double time);
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
void Selection(Player *array, int n);
bool BinarySearch(Player *array, char *n, int begin, int end);
void SwapPlayer(int elementI, int elementJ);
Player Clone(Player aux);
//region <------MAIN------>
int main()
{
	char word[1024];
	bool controller = false;
	scanf("%[^\n]%*c", word);
	int exerciseNumber = 4;
	if (exerciseNumber > 1)
	{
		array = (Player *)malloc(sizeof(Player) * arraySize);
	}
	do
	{
		controller = IsAble(word);
		if (!controller)
		{
			FILE *csvReader = fopen("players.csv", "r");
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
	// if (exerciseNumber > 1)
	// {
	//	free(final); //has to be put it down here otherwise the name gets lost in the process dont know why
	// 	free(array);
	// }
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

	// find how many times the pattern occurs in the original string
	for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen)
	{
		patcnt++;
	}

	{
		// allocate memory for the new string
		size_t const retlen = orilen + patcnt * (replen - patlen);
		char *const returned = (char *)malloc(sizeof(char) * (retlen + 1));

		if (returned != NULL)
		{
			// copy the original string,
			// replacing all the instances of the pattern
			char *retptr = returned;
			for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen)
			{
				size_t const skplen = patloc - oriptr;
				// copy the section until the occurence of the pattern
				strncpy(retptr, oriptr, skplen);
				retptr += skplen;
				// copy the replacement
				strncpy(retptr, replacement, replen);
				retptr += replen;
			}
			// copy the rest of the string.
			strcpy(retptr, oriptr);
		}
		return returned;
	}
}
void ExerciseNumber(int n)
{
	if (n == 4)
	{ //BINARY SEARCH
		Selection(array, pointerToLastValidPosition);
		char word[1024];
		bool controller = false;
		scanf("%[^\n]%*c", word);
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
	}
	else
	{
		if (n == 6)
		{
			Selection(array, pointerToLastValidPosition);
		}
		else if (n == 8)
		{
			Shelsort(array, pointerToLastValidPosition);
		}
		else if (n == 10)
		{
			Quicksort(array, pointerToLastValidPosition);
		}
		else if (n == 12)
		{
			BubbleSort(array, pointerToLastValidPosition);
		}

		for (int i = 0; i < pointerToLastValidPosition; i++)
		{
			PrintPlayer(&array[i]);
		}
	}
}
void SwapPlayer(int elementI, int elementJ)
{
	Player temp = array[elementI];
	array[elementI] = array[elementJ];
	array[elementJ] = temp;
}
void GenerateLog(char *name, int comparisons, double time)
{
	FILE *toWrite = fopen(name, "w");
	fprintf(toWrite, "634878\t%f\t%d\n", time, comparisons);
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
void RecursiveSelection(int n, int menor, Player *array, int j)
{
	if (j < n)
	{
		if (strcmp(array[menor].name, array[j].name) > 0)
		{
			menor = j;
		}
		RecursiveSelection(n, menor, array, j + 1);
	}
}
void Selection(Player *array, int n)
{
	// int i = 0;
	// int menor = i;
	// while (i < (n - 1))
	// {
	// 	RecursiveSelection(n, menor, array, 0);
	// 	if (menor != i)
	// 	{
	// 		SwapPlayer(menor, i);
	// 	}
	// 	i++;
	// }
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
void BubbleSort(Player *array, int n)
{
	for (int i = (n - 1); i > 0; i--)
	{
		for (int j = 0; j < i; j++)
		{
			if (strcmp(array[j].birthYear, array[j + 1].birthYear) > 0)
			{
				SwapPlayer(j, (j + 1));
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
		while (CompareStringSmaller(array[i].birthState, pivo.birthState))
			i++;
		while (CompareStringBigger(array[i].birthState, pivo.birthState))
			j++;
		if (i <= j)
		{
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
		while ((j >= 0) && (CompareIntBigger(array[j].weigth, tmpCache)))
		{
			array[j + h] = array[j];
			j -= h;
		}
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