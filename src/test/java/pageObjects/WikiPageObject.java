package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.DataStore;
import utilities.ReadProperty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class WikiPageObject {

    WebDriver driver = null;
    WebDriverWait wait;

    @FindBy(id = "Welcome_to_Wikipedia")
    private WebElement wikiWelcomeMessage;

    @FindBy(name = "search")
    private WebElement wikiSearchBox;

    private By searchResultsBy = By.xpath("//div[@class='mw-search-result-heading']/a");

    @FindBy(id = "firstHeading")
    private WebElement movieHeading;

    @FindBy(xpath = "//div[normalize-space()='Release date']/ancestor::tr/td")
    private WebElement movieReleaseDate;

    @FindBy(xpath = "//th[normalize-space()='Country']/..//td")
    private WebElement movieCountry;

    public WikiPageObject(){
        driver = (WebDriver) DataStore.getData("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void openWikiPage(){
        driver.get(ReadProperty.getProperty("wikiUrl"));
        driver.manage().window().maximize();
    }

    public void verifyWikiPage(){
        wait.until(ExpectedConditions.visibilityOf(wikiWelcomeMessage));
        Assert.assertEquals(wikiWelcomeMessage.getText().trim(), ReadProperty.getProperty("wikiWelcomeMsg"),
                "Expected wiki welcome message to be: "+ReadProperty.getProperty("wikiWelcomeMsg")+
                "but found: "+wikiWelcomeMessage.getText().trim());
    }

    public void searchWiki(String searchText){
        wait.until(ExpectedConditions.visibilityOf(wikiSearchBox));
        wikiSearchBox.sendKeys(searchText);
        wikiSearchBox.sendKeys(Keys.ENTER);

        //Looping through the search result to find the movie.
        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResultsBy));
        for(WebElement searchResult: searchResults){
            if(searchResult.getText().trim().equalsIgnoreCase(searchText)){
                searchResult.click();
                break;
            }
        }

        //Validating the selected movie with the movie searched to ensure that the searched movie is the correct movie.
        wait.until(ExpectedConditions.visibilityOf(movieHeading));
        Assert.assertEquals(movieHeading.getText().trim(), searchText,
                "Searched for the move: "+searchText+" but found: "+movieHeading.getText().trim());
    }

    public void getMovieInfo(){
        //Formatting and storing release date for later use
        wait.until(ExpectedConditions.visibilityOf(movieReleaseDate));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        LocalDate movieDate = LocalDate.parse(movieReleaseDate.getText().trim(), dateTimeFormatter);
        DataStore.shareData("wikiMovieDate", movieDate);

        //Storing movie country
        wait.until(ExpectedConditions.visibilityOf(movieCountry));
        DataStore.shareData("wikiMovieCountry",movieCountry.getText().trim());
    }
}
