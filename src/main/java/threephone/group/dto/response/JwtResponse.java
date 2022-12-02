package threephone.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String name;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String token,String avatar, String name, Collection<? extends GrantedAuthority> roles) {
        this.token = token;
        this.avatar = avatar;
        this.name = name;
        this.roles = roles;
    }
}
