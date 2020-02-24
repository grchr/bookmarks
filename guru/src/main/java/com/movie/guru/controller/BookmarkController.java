package com.movie.guru.controller;


import com.movie.guru.model.Bookmark;
import com.movie.guru.model.User;
import com.movie.guru.repository.BookmarkRepository;
import com.movie.guru.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookmarkController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookmarkController.class);
    private BookmarkRepository bookmarkRepository;
    private UserRepository userRepository;

    public BookmarkController(BookmarkRepository bookmarkRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/bookmark")
    Collection<Bookmark> bookmarks(){
        return bookmarkRepository.findAll();
    }

    @GetMapping("/bookmark/{userId}")
    ResponseEntity<?> bookmarksByUserId(@PathVariable Long userId){
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        List<Bookmark> bookmarkList = bookmarkRepository.findBookmarksByUser(user);
        return ResponseEntity.ok().body(bookmarkList);
    }

    @PostMapping("/bookmark")
    ResponseEntity<?> createBookmark(@Valid @RequestBody Bookmark bookmark) {
        Bookmark result = bookmarkRepository.save(bookmark);
        bookmarkRepository.flush();
        return ResponseEntity.ok().body(result);
    }
}
