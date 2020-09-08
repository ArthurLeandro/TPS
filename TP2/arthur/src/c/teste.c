#include <stdio.h>
#include <stdlib.h> // For exit() function
#include <string.h>
#define MAXLINESIZE 3923

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

int main()
{
	char c[1024];
	FILE *fptr = fopen("players.csv", "r");
	for (int i = 0; i < 107; i++)
	{
		fgets(c, 1024, fptr);
	}
	fgets(c, 1024, fptr);
	//parsing
	// char *field = strtok(c, ",");
	// int checker = 0;
	// while (field){
	// 	printf("%s\t", field);
	// 	if (((int)field[0]) < 48){
	// 		// printf("%d não informado\t", checker);
	// 		printf("não informado\t");
	// 	}
	// 	checker++;
	// 	field = strtok(NULL, ",");
	// }
	// if (checker < 8){
	// 	printf("não informado \t");
	// }
	// printf("\n%d\n", checker);
	Player playerToReturn;
	char *field = strtok(c, ",");
	int checker = 0;
	while (field)
	{
		if ((int)field[0] < 48)
		{
			//! talvez o ponteiro possa alterar  o campo do word então ... eu nem sei
			char aux[] = "nao informado";
			*	(field) = &aux;
		}
		if (checker == 0)
		{
			playerToReturn.id = atoi(field);
		}
		else if (checker == 1)
		{
			playerToReturn.name = field;
		}
		else if (checker == 2)
		{
			playerToReturn.heigth = atoi(field);
		}
		else if (checker == 3)
		{
			playerToReturn.weigth = atoi(field);
		}
		else if (checker == 4)
		{
			playerToReturn.university = field;
		}
		else if (checker == 5)
		{
			playerToReturn.birthYear = field;
		}
		else if (checker == 6)
		{
			playerToReturn.birthCity = field;
		}
		else if (checker == 7)
		{
			playerToReturn.birthState = field;
		}
		checker++;
		field = strtok(NULL, ",");
	}
	fclose(fptr);

	return 0;
}