/** Class which can be used to count how many keys was pressed in one second */
class Counter {
	private int second;
	private int count = 0;
	Counter(int second) { this.second = second; }
	int getSecond() { return this.second; }
	void increment() { this.count++; }
	int getResult() { return this.count; }
}