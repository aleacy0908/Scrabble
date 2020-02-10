import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestsExecutor {
	
	public static void main(String[] args)
	{
		
		Result resultsPool = JUnitCore.runClasses(PoolTest.class);
		Result resultsPlayer = JUnitCore.runClasses(PoolTest.class);
		Result resultsFrame = JUnitCore.runClasses(PoolTest.class);
		
		for(Failure f : resultsPool.getFailures())
		{
			System.out.println(f.toString());
		}
		
		System.out.println("Pool Class Failures: " + resultsPool.getFailureCount());
		System.out.println("Tests Successful: " + resultsPool.wasSuccessful());
		
		for(Failure f : resultsPlayer.getFailures())
		{
			System.out.println(f.toString());
		}
		
		System.out.println("Player Class Failures: " + resultsPlayer.getFailureCount());
		System.out.println("Tests Successful: " + resultsPlayer.wasSuccessful());
		
		for(Failure f : resultsFrame.getFailures())
		{
			System.out.println(f.toString());
		}
		
		System.out.println("Frame Class Failures: " + resultsFrame.getFailureCount());
		System.out.println("Tests Successful: " + resultsFrame.wasSuccessful());
	}
	

}
