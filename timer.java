

public class timer {
	
	private static Long[] startTime = new Long[10];
	
	

	public static void start(int i){
		startTime[i]=System.currentTimeMillis();
	}
	
	public static long end(int i){
		return System.currentTimeMillis()-startTime[i];
	}
}
