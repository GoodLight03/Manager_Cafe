package com.manager.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesageDto {
    private String to;
    private String toName;
    private String subject;
    private String content;
}
