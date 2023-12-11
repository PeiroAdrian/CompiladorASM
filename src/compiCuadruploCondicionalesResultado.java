import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class compiCuadruploCondicionalesResultado {
    private Map<String, Integer> prioridades = new HashMap<>();
    private static int temporalCount = 0;
    private boolean bandera = true;
    private boolean banderaLogica = true;

    public compiCuadruploCondicionalesResultado() {
        prioridades.put(">", 1);
        prioridades.put("<", 1);
        prioridades.put(">=", 1);
        prioridades.put("<=", 1);
        prioridades.put("==", 1);
        prioridades.put("!=", 1);
        prioridades.put("&&", 1);
        prioridades.put("||", 1);
    }
    
    public boolean generarCuadruplos(String expresion) {
        Stack<String> pilaOperadores = new Stack<>();
        Stack<Integer> pilaOperandos = new Stack<>();
        Stack<Boolean> pilaFinal = new Stack<>();

        String[] tokens = expresion.split(" ");

        for (String token : tokens) {
            if (esOperando(token)) {
                pilaOperandos.push(Integer.parseInt(token));
            } else if (esOperador(token)) {
                if(token.equals("&&") || token.equals("||")){
                    banderaLogica = false;
                    generarCuadruplo(pilaOperadores.pop(), pilaOperandos, pilaFinal);
                }
                pilaOperadores.push(token);
            }
        }

        while (!pilaOperadores.isEmpty()) {
            if(bandera == true) {
                generarCuadruplo(pilaOperadores.pop(), pilaOperandos, pilaFinal);
                bandera = false;
            }
            if(banderaLogica == false) {
                generarCuadruploFinal(pilaOperadores.pop(), pilaFinal);
            }
        }

        return pilaFinal.pop();
    }

    private void generarCuadruplo(String operador, Stack<Integer> operandos, Stack<Boolean> pilaFinal) {
        int operando2 = operandos.pop();
        int operando1 = operandos.pop();
        boolean resultado = true;
        switch(operador){
            case ">":
                if(operando1 > operando2){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
            case "<":
                if(operando1 < operando2){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
            case ">=":
                if(operando1 >= operando2){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
            case "<=":
                if(operando1 <= operando2){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
            case "==":
                if(operando1 == operando2){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
            case "!=":
                if(operando1 != operando2){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
        }
        pilaFinal.push(resultado);
    }

    private void generarCuadruploFinal(String operador, Stack<Boolean> operandos){
        boolean operando2 = operandos.pop();
        boolean operando1 = operandos.pop();
        boolean resultado = true;
        switch(operador){
            case "&&":
                if(operando1 == true && operando2 == true){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
            case "||":
                if(operando1 == true || operando2 == true){
                    resultado = true;
                } else {
                    resultado = false;
                }
                break;
        }
        operandos.push(resultado);
    }
    
    private boolean esOperador(String token) {
        return prioridades.containsKey(token);
    }

    private boolean esOperando(String token) {
    try {
        // Intentar convertir a double (para valores numéricos)
        Double.parseDouble(token);
        return true;
        } catch (NumberFormatException e) {
        // Verificar si es "true" o "false" (para valores booleanos)
        return "true".equalsIgnoreCase(token) || "false".equalsIgnoreCase(token) || token.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
        }
    }

}