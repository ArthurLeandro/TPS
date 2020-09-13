#include <stdio.h>
#include <stdlib.h> // For exit() function
#include <string.h>

int main()
{
	char command[50];

	strcpy(command, "ls -l");
	system(command);
}