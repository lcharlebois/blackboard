import java.util.ArrayList;

public class Buffer {

	ArrayList<String> oBuffer;
	private int readIndex = 0;

	public Buffer() {
		oBuffer = new ArrayList<>();
	}

	public synchronized void add(String value) {
		oBuffer.add(value);
	}

	public synchronized String read() {
		while (readIndex >= oBuffer.size()) {
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String msg = oBuffer.get(readIndex);
		readIndex++;
		return msg;
	}

	public synchronized boolean isEmpty() {
		return oBuffer.isEmpty();
	}
}
