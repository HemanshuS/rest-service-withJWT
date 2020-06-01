package com.demo.entities;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9165255826604701055L;
	private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
