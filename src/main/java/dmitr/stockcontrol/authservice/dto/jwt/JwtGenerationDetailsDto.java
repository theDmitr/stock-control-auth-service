package dmitr.stockcontrol.authservice.dto.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
public class JwtGenerationDetailsDto {

    private String subject;

    @Singular(value = "claim")
    private Map<String, Object> claims;

    private long lifetime;
}
