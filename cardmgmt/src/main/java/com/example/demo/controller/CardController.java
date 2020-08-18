package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Card;
import com.example.demo.repo.CardRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/cards")
@Slf4j
@Validated
public class CardController {

	@PostConstruct
	public void init() {
		log.info("Saving sample records:");
		log.info(repo
				.save(new Card("5601234534465678", "HSBC Canada", java.sql.Date.valueOf(LocalDate.of(2017, 11, 20))))
				.toString());
		log.info(repo.save(
				new Card("4519453245242456", "Royal Bank of Canada", java.sql.Date.valueOf(LocalDate.of(2017, 10, 03))))
				.toString());
		log.info(repo.save(
				new Card("3786733489653455", "American Express", java.sql.Date.valueOf(LocalDate.of(2020, 05, 20))))
				.toString());
	}

	@Autowired
	private CardRepository repo;

	@GetMapping("/findAll")
	public List<Card> listCards() {
		return repo.findAll(Sort.by(Sort.Direction.DESC, "expiryDate"));
	}

	@PostMapping("/add")
	public ResponseEntity<String> addCard(
			@RequestParam @NotBlank(message = "bank name can not be blank") @Pattern(regexp = "^[a-zA-Z]+", message = "invalid bank name (should contain only alphabets)") String bankName,

			@RequestParam @NotBlank(message = "card number can not be blank") @Size(min = 16, max = 16, message = "size must be 16") @Pattern(regexp = "^[0-9]{16}", message = "invalid card number, should contain only numbers") String cardNumber,

			@RequestParam String expiryDate) throws java.text.ParseException {
		log.info("Reading card details");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			repo.save(new Card(cardNumber, bankName, format.parse(expiryDate)));
			log.info("Saving card");
		} catch (ParseException e) {
			log.info("Invalid input. Card can not be saved.");
			return new ResponseEntity<>("Invalid Details. Card can not be added", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Card Added", HttpStatus.OK);
	}

}
