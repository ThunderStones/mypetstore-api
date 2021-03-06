package org.csu.mypetstore.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AccountVO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String country;
    private String phone;
    private String favouriteCategoryId;
    private String languagePreference;
    private boolean listOption;
    private boolean bannerOption;
    private String bannerName;
}
