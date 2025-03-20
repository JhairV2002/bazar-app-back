package jv.bazar.amacame.mappers;

import jv.bazar.amacame.dto.req.SignUpUserReqDTO;
import jv.bazar.amacame.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    User userReqDtoToUser(SignUpUserReqDTO userReqDto);
}
