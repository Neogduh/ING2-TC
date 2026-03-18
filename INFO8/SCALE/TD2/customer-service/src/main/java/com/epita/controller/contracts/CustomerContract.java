package com.epita.controller.contracts;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerContract {
	public UUID customer;
    public List<UUID> purchases;
	public Integer total;
}
