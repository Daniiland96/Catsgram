package ru.yandex.practicum.catsgram.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public Collection<Post> findAll(
            @RequestParam(name = "from", defaultValue = "1", required = false) Optional<Integer> fromOpt,
            @RequestParam(name = "size", defaultValue = "10", required = false) Optional<Integer> sizeOpt,
            @RequestParam(name = "sort", defaultValue = "desc", required = false) String sort) {

//        if (sizeOpt.isEmpty()) {
//            throw new ParameterNotValidException("size", "Не указан размер выборки.");
//        }
//        if (fromOpt.isEmpty()) {
//            throw new ParameterNotValidException("from", "Не указан начальная страница.");
//        }
        int from = fromOpt.get();
        int size = sizeOpt.get();
        if (size <= 0) {
            throw new ParameterNotValidException("size", "Некорректный размер выборки. Размер должен быть больше нуля");
        }
        if (from <= 0) {
            throw new ParameterNotValidException("from",
                    "Некорректный номер начальной страницы. Номер должен быть больше нуля");
        }
        return postService.findAll(from, size, sort);
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable("id") long id) {
        return postService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }
}