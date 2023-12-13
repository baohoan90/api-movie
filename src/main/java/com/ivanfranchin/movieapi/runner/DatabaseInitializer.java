package com.ivanfranchin.movieapi.runner;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ivanfranchin.movieapi.model.Movie;
import com.ivanfranchin.movieapi.model.Product;
import com.ivanfranchin.movieapi.model.User;
import com.ivanfranchin.movieapi.security.WebSecurityConfig;
import com.ivanfranchin.movieapi.security.oauth2.OAuth2Provider;
import com.ivanfranchin.movieapi.service.MovieService;
import com.ivanfranchin.movieapi.service.ProductService;
import com.ivanfranchin.movieapi.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

	private final UserService userService;
	private final MovieService movieService;
	private final ProductService productService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		if (!userService.getUsers().isEmpty()) {
			return;
		}
		USERS.forEach(user -> {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.saveUser(user);
		});
		MOVIES.forEach(movieService::saveMovie);
		PRODUCTS.forEach(productService::saveProduct);

		log.info("Database initialized");
	}

	private static final List<User> USERS = Arrays.asList(
			new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN, null,
					OAuth2Provider.LOCAL, "1"),
			new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER, null, OAuth2Provider.LOCAL,
					"2"));

	private static final List<Movie> MOVIES = Arrays.asList(new Movie("tt5580036", "I, Tonya",
			"https://m.media-amazon.com/images/M/MV5BMjI5MDY1NjYzMl5BMl5BanBnXkFtZTgwNjIzNDAxNDM@._V1_SX300.jpg"),
			new Movie("tt0163651", "American Pie",
					"https://m.media-amazon.com/images/M/MV5BMTg3ODY5ODI1NF5BMl5BanBnXkFtZTgwMTkxNTYxMTE@._V1_SX300.jpg"),
			new Movie("tt0480249", "I Am Legend",
					"https://m.media-amazon.com/images/M/MV5BYTE1ZTBlYzgtNmMyNS00ZTQ2LWE4NjEtZjUxNDJkNTg2MzlhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg"),
			new Movie("tt0120804", "Resident Evil",
					"https://m.media-amazon.com/images/M/MV5BZmI1ZGRhNDYtOGVjZC00MmUyLThlNTktMTQyZGE3MzE1ZTdlXkEyXkFqcGdeQXVyNDE5MTU2MDE@._V1_SX300.jpg"),
			new Movie("tt0075148", "Rocky",
					"https://m.media-amazon.com/images/M/MV5BMTY5MDMzODUyOF5BMl5BanBnXkFtZTcwMTQ3NTMyNA@@._V1_SX300.jpg"),
			new Movie("tt8540796", "The Green Line",
					"https://m.media-amazon.com/images/M/MV5BMzZkNTRjZjEtNDVhNi00NGEyLWE2NWYtNTUzOTFlNGVmMDFjXkEyXkFqcGdeQXVyODg0NjM4MDg@._V1_SX300.jpg"),
			new Movie("tt7286456", "Joker",
					"https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg"),
			new Movie("tt0112573", "Braveheart",
					"https://m.media-amazon.com/images/M/MV5BMzkzMmU0YTYtOWM3My00YzBmLWI0YzctOGYyNTkwMWE5MTJkXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"),
			new Movie("tt0758758", "Into the Wild",
					"https://m.media-amazon.com/images/M/MV5BMTAwNDEyODU1MjheQTJeQWpwZ15BbWU2MDc3NDQwNw@@._V1_SX300.jpg"),
			new Movie("tt0088763", "Back to the Future",
					"https://m.media-amazon.com/images/M/MV5BZmU0M2Y1OGUtZjIxNi00ZjBkLTg1MjgtOWIyNThiZWIwYjRiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"));

	private static final List<Product> PRODUCTS = new ArrayList<>();
	static {
		Random rand = new Random();
		BiFunction<Integer, Integer, Integer> getRandom = (min, max) -> rand.nextInt(max - min) + min;

		int size = MOVIES.size() - 1;
		for (int i = 0; i < 100; i++) {
			Movie movie = MOVIES.get(getRandom.apply(0, size));
			Product p = new Product();
			p.setDiscount(5.5d);
			p.setTitle(movie.getTitle());
			p.setImdb("SP00" + (88100 + i));
			p.setImageUrl(movie.getPoster());
			p.setCategory(getRandom.apply(0, 5) + "");
			p.setRating((double) getRandom.apply(1, 5));
			p.setPrice(new BigDecimal(getRandom.apply(100000, 700000)));
			p.setSlug(toSlug(p.getTitle()));
			PRODUCTS.add(p);
		}
	}

	public static String toSlug(String input) {
		Matcher matcher = Pattern.compile("[\\s]").matcher(input);
		String nowhitespace = matcher != null ? matcher.replaceAll("-") : input;
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		String slug = Pattern.compile("[^\\w-]").matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH) + "-" + System.currentTimeMillis();
	}
}
