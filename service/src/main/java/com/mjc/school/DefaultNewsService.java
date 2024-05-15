package com.mjc.school;

import com.mjc.school.domain.Author;
import com.mjc.school.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DefaultNewsService implements NewsService {
    private final AuthorRepository authorRepository;

    public DefaultNewsService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> listAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }
}
