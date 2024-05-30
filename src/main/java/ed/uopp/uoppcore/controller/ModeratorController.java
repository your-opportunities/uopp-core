package ed.uopp.uoppcore.controller;

import ed.uopp.uoppcore.controller.operations.ModeratorOperations;
import ed.uopp.uoppcore.data.http.ModeratorSignInDTO;
import ed.uopp.uoppcore.data.http.OpportunityDTO;
import ed.uopp.uoppcore.data.http.OpportunityStatusDTO;
import ed.uopp.uoppcore.entity.Opportunity;
import ed.uopp.uoppcore.entity.OpportunityStatus;
import ed.uopp.uoppcore.mapper.OpportunityMapper;
import ed.uopp.uoppcore.security.data.User;
import ed.uopp.uoppcore.security.jwt.util.JwtTokenProvider;
import ed.uopp.uoppcore.security.service.UserService;
import ed.uopp.uoppcore.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/moderator/")
public class ModeratorController implements ModeratorOperations {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final OpportunityService opportunityService;
    private final OpportunityMapper opportunityMapper;

    @PostMapping("/approve")
    @Override
    public ResponseEntity<OpportunityStatus> approveOpportunity(@RequestBody OpportunityStatusDTO opportunityStatusDTO) {
        return ResponseEntity.ok(opportunityService.updateStatus(
                opportunityStatusDTO.uuid(),
                opportunityStatusDTO.opportunityStatus())
        );
    }

    @GetMapping("/opportunities")
    @Override
    public ResponseEntity<List<OpportunityDTO>> getOpportunities(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Opportunity> opportunities = opportunityService
                .findOpportunities(null, null, null, null, pageable);

        return ResponseEntity.ok(opportunities.stream()
                .map(opportunityMapper::toOpportunityData)
                .filter(opportunityDTO -> opportunityDTO.opportunityStatus().equals(OpportunityStatus.PROCESSED))
                .toList());
    }

    @PostMapping("/sign-in")
    @Override
    public ResponseEntity<String> login(@RequestBody ModeratorSignInDTO moderatorSignInDto) {
        authenticate(moderatorSignInDto.email(), moderatorSignInDto.password());
        User loginUser = (User) userService.loadUserByUsername(moderatorSignInDto.email());
        final String token = jwtTokenProvider.generateJwtToken(loginUser);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
