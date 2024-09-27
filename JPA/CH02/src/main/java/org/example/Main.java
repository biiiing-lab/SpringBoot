package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // persistence name -> 설정 파일을 가지고 오는 역할
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // 기본 세팅
        try {
            entityTransaction.begin();
//            logic(entityManager);
//            Member member1 = new Member();
//            member1.setId("testId2");
//            member1.setUsername("test");
//            member1.setAge(5); // 비영속 상태
//
//            entityManager.persist(member1); // 영속 상태
//            entityManager.detach(member1); // 준영속 상태

//             1차 캐시에서 가지고 옴 이름 -> 쿼리
//             1차캐시에서 안 가지고 왔을 때는 쿼리 -> 이름
//            Member findmember = entityManager.find(Member.class, "testId2");
//            System.out.println(findmember.getUsername());
//
//            findmember.setUsername("test2 이름 변경");

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManagerFactory.close();
        }

    }

    public static void logic(EntityManager em) {
        String id = "id2";
        Member member = new Member();
        member.setId(id);
        member.setUsername("Test");
        member.setAge(2);

        em.persist(member);

        member.setAge(20);

        Member findMember = em.find(Member.class, id);
        System.out.println("find member : " + findMember.getUsername() + " / age : " + findMember.getAge());

        List<Member> memberList = em.createQuery("select m from Member  m", Member.class).getResultList();
        System.out.println("memberList.size = " + memberList.size());

//        em.remove(member);
    }
}