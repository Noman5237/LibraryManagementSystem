package com.lms.authservice.service.external;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient (name = "user-management-service")
public interface UserManagementService {


}
