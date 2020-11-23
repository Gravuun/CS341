public class TromboneGroup implements Observer {
    public void update(StateChange m) {
        System.out.println("Notification to Trombones: " + m.getMessageContent());
    }
}