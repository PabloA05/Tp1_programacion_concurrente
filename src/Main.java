public class Main {
    public static void main(String[] args) {

        boolean fairMode = false;
        int productores_cantidad=3;
        int consumideres_cantidad=2;

        Buffer buffer = new Buffer(fairMode);

        Thread[] productores = new Thread[productores_cantidad];
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i] = new Thread(new Productores(buffer), "Productores_"+ i);
        }
        Thread[] consumidores = new Thread[consumideres_cantidad];
        for (int i = 0; i < consumideres_cantidad; i++) {
            consumidores[i] = new Thread(new Consumidores(buffer),"Consumidores_"+i);
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
