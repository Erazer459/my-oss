package io.github.franzli347.foss.support.userSupport;

import io.github.franzli347.foss.entity.UserBase;

public class MockLoginUserProvider implements LoginUserProvider{
    @Override
    public UserBase getLoginUser() {
        UserBase userBase = new UserBase() {};
        userBase.setId(1);
        userBase.setUsername("123");
        userBase.setPassword("123");
        return userBase;
    }
}
