import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestsExecutor {
	
	public static void main(String[] args)
	{
		
		Result resultsBoard = JUnitCore.runClasses(BoardTest.class);

		for(Failure f : resultsBoard.getFailures())
		{
			System.out.println(f.toString());
		}
		
		System.out.println("Board Class Failures: " + resultsBoard.getFailureCount());
		System.out.println("Tests Successful: " + resultsBoard.wasSuccessful());
	}
	

}
