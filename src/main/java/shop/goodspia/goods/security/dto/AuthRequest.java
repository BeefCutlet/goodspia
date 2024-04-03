package shop.goodspia.goods.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class AuthRequest {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
