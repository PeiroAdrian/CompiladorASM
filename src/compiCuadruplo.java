import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class compiCuadruplo {
    private Map<String, Integer> prioridades = new HashMap<>();
    public static int temporalCount = 0;
    public boolean banderaCuadruplo = false;

    public compiCuadruplo() {
        prioridades.put("+", 1);
        prioridades.put("-", 1);
        prioridades.put("*", 2);
        prioridades.put("/", 2);
    }
    
    public List<cuadruplo> generarCuadruplos(String expresion) {
        List<cuadruplo> cuadruplos = new ArrayList<>();
        Stack<String> pilaOperadores = new Stack<>();
        Stack<String> pilaOperandos = new Stack<>();

        String[] tokens = expresion.split(" ");

        if(tokens.length == 2){
            pilaOperandos.push(tokens[1]);
            pilaOperandos.push(tokens[1]);
            banderaCuadruplo = true;
            generarCuadruplo("=", pilaOperandos, cuadruplos);
            return cuadruplos;
        }

        for (String token : tokens) {
            if (esOperando(token)) {
                pilaOperandos.push(token);
            } else if (token.equals("(")) {
                pilaOperadores.push(token);
            } else if (token.equals(")")) {
                while (!pilaOperadores.peek().equals("(")) {
                    generarCuadruplo(pilaOperadores.pop(), pilaOperandos, cuadruplos);
                }
                pilaOperadores.pop(); // Remover el "(" de la pila de operadores
            } else if (esOperador(token)) {
                while (!pilaOperadores.isEmpty() && !pilaOperadores.peek().equals("(") && prioridades.get(token) <= prioridades.get(pilaOperadores.peek())) {
                    generarCuadruplo(pilaOperadores.pop(), pilaOperandos, cuadruplos);
                }
                pilaOperadores.push(token);
            }
        }

        while (!pilaOperadores.isEmpty()) {
            generarCuadruplo(pilaOperadores.pop(), pilaOperandos, cuadruplos);
        }

        return cuadruplos;
    }

    private void generarCuadruplo(String operador, Stack<String> operandos, List<cuadruplo> cuadruplos) {
        String operando2 = operandos.pop();
        String operando1 = operandos.pop();
        String resultado;
        if(banderaCuadruplo == true){
            resultado = "temp0";
        } else {
            resultado = obtenerNuevoTemporal();
        }
        cuadruplos.add(new cuadruplo(operador, operando1, operando2, resultado));
        operandos.push(resultado);
    }

    private String obtenerNuevoTemporal() {
        return "temp" + (++temporalCount);
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