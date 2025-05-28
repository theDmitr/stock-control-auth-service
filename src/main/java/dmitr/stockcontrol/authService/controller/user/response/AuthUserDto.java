package dmitr.stockcontrol.authService.controller.user.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AuthUserDto {

    private UUID id;
    private List<String> rights;
}
