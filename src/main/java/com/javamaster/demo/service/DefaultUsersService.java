package com.javamaster.demo.service;

import com.javamaster.demo.convert.UsersConverter;
import com.javamaster.demo.dto.UsersDto;
import com.javamaster.demo.entity.Users;
import com.javamaster.demo.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultUsersService implements UsersService{
    private void validateUserDto(UsersDto usersDto) throws ValidationException{
        if (null == usersDto){
            throw new ValidationException("Object user is null");
        }
        if ((usersDto.getLogin()) == null || usersDto.getLogin().isEmpty()){
            throw new ValidationException("Login is empty");
        }
    }

    private final UsersRepository usersRepository;
    private final UsersConverter usersConverter;

    @Override
    public UsersDto saveUser(UsersDto usersDto) throws ValidationException {
        validateUserDto(usersDto);
        Users savedUser = usersRepository.save(usersConverter.fromUserDtoToUser(usersDto));
        return usersConverter.fromUserToUserDto(savedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        usersRepository.deleteById(userId);
    }

    @Override
    public UsersDto findByLogin(String login) {
        Users users = usersRepository.findByLogin(login);
        if (users != null){
            return usersConverter.fromUserToUserDto(users);
        }
        return null;
    }

    @Override
    public List<UsersDto> findAll() {
        return usersRepository.findAll()
                .stream()
                .map(usersConverter::fromUserToUserDto)
                .collect(Collectors.toList());
    }
}
