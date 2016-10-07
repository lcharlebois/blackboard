
public class ProducerThread extends Thread {

	BlackboardNoSync<String> board;
	int count = 0;
	String id = "";

	public ProducerThread(BlackboardNoSync<String> board, String id) {
		this.board = board;
		this.id = id;
	}

	@Override
	public void run() {

		while(true) {
			count++;
			try {
				System.out.println(id + " posts: " + count);
				board.post(String.valueOf(count));
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
