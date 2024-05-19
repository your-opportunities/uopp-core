package ed.uopp.uoppcore.controller;

import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OpportunityController {

    private final OpportunityService opportunityService;

    @GetMapping
    public List<Opportunity> findOpportunities(
            @RequestParam(required = false) String fullText,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) Boolean isAsap,
            @RequestParam(required = false) List<String> formats) {
        return opportunityService.findOpportunities(fullText, categories, isAsap, formats);
    }

}
