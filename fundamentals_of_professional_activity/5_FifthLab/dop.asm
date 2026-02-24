

ORG 0x100;
; Переменные
PST: WORD 0x500;
POS: WORD 0x500;

APOS: WORD 0x4FF;
AIND: WORD 8;
AOI: WORD 0;

CD: WORD ?;

; Программа

STOP: HLT;
; Очистка дисплея
START: 
LD #0x0F
OUT 0x14
LD #0x1F
OUT 0x14
LD #0x2F
OUT 0x14
LD #0x3F
OUT 0x14
LD #0x4F
OUT 0x14
LD #0x5F
OUT 0x14
LD #0x6F
OUT 0x14
LD #0x7F
OUT 0x14
LD #0x8F
OUT 0x14
; Очистка переменных
LD #8;
ST AIND;
LD #0;
ST AOI;
LD PST;
ST POS;
DEC
ST APOS;
; Считывание слова с Numpad'a
INPUT: IN 0x1D
AND #0x40
BEQ INPUT

IN 0x1C

CMP #0xF
BEQ CALC

ST (POS)+
JUMP INPUT

; Тело программы

CALC: LD POS
CMP PST
BEQ FORMAT
DEC
CMP PST
BEQ FORMAT
DEC
ST CD
ST POS

; Подсчет часов

; Если в текущем разряде < 6 десятков минут, то занимаем из старшего
COUNT: LD (POS)
CMP #0x6
BMI BORROW
; Иначе вычитаем 6 из разряда десятков минут
SUB #6
ST (POS)
; Загружаем текущий разряд ответа, если он равен 9, то зануляем текущий разряд, сдвигаем указатель на 1 и записываем в новый разряд 1.
LD (APOS)
CMP #9
BEQ SHIFT
; Иначе разряд +1
INC
ST (APOS)
; Цикл обновляется
JUMP COUNT






; Зануляем разряд и сдвигаем указатель
SHIFT: CLA
ST (APOS)
; проверяем следующий разряд на переполнение
LD -(APOS)
CMP #9;
BEQ SHIFT
INC
ST (APOS)
; Переход на младший разряд
LD PST
DEC
ST APOS
; Снова считать часики...
JUMP COUNT







; Подпрограмма заема из более старшего разряда
BORROW:
; Проверка что указатель разряда не превысил длину числа, иначе все разряды числа нулевые, а значит - вывод
LD CD
CMP PST
BEQ FORMAT

; Проверка что разряд не нулевой иначе заем из следующего
LD -(CD)
BEQ BORROW

; Вычитаем 1 из старшего разряда, сдвигаем указатель разряда
DEC 
ST (CD)+
; Прибавляем к младшему разряду 10
LD (CD)
ADD #0xA
ST (CD)
LD POS
ST CD
JUMP COUNT

; Форматирование полученных цифр для вывода на индикатор
FORMAT: 
; Загружаем позицию цифры
LD AOI
; сдвигаем чтобы позиция занимала старшую тетраду младшего байта
ASL
ASL
ASL
ASL
; Записываем номер разряда для вывода
OR (APOS)
ST (APOS)
LD (AOI)+
OR -(APOS)
LOOP AIND
JUMP FORMAT
OR (APOS)+
; Вывод
OUTPUT: IN 0x15
AND #0x40
BEQ OUTPUT
LD (APOS)
OUT 0x14
CLA
ST (APOS)+
LD APOS
CMP PST
BEQ STOP
JUMP OUTPUT

