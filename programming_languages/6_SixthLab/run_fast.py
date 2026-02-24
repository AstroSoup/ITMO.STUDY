import ctypes
import time
import os

# --- Загрузка библиотеки ---
# Определение имени файла библиотеки в зависимости от ОС
lib_name = "fast_lib.dll" if os.name == 'nt' else "fast_lib.so"
lib_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), lib_name)

try:
    # Загрузка C-библиотеки
    c_lib = ctypes.CDLL(lib_path)
    print(f"Библиотека успешно загружена: {lib_path}")
except Exception as e:
    print(f"ОШИБКА: Не удалось загрузить библиотеку '{lib_path}'")
    print("Выполнен ли Шаг 3 (компиляция)?")
    print(f"Детали ошибки: {e}")
    exit()

# --- Настройка ctypes ---
# Необходимо явно определить типы аргументов (argtypes)
# и возвращаемого значения (restype) для C-функций.

# 1. Рекурсивная C-функция
c_lib.fib_c.argtypes = [ctypes.c_int]
c_lib.fib_c.restype = ctypes.c_longlong

# 2. Итеративная C-функция
c_lib.fib_c_iterative.argtypes = [ctypes.c_int]
c_lib.fib_c_iterative.restype = ctypes.c_longlong

# 1. Определение аналога C-структуры в Python с помощью ctypes.Structure
class Point(ctypes.Structure):
    _fields_ = [("x", ctypes.c_int),
                ("y", ctypes.c_int)]

# 2. Определение типов для новой C-функции
# Она принимает УКАЗАТЕЛЬ на Point (ctypes.POINTER)
c_lib.process_point.argtypes = [ctypes.POINTER(Point)]
c_lib.process_point.restype = None # (void)

# --- Тест структуры ---
print("\n--- Тестирование структур ---")
my_point = Point(x=5, y=10)
print(f"[Python] До вызова C: x={my_point.x}, y={my_point.y}")

# Передача структуры по ссылке (byref)
c_lib.process_point(ctypes.byref(my_point))

print(f"[Python] После вызова C: x={my_point.x}, y={my_point.y}")

# --- Тестирование ---
if __name__ == "__main__":
    number = 35 # Используем то же значение, что и в slow_fib.py
    
    # --- Тест Python (повторно) ---
    def fib_py(n):
        if n <= 1: return n
        else: return fib_py(n-1) + fib_py(n-2)
        
    start_py = time.time()
    res_py = fib_py(number)
    end_py = time.time()
    
    print(f"\n[Python] Fib({number}) = {res_py}")
    print(f"[Python] Время выполнения: {end_py - start_py:.4f} сек")
    
    # --- Тест C (Рекурсия) ---
    start_c = time.time()
    res_c = c_lib.fib_c(number)
    end_c = time.time()
    
    print(f"\n[C, рекурсия] Fib({number}) = {res_c}")
    print(f"[C, рекурсия] Время выполнения: {end_c - start_c:.4f} сек")
    
    # --- Тест C (Итеративный) ---
    start_c_iter = time.time()
    res_c_iter = c_lib.fib_c_iterative(number)
    end_c_iter = time.time()
    
    print(f"\n[C, итеративно] Fib({number}) = {res_c_iter}")
    print(f"[C, итеративно] Время выполнения: {end_c_iter - start_c_iter:.4f} сек")
    
    py_time = end_py - start_py
    c_time = end_c - start_c
    
    if c_time > 0:
        print(f"\nИТОГ: C-версия (рекурсия) быстрее Python (рекурсия) в {py_time / c_time:.0f} раз")
    else:
        print(f"\nИТОГ: C-версия (рекурсия) выполнилась быстрее, чем позволяет измерить time.time()")
    