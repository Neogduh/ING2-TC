package com.epita.controller.contracts;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRanking {
	public UUID user;
	public Integer rank;
	public Integer amount;
}
