package dev.shreya.productservice.client.dtos;

import dev.shreya.productservice.models.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends BaseModel {
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



//    private List<User> users
}