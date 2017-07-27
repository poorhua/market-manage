package cn.lmjia.market.core.entity.support;

/**
 *提现状态
 */
public enum WithdrawStatus {
    EMPTY("E"),
    /**
     * 待审核
     */
    checkPending("待审核"),
    /**
     * 提现成功
     */
    success("成功");

    private final String message;

    WithdrawStatus(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
