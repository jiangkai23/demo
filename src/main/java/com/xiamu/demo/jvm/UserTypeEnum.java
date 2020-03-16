package com.xiamu.demo.jvm;

/**
 * @author XiaMu
 */
public enum UserTypeEnum {
    /**
     *
     */
    USER("USER", new User()),
    TESTUSER("TEST_USER", new TestUser());

    private String type;
    private FatherUser user;

    UserTypeEnum(String type, FatherUser user) {
        this.type = type;
        this.user = user;
    }

    public FatherUser getUser() {
        return this.user;
    }
}
