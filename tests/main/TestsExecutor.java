package tests.main;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.mechanics.BoardTest;
import tests.mechanics.FrameTest;
import tests.mechanics.PoolTest;


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

		Result resultsFrame = JUnitCore.runClasses(FrameTest.class);

		for(Failure f : resultsFrame.getFailures())
		{
			System.out.println(f.toString());
		}

		System.out.println("Frame Class Failures: " + resultsFrame.getFailureCount());
		System.out.println("Tests Successful: " + resultsFrame.wasSuccessful());

		Result resultsPool = JUnitCore.runClasses(PoolTest.class);

		for(Failure f : resultsPool.getFailures())
		{
			System.out.println(f.toString());
		}

		System.out.println("Pool Class Failures: " + resultsPool.getFailureCount());
		System.out.println("Tests Successful: " + resultsPool.wasSuccessful());
	}
	

}
