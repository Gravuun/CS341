
public class MyApp {

	public static void main(String[] args) {
		TromboneGroup trombones = new TromboneGroup();
        TrumpetGroup trumpets = new TrumpetGroup();
        TubaGroup tubas = new TubaGroup();

        MusicConductor mc = new MusicConductor();

        mc.attach(trombones);
        mc.attach(trumpets);

        mc.notifyUpdate(new StateChange("Appassionato"));

        mc.detach(trombones);
        mc.attach(tubas);

        mc.notifyUpdate(new StateChange("Crescendo"));

	}

}
