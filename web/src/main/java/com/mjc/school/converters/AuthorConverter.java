package com.mjc.school.converters;

import com.mjc.school.domain.Author;
import com.mjc.school.news.model.AuthorModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {

    public AuthorModel createAuthorModel(Author author) {
        Long id = author.getId();
        String name = author.getName();
        AuthorModel authorModel = new AuthorModel(id, name);
        return authorModel;
    }

    public List<AuthorModel> createListOfAuthorModels(List<Author> authors) {
        List<AuthorModel> authorModelList =
                authors.stream().map(a -> createAuthorModel(a))
                        .collect(Collectors.toList());
        return authorModelList;
    }

    public void updateAuthorFromUpdateAuthorModel(Author author, AuthorModel updateAuthorModel) {
        author.setName(updateAuthorModel.getName());

    }
}
