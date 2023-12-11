.MODEL SMALL
.STACK 100H

.DATA
    espacio db 10,13,'$'
    temporal DW ?
    n1 DW ?
    n2 DW ?
    suma DW ?
    resta DW ?
    multiplicacion DW ?
    division DW ?
    n11 DB '3', 0Dh, 0Ah, '$'
    n22 DB '6', 0Dh, 0Ah, '$'
    suma3 DB '9', 0Dh, 0Ah, '$'
    resta4 DB '-3', 0Dh, 0Ah, '$'
    multiplicacion5 DB '18', 0Dh, 0Ah, '$'
    division6 DB '0', 0Dh, 0Ah, '$'
.CODE

asigString MACRO string1, string2
   lea di,string1
   mov si,offset string2
   mov cx,64
   rep movsb
ENDM

suma MACRO operando1, operando2
   MOV AX, operando1
   ADD AX, operando2
   push ax
ENDM

printString MACRO variable
   mov dx, offset espacio
   mov ah, 09h
   int 21h

   mov dx, offset variable
   mov ah, 09h
   int 21h
ENDM

resta MACRO operando1, operando2
   MOV AX, operando1
   SUB AX, operando2
   push ax
ENDM

multiplicacion MACRO operando1, operando2
   MOV AX, operando1
   MOV BX, operando2
   IMUL BX
   PUSH AX
ENDM

division MACRO operando1, operando2
   MOV AX, operando1
   MOV BX, operando2
   MOV DX, 0
   IDIV BX
   PUSH AX
ENDM

asigNum MACRO operando1, resultado
   MOV AX, operando1
   MOV resultado, AX
ENDM

operacionAnd MACRO variable1, variable2
   MOV AX, variable1
   MOV BX, variable2
   AND AX, BX
   PUSH AX
ENDM

operacionOr MACRO variable1, variable2
   MOV AX, variable1
   MOV BX, variable2
   OR AX, BX
   PUSH AX
ENDM

asigBool MACRO operando1, resultado
   MOV AL, operando1
   MOV resultado, AL
ENDM

    main PROC
        mov ax,@data
        mov ds,ax
        mov es,ax

        asigNum 3, n1
        asigNum 6, n2
        suma n1, n2
        POP AX
        asigNum AX, suma
        resta n1, n2
        POP AX
        asigNum AX, resta
        multiplicacion n1, n2
        POP AX
        asigNum AX, multiplicacion
        division n1, n2
        POP AX
        asigNum AX, division
        printString suma3
        printString resta4
        printString multiplicacion5
        printString division6
        .exit
    main endp

    MostrarNumero PROC
        MOV CX, 0
        MOV BX, 10

        L1:
            MOV DX, 0
            DIV BX
            PUSH DX
            INC CX
            CMP AX, 0
            JNZ L1

        L2:
            POP DX
            ADD DL, 30H
            MOV AH, 02H
            INT 21H
            LOOP L2

        RET
    MostrarNumero ENDP
END main