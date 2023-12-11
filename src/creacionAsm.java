import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class creacionAsm {
    
    String outputFile = "C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Compilador_LyA2\\src\\output.txt";
    String asmFile = "C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Compilador_LyA2\\src\\codigoASM.asm";
    String variables = ".MODEL SMALL\n.STACK 100H\n\n.DATA\n    espacio db 10,13,'$'\n    temporal DW ?\n";
    String contenido = "";
    String esIgual = "";
    String noEsIgual = "";
    String contenidoCondicional = "";
    int contador = 0, contadorBloques = 0, contadorCondicionales = 0, contadorPila = 0;
    int banderaElse = 0;
    LinkedList<nodo> listaVariables;
    Queue<Boolean> colaCondicionales;
    String valorParaAsm = "";
    Stack<String> historialVariables = new Stack<String>();
    Stack<String> backupHistorialVariables = new Stack<String>();
    Stack<String> llaves = new Stack<String>();
    Stack<String> tipoCondicional = new Stack<String>();
    Stack<pila> pilaPrint = new Stack<pila>();
    Stack<pila> backupPilaPrint = new Stack<pila>();
    Stack<pila> otroPilaPrint = new Stack<pila>();
    
    

    public creacionAsm(LinkedList<nodo> lista, Queue<Boolean> cola, Stack<pila> pila){
        listaVariables = lista;
        colaCondicionales = cola;
        pilaPrint = pila;
        
    
    try (BufferedReader br = new BufferedReader(new FileReader(outputFile))) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(asmFile));
            String line;
            int bandera = 0;
            String concat = "";
            while ((line = br.readLine()) != null) {
                String[] linea = line.split(" ");
                //System.out.println(linea[0]);
                if(linea[0].equals("int") || linea[0].equals("float") || linea[0].equals("string") || linea[0].equals("bool")){
                    declaracionVariablesAsm(linea);
                    
                }else{
                    if(bandera == 0){
                        bandera++;
                        contenido = contenido +".CODE\n\nasigString MACRO string1, string2\n"+
                        "   lea di,string1\n   mov si,offset string2\n   mov cx,64\n   rep movsb\nENDM";
                        contenido = contenido +"\n\nsuma MACRO operando1, operando2\n"+
                        "   MOV AX, operando1\n   ADD AX, operando2\n   push ax\nENDM";
                        contenido = contenido +"\n\nprintString MACRO variable\n"+
                        "   mov dx, offset espacio\n   mov ah, 09h\n   int 21h\n\n" +
                        "   mov dx, offset variable\n   mov ah, 09h\n   int 21h\nENDM";
                        contenido = contenido +"\n\nresta MACRO operando1, operando2\n"+
                        "   MOV AX, operando1\n   SUB AX, operando2\n   push ax\nENDM";
                        contenido = contenido +"\n\nmultiplicacion MACRO operando1, operando2\n"+
                        "   MOV AX, operando1\n   MOV BX, operando2\n   IMUL BX\n   PUSH AX\nENDM";
                        contenido = contenido +"\n\ndivision MACRO operando1, operando2\n"+
                        "   MOV AX, operando1\n   MOV BX, operando2\n   MOV DX, 0\n   IDIV BX\n   PUSH AX\nENDM";
                        contenido = contenido +"\n\nasigNum MACRO operando1, resultado\n"+
                        "   MOV AX, operando1\n   MOV resultado, AX\nENDM";
                        contenido = contenido + "\n\noperacionAnd MACRO variable1, variable2\n" +
                        "   MOV AX, variable1\n   MOV BX, variable2\n   AND AX, BX\n   PUSH AX\nENDM";
                        contenido = contenido + "\n\noperacionOr MACRO variable1, variable2\n" +
                        "   MOV AX, variable1\n   MOV BX, variable2\n   OR AX, BX\n   PUSH AX\nENDM";
                        contenido = contenido +"\n\nasigBool MACRO operando1, resultado\n"+
                        "   MOV AL, operando1\n   MOV resultado, AL\nENDM\n\n    main PROC\n        mov ax,@data\n"+
                        "        mov ds,ax\n        mov es,ax\n\n        ";
                    }
                    
                    if (bandera>0) {
                        String primerosDosCaracteres = "";
                        if (linea[0].length() >= 2){
                            primerosDosCaracteres = linea[0].substring(0, 2);
                        } else {
                            primerosDosCaracteres = linea[0].substring(0, 1);
                        }
                        if(primerosDosCaracteres.equals("=,")){
                            String[] lineaCuadruplos = line.split(",");
                            if(lineaCuadruplos[1].contains("true")){
                                contenido = contenido + "asigBool 1,"+lineaCuadruplos[3]+"\n        ";
                            }else if(lineaCuadruplos[1].contains("false")){
                                contenido = contenido + "asigBool 0,"+lineaCuadruplos[3]+"\n        ";
                            }else if(lineaCuadruplos[1].contains("temp")){
                                contador++;
                                contadorPila++;
                                while(!pilaPrint.isEmpty() && (pilaPrint.size() != contadorPila)){
                                    //System.out.println(pilaPrint.size());
                                    backupPilaPrint.push(pilaPrint.pop());
                                }
                                otroPilaPrint.push(pilaPrint.peek());
                                variables = variables +"    "+ pilaPrint.peek().lexema + " DB '" +pilaPrint.peek().valor+"', 0Dh, 0Ah, '$'" + "\n";
                                while(!backupPilaPrint.isEmpty()){
                                    pilaPrint.push(backupPilaPrint.pop());
                                }

                                contenido = contenido + "POP AX"+"\n        ";
                                contenido = contenido + "asigNum AX,"+lineaCuadruplos[3]+"\n        ";
                            }else if(lineaCuadruplos[1].contains(".")){
                                //JIJIJIJA
                            }else{
                                contador++;
                                contadorPila++;
                                while(!pilaPrint.isEmpty() && (pilaPrint.size() != contadorPila)){
                                    //System.out.println(pilaPrint.size());
                                    backupPilaPrint.push(pilaPrint.pop());
                                }
                                otroPilaPrint.push(pilaPrint.peek());
                                variables = variables +"    "+ pilaPrint.peek().lexema + " DB '" +pilaPrint.peek().valor+"', 0Dh, 0Ah, '$'" + "\n";
                                while(!backupPilaPrint.isEmpty()){
                                    pilaPrint.push(backupPilaPrint.pop());
                                }
                                contenido = contenido + "asigNum"+lineaCuadruplos[1]+","+lineaCuadruplos[3]+"\n        ";
                            }
                        } else if(primerosDosCaracteres.equals("+,")){ 
                            String[] lineaCuadruplos = line.split(",");
                            if (lineaCuadruplos[1].contains("temp")) {
                                if(lineaCuadruplos[2].contains("temp")){
                                    contenido = contenido + "POP BX"+"\n        ";
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "suma ax, bx\n        ";
                                }else{
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "suma ax,"+lineaCuadruplos[2]+"\n        ";
                                }
                            }else if(lineaCuadruplos[2].contains("temp")){
                                contenido = contenido + "POP AX"+"\n        ";
                                contenido = contenido + "suma"+lineaCuadruplos[1]+", ax\n        ";
                            } else {
                                contenido = contenido + "suma"+lineaCuadruplos[1]+","+lineaCuadruplos[2]+"\n        ";
                            }
                            
                        } else if(primerosDosCaracteres.equals("-,")){ 
                            String[] lineaCuadruplos = line.split(",");
                            if (lineaCuadruplos[1].contains("temp")) {
                                if(lineaCuadruplos[2].contains("temp")){
                                    contenido = contenido + "POP BX"+"\n        ";
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "resta ax, bx\n        ";
                                }else{
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "resta ax,"+lineaCuadruplos[2]+"\n        ";
                                }
                            }else if(lineaCuadruplos[2].contains("temp")){
                                contenido = contenido + "POP AX"+"\n        ";
                                contenido = contenido + "resta"+lineaCuadruplos[1]+", ax\n        ";
                            } else {
                                contenido = contenido + "resta"+lineaCuadruplos[1]+","+lineaCuadruplos[2]+"\n        ";
                            }
                            
                        } else if(primerosDosCaracteres.equals("*,")){ 
                            String[] lineaCuadruplos = line.split(",");
                            if (lineaCuadruplos[1].contains("temp")) {
                                if(lineaCuadruplos[2].contains("temp")){
                                    contenido = contenido + "POP BX"+"\n        ";
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "multiplicacion ax, bx\n        ";
                                }else{
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "multiplicacion ax,"+lineaCuadruplos[2]+"\n        ";
                                }
                            }else if(lineaCuadruplos[2].contains("temp")){
                                contenido = contenido + "POP AX"+"\n        ";
                                contenido = contenido + "multiplicacion"+lineaCuadruplos[1]+", ax\n        ";
                            } else {
                                contenido = contenido + "multiplicacion"+lineaCuadruplos[1]+","+lineaCuadruplos[2]+"\n        ";
                            }
                            
                        }else if(primerosDosCaracteres.equals("/,")){ 
                            String[] lineaCuadruplos = line.split(",");
                            if (lineaCuadruplos[1].contains("temp")) {
                                if(lineaCuadruplos[2].contains("temp")){
                                    contenido = contenido + "POP BX"+"\n        ";
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "division ax, bx\n        ";
                                }else{
                                    contenido = contenido + "POP AX"+"\n        ";
                                    contenido = contenido + "division ax,"+lineaCuadruplos[2]+"\n        ";
                                }
                            }else if(lineaCuadruplos[2].contains("temp")){
                                contenido = contenido + "POP AX"+"\n        ";
                                contenido = contenido + "division"+lineaCuadruplos[1]+", ax\n        ";
                            } else {
                                contenido = contenido + "division"+lineaCuadruplos[1]+","+lineaCuadruplos[2]+"\n        ";
                            }
                        } else if(primerosDosCaracteres.equals("pr")){
                            String[] lineaCuadruplos = line.split(",");
                            String cadena = "";
                            cadena =  lineaCuadruplos[0];
                            int indiceInicio = cadena.indexOf('(');
                            int indiceFin = cadena.indexOf(')', indiceInicio);

                            if (indiceInicio != -1 && indiceFin != -1) {
                                String contenidoEntreParentesis = cadena.substring(indiceInicio + 1, indiceFin);
                                //System.out.println("Contenido dentro de paréntesis: " + contenidoEntreParentesis);
                                for(nodo elemento : listaVariables){
                                    if(elemento.lexema.equals(contenidoEntreParentesis)){
                                        if(elemento.tipoDato.equals("String") || elemento.tipoDato.equals("Float")){
                                            while(!historialVariables.peek().contains(elemento.lexema)){
                                                backupHistorialVariables.push(historialVariables.pop());
                                            }
                                            contenido = contenido + "printString " + historialVariables.pop() + "\n        ";
                                            while(!backupHistorialVariables.empty()){
                                                historialVariables.push(backupHistorialVariables.pop());
                                            }
                                        } else if(elemento.tipoDato.equals("Boolean")){
                                            contenido = contenido + "MOV AL, " + contenidoEntreParentesis + "\n        ";
                                            contenido = contenido + "CALL MostrarNumero\n        ";
                                        } else {
                                            while(!otroPilaPrint.peek().lexema.contains(elemento.lexema)){
                                                backupPilaPrint.push(otroPilaPrint.pop());
                                            }
                                            contenido = contenido + "printString " + otroPilaPrint.peek().lexema + "\n        ";
                                            while(!backupPilaPrint.empty()){
                                                otroPilaPrint.push(backupPilaPrint.pop());
                                            }
                                        }
                                    }
                                }
                            } else {
                                System.out.println("No se encontraron paréntesis o el formato es incorrecto.");
                            }
                        } else if(linea[0].contains("[")){
                            String[] lineaCuadruplos = line.split(",");
                            String cadena = "";
                            cadena =  lineaCuadruplos[0];
                            int indiceInicio = cadena.indexOf("'");
                            int indiceFin = cadena.indexOf("'", indiceInicio + 1);

                            if (indiceInicio != -1 && indiceFin != -1) {
                                String contenidoEntreComillas = cadena.substring(indiceInicio + 1, indiceFin);
                                if (contenidoEntreComillas.equals("\0")) {
                                    char letra = lineaCuadruplos[0].charAt(0);
                                    contador++;
                                    variables = variables +"    "+ letra + contador + " DB '" +concat+"', 0Dh, 0Ah, '$'" + "\n";
                                    historialVariables.push(Character.toString(letra) + Integer.toString(contador));
                                    concat = "";
                                } else {
                                    concat = concat + contenidoEntreComillas;
                                }
                            } else {
                                System.out.println("No se encontraron comillas simples o el formato es incorrecto.");
                            }      
                        } else if(primerosDosCaracteres.equals(">,")){
                            String[] lineaCuadruplos = line.split(",");
                            contadorCondicionales++;
                            contenido = contenido + "MOV AX," + lineaCuadruplos[1] + "\n   MOV BX," + lineaCuadruplos[2] + "\n   CMP AX, BX\n   JG mayor"+contadorCondicionales+"\n   PUSH 0\n   JMP finComparacion"+contadorCondicionales+"\nmayor"+contadorCondicionales+":\n    PUSH 1\nfinComparacion"+contadorCondicionales+":\n";
                            contadorCondicionales++;
                            contenidoCondicional = contenidoCondicional + "MOV AX," + lineaCuadruplos[1] + "\n   MOV BX," + lineaCuadruplos[2] + "\n   CMP AX, BX\n   JG mayor"+contadorCondicionales+"\n   PUSH 0\n   JMP finComparacion"+contadorCondicionales+"\nmayor"+contadorCondicionales+":\n    PUSH 1\nfinComparacion"+contadorCondicionales+":\n";
                            } else if(primerosDosCaracteres.equals("<,")){
                            String[] lineaCuadruplos = line.split(",");
                            contadorCondicionales++;
                            contenido = contenido + "\n\nMOV AX," + lineaCuadruplos[1] +"\n   MOV BX," + lineaCuadruplos[2] +"\n   CMP AX, BX\n   JL menor" + contadorCondicionales +"\n   PUSH 0\n   JMP finComparacion" + contadorCondicionales +"\nmenor" + contadorCondicionales +":\n    PUSH 1\nfinComparacion" + contadorCondicionales +":\n";
                            contadorCondicionales++;
                            contenidoCondicional = contenidoCondicional + "\n\nMOV AX," + lineaCuadruplos[1] +"\n   MOV BX," + lineaCuadruplos[2] +"\n   CMP AX, BX\n   JL menor" + contadorCondicionales +"\n   PUSH 0\n   JMP finComparacion" + contadorCondicionales +"\nmenor" + contadorCondicionales +":\n    PUSH 1\nfinComparacion" + contadorCondicionales +":\n";
                        } else if(primerosDosCaracteres.equals(">=")){
                            contadorCondicionales++;
                            String[] lineaCuadruplos = line.split(",");
                            contenido = contenido + "\n\nMOV AX," + lineaCuadruplos[1] +"\n   MOV BX," + lineaCuadruplos[2] +"\n   CMP AX, BX\n   JGE mayorIgual" + contadorCondicionales +"\n   PUSH 0\n   JMP finComparacion" + contadorCondicionales +"\nmayorIgual" + contadorCondicionales +":\n    PUSH 1\nfinComparacion" + contadorCondicionales +":\n";
                            contadorCondicionales++;
                            contenidoCondicional = contenidoCondicional + "\n\nMOV AX," + lineaCuadruplos[1] +"\n   MOV BX," + lineaCuadruplos[2] +"\n   CMP AX, BX\n   JGE mayorIgual" + contadorCondicionales +"\n   PUSH 0\n   JMP finComparacion" + contadorCondicionales +"\nmayorIgual" + contadorCondicionales +":\n    PUSH 1\nfinComparacion" + contadorCondicionales +":\n";
                        } else if(primerosDosCaracteres.equals("<=")){
                            contadorCondicionales++;
                            String[] lineaCuadruplos = line.split(",");
                            contenido = contenido + "MOV AX," + lineaCuadruplos[1] + "\n   MOV BX," + lineaCuadruplos[2] + "\n   CMP AX, BX\n   JLE menorIgual"+contadorCondicionales+"\n   PUSH 0\n   JMP finComparacion"+contadorCondicionales+"\nmenorIgual"+contadorCondicionales+":\n    PUSH 1\nfinComparacion"+contadorCondicionales+":\n";
                            contadorCondicionales++;
                            contenidoCondicional = contenidoCondicional + "MOV AX," + lineaCuadruplos[1] + "\n   MOV BX," + lineaCuadruplos[2] + "\n   CMP AX, BX\n   JLE menorIgual"+contadorCondicionales+"\n   PUSH 0\n   JMP finComparacion"+contadorCondicionales+"\nmenorIgual"+contadorCondicionales+":\n    PUSH 1\nfinComparacion"+contadorCondicionales+":\n";
                        } else if(primerosDosCaracteres.equals("==")){
                            contadorCondicionales++;
                            String[] lineaCuadruplos = line.split(",");
                            contenido = contenido + "\n\nMOV AX," + lineaCuadruplos[1] +"\n   MOV BX," + lineaCuadruplos[2] +"\n   CMP AX, BX\n   JE igual" + contadorCondicionales +"\n   PUSH 0\n   JMP finComparacion" + contadorCondicionales +"\nigual" + contadorCondicionales +":\n    PUSH 1\nfinComparacion" + contadorCondicionales +":\n";
                            contadorCondicionales++;
                            contenidoCondicional = contenidoCondicional + "\n\nMOV AX," + lineaCuadruplos[1] +"\n   MOV BX," + lineaCuadruplos[2] +"\n   CMP AX, BX\n   JE igual" + contadorCondicionales +"\n   PUSH 0\n   JMP finComparacion" + contadorCondicionales +"\nigual" + contadorCondicionales +":\n    PUSH 1\nfinComparacion" + contadorCondicionales +":\n";
                        } else if(primerosDosCaracteres.equals("!=")){
                            contadorCondicionales++;
                            String[] lineaCuadruplos = line.split(",");
                            contenido = contenido + "MOV AX," + lineaCuadruplos[1] + "\n   MOV BX," + lineaCuadruplos[2] + "\n   CMP AX, BX\n   JNE noIgual"+contadorCondicionales+"\n   PUSH 0\n   JMP finComparacion"+contadorCondicionales+"\nnoIgual"+contadorCondicionales+":\n    PUSH 1\nfinComparacion"+contadorCondicionales+":\n";
                            contadorCondicionales++;
                            contenidoCondicional = contenidoCondicional + "MOV AX," + lineaCuadruplos[1] + "\n   MOV BX," + lineaCuadruplos[2] + "\n   CMP AX, BX\n   JNE noIgual"+contadorCondicionales+"\n   PUSH 0\n   JMP finComparacion"+contadorCondicionales+"\nnoIgual"+contadorCondicionales+":\n    PUSH 1\nfinComparacion"+contadorCondicionales+":\n";
                        } else if(primerosDosCaracteres.equals("&&")){
                            contenido = contenido + "POP AX\n        POP BX\n        operacionAnd AX, BX" + "\n        ";
                            contenidoCondicional = contenidoCondicional + "POP AX\n        POP BX\n        operacionAnd AX, BX" + "\n        ";
                        } else if(primerosDosCaracteres.equals("||")){
                            contenido = contenido + "POP AX\n        POP BX\n        operacionOr AX, BX" + "\n        ";
                            contenidoCondicional = contenidoCondicional + "POP AX\n        POP BX\n        operacionOr AX, BX" + "\n        ";
                        } else if(primerosDosCaracteres.equals("if")){
                            tipoCondicional.push("if");
                            llaves.push("{");
                            contadorBloques++;
                            contenido = contenido + "POP AX\n        CMP AX, 1\n        JE esIgual"+contadorBloques+"\n        JMP noEsIgual"+contadorBloques+"\n\n        esIgual"+contadorBloques+":\n        ";
                        }else if (primerosDosCaracteres.equals("wh")) {
                            tipoCondicional.push("while");
                            contenido = contenido + "POP AX\n        CMP AX, 1\n        JE loop"+contadorBloques+"\n        JMP noLoop"+contadorBloques+"\n\n        loop"+contadorBloques+":\n        ";
                        } else if(linea[0].contains("}")){
                            System.out.println(!tipoCondicional.isEmpty());
                            System.out.println(tipoCondicional.peek().equals("while"));
                            String valor = tipoCondicional.pop();
                            if(valor.equals("if")){
                                contenido = contenido + "noEsIgual"+contadorBloques+":\n        ";
                                contadorBloques--;
                                contenidoCondicional = "";
                            } else if(valor.equals("while")){
                                contenido = contenido + contenidoCondicional;
                                contenido = contenido + "POP AX\n        CMP AX, 1\n        JE loop"+contadorBloques+"\n        ";
                                contenido = contenido + "noLoop"+contadorBloques+":\n        ";
                                contadorBloques--;
                                contenidoCondicional = "";
                            } else if(valor.equals("else")){

                            }
                        } if(primerosDosCaracteres.equals("el")){
                            contadorBloques++;
                            tipoCondicional.push("else");
                        }
                    }
                    
                    
                }
                
                
                //System.out.println(line); // Imprime cada línea leída del archivo
                // Aquí puedes realizar cualquier procesamiento adicional con la línea leída
                
            }
            System.out.println(concat);
            contenido = contenido + ".exit\n    main endp\n\n    MostrarNumero PROC\n        MOV CX, 0\n        MOV BX, 10"+
            "\n\n        L1:\n            MOV DX, 0\n            DIV BX\n            PUSH DX\n            INC CX\n"+
            "            CMP AX, 0\n            JNZ L1\n\n        L2:\n            POP DX\n            ADD DL, 30H\n"+
            "            MOV AH, 02H\n            INT 21H\n            LOOP L2\n\n        RET\n    MostrarNumero ENDP\nEND main";
            variables = variables + contenido;
            bw.write(variables);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void declaracionVariablesAsm(String[] linea){
        switch(linea[0]){
                    case "int":
                        variables = variables+ "    "+ linea[1] + " DW ?" +"\n";
                        break;
                    case "float":
                        variables = variables +"    "+ linea[1] + " DB ?" + "\n";
                        break;
                    case "string":
                        variables = variables +"    "+ linea[1] + " DB '$'" + "\n";
                        break;
                    case "bool":
                        variables = variables +"    "+ linea[1] + " DB ?"+"\n";
                        break;
            }
    }
}
