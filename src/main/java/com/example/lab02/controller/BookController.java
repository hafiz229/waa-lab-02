package com.example.lab02.controller;

import com.example.lab02.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add(new Book(1, "Harry Potter", "1234", 100.0));
        books.add(new Book(2, "Batman vs Superman", "3456", 200.0));
        books.add(new Book(3, "One Hundred Roses", "5678", 300.0));
    }

    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("/v1")
    List<Book> getBooksV1() {
        return books;
    }

    @GetMapping(headers = "X-API-VERSION=2")
    public List<Book> getBooksV2() {
        return books;
    }

    @GetMapping(params = "version=1")
    ResponseEntity<Book> getBookById(@RequestParam("id") int id) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}", produces = "application/cs.miu.edu-v2+json")
    ResponseEntity<Book> getBookByIdV2(@PathVariable("id") int id) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<Book> addBook(@RequestBody Book book) {
        books.add(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    ResponseEntity<Book> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        Optional<Book> existingBook = books.stream().filter(b -> b.getId() == id).findFirst();
        if (existingBook.isPresent()) {
            books.remove(existingBook.get());
            books.add(book);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Book> deleteBook(@PathVariable("id") int id) {
        Optional<Book> book = books.stream().filter(b -> b.getId() == id).findFirst();
        if (book.isPresent()) {
            books.remove(book.get());
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
