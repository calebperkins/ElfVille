package testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ServerStart.class, WelcomeScreenTest.class, CentralBoardTest.class})//, ClanDirectoryTest.class}) //, ClanDirectoryTest.class, ClanBoardTest.class})
public class AllTests {

}
