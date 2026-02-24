import ctypes
import ctypes.util
import os

if os.name == 'nt':
    # --- Windows ---
    # Используем 'windll' для библиотек, использующих конвенцию stdcall
    user32 = ctypes.windll.user32
    
    # Описываем прототип функции MessageBoxW
    # (HWND, LPCWSTR, LPCWSTR, UINT) -> int
    user32.MessageBoxW.argtypes = [ctypes.c_void_p, ctypes.c_wchar_p, ctypes.c_wchar_p, ctypes.c_uint]
    user32.MessageBoxW.restype = ctypes.c_int

    print("Вызов MessageBoxW из user32.dll...")
    # 0x00000040 = MB_OK | MB_ICONINFORMATION
    user32.MessageBoxW(0, "Привет из Python!", "ctypes Демо (Windows)", 0x00000040)
    print("Диалоговое окно закрыто.")

else:
    # --- Linux & macOS ---
    # Находим стандартную библиотеку C
    libc_name = ctypes.util.find_library('c')
    if not libc_name:
        print("Ошибка: не могу найти libc. Что-то пошло не так.")
        exit()
        
    # Используем 'CDLL' для стандартной конвенции cdecl
    libc = ctypes.CDLL(libc_name)
    
    # Описываем прототип функции puts
    # (const char*) -> int
    libc.puts.argtypes = [ctypes.c_char_p]
    libc.puts.restype = ctypes.c_int
    
    print(f"Вызов puts из {libc_name}...")
    # В C-функции ожидаются байты, а не str, поэтому b""
    message = b"Hello from Python! (ctypes, POSIX)"
    libc.puts(message)
