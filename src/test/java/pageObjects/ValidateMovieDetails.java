package pageObjects;

import org.testng.Assert;
import utilities.DataStore;

public class ValidateMovieDetails {

    public void validateMovieInformation(){
        String imdbMovieReleaseDate = DataStore.getSharedData("imdbMovieDate").toString();
        String imdbMovieCountry = DataStore.getSharedData("imdbMovieCountry").toString();
        String wikiMovieReleaseDate = DataStore.getSharedData("wikiMovieDate").toString();
        String wikiMovieCountry = DataStore.getSharedData("wikiMovieCountry").toString();

        Assert.assertEquals(imdbMovieCountry, wikiMovieCountry, "Country in IMDB: "+imdbMovieCountry
        +", country in Wiki: "+wikiMovieCountry);
        Assert.assertEquals(imdbMovieReleaseDate, wikiMovieReleaseDate, "Release date in IMDB: "+imdbMovieReleaseDate
        +", release date in Wiki: "+wikiMovieReleaseDate);
    }
}
