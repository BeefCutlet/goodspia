package shop.goodspia.goods.entity;

public enum AccountBank {
    KB("KB"),
    SHINHAN("SHINHAN"),
    NH("NH");

    private final String bankName;

    AccountBank(String bankName) {
        this.bankName = bankName;
    }

    public static AccountBank covertToBank(String bank) {
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
}
