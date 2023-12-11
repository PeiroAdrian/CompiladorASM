import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class lexico {
    
    
    public static void imprimir(LinkedList<nodo> lista) {
        for (nodo elemento : lista)
            System.out.println(elemento.lexema + " " + elemento.token);
        //System.out.println();
    }

    int antiLoopTermino, antiLoopExpSimple;
    boolean errorFlag = false, dataTypeFlag = false, variableBuleana = false, banderaExpCond = false;
    LinkedList<nodo> listaVariables = new LinkedList<nodo>();
    LinkedList<nodo> listaExpresion = new LinkedList<nodo>();
    LinkedList<String> listaPrint = new LinkedList<String>();
    Stack<pila> pilaPrint = new Stack<pila>();
    Queue<Boolean> colaCondicionales = new LinkedList<>();


    nodo cabeza = null, p;
    int estado = 0, columna, valorMT, numRenglon = 1;
    String tipoDato = "", copyLexema = "", CopiaTipoDeDato = "", copiaCopiaTipodDeDato = "", expresion = "", expresion2 = "", expresion3  = "", resultadoTemporal = "", expresion4 = "";
    int caracter = 0, contador = 0;
    String lexema = "", tipoDeDato = "", valor = "", txtOutput = "";
    boolean errorEncontrado = false;
    boolean errorEncontradoSintactico = false;
    boolean errorEncontradoSemantico = false;
    String copiaTipoDato1 = "", copiaTipoDato2 = "", variableAsignacion = "";
    int copiaToken1 = 0, copiaToken2 = 0, pCounter = 0;
    String nombreArchivo = "C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Compilador_LyA2\\src\\output.txt";
    String archivo = "C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Compilador_LyA2\\src\\codigo.txt";
    //String nombreArchivo = "C:\\Users\\Adrian\\Documents\\TAREAS SEPTIMO SEMSTRE\\Compi\\compiV23asmExtProMasterFinal\\output.txt";
    //String archivo = "C:\\Users\\Adrian\\Documents\\TAREAS SEPTIMO SEMSTRE\\Compi\\compiV23asmExtProMasterFinal\\prueba.txt";
    //String archivo = "C:\\Users\\Adrian\\Documents\\TAREAS SEPTIMO SEMSTRE\\Compi\\compiV23asmExtProMasterFinal\\prueba.txt";
    RandomAccessFile file = null;

    int matriz[][] = {
            //     letras @ _ digitos + - * / ^ < > = ! & | ( ) { } , ; " espacio blanco TAB línea nueva . EOF otra cosa
            //        0  1  2  3   4    5    6   7   8   9  10 11  12  13  14  15   16   17   18   19   20   21  22 23 24 25   26   27
            /* 0 */ { 1, 1, 1, 2, 103, 104, 105, 5, 107, 8, 9, 10, 11, 12, 13, 117, 118, 119, 120, 124, 125, 14, 0, 0, 0, 505, 505, 505 },
            /* 1 */ { 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 },
            /* 2 */ { 101, 101, 101, 2, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 3, 101, 101 },
            /* 3 */ { 500, 500, 500, 4, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500 },
            /* 4 */ { 102, 102, 102, 4, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102 },
            /* 5 */ { 106, 106, 106, 106, 106, 106, 6, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106 },
            /* 6 */ { 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 501, 6 },
            /* 7 */ { 6, 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
            /* 8 */ { 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 110, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108 },
            /* 9 */ { 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 111, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109 },
            /* 10 */{ 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 112, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123 },
            /* 11 */{ 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 113, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116 },
            /* 12 */{ 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 114, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502 },
            /* 13 */{ 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 115, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503 },
            /* 14 */{ 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 122, 14, 14, 504, 14, 14, 14 }

    };
    String palReservadas[][] = {
            // 0 1 palabras reservadas de pawn
            /* 0 */{ "break", "200" },
            /* 1 */{ "if", "201" },
            /* 2 */{ "else", "202" },
            /* 3 */{ "main", "203" },
            /* 4 */{ "while", "204" },
            /* 5 */{ "go to", "205" },
            /* 6 */{ "print", "206" },
            /* 7 */{ "new", "207" },
            /* 8 */{ "float", "208" },
            /* 9 */{ "int", "209" },
            /* 10 */{ "false", "210" },
            /* 11 */{ "true", "211" },
            /* 12 */{ "string", "212" },
            /* 13 */{ "bool", "213" },
            /* 14 */{ "getvalue", "214" },
    };
    String errores[][] = {
            // 0 1 Número de columna del arreglo
            /* 0 */{ "Se espera un digito", "500" },
            /* 1 */{ "Se espera cierre de comentario", "501" },
            /* 2 */{ "Se espera un &", "502" },
            /* 3 */{ "Se espera un |", "503" }, 
            /* 4 */{ "Se espera cierre de cadena", "504" },
            /* 5 */{ "Caracter no valido", "505" }
    };
    
    public lexico() {
        try {
            file = new RandomAccessFile(archivo, "r");
            while (caracter != -1) {
                caracter = file.read();
                if (Character.isLetter(((char) caracter))) {
                    columna = 0;

                } else if (Character.isDigit(((char) caracter))) {
                    columna = 3;
                } else if (caracter == -1) {
                    columna = 26;
                } else {
                    switch ((char) caracter) {
                        case '@':
                            columna = 1;
                            break;
                        case '_':
                            columna = 2;
                            break;
                        case '+':
                            columna = 4;
                            break;
                        case '-':
                            columna = 5;
                            break;
                        case '*':
                            columna = 6;
                            break;
                        case '/':
                            columna = 7;
                            break;
                        case '^':
                            columna = 8;
                            break;
                        case '<':
                            columna = 9;
                            break;
                        case '>':
                            columna = 10;
                            break;
                        case '=':
                            columna = 11;
                            break;
                        case '!':
                            columna = 12;
                            break;
                        case '&':
                            columna = 13;
                            break;
                        case '|':
                            columna = 14;
                            break;
                        case '(':
                            columna = 15;
                            break;
                        case ')':
                            columna = 16;
                            break;
                        case '{':
                            columna = 17;
                            break;
                        case '}':
                            columna = 18;
                            break;
                        case ',':
                            columna = 19;
                            break;
                        case ';':
                            columna = 20;
                            break;
                        case '"':
                            columna = 21;
                            break;
                        case ' ':
                            columna = 22;
                            break;
                        case 9:
                            columna = 23;// tab
                            break;
                        case 10: 
                            columna = 24;// nueva linea
                            numRenglon = numRenglon + 1;
                        
                            break;
                        case 13:
                            columna = 24;// retorno de carro
                            break;
                        case '.':
                            columna = 25;
                            break;
                        /*
                         * case 3:columna=26;//eof
                         * break;
                         */
                        default:
                            columna = 27;
                            break;
                    }
                }
                valorMT = matriz[estado][columna];
                if (valorMT < 100) { // cambiar de estado
                    estado = valorMT;
                    if (estado == 0) {
                        lexema = "";
                    } else {
                        lexema = lexema + (char) caracter;
                    }
                } else if (valorMT >= 100 && valorMT < 500) {
                    if (valorMT == 100) {
                        validarSiEsPalabraReservada();
                    }
                    if (valorMT == 100 || valorMT == 101 || valorMT == 102 ||
                            valorMT == 106 || valorMT == 123 || valorMT == 108 || valorMT == 109
                            || valorMT == 116 || valorMT == 200 || valorMT == 201 || valorMT == 202 || valorMT == 203
                            || valorMT == 204 || valorMT == 205 || valorMT == 206 || valorMT == 207 || valorMT == 208
                            || valorMT == 209 || valorMT == 210 || valorMT == 211 || valorMT == 212 || valorMT == 214
                            || valorMT == 213) {
                        file.seek(file.getFilePointer() - 1); // i--
                    } else {
                        lexema = lexema + (char) caracter;
                    }
                    insertarNodo();
                    estado = 0;
                    lexema = "";
                } else {// estado de error
                    imprimirMensajeError();
                    break;
                }
            }
            if (valorMT == 7 || valorMT == 6) {
                valorMT = 501;
                imprimirMensajeError();
            }
            sintaxis();
            imprimir(listaExpresion);
            // for(boolean elemento : listaCondicionales) {
            //     System.out.println(elemento);
            // }
            escribirEnArchivo("output.txt", txtOutput);
            
            creacionAsm objetoCreacionAsm = new creacionAsm(listaVariables, colaCondicionales, pilaPrint);
            
            System.out.println("Nodos:");
            imprimirNodos();
            System.out.println("\nLista de variables:");
            imprimir(listaVariables);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // private void imprimirNodos(){
    // p=cabeza;
    // while (p!=null){
    // System.out.println(p.lexema+" "+p.token+" "+p.renglon+" "+p.tipoDato);
    // p=p.sig;
    // }
    // }
    private void validarSiEsPalabraReservada() {
        for (String[] palReservada : palReservadas) {
            if (lexema.equals(palReservada[0])) {
                valorMT = Integer.valueOf(palReservada[1]);
            }
        }
    }

    private void imprimirMensajeError() {
        if ((caracter != -1 && valorMT >= 500) || (caracter == -1 && valorMT == 501)) {
            for (String[] errores : errores) {
                if (valorMT == Integer.valueOf(errores[1])) {
                    System.out.println("El error encontrado es: " + errores[0] + " error " + valorMT +
                            " caracter " + caracter + " en el renglon " + numRenglon);
                }
            }
            errorEncontrado = true;
        }
    }

    private void insertarNodo() {
        nodo nodo = new nodo(lexema, valorMT, numRenglon, tipoDato, valor);
        if (cabeza == null) {
            cabeza = nodo;
            p = cabeza;
        } else {
            p.sig = nodo;
            p = nodo;
        }
    }

    // ------------------------------------------análisis de
    // sintaxis-----------------------------------------------------------------------------------------------------------------------------
    private void sintaxis() {
        p = cabeza;
        if (p != null) {
            if (p.token == 203) {// token de palabra reservada Main para inicio de programa
                if (Avanzar()) {
                    p = p.sig;
                    if (p.token == 117) {// token de apertura de paréntesis
                        if (Avanzar()) {
                            p = p.sig;
                            if (p.token == 118) {// token de cierre de paréntesis
                                if (Avanzar()) {
                                    p = p.sig;
                                    if (p.token == 119) {// token de apertura de llave
                                        if (Avanzar()) {
                                            p = p.sig;
                                            if (p.token != 120) {
                                                variables();// siempre retorna estando en nodo con token de ;
                                                for(nodo elemento: listaVariables){
                                                    switch(elemento.tipoDato){
                                                        case "Integer":
                                                            txtOutput = txtOutput + "int " + elemento.lexema + "\n";
                                                            break;
                                                        case "Float":
                                                            txtOutput = txtOutput + "float " + elemento.lexema + "\n";
                                                            break;
                                                        case "Boolean":
                                                            txtOutput = txtOutput + "bool " + elemento.lexema + "\n";
                                                            break;
                                                        case "String":
                                                            txtOutput = txtOutput + "string " + elemento.lexema + "\n";
                                                            break;
                                                    }
                                                }
                                                if (errorFlag) {
                                                    return;
                                                } else {
                                                    if (Avanzar()) {
                                                        p = p.sig;
                                                        if (p.token != 120) {
                                                            statement();// agregar dsps errorflag antes de terminar
                                                            if (errorFlag) {
                                                                return;
                                                            }
                                                            if (Avanzar()) {
                                                                System.out.println(
                                                                        "Error, se encontró código fuera del programa. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                errorFlag = true;
                                                                return;
                                                            } else {
                                                                return;// se termina el programa
                                                            }
                                                        } else if (p.token == 120) {
                                                            if (Avanzar()) {
                                                                System.out.println(
                                                                        "Error, se encontró código fuera del programa. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                errorFlag = true;
                                                                return;
                                                            } else {
                                                                return;// se termina el programa
                                                            }
                                                        }
                                                    } else {
                                                        System.out.println("Se espera cierre de llave del programa. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                        errorFlag = true;
                                                        return;
                                                    }
                                                }
                                            } else if (p.token == 120) {
                                                if (Avanzar()) {
                                                    System.out.println("Error, se encontró código fuera del programa. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                    errorFlag = true;
                                                    return;
                                                } else {
                                                    return;// se termina el programa
                                                }

                                            }
                                        } else {
                                            System.out.println("Se espera cierre de llave o declaración de variables. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                            errorFlag = true;
                                            return;
                                        }
                                    } else {
                                        System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                        errorFlag = true;
                                        return;
                                    }
                                } else {
                                    System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    return;
                                }
                            } else {
                                System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                errorFlag = true;
                                return;
                            }
                        } else {
                            System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            return;
                        }
                    } else {
                        System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        return;
                    }
                } else {
                    System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    return;
                }
            } else {
                System.out.println("Se espera inicio de programa Main(). Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                errorFlag = true;
                return;
            }
        }
        return;
    }

    private boolean Avanzar() {
        if (p.sig != null) {
            return true;
        } else {
            return false;
        }
    }

    private void checkIdsPrint() {// llega con la coma
        if (Avanzar()) {
            if (p.sig.token == 100) {
                p = p.sig;
                boolean banderaVariables = false;
                for(nodo elemento : listaVariables){
                    if (elemento.lexema.equals(p.lexema)) {
                        banderaVariables = true;
                        if (!elemento.valor.equals("")) {
                            listaPrint.add(p.lexema);
                        }else{
                            errorEncontradoSemantico = true;
                            errorFlag = true;
                            System.out.println(
                            "La variable ha sido definida, pero no tiene valor. \nRenglon: " + p.renglon +"  Lexema: "+ p.lexema);
                            break;
                        }
                    }
                }
                if (errorFlag) {
                    return;
                }
                if(!banderaVariables){
                    System.out.println("La variable no ha sido definida. \nRenglon: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorEncontradoSemantico = true;
                    errorFlag = true;
                    return;
                } 
                if (Avanzar()) {
                    if (p.sig.token == 124) {// token coma
                        p = p.sig;
                        checkIdsPrint();
                        return;
                    } else if (p.sig.token == 100) {// id
                        System.out.println("Se espera una coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        return;
                    } else if (p.sig.token == 118) {// )
                        p = p.sig;
                        if (Avanzar()) {
                            return;
                        } else {
                            System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            return;
                        }
                    } else {
                        System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        return;
                    }
                } else {
                    System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    return;
                }
            } else {
                System.out.println("Se espera id de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                errorFlag = true;
                return;
            }
        } else {
            System.out.println("Se espera id de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
            errorFlag = true;
            return;
        }
    }

    private void checkIdsVariables() {// llega con la coma
        // imprime la coma
        if (Avanzar()) {
            if (p.sig.token == 100) {
                p = p.sig;
                p.tipoDato = tipoDeDato;
                for (nodo elemento : listaVariables) {
                    if (elemento.lexema.equals(p.lexema)) {
                        System.out.println("La variable ya fue definida anteriormente. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorEncontradoSemantico = true;
                        errorFlag = true;
                        return;
                    }
                }
                if (errorFlag) {
                    return;
                } else {
                    listaVariables.add(p);
                }
                // imprime el id
                if (Avanzar()) {
                    if (p.sig.token == 124) {// token coma
                        p = p.sig;
                        checkIdsVariables();
                        return;
                    } else if (p.sig.token == 100) {// id
                        System.out.println("Se espera una coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        return;
                    } else if (p.sig.token == 125) {// ;
                        p = p.sig;
                        tipoDeDato = "";
                        if (Avanzar()) {
                            if (p.sig.token == 207) {// token new
                                p = p.sig;
                                variables();
                                return;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        return;
                    }
                } else {
                    System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    return;
                }
            } else {
                System.out.println("Se espera id de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                errorFlag = true;
                return;
            }
        } else {
            System.out.println("Se espera id de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
            errorFlag = true;
            return;
        }
    }

    // métodos
    private void variables() {// método de variables
        if (p.token == 207) { // token de palabra reservada new
            if (Avanzar()) {
                p = p.sig;
                if (tipos()) {// se manda a llamar el método para definir el tipo de variable
                    if (Avanzar()) {
                        p = p.sig;
                        if (p.token == 100) { // token de id
                            p.tipoDato = tipoDeDato;
                            for (nodo elemento : listaVariables) {
                                if (elemento.lexema.equals(p.lexema)) {
                                    System.out.println("La variable ya fue definida anteriormente. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorEncontradoSemantico = true;
                                    errorFlag = true;
                                    return;
                                }
                            }
                            if (errorFlag) {
                                return;
                            } else {
                                listaVariables.add(p);
                            }

                            if (Avanzar()) {
                                p = p.sig;// ---------------- ; o ,
                                if (p.token == 125) { // token de punto y coma
                                    tipoDeDato = "";
                                    if (Avanzar()) {
                                        if (p.sig.token == 207) { // si la siguiente palabra es new, se llama el metodo
                                                                  // // recursivamente
                                            p = p.sig;
                                            variables();
                                            return;
                                        } else { // si la siguiente palabra no es new, se termina el método
                                            return;
                                        }
                                    } else {
                                        errorFlag = true;
                                        return;
                                    }
                                } else if (p.token == 124) {// token de coma
                                    checkIdsVariables();
                                    return;
                                } else if (p.token == 100) {// token de id
                                    System.out.println("Se espera una coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    return;
                                } else {
                                    System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    return;
                                }
                            } else {
                                System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                errorFlag = true;
                                return;
                            }
                        } else {
                            System.out.println("Se espera id de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            return;
                        }
                    } else {
                        System.out.println("Se espera id de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        return;
                    }
                } else {
                    errorFlag = true;
                    return;
                }
            } else {
                System.out.println("Se espera definir tipo de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                errorFlag = true;
                return;
            }
        } else {
            System.out.println("Se espera la palabra reservada new. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
            errorFlag = true;
            return;
        }
    }

    private boolean tipos() { // metodo de tipo de dato
        if (p.token == 209 || p.token == 208 || p.token == 213 || p.token == 212) { // token de tipo de variable
            switch (p.token) {
                case 209:
                    tipoDeDato = "Integer";
                    break;
                case 208:
                    tipoDeDato = "Float";
                    break;
                case 213:
                    tipoDeDato = "Boolean";
                    break;
                case 212:
                    tipoDeDato = "String";
                    break;
            }
            return true;
        } else {
            System.out.println("Se espera definir tipo de variable. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
            return false;
        }
    }

    // -----------------------------------------------------bloques o métodos para
    // statement-----------------------------------------------
    private void statement() {
        switch (p.token) {// llega con el token del switch a leer
            case 201:// token de if
                if (Avanzar()) {
                    p = p.sig;
                    if (p.token == 117) {// token de apertura de parentesis
                        if (Avanzar()) {
                            p = p.sig;
                            if (expCond()) {
                                //System.out.println(expresion3);FALTA REVISAR LO DE QUE AGARRA EL ÚLTIMO PARÉNTESIS DE LA EXPCOND 
                                if(errorFlag){
                                    break;
                                }
                                if (p.token == 118) {// token de cerrar parentesis
                                    //aqui se va poner metodo cuadruplosExpCond
                                    cuadruplosExpresionesFinales(expresion4);
                                    //System.out.println(expresion4);
                                    cuadruplosExpresiones(expresion3);
                                    expresion3 = "";
                                    expresion4 = "";
                                    if (Avanzar()) {
                                        p = p.sig;
                                        if (p.token == 119) {// token de apertura de llaves de if
                                            if (Avanzar()) {
                                                p = p.sig;
                                                txtOutput = txtOutput + "if("+resultadoTemporal+") {" + "\n";
                                                while (p.token == 201 || p.token == 204 || p.token == 206
                                                        || p.token == 214
                                                        || p.token == 100) {
                                                    statement();//
                                                    if (errorFlag) {
                                                        break;
                                                    }
                                                }
                                                if (errorFlag) {
                                                    break;
                                                }
                                                if (p.token == 120) {// token de cerradura de llaves del if
                                                    if (Avanzar()) {
                                                        p = p.sig;
                                                        txtOutput = txtOutput + "}" + "\n";
                                                        while (p.token == 201 || p.token == 204 || p.token == 206
                                                                || p.token == 214 || p.token == 100) {
                                                            statement();
                                                            if (errorFlag) {
                                                                break;
                                                            }
                                                        }
                                                        if (errorFlag) {
                                                            break;
                                                        }
                                                        if (p.token == 120) {// token de cerradura de llaves de main
                                                            break;// en teoría sale del programa cuando dsps de los
                                                                  // statements de if terminan en } (aplica en caso de
                                                                  // rec,
                                                                  // )
                                                        } else if (p.token == 202) {// token de else
                                                            if (Avanzar()) {
                                                                p = p.sig;
                                                                if (p.token == 119) {// token de apertura de llave else
                                                                    if (Avanzar()) {
                                                                        p = p.sig;
                                                                        txtOutput = txtOutput + "else {" + "\n";
                                                                        while (p.token == 201 || p.token == 204
                                                                                || p.token == 206 || p.token == 214
                                                                                || p.token == 100) {
                                                                            statement();//
                                                                            if (errorFlag) {
                                                                                break;
                                                                            }
                                                                        }
                                                                        if (errorFlag) {
                                                                            break;
                                                                        }
                                                                        if (p.token == 120) {// token de cerradura de
                                                                                             // llaves
                                                                                             // del else
                                                                            if (Avanzar()) {
                                                                                p = p.sig;
                                                                                txtOutput = txtOutput + "}" + "\n";
                                                                                while (p.token == 201 || p.token == 204
                                                                                        || p.token == 206
                                                                                        || p.token == 214
                                                                                        || p.token == 100) {
                                                                                    statement();
                                                                                    if (errorFlag) {
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                if (errorFlag) {
                                                                                    break;
                                                                                }
                                                                                if (p.token == 120) {// token de
                                                                                                     // cerradura
                                                                                                     // de llaves de
                                                                                                     // main
                                                                                    break;// en teoría sale del programa
                                                                                          // cuando dsps de los
                                                                                          // statements
                                                                                          // de else terminan en }
                                                                                          // (aplica
                                                                                          // en caso de rec, )
                                                                                } else {
                                                                                    System.out.println(
                                                                                            "Se espera un statement válido. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                                    errorFlag = true;
                                                                                    break;
                                                                                }
                                                                            } else {
                                                                                errorFlag = true;
                                                                                System.out.println(
                                                                                        "Se espera statement o cierre de llave final o de ciclo pasado. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            System.out.println(
                                                                                    "Se espera cierre de llave, o error dentro de else. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                            errorFlag = true;
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        System.out.println("Se espera cuerpo de else. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                        errorFlag = true;
                                                                        break;
                                                                    }
                                                                } else {
                                                                    System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                    errorFlag = true;
                                                                    break;
                                                                }
                                                            } else {
                                                                System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                                errorFlag = true;
                                                                break;
                                                            }
                                                        } else {
                                                            System.out.println("Se espera un statement válido. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                            errorFlag = true;
                                                            break;
                                                        }
                                                    } else {
                                                        System.out.println(
                                                                "Se espera statement o else, o llave de ciclo pasado o de programa. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                        errorFlag = true;
                                                        break;
                                                    }
                                                } else {
                                                    System.out.println(
                                                            "Se espera cierre de llave, ó error dentro de if. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                    errorFlag = true;
                                                    break;
                                                }
                                            } else {
                                                System.out.println("Se espera cuerpo de if. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                errorFlag = true;
                                                break;
                                            }

                                        } else {
                                            System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                            errorFlag = true;
                                            break;
                                        }
                                    } else {
                                        System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                        errorFlag = true;
                                        break;
                                    }
                                } else {
                                    System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    break;
                                }
                            } else {
                                System.out.println("Error en expresión condicional. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                errorFlag = true;
                                break;
                            }
                        } else {
                            System.out.println("Se espera expresión condicional. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            break;
                        }
                    } else {
                        System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        break;
                    }
                } else {
                    System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    break;
                }
            case 204:// token de while
                if (Avanzar()) {
                    p = p.sig;
                    if (p.token == 117) {// token de apertura de paréntesis while
                        if (Avanzar()) {
                            p = p.sig;
                            if (expCond()) {
                                if(errorFlag){
                                    break;
                                }
                                if (p.token == 118) {// token de cerradura de parentesis while
                                    cuadruplosExpresionesFinales(expresion4);
                                    cuadruplosExpresiones(expresion3);
                                    expresion3 = "";
                                    expresion4 = "";
                                    if (Avanzar()) {
                                        p = p.sig;
                                        if (p.token == 119) {// token de apertura de llaves while
                                            if (Avanzar()) {
                                                p = p.sig;
                                                txtOutput = txtOutput + "while("+resultadoTemporal+") {" + "\n";
                                                while (p.token == 201 || p.token == 204 || p.token == 206
                                                        || p.token == 214
                                                        || p.token == 100) {
                                                    statement();
                                                    if (errorFlag) {
                                                        break;
                                                    }
                                                }
                                                if (errorFlag) {
                                                    break;
                                                }
                                                if (p.token == 120) {// token de cerradura de llaves del while
                                                    if (Avanzar()) {
                                                        p = p.sig;
                                                        txtOutput = txtOutput + "}" + "\n";
                                                        while (p.token == 201 || p.token == 204 || p.token == 206
                                                                || p.token == 214 || p.token == 100) {
                                                            statement();
                                                            if (errorFlag) {
                                                                break;
                                                            }
                                                        }
                                                        if (errorFlag) {
                                                            break;
                                                        }
                                                        if (p.token == 120) {// token de cerradura de llaves de main
                                                            break;// en teoría sale del programa cuando dsps de los
                                                                  // statements de while terminan en } (aplica en caso
                                                                  // de
                                                                  // rec, )
                                                        } else {
                                                            System.out.println("Se espera un statement válido. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                            errorFlag = true;
                                                            break;
                                                        }
                                                    } else {
                                                        errorFlag = true;
                                                        System.out.println(
                                                                "Se espera statement o cierre de llave final de o de ciclo pasado. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                        break;
                                                    }
                                                } else {
                                                    System.out
                                                            .println(
                                                                    "Se espera cierre de llave, ó error dentro de while. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                    errorFlag = true;
                                                    break;
                                                }
                                            } else {
                                                System.out.println("Se espera cuerpo de while. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                errorFlag = true;
                                                break;
                                            }
                                        } else {
                                            System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                            errorFlag = true;
                                            break;
                                        }
                                    } else {
                                        System.out.println("Se espera apertura de llave. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                        errorFlag = true;
                                        break;
                                    }
                                } else {
                                    System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    break;
                                }
                            } else {
                                System.out.println("Error en expresión condicional. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                errorFlag = true;
                                break;
                            }
                        } else {
                            System.out.println("Se espera expresión condicional. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            break;
                        }
                    } else {
                        System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        break;
                    }
                } else {
                    System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    break;
                }
            case 206:// token de print
                if (Avanzar()) {
                    p = p.sig;
                    if (p.token == 117) {// token de apertura de parentesis
                        if (Avanzar()) {
                            p = p.sig;
                            if (p.token == 100) {// token de id
                                boolean banderaVariables = false;
                                for(nodo elemento : listaVariables){
                                    if (elemento.lexema.equals(p.lexema)) {
                                        banderaVariables = true;
                                        if (!elemento.valor.equals("")) {
                                            listaPrint.add(p.lexema);
                                        }else{
                                            errorFlag = true;
                                            System.out.println(
                                                "La variable ha sido definida, pero no tiene valor. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                            break;
                                        }
                                    }
                                }
                                if (errorFlag) {
                                    break;
                                }
                                if(!banderaVariables){
                                    System.out.println("La variable no ha sido definida. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorEncontradoSemantico= true;
                                    errorFlag = true;
                                    break;
                                } 
                                if (Avanzar()) {
                                    p = p.sig;
                                    if (p.token == 124) {// token de coma
                                        checkIdsPrint();
                                        if (errorFlag) {
                                            break;
                                        }
                                    }
                                    if (p.token == 118) {// token de cerradura de parentesis
                                        if (Avanzar()) {
                                            p = p.sig;
                                            if (p.token == 125) {// token de punto y coma
                                                for(String elemento: listaPrint){
                                                    txtOutput = txtOutput + "print(" + elemento + ")" + "\n";
                                                }
                                                listaPrint.clear();
                                                if (Avanzar()) {
                                                    p = p.sig;
                                                    while (p.token == 201 || p.token == 204 || p.token == 206
                                                            || p.token == 214 || p.token == 100) {
                                                        statement();
                                                        if (errorFlag) {
                                                            break;
                                                        }
                                                    }
                                                    if (errorFlag) {
                                                        break;
                                                    }
                                                    if (p.token == 120) {// token de cerradura de llaves de main
                                                        break;// en teoría sale del programa cuando dsps de los
                                                              // statements de while terminan en } (aplica en caso de
                                                              // rec, )
                                                    } else {
                                                        System.out.println("Se espera un statement válido. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                        errorFlag = true;
                                                        break;
                                                    }
                                                } else {
                                                    errorFlag = true;
                                                    System.out.println(
                                                            "Se espera statement o cierre de llave final o de ciclo pasado. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                    break;
                                                }
                                            } else {
                                                System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                errorFlag = true;
                                                break;
                                            }
                                        } else {
                                            System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                            errorFlag = true;
                                            break;
                                        }
                                    } else if (p.token == 100) {// id
                                        System.out.println("Se espera una coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                        errorFlag = true;
                                        return;
                                    } else {
                                        System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                        errorFlag = true;
                                        break;
                                    }
                                } else {
                                    System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    break;
                                }
                            } else {
                                System.out.println("Se espera identificador. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                errorFlag = true;
                                break;
                            }
                        } else {
                            System.out.println("Se espera identificador. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            break;
                        }
                    } else {
                        System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        break;
                    }
                } else {
                    System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    break;
                }
            case 214:// token de getvalue
                if (Avanzar()) {
                    p = p.sig;
                    if (p.token == 117) {// token de apertura de parentesis
                        if (Avanzar()) {
                            p = p.sig;
                            if (p.token == 118) {// token de cerradura de parentesis
                                if (Avanzar()) {
                                    p = p.sig;
                                    if (p.token == 125) {// token de punto y coma
                                        txtOutput = txtOutput + "getvalue()" + "\n";
                                        if (Avanzar()) {
                                            p = p.sig;
                                            while (p.token == 201 || p.token == 204 || p.token == 206
                                                    || p.token == 214 || p.token == 100) {
                                                statement();
                                                if (errorFlag) {
                                                    break;
                                                }
                                            }
                                            if (errorFlag) {
                                                break;
                                            }
                                            if (p.token == 120) {// token de cerradura de llaves de main
                                                break;// en teoría sale del programa cuando dsps de los
                                                      // statements de while terminan en } (aplica en caso de
                                                      // rec, )
                                            } else {
                                                System.out.println("Se espera un statement válido. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                                errorFlag = true;
                                                break;
                                            }
                                        } else {
                                            errorFlag = true;
                                            System.out.println(
                                                    "Se espera statement o cierre de llave final de o de ciclo pasado. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                            break;
                                        }
                                    } else {
                                        System.out.println("Se espera punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                        errorFlag = true;
                                        break;
                                    }
                                } else {
                                    System.out.println("Se espera punto y coma Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    break;
                                }
                            } else {
                                System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                errorFlag = true;
                                break;
                            }
                        } else {
                            System.out.println("Se espera cierre de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            break;
                        }
                    } else {
                        System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        break;
                    }
                } else {
                    System.out.println("Se espera apertura de paréntesis. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    break;
                }
            case 100:// token de id
                if (Avanzar()) {
                    for (nodo elemento : listaVariables) {
                        if (elemento.lexema.equals(p.lexema)) {
                            variableAsignacion = p.lexema;
                            copiaTipoDato1 = elemento.tipoDato;
                            errorFlag = false;
                            break;
                    
                        } else{
                            errorFlag = true;
                        }
                    }
                    if(errorFlag){
                        System.out.println("La variable no ha sido definida. Renglón: " + p.renglon +" Lexema: "+ p.lexema);
                        errorFlag = true;
                        errorEncontradoSemantico = true;
                        break;
                    }
                    p = p.sig;
                    if (p.token == 123) {// token de "="
                        if (Avanzar()) {
                            p = p.sig;
                            expresion = "";
                            expresion2 = "";
                            expresion3 = "";
                            expresion4 = "";
                            
                            if (expSimple()) {
                                if (p.token == 125) {// token de punto y coma
                                    ///////////////////////////////////////////////////

                                    if (Avanzar()) {
                                        p = p.sig;
                                        while (p.token == 201 || p.token == 204 || p.token == 206
                                                || p.token == 214 || p.token == 100) {
                                            statement();
                                            if (errorFlag) {
                                                break;
                                            }
                                        }
                                        if (errorFlag) {
                                            break;
                                        }
                                        if (p.token == 120) {// token de cerradura de llaves de main
                                            break;// en teoría sale del programa cuando dsps de los
                                                  // statements de while terminan en } (aplica en caso de
                                                  // rec, )
                                        } else {
                                            System.out.println("Se espera un statement válido. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                            errorFlag = true;
                                            break;
                                        }
                                    } else {
                                        errorFlag = true;
                                        System.out.println(
                                                "Se espera statement o cierre de llave final de o de ciclo pasado. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                        break;
                                    }
                                } else {
                                    System.out.println("Se espera un punto y coma. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    break;
                                }
                            } else {
                                
                                if(copiaTipoDato1.equals("String") && (p.token == 104 || p.token == 105 || p.token == 106)){
                                    System.out.println("Error, tipo de dato String no permite el operador " + p.lexema + " Renglón: " + p.renglon);
                                    errorFlag = true;
                                    break;
                                }else if(copiaTipoDato1.equals("Boolean")){
                                    System.out.println("Error, tipo de datos Boolean no permiten el operador " + p.lexema + " Renglón: " + p.renglon);
                                    errorFlag = true;
                                    break;
                                }else{
                                    System.out.println("Se espera una expresión simple. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                    errorFlag = true;
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Se espera una expresión simple. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            errorFlag = true;
                            break;
                        }
                    } else {
                        System.out.println("Se espera un igual. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                        errorFlag = true;
                        break;
                    }
                } else {
                    System.out.println("Se espera un igual. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    break;
                }
            default:
                System.out.println("Se espera un statement válido. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                errorFlag = true;
                break;

        }
    }

    private boolean expCond() {
        if (errorFlag) {
            return false;
        }
        antiLoopExpSimple = 0;
        variableBuleana = true;
        if (expSimple()) {// avanza estando ya en el "opRel"
            copiaCopiaTipodDeDato = copiaTipoDato1;
            if (opRel()) {
                if (Avanzar()) {
                    p = p.sig;
                    variableBuleana = true;
                    if (expSimple()) {// avanza estando ya lo que sigue de la expCond
                        if(copiaCopiaTipodDeDato.equals(copiaTipoDato1)){
                            if(p.token == 118 && p.sig.token == 119){
                                return true;
                            } else if((p.token == 118 && p.sig.token == 118) || (p.token == 118 && p.sig.token == 114) || (p.token == 118 && p.sig.token == 115)){
                                if(Avanzar()){
                                    p = p.sig;
                                    pCounter--;
                                    while((p.token == 118 && p.sig.token == 118) || (p.token == 118 && p.sig.token == 114) || (p.token == 118 && p.sig.token == 115)){
                                        if(p.token == 118 && p.sig.token == 119){
                                            return true;
                                        }
                                        p = p.sig;
                                        pCounter--;
                                    }
                                }
                            }
                            if (p.token == 114 || p.token == 115) {
                                if (Avanzar()) {
                                    p = p.sig;
                                    if(expCond()){
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            }
                            if((pCounter != 0 && p.token != 114) || (pCounter != 0 && p.token != 115)){
                                System.out.println("Falta cerrar algo. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                                System.out.println(pCounter);
                                errorFlag = true;
                                return false;
                            }
                            else {
                                return true;
                            }
                        } else {
                            System.out.println("Error en el tipo de dato. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                            return false;
                        }
                    } else {
                        return false;
                    }
                    } else {
                        return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean expSimple() {
        if (errorFlag) {
            return false;
        }
        antiLoopExpSimple++;
        antiLoopTermino = 0;
        if (antiLoopExpSimple >= 50) {
            return false;
        } else {
            if (signo()) {// manda a llamar signo
                if (termino()) {// manda a llamar termino
                    return true;
                }
            } else if (termino()) {// manda a llamar termino
                return true;
            } else if (expSimple()) {// si no es termino, manda a llamar expresion simple
                if (opAditivo()) {// manda a llamar operador aditivo
                    if (termino()) { // manda a llamar termino
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private boolean termino() {
        if (errorFlag) {
            return false;
        }
        antiLoopTermino++;
        if (antiLoopTermino >= 50) {
            return false;
        }else {
            if (factor()) {// manda a llamar factor
                if(p.token == 125){ ///////////////////SI SE SURRÓ EL ACOMODO DE LA EXPRESIÓN DE LA ASIGNACIÓN ESTE ES EL PEDO
                }// else if(p.token == 118){
                // }
                else {
                    expresion2 = expresion2 + " " + p.lexema;
                    expresion = expresion + " " + p.lexema;
                }
                if(p.token == 118){ 
                } else {
                    expresion3 = expresion3 + " " + p.lexema;
                    expresion4 = expresion4 + " " + p.lexema;
                }

                if(opAditivo()||opMult()){
                    if(Avanzar()){
                        if(termino()){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }else if(p.token == 125){//;
                /////////// metodo operaciones
                    //compiCuadruplo compilador = new compiCuadruplo();
                    //List<cuadruplo> cuadruplos = compilador.generarCuadruplos(expresion);
                    if(copiaTipoDato1.equals("Integer")){
                        compiCuadruploInt compiladorInt = new compiCuadruploInt();
                        for(nodo elemento : listaVariables){
                            if(elemento.lexema.equals(variableAsignacion)){         
                                elemento.valor = String.valueOf(compiladorInt.generarCuadruplos(expresion));
                                pila añadir = new pila(elemento.lexema + ++contador, compiladorInt.generarCuadruplos(expresion));
                                pilaPrint.add(añadir);
                                compiCuadruplo compilador = new compiCuadruplo();
                                List<cuadruplo> cuadruplos = compilador.generarCuadruplos(expresion2);
                                for (cuadruplo cuadruplo : cuadruplos){
                                    if(cuadruplos.size() >= 1 && compilador.banderaCuadruplo == false) {
                                        txtOutput = txtOutput + cuadruplo.operador + ", " + cuadruplo.operando1 + ", " + cuadruplo.operando2 + ", " + cuadruplo.resultado + "\n";
                                    } else {
                                        txtOutput = txtOutput + "=, " + cuadruplos.get(cuadruplos.size() - 1).operando1 + ", , " + variableAsignacion + "\n";
                                    }
                                }
                                if(cuadruplos.size() >= 1 && compilador.banderaCuadruplo == false){
                                    txtOutput = txtOutput + "=, " + cuadruplos.get(cuadruplos.size() - 1).resultado + ", , " + variableAsignacion + "\n";
                                }
                            }
                        }
                    } else if(copiaTipoDato1.equals("Float")){
                        compiCuadruploFloat compiladorFloat = new compiCuadruploFloat();
                        for(nodo elemento : listaVariables){
                            if(elemento.lexema.equals(variableAsignacion)){                           
                                elemento.valor = String.valueOf(compiladorFloat.generarCuadruplos(expresion));
                                compiCuadruplo compilador = new compiCuadruplo();
                                List<cuadruplo> cuadruplos = compilador.generarCuadruplos(expresion2);
                                for (cuadruplo cuadruplo : cuadruplos){
                                    if(cuadruplos.size() >= 1 && compilador.banderaCuadruplo == false) {
                                         txtOutput = txtOutput + cuadruplo.operador + ", " + cuadruplo.operando1 + ", " + cuadruplo.operando2 + ", " + cuadruplo.resultado + "\n";
                                    } else {
                                        txtOutput = txtOutput + "=, " + cuadruplos.get(cuadruplos.size() - 1).operando1 + ", , " + variableAsignacion + "\n";
                                    }
                                }
                                if(cuadruplos.size() >= 1 && compilador.banderaCuadruplo == false){
                                    txtOutput = txtOutput + "=, " + cuadruplos.get(cuadruplos.size() - 1).resultado + ", , " + variableAsignacion + "\n";
                                }
                            }
                        }
                    } else if(copiaTipoDato1.equals("String")){
                        for (nodo elemento : listaVariables) {
                            if (elemento.lexema.equals(variableAsignacion)) {
                                String expresionSinComillas = expresion.replace("\"", "");
                                elemento.valor = expresionSinComillas;
                                String tokens[] = expresionSinComillas.split("");
                                int contadorString = 0;
                                for(String token : tokens){
                                    txtOutput = txtOutput + variableAsignacion + "[" + contadorString + "]='" + token + "'" + "\n";
                                    contadorString++;
                                }
                                txtOutput = txtOutput + variableAsignacion + "[" + contadorString + "]='" + "\0" + "'" + "\n";
                            }
                        }
                    }else if(copiaTipoDato1.equals("Boolean")){
                        for(nodo elemento : listaVariables){
                            if(elemento.lexema.equals(variableAsignacion)){                           
                                elemento.valor = expresion;
                                compiCuadruplo compilador = new compiCuadruplo();
                                List<cuadruplo> cuadruplos = compilador.generarCuadruplos(expresion2);
                                for (cuadruplo cuadruplo : cuadruplos){
                                    if(cuadruplos.size() >= 1 && compilador.banderaCuadruplo == false) {
                                        txtOutput = txtOutput + cuadruplo.operador + ", " + cuadruplo.operando1 + ", " + cuadruplo.operando2 + ", " + cuadruplo.resultado + "\n";
                                    }else{
                                        txtOutput = txtOutput + "=, " + cuadruplos.get(cuadruplos.size() - 1).operando1 + ", , " + variableAsignacion + "\n";
                                    }
                                }
                                if(cuadruplos.size() >= 1 && compilador.banderaCuadruplo == false){
                                    txtOutput = txtOutput + "=, " + cuadruplos.get(cuadruplos.size() - 1).resultado + ", , " + variableAsignacion + "\n";
                                }
                            }
                        }
                    }
                    expresion = "";
                    expresion2 = "";
                    expresion3 = "";
                    expresion4 = "";
                    return true;
                    }if((!opAditivo() || !opMult()) && (!opRel()) && (p.token != 114 && p.token != 115) && p.token != 118){
                        errorFlag = true;
                        return false;
                    } else {
                        return true;
                    }
                    
            } else if (termino()) {// manda a llamar termino
                if (opMult()) { // manda a llamar a operador multiplicativo
                    if (factor()) {// manda a llamar a factor
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private boolean factor() {
        if (errorFlag) {
            return false;
        }
        if (p.token == 100) {// comprueba token id
            if (Avanzar()) {
                // en el id
                boolean banderaVariables = false;
                for (nodo elemento : listaVariables) {
                    if (elemento.lexema.equals(p.lexema)) {
                        if(!elemento.valor.equals("")){
                            if(elemento.tipoDato.equals("String")){
                                expresion2 = expresion2 + p.lexema;
                                expresion = expresion + elemento.valor;
                            } else {
                                expresion2 = expresion2 + " " + p.lexema;
                                expresion = expresion + " " + elemento.valor;
                                expresion3 = expresion3 + " " + p.lexema;
                                expresion4 = expresion4 + " " + elemento.valor;
                            }
                            copiaTipoDato2 = elemento.tipoDato;
                            if(metodo()){
                                banderaVariables = true;
                                break;
                            }else{
                                errorFlag = true;
                                return false;
                            }
                        }
                    }
                }
                if(!banderaVariables){
                    System.out.println("La variable no ha sido definida. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    errorEncontradoSemantico = true;
                    return false;
                }
                if(Avanzar()){
                    p = p.sig;
                    return true;
                }else{
                    errorFlag = true;
                    return false;
                }
                
            } else {
                errorFlag = true;
                return false;
            }
        } else if (p.token == 116) {// comprueba token signo exclamación
            if (Avanzar()) {
                p = p.sig;
                if (p.token == 100) {
                    if (factor()) {// ciclo método factor
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    System.out.println("Se espera identificador. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                    errorFlag = true;
                    return false;
                }
            } else {
                System.out.println("Se espera identificador. Renglón: " + p.renglon +"  Lexema: "+ p.lexema);
                errorFlag = true;
                return false;
            }
        } else if (p.token == 117) { // comprueba token de apertura de parentesis
            expresion2 = expresion2 + " " + p.lexema;
            expresion = expresion + " " + p.lexema;
            expresion3 = expresion3 + " " + p.lexema;
            expresion4 = expresion4 + " " + p.lexema;
            pCounter++;
            if (Avanzar()) {
                p = p.sig;
                if (expSimple()) { // comprueba si el token es una expresion simple
                    if (p.token == 118) { // comprueba token de cierre de parentesis
                        pCounter--;
                        if (Avanzar()) {
                            p = p.sig;
                            return true;
                        } else {
                            errorFlag = true;
                            return false;
                        }
                    }else if(pCounter != 0){
                        return true;
                    }else{
                        errorFlag = true;
                        return false;
                    }
                } else {
                    errorFlag = true;
                    return false;
                }
            } else {
                errorFlag = true;
                return false;
            }
        } else if (p.token == 101 || p.token == 102) {
            expresion2 = expresion2 + " " + p.lexema;
            expresion = expresion + " " + p.lexema;
            expresion3 = expresion3 + " " + p.lexema;
            expresion4 = expresion4 + " " + p.lexema;
            if (Avanzar()) {
                if(metodo()){
                    p = p.sig;
                    return true;
                }else{
                    errorFlag = true;
                    return false;
                }
            } else {
                errorFlag = true;
                return false;
            }
        } else if (p.token == 210 || p.token == 211){
            expresion2 = expresion2 + " " + p.lexema;
            expresion = expresion + " " + p.lexema;
            expresion3 = expresion3 + " " + p.lexema;
            expresion4 = expresion4 + " " + p.lexema;
            if (Avanzar()) {
                if(metodo()){
                    p = p.sig;
                    return true;
                }else{
                    errorFlag = true;
                    return false;
                }
            } else {
                errorFlag = true;
                return false;
            }
        } else if(p.token == 122){
            expresion2 = expresion2 + p.lexema;
            expresion = expresion + p.lexema;
            expresion3 = expresion3 + p.lexema;
            expresion4 = expresion4 + p.lexema;
            if (Avanzar()) {
                if(metodo()){
                    p = p.sig;
                    return true;
                }else{
                    errorFlag = true;
                    return false;
                }
            } else {
                errorFlag = true;
                return false;
            }
        } else {
            errorFlag = true;
            return false;
        }

    }

    private boolean signo() {
        if (p.token == 103 || p.token == 104) {
            if (Avanzar()) {
                p = p.sig;
                return true;
            } else {
                errorFlag = true;
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean opRel() {
        if (p.token == 108 || p.token == 109 || p.token == 110 || p.token == 111 || p.token == 112 || p.token == 113) {
            return true;
        } else {
            return false;
        }
    }

    private boolean opMult() {
        if (p.token == 105 || p.token == 106 /*|| p.token == 114*/) {
            if(copiaTipoDato1.equals("String") || copiaTipoDato1.equals("Boolean")){
                return false;
            }else{
                p = p.sig;
                return true;
            }
        } else {
            // System.out.println("Operador multiplicativo no válido.");
            return false;
        }
    }
    
    private boolean opAditivo() {
        
        if (p.token == 103 || p.token == 104 /*|| p.token == 115*/) {
            if(copiaTipoDato1.equals("String") && p.token == 104){
                return false;
            } else {
                p = p.sig;
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean metodo() {
        if(variableBuleana){
            if(p.token == 100){
                copiaTipoDato1 = copiaTipoDato2;
                variableBuleana = false;
                return true;
            } else if(p.token == 101 || p.token == 102 || p.token == 210 || p.token == 211 || p.token == 122){
                switch(p.token){
                    case 101:
                        CopiaTipoDeDato = "Integer";
                    break;
                    case 102:
                        CopiaTipoDeDato = "Float";
                    break;
                    case 210:
                        CopiaTipoDeDato = "Boolean";
                    break;
                    case 211:
                        CopiaTipoDeDato = "Boolean";
                    break;
                    case 122:
                        CopiaTipoDeDato = "String";
                    break;
                }
                copiaTipoDato1 = CopiaTipoDeDato;
                variableBuleana = false;
                return true;
            }
        } else {
            if(p.token == 100){//caso id
            if(copiaTipoDato1.equals(copiaTipoDato2)){
                return true;
            } else {
                System.out.println("Error en el tipo de dato. " + p.renglon +" Lexema: "+ p.lexema);
                return false;
            }
            }else if(p.token == 101 || p.token == 102 || p.token == 210 || p.token == 211 || p.token == 122){
                switch(p.token){
                    case 101:
                        CopiaTipoDeDato = "Integer";
                    break;
                    case 102:
                        CopiaTipoDeDato = "Float";
                    break;
                    case 210:
                        CopiaTipoDeDato = "Boolean";
                    break;
                    case 211:
                        CopiaTipoDeDato = "Boolean";    
                    break;
                    case 122:
                        CopiaTipoDeDato = "String";
                    break;
                }
                if(copiaTipoDato1.equals(CopiaTipoDeDato)){
                    return true;
                } else {
                    System.out.println("Error en el tipo de dato. Renglón: " + p.renglon +" Lexema: "+ p.lexema);
                    return false;
                }
            }
        }
        return false;
    }

    private void cuadruplosExpresiones(String expresionCondicional){
        compiCuadruploCondicionales compiCondicional = new compiCuadruploCondicionales();
        List<cuadruplo> cuadruplosCondicionales = compiCondicional.generarCuadruplos(expresionCondicional);
        for(cuadruplo cuadruplo: cuadruplosCondicionales){
            txtOutput = txtOutput + cuadruplo.operador + ", " + cuadruplo.operando1 + ", " + cuadruplo.operando2 + ", " + cuadruplo.resultado + "\n";
            resultadoTemporal = cuadruplo.resultado;
        }
    }

    private void cuadruplosExpresionesFinales(String expresionCondicional){
        compiCuadruploCondicionalesResultado compiCondicional = new compiCuadruploCondicionalesResultado();
        colaCondicionales.add(compiCondicional.generarCuadruplos(expresionCondicional));
    }

    public void escribirEnArchivo(String nombreArchivo, String contenido) {

        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write(contenido);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public LinkedList<nodo> obtenerLista() {
        return listaVariables;
    }

    public void imprimirNodos() {
        p = cabeza;
        while (p != null) {
            System.out.println(p.lexema + " " + p.token + " " + p.renglon);
            p = p.sig;
        }
    }
     
}