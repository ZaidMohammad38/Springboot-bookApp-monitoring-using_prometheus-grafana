package com.springBoot.bookApp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.bookApp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	// Search by title
    Page<Book> findByTitleContaining(String keyword, PageRequest pageRequest);
}
