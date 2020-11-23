public class CakeStore {
    private CakeFactory cakeFactory;

    public CakeStore(CakeFactory cakeFactory) {
        this.cakeFactory = cakeFactory;
    }

    public Cake onlineOrder(String type) {
        Cake cake = cakeFactory.orderCake(type);

        cake.prepare();
        cake.bake();
        cake.box();

        return cake;
    }
}