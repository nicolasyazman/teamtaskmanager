package io.github.nicolasyazman.teamtaskmanager.dto;

public class ApiResponse {
    private String message;
    private String token;
    
    public ApiResponse(String message, String token) {
        this.message = message;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
    
}