package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

	@Id
	@NotBlank(message = "card number can not be blank")
	@Size(min = 16, max = 16, message = "size must be 16")
	@Pattern(regexp = "^[0-9]{16}", message = "invalid card number, should contain only numbers")
	String cardNumber;

	@NotBlank(message = "bank name can not be blank")
	@Pattern(regexp = "^[a-zA-Z]+", message = "invalid bank name (should contain only alphabets)")
	String bankName;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	@NotBlank(message = "expiry date can not be blank")
	@JsonFormat(pattern = "yyyy-mm-dd")
	Date expiryDate;

}