
public class ReaderThread extends Thread {

	BlackboardNoSync<String> board;
	String id = "";
	public ReaderThread(BlackboardNoSync<String> board, String id) {
		this.board = board;
		this.id = id;
		board.register(this);
	}

	@Override
	public void run() {
		while(true) {
			try {
				String msg = board.read(this);
				if  (msg != null) {
					System.out.println(id + " reads: " + msg);
				}
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
