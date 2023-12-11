

public class nodo {
    String lexema;
    int token;
    int renglon;
    String tipoDato;
    String valor;
    nodo sig = null;
    
    // String direccionMemoria;
    // String ambito;

    nodo(String lexema, int token, int renglon, String tipoDato, String valor) {
        this.lexema = lexema;
        this.token = token;
        this.renglon = renglon;
        this.tipoDato = tipoDato;
        this.valor = valor;
    }
}