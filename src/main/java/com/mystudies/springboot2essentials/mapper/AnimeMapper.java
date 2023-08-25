package com.mystudies.springboot2essentials.mapper;

import com.mystudies.springboot2essentials.domain.Anime;
import com.mystudies.springboot2essentials.requests.AnimePostRequestBody;
import com.mystudies.springboot2essentials.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
