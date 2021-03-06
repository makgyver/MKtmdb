/*******************************************************************************
 * Copyright (C) 2012-2013  Mirko Polato
 * 
 * This file is part of MKtmdb.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/

package mk.tmdb.core;

/**
 * Class that contains a series of constants used by other classes in this library.
 * 
 * @author Mirko Polato
 *
 */
public final class TMDbConstants {
	
	public static final String ACCOUNT = "account";
	public static final String ACTOR = "Actor";
	public static final String ADULT = "adult";
	public static final String AKA = "also_known_as";
	public static final String ALT_TITLES = "alternative_titles";
	public static final String API_BASE_URL = "http://api.themoviedb.org/";
	public static final String API_KEY = "?api_key";
	public static final String AUTHENTICATION = "authentication/";
	public static final String AVERAGE = "vote_average";
	public static final String BACKDROP_PATH = "backdrop_path";
	public static final String BACKDROP_SIZES = "backdrop_sizes";
	public static final String BACKDROPS = "backdrops";
	public static final String BASE_URL = "base_url";
	public static final String BELONGS_TO_COLLECTION = "belongs_to_collection";
	public static final String BIO = "biography";
	public static final String BIRTHDAY = "birthday";
	public static final String BUDGET = "budget";
	public static final String CAST_ID = "cast_id";
	public static final String CAST = "cast";
	public static final String CASTS = "casts";
	public static final String CERTIFICATION = "certification";
	public static final String CHANGE_KEYS = "change_keys";
	public static final String CHANGES = "changes";	
	public static final String CHARACTER = "character";
	public static final String COLLECTION = "collection";
	public static final String COMPANIES = "production_companies";
	public static final String COMPANY = "company";
	public static final String CONFIGURATION = "configuration";
	public static final String COUNT = "vote_count";
	public static final String COUNTRIES = "countries";
	public static final String PRODUCTION_COUNTRIES = "production_countries";
	public static final String CREATED_BY = "created_by";
	public static final String CREDITS = "credits";
	public static final String CREW = "crew";
	public static final String DEATHDAY = "deathday";
	public static final String DEPARTMENT = "department";
	public static final String DESCRIPTION = "description";
	public static final String END_DATE = "end_date";
	public static final String ENGLISH_NAME = "english_name";
	public static final String EXPIRATION = "expires_at";
	public static final String FALSE = "false";
	public static final String FAVORITE = "favorite";	
	public static final String FAVORITE_COUNT = "favorite_count";
	public static final String FAVORITE_MOVIES = "favorite_movies";
	public static final String GENRE = "genre";
	public static final String GENRES = "genres";
	public static final String GET_AUTH_SESSION = AUTHENTICATION + "session/new";
	public static final String GET_AUTH_TOKEN = AUTHENTICATION + "token/new";
	public static final String GET_GUEST_SESSION = AUTHENTICATION + "guest_session/new";
	public static final String GUEST_ID = "guest_session_id";
	public static final String HEADQUARTERS = "headquarters";
	public static final String HEIGHT = "height";
	public static final String HOMEPAGE = "homepage";
	public static final String ID = "id";
	public static final String IMAGES = "images";
	public static final String IMDB = "imdb_id";
	public static final String INCLUDE_ADULT = "include_adult";
	public static final String ISO_31661 = "iso_3166_1";
	public static final String ISO_6391 = "iso_639_1";
	public static final String ITEM_COUNT = "item_count";
	public static final String ITEMS = "items";
	public static final String JOB = "job";
	public static final String KEYWORD = "keyword";
	public static final String KEYWORDS = "keywords";
	public static final String LANGUAGE = "language";
	public static final String LANGUAGES = "spoken_languages";
	public static final String LATEST = "latest";
	public static final String LINK = "source";
	public static final String LIST = "list";
	public static final String LIST_TYPE = "list_type";
	public static final String LISTS = "lists";
	public static final String LOGO_PATH = "logo_path";
	public static final String LOGO_SIZES = "logo_sizes";
	public static final String MOVIE = "movie";
	public static final String MOVIE_ID = "movie_id";
	public static final String MOVIES = "movies";
	public static final String MOVIES_INTHEATRE = "movie/now_playing";
	public static final String MOVIES_UPCOMING = "movie/upcoming";
	public static final String NAME = "name";
	public static final String ORDER = "order";
	public static final String ORIGINAL_TITLE = "original_title";
	public static final String OVERVIEW = "overview";
	public static final String PATH = "file_path";
	public static final String PAGE = "page";
	public static final String PARENT_COMPANY = "parent_company";
	public static final String PARTS = "parts";
	public static final String PERSON = "person";
	public static final String PLACE_OF_BIRTH = "place_of_birth";
	public static final String POPULAR_MOVIES = "movie/popular";
	public static final String POPULARITY = "popularity";
	public static final String POSTER_PATH = "poster_path";
	public static final String POSTER_SIZES = "poster_sizes";
	public static final String POSTERS = "posters";
	public static final String PROFILE_PATH = "profile_path";
	public static final String PROFILE_SIZES = "profile_sizes";
	public static final String PROFILES = "profiles";
	public static final String QUERY = "query";
	public static final String QUICKTIME = "quicktime";
	public static final String RATED_MOVIES = "rated_movies";
	public static final String RATING = "rating";
	public static final String RATIO = "aspect_ratio";
	public static final String RELEASE_DATE = "release_date";
	public static final String RELEASES = "releases";
	public static final String RESULT = "results";
	public static final String REVENUE = "revenue";
	public static final String RUNTIME = "runtime";
	public static final String SEARCH = "search/";
	public static final String SECURE_URL = "secure_base_url";
	public static final String SESSION_ID = "session_id";
	public static final String SIMILAR = "similar_movies";
	public static final String SIZE = "size";
	public static final String SLASH = "/";
	public static final String SOURCES = "sources";
	public static final String START_DATE = "start_date";
	public static final String STATUS = "status";
	public static final String STATUS_CODE = "status_code";
	public static final String STATUS_MESSAGE = "status_message";
	public static final String TAGLINE = "tagline";
	public static final String TITLE = "title";
	public static final String TITLES = "titles";
	public static final String TOKEN = "request_token";
	public static final String TOPRATED_MOVIES = "movie/top_rated";
	public static final String TOTAL_PAGES = "total_pages";
	public static final String TOTAL_RESULTS = "total_results";
	public static final String TRAILERS = "trailers";
	public static final String TRANSLATIONS = "translations";
	public static final String TRUE = "true";
	public static final String USERNAME = "username";
	public static final String VALUE = "value";
	public static final String VERSION = "3/";
	public static final String WATCHLIST = "movie_watchlist";
	public static final String WIDTH = "width";
	public static final String YEAR = "year";
	public static final String YOUTUBE = "youtube";
	
}
