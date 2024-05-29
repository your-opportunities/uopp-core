package ed.uopp.uoppcore.mapper;

import ed.uopp.uoppcore.data.http.CategoryDTO;
import ed.uopp.uoppcore.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toCategoryData(Category category);
}
