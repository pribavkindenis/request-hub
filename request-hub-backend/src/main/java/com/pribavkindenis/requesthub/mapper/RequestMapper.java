package com.pribavkindenis.requesthub.mapper;

import com.pribavkindenis.requesthub.annotation.stereotype.ModelMapper;
import com.pribavkindenis.requesthub.model.dto.RequestDto;
import com.pribavkindenis.requesthub.model.jpa.Request;

@ModelMapper
public class RequestMapper implements Mapper<Request, RequestDto> {

    @Override
    public RequestDto entityToDto(Request request) {
        if (request == null || request.getUser() == null) {
            return null;
        }
        return RequestDto.builder()
                         .id(request.getId())
                         .userId(request.getUser().getId())
                         .header(request.getHeader())
                         .description(request.getDescription())
                         .build();
    }

    @Override
    public Request dtoToEntity(RequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        return Request.builder()
                      .id(null)
                      .user(null)
                      .header(requestDto.getHeader())
                      .description(requestDto.getDescription())
                      .build();
    }

    @Override
    public void updateEntity(Request entity, RequestDto dto) {
        entity.setDescription(dto.getDescription());
        entity.setHeader(dto.getHeader());
    }

}
