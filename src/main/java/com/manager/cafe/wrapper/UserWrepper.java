package com.manager.cafe.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWrepper {
    private Integer id;

    private String name;

    private String contactNumber;

    private String email;

    private String password;

    private String status;

    //private String role;
}
