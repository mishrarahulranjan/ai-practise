package com.rr.ai.vector.util;

import com.rr.ai.vector.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookDataPopulator {

    public static List<Book> populate(){
        List<Book> books = new ArrayList<>();

        books.add(new Book(UUID.randomUUID().toString(), "computer-science", "engg1", "This is a detailed description for java Book"));
        books.add(new Book(UUID.randomUUID().toString(), "computer-science", "engg2", "This is a detailed description for java18 Book"));
        books.add(new Book(UUID.randomUUID().toString(), "computer-science", "engg2", "This is a detailed description for java21 Book"));

        books.add(new Book(UUID.randomUUID().toString(), "chemistry", "scientist1", "This is a detailed description for physical chemistry Book"));
        books.add(new Book(UUID.randomUUID().toString(), "chemistry", "scientist2", "This is a detailed description for organic chemistry Book"));
        books.add(new Book(UUID.randomUUID().toString(), "chemistry", "scientist3", "This is a detailed description for organic chemical reaction chemistry Book"));

        return books;
    }

}
