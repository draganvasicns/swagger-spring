package com.vedait.swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedait.swagger.exception.ResourceNotFoundException;
import com.vedait.swagger.model.Book;
import com.vedait.swagger.repository.BookRepository;

@RestController
@RequestMapping("/api/v1")
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("/books")
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Long id) throws ResourceNotFoundException {
		Book book = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book with id not found:"+id));
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@Validated @RequestBody Book book) {
		Book createdBook = bookRepository.save(book);
		return new ResponseEntity<Book>(createdBook, HttpStatus.OK);
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @Validated  @RequestBody Book book) throws ResourceNotFoundException {
		Book exitingBook = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book with id not found:"+id));
		exitingBook.setCode(book.getCode());
		exitingBook.setName(book.getName());
		exitingBook = bookRepository.save(exitingBook);
		return ResponseEntity.ok(exitingBook);
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Boolean> deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
		Book exitingBook = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book with id not found:"+id));
		bookRepository.delete(exitingBook);
		return ResponseEntity.ok(Boolean.TRUE);
	}
}
