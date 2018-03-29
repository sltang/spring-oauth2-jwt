package oauth.authorization.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class OAuthUser implements UserDetails {
	
	private static final long serialVersionUID = -4134945175896159961L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String username;
    private String password;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="oauth_user_roles")
    private List<String> roles;

    @Column(name="ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired;
    @Column(name="ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked;
    @Column(name="CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired;
    private boolean enabled;
    
    public OAuthUser() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }
    
    public void grantAuthority(String authority) {
        if (roles == null) roles = new ArrayList<>();
        roles.add(authority);
    }

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
        this.password = password;
    }

	public String getUsername() {
		return this.username;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	
	public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
	
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
