package com.mjc.school.mappers;

import com.mjc.school.domain.Author;
import com.mjc.school.dtos.AuthorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }
    public AuthorDTO getAuthorDtoFromAuthor(Author author){
        return authorMapper.entityToDTO(author);
    }
    public List<AuthorDTO> getAuthorDTOsFromAuthors(List<Author>authors){
        return authorMapper.authorsToAuthorDTOs(authors);
    }
}
