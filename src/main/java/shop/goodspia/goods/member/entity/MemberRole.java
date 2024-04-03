package shop.goodspia.goods.member.entity;

public enum MemberRole {
    USER("ROLE_USER"),
    ARTIST("ROLE_ARTIST"),
    ADMIN("ROLE_ADMIN")
    ;

    private String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
