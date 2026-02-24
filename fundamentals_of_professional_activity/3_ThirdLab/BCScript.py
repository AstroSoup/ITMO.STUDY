from pynput.keyboard import Controller, Key
import time

keyboard = Controller()

time.sleep(5)
# Ввод адреса
keyboard.type("0000010100001010")
keyboard.press(Key.f4)
time.sleep(1)

# Ввод программы

# Ввод мета-информации

keyboard.type("0000010100011111") # 50A 051F
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1010000000000000") # 50B A000
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1110000000000000") # 50C E000
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1110000000000000") # 50D E000
keyboard.press(Key.f5)

time.sleep(1)

# Ввод программы

keyboard.type("0000001000000000") # 50E 0200
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1110111011111101") # 50F EEFD
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1010111100000100") # 510 AF04
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1110111011111010") # 511 EEFA
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1010111011110111") # 512 AEF7
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1110111011110111") # 513 EEF7
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1010101011110110") # 514 AAF6
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000010010000000") # 515 0480
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1111010000000101") # 516 F405
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000010010000000") # 517 0480
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1111010000000011") # 518 F403
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000010000000000") # 519 0400
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000010000000000") # 51A 0400
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1010101011110001") # 51B AAF1
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1000010100001100") # 51C 850С
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1100111011110110") # 51D CEF6
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000000100000000") # 51E 0100
keyboard.press(Key.f5)

time.sleep(1)

# Массив данных

keyboard.type("0000001100000010") # 51F 0302
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0001000100000011") # 520 1103
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000011101000001") # 521 0741
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000011100000000") # 522 0700
keyboard.press(Key.f5)

time.sleep(1)

# Установка поинтера на 50E
keyboard.type("0000010100001110")
keyboard.press(Key.f4)

time.sleep(1)