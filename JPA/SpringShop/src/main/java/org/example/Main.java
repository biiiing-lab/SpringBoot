package org.example;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        // 엔티티매니저팩토리 생성
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        // 엔티티매니저 생성
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 트랜잭션 획득
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            testSave(entityManager);
            queryJoin(entityManager);
            entityTransaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();
//
//        Member member1 = new Member("member1", "회원1");
//        Member member2 = new Member("member2", "회원2");
//
//        Team team1 = new Team("team1", "팀1");
//
//        member1.setTeam(team1);
//        member2.setTeam(team1);
//
//        // 객체 그래프 탐색
//        Team findTeam = member1.getTeam();
    }

    public static void testSave(EntityManager em) {

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);

    }

    public static void testSaveNonOwner(EntityManager em) {

        Member member1 = new Member("member1","USER1");
        em.persist(member1);
        Member member2 = new Member("member2","USER2");
        em.persist(member2);

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        team1.getMembers().add(member1);
        team1.getMembers().add(member2);
    }

    public static void queryJoin(EntityManager em) {
        String jpql = "select m from Member m join m.team t where t.name = :teamName";

        List<Member> members = em.createQuery(jpql, Member.class).setParameter("teamName", "팀1").getResultList();
        for(Member member : members) {
            System.out.println("[query] member.username="+ member.getUsername());
        }
    }

    public static void updateRelation(EntityManager em) {
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);

    }

    public static void deleteRelation(EntityManager em) {
        Member member2 = em.find(Member.class, "member2");
        member2.setTeam(null); // 연관관계 제거
    }

    public static void originalJava() {
        Member member1 = new Member("member1","USER1");
        Member member2 = new Member("member2","USER2");
        Team team1 = new Team("team1", "팀1");

        member1.setTeam(team1);
        member2.setTeam(team1);

        List<Member> members = team1.getMembers();
        System.out.println(members.size());
    }

    public static void biDirection(EntityManager em) {

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);

        em.flush();
        em.clear();

        Team findTeam = em.find(Team.class, team1.getId());
        List<Member> members = findTeam.getMembers();

        for(Member member : members) {
            System.out.println("member.username = "+ member.getUsername());
        }

    }

}