#include <stdio.h>
#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

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

//region <------MAIN------>
int main()
{
	char word[1024];
	bool controller = false;
	scanf("%[^\n]%*c", word);
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
			Player aux = GetPlayerFromFile(buffer);
			PrintPlayer(&aux);
			fclose(csvReader);
		}
		scanf("%[^\n]%*c", word);
	} while (!controller);
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

/*
PESQUISA BINARIA
SELECAO RECURSIVA
SHELLSORT
QUICKSORT
BOLHA
RADIXSORT
INSERCAO PARCIAL
HEAPSORT PARCIAL
*/