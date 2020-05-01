public class Consumidores implements Runnable {
    private Buffer buffer;//Se crea una variable de tipo Buffer

    //Inicializa el objeto Consumidores
    public Consumidores(Buffer buffer) {
        this.buffer = buffer;//Se almacena el buffer en la variable
    }

    //Sobreescribe el metodo run
    @Override
    public void run() {
        //Mientras la cantidad de operaciones sea menor a 1000 ejecuta el codigo
        while ((buffer.num < 1000)) {
                buffer.consume();//Ejecuta consume del buffer
        }
        System.out.println(Thread.currentThread().getName() + " TERMINO");//Imprime por pantalla cuando el hilo se finaliza
    }
}