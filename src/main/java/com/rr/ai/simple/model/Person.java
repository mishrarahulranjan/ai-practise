package com.rr.ai.simple.model;

public record Person (Integer id, String firstName, String lastName, int age, Gender gender,
                      String nationality) {
}

