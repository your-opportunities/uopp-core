package ed.uopp.uoppcore.controller;

import ed.uopp.uoppcore.controller.operations.OpportunityOperations;
import ed.uopp.uoppcore.data.http.OpportunityDTO;
import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import ed.uopp.uoppcore.mapper.OpportunityMapper;
import ed.uopp.uoppcore.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/opportunities")
public class OpportunityController implements OpportunityOperations {

    private final OpportunityService opportunityService;
    private final OpportunityMapper opportunityMapper;

    @GetMapping
    @Override
    public ResponseEntity<List<OpportunityDTO>> findOpportunities(
            @RequestParam(required = false) String fullText,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) Boolean isAsap,
            @RequestParam(required = false) List<String> formats,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Opportunity> opportunities = opportunityService.findOpportunities(fullText, categories, isAsap, formats, pageable);

        return ResponseEntity.ok(opportunities.stream()
                .map(opportunityMapper::toOpportunityData)
                .filter(opportunityDTO -> opportunityDTO.opportunityStatus().equals(OpportunityStatus.APPROVED))
                .toList());
    }

    @GetMapping("/{uuid}")
    @Override
    public ResponseEntity<OpportunityDTO> findOpportunity(@PathVariable String uuid) {
        UUID uuidVal = UUID.fromString(uuid);
        Opportunity opportunity = opportunityService.findOpportunityByUuid(uuidVal).orElseThrow(
                () -> new NoSuchElementException(String.format("No opportunity with uuid '{}'", uuid))
        );

        return ResponseEntity.ok(opportunityMapper.toOpportunityData(opportunity));
    }

}
