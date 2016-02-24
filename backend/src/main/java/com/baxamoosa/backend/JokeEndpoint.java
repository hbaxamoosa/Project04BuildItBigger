package com.baxamoosa.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "backend.baxamoosa.com",
                ownerName = "backend.baxamoosa.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    private static final Logger logger = Logger.getLogger(JokeEndpoint.class.getName());

    /**
     * This method gets the <code>Joke</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Joke</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getJoke")
    public Joke getJoke(@Named("id") int id) {
        Joke myJoke = new Joke();
        Joke.setMyJoke(id);
        logger.info("Calling getJoke method");
        return myJoke;
    }

    /**
     * This inserts a new <code>Joke</code> object.
     *
     * @param joke The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertJoke")
    public Joke insertJoke(Joke joke) {
        // TODO: Implement this function
        logger.info("Calling insertJoke method");
        return joke;
    }
}