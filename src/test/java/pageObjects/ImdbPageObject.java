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

public class ImdbPageObject {

    WebDriver driver = null;
    WebDriverWait wait;
    public ImdbPageObject(){
        driver = (WebDriver) DataStore.getData("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "home_img_holder")
    private WebElement imdbLogo;

    @FindBy(id = "suggestion-search")
    private WebElement searchBox;

    private By searchResultsBy = By.xpath("//ul[contains(@class,'ipc-metadata-list')]/li//a");

    @FindBy(xpath = "//h1[contains(@data-testid,'title')]") //As of now only one h1 is present in the page. Can also search by (tagName = 'h1')
    private WebElement movieHeading;

    @FindBy(xpath = "//a[normalize-space()='Release date']/..//li/a")
    private WebElement movieReleaseDate;

    @FindBy(xpath = "//button[normalize-space()='Country of origin']/..//a")
    private WebElement movieCountry;

    public void openImdbPage(){
        driver.get(ReadProperty.getProperty("imdbUrl"));
        driver.manage().window().maximize();
    }

    public void verifyImdbPage(){
        wait.until(ExpectedConditions.visibilityOf(imdbLogo));
        Assert.assertTrue(imdbLogo.isDisplayed(), "Failed to load IMDB page");
    }

    public void searchImdb(String searchText){
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.sendKeys(searchText);
        searchBox.sendKeys(Keys.ENTER);

        //Looping through the search result to find the movie.
        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResultsBy));
        for(WebElement searchResult: searchResults){
            if(searchResult.getText().trim().contains(searchText)){
                searchResult.click();
                break;
            }
        }

        //Validating the selected movie with the movie searched to ensure that the searched movie is the correct movie.
        wait.until(ExpectedConditions.visibilityOf(movieHeading));
        Assert.assertTrue(movieHeading.getText().trim().contains(searchText),
                "searched for movie: "+searchText+" but found: "+movieHeading.getText().trim());
    }

    public void getMovieInfo(){
        wait.until(ExpectedConditions.visibilityOf(movieReleaseDate));
        String releaseDate;

        //To get only the date and remove the country name present at the end
        String[] dates = movieReleaseDate.getText().trim().split(" ");
        releaseDate = dates[1] +" "+ dates[0] +" "+ dates[2];
        releaseDate = releaseDate.replaceAll(",", "");

        //Formatting and storing release date for later use
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        LocalDate movieDate = LocalDate.parse(releaseDate, dateTimeFormatter);
        DataStore.shareData("imdbMovieDate", movieDate);

        //Storing movie country
        wait.until(ExpectedConditions.visibilityOf(movieCountry));
        DataStore.shareData("imdbMovieCountry", movieCountry.getText().trim());
    }
}
