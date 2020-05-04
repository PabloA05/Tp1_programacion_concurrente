public class Main {
    public static void main(String[] args) {

        boolean fairMode = true;
        int productores_cantidad=10;
        int consumideres_cantidad=10;

        Buffer buffer = new Buffer(fairMode);
        //Productores prod=new Productores(buffer);
        //prod.start();

        //Thread[] productores = new Thread[productores_cantidad];
        Productores[] productores=new Productores[productores_cantidad];
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i] = new Productores(buffer);
            productores[i].setName("Productores_"+i);
        }
        Consumidores[] consumidores = new Consumidores[consumideres_cantidad];
        for (int i = 0; i < consumideres_cantidad; i++) {
            consumidores[i] = new Consumidores(buffer);
            consumidores[i].setName("Consumidores_"+i);
        }
        Thread log=new Thread(new Log(buffer,consumidores,consumideres_cantidad),"LOG");
        log.start();
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i].start();
        }
        for (int i = 0; i < consumideres_cantidad; i++) {
            consumidores[i].start();
        }
    }
}
