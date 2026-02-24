import time

def fib_py(n):
    if n <= 1:
        return n
    else:
        return fib_py(n-1) + fib_py(n-2)

if __name__ == "__main__":
    number = 35 # Значение, требующее ощутимого времени для расчета
    
    start_time = time.time()
    result = fib_py(number)
    end_time = time.time()
    
    print(f"[Python] Fib({number}) = {result}")
    print(f"[Python] Время выполнения: {end_time - start_time:.4f} сек")
