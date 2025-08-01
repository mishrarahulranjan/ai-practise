package com.rr.ai.mcp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record Person(
        String name,
        String gender,
        String identity){

}