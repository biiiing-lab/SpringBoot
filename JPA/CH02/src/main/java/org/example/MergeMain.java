package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MergeMain {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");

    public static void main(String[] args) {
        Member member = createMember("member2", "member2 name", 20); // 최종적으로 준영속 상태
        System.out.println(member.getUsername());

        // 준영속 상태에서 변경
        member.setUsername("회원명변경");

        mergeMember(member);
    }

    static void mergeMember(Member member) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        // 준영속상태
        Member mergeMember = entityManager.merge(member);

        entityTransaction.commit();

        // 준영속 상태
        System.out.println("member = "+ member.getUsername());
        // 영속 상태
        System.out.println("mergeMember = "+ member.getUsername());

        // 파라미터로 넘어온 엔티티는 병합 후에도 준영속 상태로 남아있다.
        System.out.println("em2 contains member = "+ entityManager.contains(member));
        System.out.println("em2 contains mergeMember = "+ entityManager.contains(mergeMember));

        entityManager.close();

    }

    static Member createMember(String id, String username, Integer age) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setAge(age);

        entityManager.persist(member);

        entityTransaction.commit();
        entityManager.close(); // 준영속상태

        return member;
    }
}
