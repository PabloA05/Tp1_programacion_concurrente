public class Main {
    public static void main(String[] args) {

        boolean fairMode = true;
        int productores_cantidad=5;
        int consumideres_cantidad=5;

        Buffer buffer = new Buffer(fairMode);

        Thread[] productores = new Thread[productores_cantidad];
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i] = new Thread(new Productores(buffer), "productores_"+ i);
        }
        Thread[] consumidores = new Thread[consumideres_cantidad];
        for (int i = 0; i < consumideres_cantidad; i++) {
            consumidores[i] = new Thread(new Consumidores(buffer),"consumidores_"+i);
        }
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i].start();
        }
        for (int i = 0; i < consumideres_cantidad; i++) {
            consumidores[i].start();
        }
        Thread log=new Thread(new Log(buffer,consumidores,consumideres_cantidad));
        log.start();

    }
}
