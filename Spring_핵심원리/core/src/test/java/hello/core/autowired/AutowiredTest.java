package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {

        @Autowired(required = false)
        /**
         * Member는 스프링 빈에 등록되어 있지 않음
         * required = false 로 호출 X
         * 만약 required = true 이면 에러 발생
         */
        public void setNoBean1(Member noBean1) {
            System.out.println("member1 = " + noBean1);
        }

        // Nullable
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("member2 = " + noBean2);
        }

        // Optional : Java8 문법, Null값 가능
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("member3 = " + noBean3);
        }

    }


}
