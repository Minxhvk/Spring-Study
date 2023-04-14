package hello.core.dicount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPrecent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrad() == Grade.VIP) {
            return price * discountPrecent / 100 ;
        } else {
            return 0;
        }
    }
}
