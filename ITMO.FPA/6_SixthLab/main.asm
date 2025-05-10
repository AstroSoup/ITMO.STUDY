ORG 0x0 ; Инициализация векторов прерывания
V0:	WORD $DEFAULT,0x180 ; Вектор прерывания #0 0 1
V1: WORD $DEFAULT,0x180 ; Вектор прерывания #1 2 3
V2: WORD $INT2,0x180 	; Вектор прерывания #2 4 5
V3: WORD $INT3,0x180 	; Вектор прерывания #3 6 7
V4: WORD $DEFAULT,0x180 ; Вектор прерывания #4 8 9
V5: WORD $DEFAULT,0x180 ; Вектор прерывания #5 A B
V6: WORD $DEFAULT,0x180 ; Вектор прерывания #6 C D
V7: WORD $DEFAULT,0x180 ; Вектор прерывания #7 E F

ORG 0x27
X: WORD 0
MIN: WORD 0xFFEC	; левая граница ОДЗ = -20 Включена в ОДЗ ( [-20; 22) )
MAX: WORD 0x0016	; правая граница ОДЗ = 22 Не включена в ОДЗ
TMP: WORD 0

DEFAULT:
IN 6
IRET
	
START:
DI
CLA
OUT 0x1 	; MR КВУ-0 на вектор 0
OUT 0x3 	; MR КВУ-1 на вектор 0
OUT 0xB 	; MR КВУ-4 на вектор 0
OUT 0xD 	; MR КВУ-5 на вектор 0
OUT 0x11 	; MR КВУ-6 на вектор 0
OUT 0x15 	; MR КВУ-7 на вектор 0
OUT 0x19 	; MR КВУ-8 на вектор 0
OUT 0x1D	; MR КВУ-9 на вектор 0

LD #0xA 	; разрешить прерывания и вектор №2
OUT 0x5 	
LD #0xB 	; разрешить прерывания и вектор №3 
OUT 0x7 	

JUMP PROG
PROG:
EI 
FOR:
LD X
INC
CMP MIN
BLT OVERFLOW
CMP MAX
BGE OVERFLOW
ST X
JUMP FOR

OVERFLOW:
LD MIN
NOP ; Проверка работоспособности механизма переполнения
ST X
JUMP FOR

INT3:
PUSH
LD X
ASL
ASL
NEG
ST TMP
LD X
ASL
NEG
ADD TMP
ADD #4
OUT 6
NOP ; Проверка правильности работы прерывания с ВУ-3
POP
IRET

INT2:
DI
IN 4

ST TMP
ADD TMP
ADD TMP
ST TMP
LD X
SUB TMP



CMP MIN
BLT IOVERFLOW
CMP MAX
BGE IOVERFLOW
ISTATE:
NOP ; Проверка правильности работы прерывания с ВУ-2
ST X
EI
IRET

IOVERFLOW:
LD MIN
JUMP ISTATE
