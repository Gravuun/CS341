public class TubaGroup implements Observer {
    public void update(StateChange m) {
        System.out.println("Notification to Tuba: " + m.getMessageContent());
    }
}