package ed.uopp.uoppcore.mapper;

import ed.uopp.uoppcore.data.http.OpportunityDTO;
import ed.uopp.uoppcore.entity.Opportunity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, FormatMapper.class})
public interface OpportunityMapper {

//    @Mapping(target = "postCreatedDatetime", ignore = true)
//    @Mapping(target = "postScrapperDatetime", ignore = true)
//    @Mapping(target = "createdDatetime", ignore = true)
    OpportunityDTO toOpportunityData(Opportunity opportunity);

}
