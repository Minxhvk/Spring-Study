package hello.core.dicount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000; // 할인 금액

    @Override
    public int discount(Member member, int price) {
        if (member.getGrad() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
