package com.shekhar.shopeasy.service;

import com.shekhar.shopeasy.dto.request.RegisterRequest;
import com.shekhar.shopeasy.dto.response.ApiResponse;

public interface AuthService {

    ApiResponse register(RegisterRequest request);
}