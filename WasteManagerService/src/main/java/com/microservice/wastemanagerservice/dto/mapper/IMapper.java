package com.microservice.wastemanagerservice.dto.mapper;

import java.util.List;

public interface IMapper<Model, Dto>{
    public Dto mapToDto(Model m);
    public List<Dto> mapToDtoList(List<Model> lis);

}
