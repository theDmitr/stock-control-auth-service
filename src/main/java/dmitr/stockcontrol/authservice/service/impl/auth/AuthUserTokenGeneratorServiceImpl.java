package dmitr.stockcontrol.authservice.service.impl.auth;

import dmitr.stockcontrol.authservice.dto.auth.AuthUserDto;
import dmitr.stockcontrol.authservice.dto.jwt.JwtGenerationDetailsDto;
import dmitr.stockcontrol.authservice.service.face.auth.AuthUserTokenGeneratorService;
import dmitr.stockcontrol.authservice.service.face.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthUserTokenGeneratorServiceImpl implements AuthUserTokenGeneratorService {

    private final JwtService jwtService;

    private static final long FIVE_MINUTES_IN_MILLIS = TimeUnit.MINUTES.toMillis(5);
    private static final long ONE_MONTH_IN_MILLIS = TimeUnit.DAYS.toMillis(30);

    @Override
    public String generateAccessToken(AuthUserDto authUser) {
        List<String> rightLabels = authUser.getRights()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtGenerationDetailsDto jwtGenerationDetails = JwtGenerationDetailsDto.builder()
                .subject(authUser.getId().toString())
                .claim("rights", rightLabels)
                .lifetime(FIVE_MINUTES_IN_MILLIS)
                .build();

        return jwtService.buildToken(jwtGenerationDetails);
    }

    @Override
    public String generateRefreshToken(AuthUserDto authUser) {
        JwtGenerationDetailsDto jwtGenerationDetails = JwtGenerationDetailsDto.builder()
                .subject(authUser.getId().toString())
                .lifetime(ONE_MONTH_IN_MILLIS)
                .build();
        return jwtService.buildToken(jwtGenerationDetails);
    }
}
