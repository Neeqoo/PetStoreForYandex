package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JsonProperty("id")
    private Long id = 0L;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("userStatus")
    private Integer userStatus = 0;

    /* id и userStatus зафиксировал на 0, т.к., при id=0, система самостоятельно назначает id, а userStatus, как я понял, это права пользователя, поэтому так же в рандомайзер добавлять не стал */

}