public class CakeFactory {
    public Cake orderCake(String type){
        if(type.equals("Vanilla")) {
            return new VanillaCake();
        }
        if(type.equals("Chocolate")) {
            return new ChocolateCake();
        }
        else {
            return new LemonCake();
        }
    }
}