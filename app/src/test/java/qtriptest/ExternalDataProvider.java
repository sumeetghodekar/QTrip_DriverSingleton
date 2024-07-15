 package qtriptest;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class ExternalDataProvider {

    DP dataProviderUtility;

    @DataProvider(name="qtripData")
    public Object[][] qtripData() throws IOException
    {
        dataProviderUtility = new DP();
        return dataProviderUtility.dpMethodTestCase1("TestCase01");
    }

    @DataProvider(name="qtripData2")
    public Object[][] qtripData2() throws IOException
    {
        dataProviderUtility = new DP();
        return dataProviderUtility.dpMethodTestCase2("TestCase02");
    }

    @DataProvider(name="qtripData3")
    public Object[][] qtripData3() throws IOException
    {
        dataProviderUtility = new DP();
        return dataProviderUtility.dpMethodTestCase3("TestCase03");
    }
    @DataProvider(name="qtripData4")
    public Object[][] qtripData4() throws IOException
    {
        dataProviderUtility = new DP();
        return dataProviderUtility.dpMethodTestCase3("TestCase04");
    }
}
