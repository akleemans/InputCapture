import java.util.Date;

public class Timer {
	private long _startTime;

	public Timer() {
		this.reset();
	}

	public void reset() {
		_startTime = this.timeNow();
	}

	public long timeElapsed() {
		return this.timeNow() - _startTime;
	}

	protected long timeNow() {
		return new Date().getTime();
	}
}