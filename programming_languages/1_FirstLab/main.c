#include <stdio.h>
#include <stdlib.h>
#include <string.h>


// Прототип функции шифрования
void encrypt(char* text, int key);
void decrypt(char* text, int key);

int main(int argc, char* argv[]) {
    if (argc <= 3) {
        printf("Usage: ./caesar <mode> <str> <key>\n");
        return 1;
    }

    char* mode = argv[1];

    char* text = argv[2];

    
    int key = atoi(argv[3]);




    // Вызываем функцию для выполнения шифрования

    if (strcmp(mode, "decrypt") == 0) {
        printf("Source text: %s\n", text);
        printf("Key: %d\n", key);
        decrypt(text, key);
        printf("Decrypted text: %s\n", text);
    }
    else {
        if (strcmp(mode, "encrypt") == 0) {
            printf("Source text: %s\n", text);
            printf("Key: %d\n", key);
            encrypt(text, key);
            printf("Encrypted text: %s\n", text);
        } else {
            printf("mode parameter is strictly encrypt or decrypt.\n");
        }
    }
    return 0;
}

// Реализация функции шифрования
void encrypt(char* text, int key) {
    for (int i = 0; text[i] != '\0'; i++) {
        
        
        char currentChar = text[i];

        if (currentChar >= 'a' && currentChar <= 'z') {
            char shiftedChar = 'a' + (currentChar - 'a' + key) % 26;
            text[i] = shiftedChar;
        }
        else if (currentChar >= 'A' && currentChar <= 'Z') {
            char shiftedChar = 'A' + (currentChar - 'A' + key) % 26;
            text[i] = shiftedChar;
        }
    }
}
// Реализация функции расшифровки
void decrypt(char* text, int key) {
    for (int i = 0; text[i] != '\0'; i++) {
        
        
        char currentChar = text[i];

        if (currentChar >= 'a' && currentChar <= 'z') {
            int shift = (currentChar - 'a' - key) % 26;
            if (shift < 0) {
                shift += 26;
            }
            char shiftedChar = 'a' + shift;
            text[i] = shiftedChar;
        }
        else if (currentChar >= 'A' && currentChar <= 'Z') {
            int shift = (currentChar - 'A' - key) % 26;
            if (shift < 0) {
                shift += 26;
            }
            char shiftedChar = 'A' + shift;
            text[i] = shiftedChar;
        }
    }
}