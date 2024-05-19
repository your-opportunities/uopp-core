package ed.uopp.uoppcore.controller;

import ed.uopp.uoppcore.data.OpportunityStatusData;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import ed.uopp.uoppcore.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// TODO:  protect with spring security

@RequiredArgsConstructor
@RestController
@RequestMapping("/moderator/")
public class ModeratorController {

    private final OpportunityService opportunityService;

    @PostMapping("/approve")
    public ResponseEntity<OpportunityStatus> approveOpportunity(@RequestBody OpportunityStatusData opportunityStatusData) {
        return ResponseEntity.ok(opportunityService.updateStatus(
                opportunityStatusData.uuid(),
                OpportunityStatus.valueOf(opportunityStatusData.opportunityStatus())
        ));
    }

}
