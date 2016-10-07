
public class Main {

	public static void main(String[] args) {
		BlackboardNoSync<String> board = new BlackboardNoSync<>();
		ReaderThread reader = new ReaderThread(board, "read 1");
		ReaderThread reader2 = new ReaderThread(board, "read 2");

		ProducerThread producer = new ProducerThread(board, "prod 1");

		producer.start();
		reader.start();
		reader2.start();
	}

}
