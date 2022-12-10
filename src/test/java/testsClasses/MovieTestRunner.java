package testsClasses;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.ImdbPageObject;
import pageObjects.ValidateMovieDetails;
import pageObjects.WikiPageObject;
import utilities.DataStore;
import utilities.DriverFactory;
import utilities.ReadProperty;

public class MovieTestRunner {

    @BeforeMethod
    public void setUp(){
        DriverFactory driverFactory = new DriverFactory();
        DataStore.putData("driver", driverFactory.initiateDriver());
    }

    @Test
    public void getMovieInfoFromImdb(){
        ImdbPageObject imdbPageObject = new ImdbPageObject();
        imdbPageObject.openImdbPage();
        imdbPageObject.verifyImdbPage();
        imdbPageObject.searchImdb(ReadProperty.getProperty("movieName"));
        imdbPageObject.getMovieInfo();
    }

    @Test
    public void getMovieInfoFromWiki(){
        WikiPageObject wikiPageObject = new WikiPageObject();
        wikiPageObject.openWikiPage();
        wikiPageObject.verifyWikiPage();
        wikiPageObject.searchWiki(ReadProperty.getProperty("movieName"));
        wikiPageObject.getMovieInfo();
    }

    @Test(dependsOnMethods = {"getMovieInfoFromWiki", "getMovieInfoFromImdb"})
    public void validateMovieInfo(){
        ValidateMovieDetails validateMovieDetails = new ValidateMovieDetails();
        validateMovieDetails.validateMovieInformation();
    }

    @AfterMethod
    public void tearDown(){
        ((WebDriver) DataStore.getData("driver")).quit();
    }
}
