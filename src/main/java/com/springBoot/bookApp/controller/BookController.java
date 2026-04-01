package com.springBoot.bookApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springBoot.bookApp.model.Book;
import com.springBoot.bookApp.service.BookService;

import jakarta.validation.Valid;

@Controller
public class BookController {
	@Autowired
    private BookService service;

    // Home page
    @GetMapping("/")
    public String viewHomePage(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword) {

        Page<Book> bookPage;

        if (keyword != null && !keyword.isEmpty()) {
            bookPage = service.searchBooks(keyword, page, size);
            model.addAttribute("keyword", keyword);
        } else {
            bookPage = service.getAllBooks(page, size);
        }

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());

        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book()); // required for form binding
        return "add";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute Book book,
                           BindingResult result) {

        if (result.hasErrors()) {
            return "add";
        }

        service.saveBook(book);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", service.getBookById(id));
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute Book book,
                             BindingResult result) {

        if (result.hasErrors()) {
            return "edit";
        }

        book.setId(id); // important for update
        service.saveBook(book);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return "redirect:/";
    }
}
