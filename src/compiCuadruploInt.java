import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class compiCuadruploInt {
    private Map<String, Integer> prioridades = new HashMap<>();
    private int temporalCount = 0;

    public compiCuadruploInt() {
        prioridades.put("+", 1);
        prioridades.put("-", 1);
        prioridades.put("*", 2);
        prioridades.put("/", 2);
    }
    
    public int generarCuadruplos(String expresion) {
        List<cuadruploInt> cuadruplos = new ArrayList<>();
        Stack<String> pilaOperadores = new Stack<>();
        Stack<Integer> pilaOperandos = new Stack<>();

        String[] tokens = expresion.split(" ");

        for (String token : tokens) {
            if(esOperando(token)) {
                pilaOperandos.push(Integer.parseInt(token));
            }else if (token.equals("(")) {
                pilaOperadores.push(token);
            }else if (token.equals(")")) {
                while (!pilaOperadores.peek().equals("(")) {
                    generarCuadruplo(pilaOperadores.pop(), pilaOperandos, cuadruplos);
                }
                pilaOperadores.pop(); // Remover el "(" de la pila de operadores
            }else if (esOperador(token)) {
                while (!pilaOperadores.isEmpty() && !pilaOperadores.peek().equals("(") && prioridades.get(token) <= prioridades.get(pilaOperadores.peek())) {
                    generarCuadruplo(pilaOperadores.pop(), pilaOperandos, cuadruplos);
                }
                pilaOperadores.push(token);
            }
        }

        while (!pilaOperadores.isEmpty()) {
            generarCuadruplo(pilaOperadores.pop(), pilaOperandos, cuadruplos);
        }

        return pilaOperandos.pop();
    }

    private void generarCuadruplo(String operador, Stack<Integer> operandos, List<cuadruploInt> cuadruplos) {
        int operando2 = operandos.pop();
        int operando1 = operandos.pop();
        int resultado = 0;
        switch(operador){
            case "+":
                resultado = operando1 + operando2;
                break;
            case "-":
                resultado = operando1 - operando2;
                break;
            case "/":
                resultado = operando1 / operando2;
                break;
            case "*":
                resultado = operando1 * operando2;
                break;
        }
        cuadruplos.add(new cuadruploInt(operador, operando1, operando2, resultado));
        operandos.push(resultado);
    }

    private boolean esOperador(String token) {
        return prioridades.containsKey(token);
    }

    private boolean esOperando(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
