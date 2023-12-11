public class compi {
    nodo p;
    public static void main(String[] args) {
        lexico objLexico=new lexico();
        if(objLexico.errorEncontrado){
            System.out.println("error");
        }else{
            if(objLexico.errorFlag){
            }else{
                System.out.println("Programa terminado.");
            }
        } 
    }
}