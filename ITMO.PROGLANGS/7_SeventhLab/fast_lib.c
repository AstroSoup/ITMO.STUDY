#ifdef _WIN32
    #define DLLEXPORT __declspec(dllexport)
#else
    #define DLLEXPORT
#endif

// Важно: используется 'long long' для предотвращения переполнения
DLLEXPORT long long fib_c(int n) {
    if (n <= 1) {
        return n;
    } else {
        return fib_c(n - 1) + fib_c(n - 2);
    }
}

// Оптимальная итеративная версия для демонстрации
DLLEXPORT long long fib_c_iterative(int n) {
    if (n <= 1) return n;
    
    long long a = 0;
    long long b = 1;
    long long temp;
    
    for (int i = 2; i <= n; i++) {
        temp = a + b;
        a = b;
        b = temp;
    }
    return b;
}

#include <stdio.h> // Требуется для printf

// Определение структуры 'Point'
typedef struct {
    int x;
    int y;
} Point;

// Функция принимает УКАЗАТЕЛЬ на структуру
DLLEXPORT void process_point(Point* p) {
    printf("[C] Получена структура. x = %d, y = %d\n", p->x, p->y);
    // Модифицируем данные по указателю
    p->x = p->x * 10;
    p->y = p->y * 10;
    printf("[C] Значения модифицированы.\n");
}
