
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
			String msg;
			try {
				msg = board.read(this);
				while (msg != null) {
					System.out.println(id + " reads: " + msg);
					msg = board.read(this);
				}
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
