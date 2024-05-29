package ed.uopp.uoppcore.mapper;

import ed.uopp.uoppcore.data.http.FormatDTO;
import ed.uopp.uoppcore.entity.Format;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormatMapper {
    FormatDTO toFormatData(Format format);
}
