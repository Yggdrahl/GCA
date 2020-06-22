package package01;

import package01.MeinInterface;

public class TestMain {

//	public static void main(String[] args) {
//		
//		var test = "fdgdfg";
//		String test2 = String.valueOf(test);
//		System.out.println(test2);
//
//	}
	
	private final MeinInterface meinInterface;
	
	public TestMain(MeinInterface meinInterface) {
		this.meinInterface = meinInterface;
	}
	
	public int insert(int n) {
		return meinInterface.gibMirNeNummer(n);
	}
	
	
	
	
	

}
