package shop.goodspia.goods.global.common.dto;

public enum AccountBank {
    KB("KB", "04"),
    SHINHAN("SHINHAN", "88"),
    NH("NH", "11");

    private final String bankName;
    private final String code;

    AccountBank(String bankName, String code) {
        this.bankName = bankName;
        this.code = code;
    }

    public static AccountBank convertStringToBank(String bank) {
        if (bank == null) {
            throw new IllegalArgumentException("은행 이름이 입력되지 않았습니다.");
        }

        for (AccountBank accountBank : AccountBank.values()) {
            if (accountBank.bankName.equals(bank)) {
                return accountBank;
            }
        }

        throw new IllegalArgumentException("일치하는 은행이 없습니다.");
    }

    public static AccountBank convertCodeToBank(String code) {
        if (code == null) {
            throw new IllegalArgumentException("은행 코드가 입력되지 않았습니다.");
        }

        for (AccountBank accountBank : AccountBank.values()) {
            if (accountBank.code.equals(code)) {
                return accountBank;
            }
        }

        throw new IllegalArgumentException("일치하는 은행이 없습니다.");
    }
}
