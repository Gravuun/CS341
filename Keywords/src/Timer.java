public class Timer {
	// This is stored in milliseconds just because the program runs so fast
	private long elapsedTime;
	// This is in milliseconds
	private long startTime;
	
	public Timer() {
		elapsedTime = 0;
		startTime = 0;
	}
	
	public void startTimer() {
		startTime = System.currentTimeMillis();
	}
	
	public void stopTimer() {
		if(startTime == 0) {
			System.out.println("Error: You tried to stop a timer that never started");
			return;
		}
		// Calculate elapsed time and convert to seconds
		elapsedTime += (System.currentTimeMillis() - startTime);
	}
	
	public long getTime() {
		return elapsedTime;
	}
	
	public void resetTimer() {
		elapsedTime = 0;
		startTime = 0;		
	}
}
