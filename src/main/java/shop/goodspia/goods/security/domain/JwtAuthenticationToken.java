package shop.goodspia.goods.security.domain;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import shop.goodspia.goods.security.dto.MemberPrincipal;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String accessToken;
    private MemberPrincipal principal;
    private Object credentials;

    public JwtAuthenticationToken(final String jwtToken) {
        super(null);
        this.accessToken = jwtToken;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(final MemberPrincipal principal,
                                  final Object credentials,
                                  final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }
}
