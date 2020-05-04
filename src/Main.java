public class Main {
    public static void main(String[] args) {

        boolean fairMode = true;
        int productores_cantidad=5;
        int consumideres_cantidad=5;

        Buffer buffer = new Buffer(fairMode);

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
