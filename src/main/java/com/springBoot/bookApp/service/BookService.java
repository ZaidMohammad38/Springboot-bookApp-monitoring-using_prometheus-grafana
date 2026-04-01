package com.springBoot.bookApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springBoot.bookApp.model.Book;
import com.springBoot.bookApp.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
    private BookRepository repo;

    // Get all books with pagination
    public Page<Book> getAllBooks(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }

    // Search books
    public Page<Book> searchBooks(String keyword, int page, int size) {
        return repo.findByTitleContaining(keyword, PageRequest.of(page, size));
    }

    public void saveBook(Book book) {
        repo.save(book);
    }

    public Book getBookById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        repo.deleteById(id);
    }
}
