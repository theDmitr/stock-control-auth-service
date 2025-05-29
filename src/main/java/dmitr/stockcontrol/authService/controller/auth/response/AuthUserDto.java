package dmitr.stockcontrol.authService.controller.auth.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AuthUserDto {

    private UUID id;
    private String username;
    private List<String> rights;
}
