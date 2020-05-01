public class Main {
    public static void main(String[] args) {

        //Se le da un valor al fairMode
        boolean fairMode = true;
        //Cantidad de threads productores que vamos a crear
        int productores_cantidad=3;
        //Cantidad de threads consumidores que vamos a crear
        int consumidores_cantidad=2;

        //Se crea el buffer
        Buffer buffer = new Buffer(fairMode);

        //Vector que va a contener a los threads productores
        Thread[] productores = new Thread[productores_cantidad];
        //Se crean los threads de productores
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i] = new Thread(new Productores(buffer), "Productores_"+ i);
        }

        //Vector que va a contener a los threads consumidores
        Thread[] consumidores = new Thread[consumidores_cantidad];
        //Se crean los threads de consumidores
        for (int i = 0; i < consumidores_cantidad; i++) {
            consumidores[i] = new Thread(new Consumidores(buffer),"Consumidores_"+i);
        }

        //Se crea un thread Log
        Thread log=new Thread(new Log(buffer,consumidores,consumidores_cantidad),"LOG");

        //Se corren todos los threads creados anteriormente
        log.start();
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i].start();
        }
        for (int i = 0; i < consumidores_cantidad; i++) {
            consumidores[i].start();
        }
    }
}
