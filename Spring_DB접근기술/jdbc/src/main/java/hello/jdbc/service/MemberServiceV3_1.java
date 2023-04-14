package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

//    private final DataSource dataSource;
    // OCP를 위해 DI 할 것임
    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String formId, String toId, int money) throws SQLException {
        //트랜잭션 시작
        // setAutoCommit 은 어디서 false ??
        // setAutoCommit이 필요하지 않을 경우도 false로 하나 ?
        // 이해한 것 : Service에서 트랜잭션 동기화 매니저에 올릴 때는 setAutoCommit false
        // 트랜잭션 동기화 매니저에 없는 트랜잭션일 경우에는 setAutoCommit true로 커넥션 가져옴,
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            bizLogic(formId, toId, money);
            transactionManager.commit(status);// 성공시 커밋
        } catch (Exception e) {
            transactionManager.rollback(status);// 실패시 롤백
            throw new IllegalStateException(e);
        } // commit or rollback 시 자동으로 release 됨
    }

    private void bizLogic(String formId, String toId, int money) throws SQLException {
        // 비즈니스 로직
        Member fromMember = memberRepository.findById(formId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(formId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
