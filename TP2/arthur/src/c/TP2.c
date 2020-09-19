#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
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
bool IsAble(char *_word);
char *GetLineFromFile(int lineToRead, FILE *csvReader);
Player GetPlayerFromFile(char *word);
void PlayerSet(int checker, Player *player, char *valueToSet);
void PrintPlayer(Player *);

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
		//int place = strlen(field);
		//if (field[place - 1] == '\n')
		//{
		//	field[place - 1] = field[place];
		//}
		playerToReturn->birthCity = field;
	}
	else if (checker == 7)
	{
		// field[strlen(field) - 1] = field[strlen(field)];
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
			char NOT_FOUND[14] = {'n', 'a', 'o', ' ', 'i', 'n', 'f', 'o', 'r', 'm', 'a', 'd', 'o'};
			PlayerSet(checker, &playerToReturn, NOT_FOUND);
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
		char NOT_FOUND[14] = {'n', 'a', 'o', ' ', 'i', 'n', 'f', 'o', 'r', 'm', 'a', 'd', 'o'};
		playerToReturn.birthState = NOT_FOUND;
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
