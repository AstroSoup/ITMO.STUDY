from pynput.keyboard import Controller, Key
import time

keyboard = Controller()

time.sleep(5)
# Ввод адреса
keyboard.type("0000000001010110")
keyboard.press(Key.f4)
time.sleep(1)
# Ввод программы
keyboard.type("1110000001010111") # 056  E057
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000001000000000") # 057  0200
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0010000001100010") # 058  2062
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1010000001010110") # 059  A056
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0010000001100010") # 05A  2062
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1110000001010111") # 05B  E057
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000001000000000") # 05C  0200
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0110000001100001") # 05D  6061
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0100000001010111") # 05E  4057
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("1110000001011000") # 05F  E058
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000000100000000") # 060  0100
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0110000001100001") # 061  6061
keyboard.press(Key.f5)

time.sleep(1)

keyboard.type("0000001000000000") # 062  0200
keyboard.press(Key.f5)

time.sleep(1)
# Установка поинтера на 059
keyboard.type("0000000001011001")
keyboard.press(Key.f4)

time.sleep(1)