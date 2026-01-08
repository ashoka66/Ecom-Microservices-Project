package dto;

import lombok.Data;

@Data
public class UserRequestDto {
	
	public String name;
	
	public String email;
	
	public String password;
	
	public String role;

}
