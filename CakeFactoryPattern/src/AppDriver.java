public class AppDriver {
    public static void main(String[] args) {
        CakeFactory cf = new CakeFactory();
        CakeStore store = new CakeStore(cf);

        Cake cake1 = store.onlineOrder("Lemon");
        Cake cake2 = store.onlineOrder("Chocolate");
        Cake cake3 = store.onlineOrder("Vanilla");

        System.out.println("Completed order: " + cake1.getName());
        System.out.println("Completed order: " + cake2.getName());
        System.out.println("Completed order: " + cake3.getName());
    }
}