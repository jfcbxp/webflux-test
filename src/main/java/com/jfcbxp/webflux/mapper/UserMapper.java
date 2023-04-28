package com.jfcbxp.webflux.mapper;

import com.jfcbxp.webflux.entity.User;
import com.jfcbxp.webflux.model.request.UserRequest;
import com.jfcbxp.webflux.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    User toEntity(final UserRequest userRequest);

    @Mapping(target = "id",ignore = true)
    User toEntity(final UserRequest userRequest, @MappingTarget final User entity);

    UserResponse toResponse(final User user);
}
