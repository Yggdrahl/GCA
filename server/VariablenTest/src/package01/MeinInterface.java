package package01;

public interface MeinInterface {
	
	default int gibMirNeNummer(int n) {
		return n + 100;
	}
	
	boolean test(boolean bool);

}
