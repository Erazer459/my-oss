package io.github.franzli347.foss.support.userSupport;

import io.github.franzli347.foss.entity.AbstractUser;

public class MockLoginUserProvider implements LoginUserProvider{
    @Override
    public AbstractUser getLoginUser() {
        AbstractUser abstractUser = new AbstractUser() {};
        abstractUser.setId(1);
        abstractUser.setUsername("123");
        abstractUser.setPassword("123");
        return abstractUser;
    }
}
