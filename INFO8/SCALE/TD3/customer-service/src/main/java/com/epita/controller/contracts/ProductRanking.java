package com.epita.controller.contracts;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRanking {
	public String name;
	public Integer rank;
	public Integer customers;
}
