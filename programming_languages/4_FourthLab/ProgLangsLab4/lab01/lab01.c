#include <stdlib.h>
#include <stdio.h>
#include <memory.h>

//

#define _CRT_SECURE_NO_WARNINGS

typedef struct {
	int x;
	int y;
} Point;

typedef enum {
	WARRIOR,
	MAGE,
	ARCHER
} UnitTypes;

typedef struct {
	int health;
	int maxHealth;
	UnitTypes type;
	Point position;
	char name[50];
} GameUnit;

GameUnit* createUnit(const char* name, UnitTypes type, int maxHealth, Point position) {
	if (maxHealth <= 0 || !name) {
		return NULL;
	}

	GameUnit* unit = (GameUnit*)malloc(sizeof(GameUnit));
	
	if (!unit) {
		return NULL;
	}

	//unit->name = (char*)malloc(strlen(name) + 1);
	strcpy(unit->name, name);
	if (!unit->name) {
		free(unit);
		return NULL;
	}

	strcpy(unit->name, name);
	unit->type = type;
	unit->maxHealth = maxHealth;
	unit->health = maxHealth;
	unit->position = position;
	return unit;
}

void freeUnit(GameUnit* unit) {
	if (unit) {
	//	free(unit->name);
		free(unit);
	}
}

void printUnit(const GameUnit* unit) {
	if (unit) {
		printf("Unit: %s, Type: %d, Health: %d/%d, Position: (%d, %d)\n",
			unit->name, unit->type, unit->health, unit->maxHealth, unit->position.x, unit->position.y);
	}
	//(*unit).name
}

void serializeUnit(const GameUnit* unit, const char* filename) {
	if (!unit || !filename) {
		return;
	}

	FILE* file = fopen(filename, "wb");
	if (!file) {
		return;
	}

	fwrite(unit, sizeof(GameUnit), 1, file);

	//fwrite(&unit->health, sizeof(int), 1, file);
	//fwrite(&unit->maxHealth, sizeof(int), 1, file);
	//fwrite(&unit->type, sizeof(UnitTypes), 1, file);
	//fwrite(&unit->position, sizeof(Point), 1, file);

	//int nameLength = strlen(unit->name) + 1;
	//fwrite(&nameLength, sizeof(int), 1, file);
	//fwrite(unit->name, sizeof(char), nameLength, file);

	fclose(file);
}

GameUnit* deserializeUnit(const char* filename) {
	if (!filename) {
		return NULL;
	}

	FILE* file = fopen(filename, "rb");
	if (!file) {
		return NULL;
	}

	GameUnit* unit = (GameUnit*)malloc(sizeof(GameUnit));
	if (!unit) {
		fclose(file);
		return NULL;
	}

	fread(unit, sizeof(GameUnit), 1, file);

	//fread(&unit->health, sizeof(int), 1, file);
	//fread(&unit->maxHealth, sizeof(int), 1, file);
	//fread(&unit->type, sizeof(UnitTypes), 1, file);
	//fread(&unit->position, sizeof(Point), 1, file);

	//int nameLength;
	//fread(&nameLength, sizeof(int), 1, file);
	//unit->name = (char*)malloc(nameLength);
	//if (!unit->name) {
	//	free(unit);
	//	fclose(file);
	//	return NULL;
	//}
	//fread(unit->name, sizeof(char), nameLength, file);

	fclose(file);
	return unit;
}	


void ptrDemo() {
	int arr[] = { 1,2,3,4,5 }; // int* const arr
	int* ptr = arr;

	int** ptr = malloc(sizeof(int*));

	for (int i = 0; i < 5; i++) {
		*(ptr + i) = malloc(sizeof(int));
	}	
}

int main() {
	GameUnit* unit = createUnit("Hero", WARRIOR, 100, (Point) { 0, 0 });
	if (unit) {
		printUnit(unit);
		freeUnit(unit);
	}
	else {
		printf("Failed to create unit.\n");
	}

	GameUnit* unit2 = createUnit("Mage", MAGE, 80, (Point) { 10, 5 });
	if (unit2) {
		serializeUnit(unit2, "unit.dat");
		freeUnit(unit2);
	}

	GameUnit* loadedUnit = deserializeUnit("unit.dat");
	if (loadedUnit) {
		printUnit(loadedUnit);
		freeUnit(loadedUnit);
	}
	else {
		printf("Failed to load unit.\n");
	}
	
	return 0;
}
